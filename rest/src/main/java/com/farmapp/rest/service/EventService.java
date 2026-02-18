package com.farmapp.rest.service;

import java.util.List;
import java.util.Optional;

import com.farmapp.rest.entity.Event;

public interface EventService {
  
    Event setEvent(Event event);
    List<Event> getAllEvents();
    Optional<Event> getEventById(Long id);
    List<Event> getEventsById(List<Long> ids);
    void deleteEvent(Long id);
   
}
