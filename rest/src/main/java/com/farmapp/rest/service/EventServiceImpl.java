package com.farmapp.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.farmapp.rest.entity.Event;
import com.farmapp.rest.repository.EventRepository;

@Service
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    @Override
    public Event setEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> getEventsById(List<Long> ids) {
        return eventRepository.findAllById(ids);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);      
    }

}
