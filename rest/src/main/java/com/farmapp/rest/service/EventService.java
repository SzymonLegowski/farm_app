package com.farmapp.rest.service;

import java.util.List;

import com.farmapp.rest.dto.EventDto;
import com.farmapp.rest.dto.EventRequest;

public interface EventService {
  
    EventDto createEvent(EventRequest eventRequest);
    EventDto updateEvent(EventRequest eventRequest, Long id);
    List<EventDto> getAllEvents();
    EventDto getEventById(Long id);
    List<EventDto> getEventsById(List<Long> ids);
    void deleteEvent(Long id);
   
}
