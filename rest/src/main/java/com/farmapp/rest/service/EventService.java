package com.farmapp.rest.service;

import java.util.List;

import com.farmapp.rest.dto.EventDto;
import com.farmapp.rest.dto.EventRequest;
import com.farmapp.rest.entity.Event;

public interface EventService {
  
    Event createEvent(EventRequest eventRequest);
    EventDto updateEvent(EventRequest eventRequest, Long id);
    List<EventDto> getAllEvents();
    List<Event> getEventsOfMonth(int year, int month);
    EventDto getEventById(Long id);
    List<EventDto> getEventsById(List<Long> ids);
    void deleteEvent(Long id);
   
}
