package com.farmapp.rest.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farmapp.rest.dto.EventDto;
import com.farmapp.rest.dto.EventRequest;
import com.farmapp.rest.entity.Event;
import com.farmapp.rest.entity.Litter;
import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.enums.EventType;
import com.farmapp.rest.exceptions.NoAssignedEntitiesException;
import com.farmapp.rest.exceptions.NoFieldsException;
import com.farmapp.rest.exceptions.NotFoundException;
import com.farmapp.rest.repository.EventRepository;

import jakarta.transaction.Transactional;

@Service
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final SowService sowService;
    private final LitterService litterService;

    public EventServiceImpl(EventRepository eventRepository, LitterService litterService, SowService sowService){
        this.eventRepository = eventRepository;
        this.sowService = sowService;
        this.litterService = litterService;
    }

    @Override
    @Transactional
    public EventDto createEvent(EventRequest eventRequest) {
        EventDto eventDto = eventRequest.eventDto();
        Set<Long> sowIds = eventDto.sowIds() != null ? eventDto.sowIds() : new HashSet<>();
        Set<Long> litterIds = eventDto.litterIds() != null ? eventDto.litterIds() : new HashSet<>();
        StringBuilder missingFields = new StringBuilder();
        
        if(sowIds.isEmpty() && litterIds.isEmpty()) 
            throw new NoAssignedEntitiesException("Event must have at least 1 sow or litter assigned!");        

        if(eventDto.date() == null) missingFields.append("date, ");
        if(eventDto.eventType() == null) missingFields.append("event type, ");
        if(!missingFields.isEmpty()) {
            missingFields.setLength(missingFields.length() - 2);
            throw new NoFieldsException("Missing fields: " + missingFields);
        }

        Event event = mapToEntity(eventDto);
        Set<Litter> litters = litterService.getLittersById(litterIds);        
        Set<Sow> sows = sowService.getSowEntitiesById(sowIds);
        
        if(eventRequest.updateSowStatus() && event.getEventType() != EventType.VACCINATION)
            sows.forEach(sow -> sow.setStatus(Sow.getStatusByEventType(event.getEventType())));

        if(eventRequest.sowGroup() != 0)
            sows.forEach(sow -> sow.setGroup(eventRequest.sowGroup()));

        if(event.getEventType() == EventType.INSEMINATION)
            sows.forEach(sow -> {
                if(litters.stream().noneMatch(l -> l.getSow().getId().equals(sow.getId()))){
                    Litter litter = sow.getLatestOrDefaultLitter();
                    if(litter.isFarrowed())
                        litter = Litter.defaultLitter(sow);
                    litters.add(litter);
            }});  

        event.addLitters(litters);
        event.addSows(sows);
        
        return mapToDto(eventRepository.save(event));
    }

    @Override
    public EventDto updateEvent(EventRequest eventRequest, Long id) {
        EventDto eventDto = eventRequest.eventDto();
        Event oldEvent = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event not found"));
        Set<Long> sowIds = eventDto.sowIds() != null ? eventDto.sowIds() : new HashSet<>();
        Set<Long> litterIds = eventDto.litterIds() != null ? eventDto.litterIds() : new HashSet<>();
        
        if(sowIds.isEmpty() && litterIds.isEmpty())
            throw new NoAssignedEntitiesException("Event must have at least 1 sow or litter assigned!");

        oldEvent.getSows().removeIf(sow -> !sowIds.contains(sow.getId()));
        oldEvent.getLitters().removeIf(litter -> !litterIds.contains(litter.getId()) || !sowIds.contains(litter.getSow().getId()));

        Set<Long> existingSowIds = oldEvent.getSows()
                .stream()
                .map(Sow::getId)
                .collect(Collectors.toSet());

        Set<Long> existingLitterIds = oldEvent.getLitters()
                .stream()
                .map(Litter::getId)
                .collect(Collectors.toSet());

        sowIds.removeIf(existingSowIds::contains);
        litterIds.removeIf(existingLitterIds::contains);

        Set<Sow> sows = sowService.getSowEntitiesById(sowIds);
        Set<Litter> litters = litterService.getLittersById(litterIds);

        if(eventDto.date() != null)
            oldEvent.setDate(eventDto.date());
        if(eventDto.eventType() != null)
            oldEvent.setEventType(eventDto.eventType());
        if(eventDto.note() != null)
            oldEvent.setNote(eventDto.note());

        oldEvent.addSows(sows);
        oldEvent.addLitters(litters);
        
        if(eventRequest.updateSowStatus() && oldEvent.getEventType() != EventType.VACCINATION)
            sows.forEach(sow -> sow.setStatus(Sow.getStatusByEventType(oldEvent.getEventType())));

        if(oldEvent.getEventType() == EventType.INSEMINATION){
            sows.forEach(sow -> {
                if(litters.stream().noneMatch(l -> l.getSow().getId().equals(sow.getId()))){
                    Litter litter = sow.getLatestOrDefaultLitter();
                    if(litter.isFarrowed())
                        litter = Litter.defaultLitter(sow);
                    litters.add(litter);
            }});
        }        
        return null;
    }

    @Override
    public List<EventDto> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(this::mapToDto).toList();
    }

    @Override
    public EventDto getEventById(Long id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event not found"));
        return mapToDto(event);
    }

    @Override
    public List<EventDto> getEventsById(List<Long> ids) {
        List<Event> events = eventRepository.findAllById(ids);
        return events.stream().map(this::mapToDto).toList();
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);      
    }

    private Event mapToEntity(EventDto eventDto){
        Event event = new Event();
        event.setDate(eventDto.date());
        event.setEventType(eventDto.eventType());
        if(eventDto.note() != null)
            event.setNote(eventDto.note());
        else
            event.setNote("");
        return event;
    }

    private EventDto mapToDto(Event event){
        Set<Long> sowIds = new HashSet<>();
        Set<Long> litterIds = new HashSet<>();
        if(event.getSows() != null){
            sowIds = event.getSows().stream().map(Sow::getId).collect(Collectors.toSet());
            if(event.getLitters() != null)
                litterIds = event.getLitters().stream().map(Litter::getId).collect(Collectors.toSet());
        }
        EventDto eventDto = new EventDto(
            event.getId(),
            event.getEventType(),
            event.getDate(),
            event.getNote(),
            litterIds,
            sowIds
        );
        return eventDto;
    }

}
