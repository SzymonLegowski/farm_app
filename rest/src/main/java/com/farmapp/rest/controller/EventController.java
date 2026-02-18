package com.farmapp.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmapp.rest.entity.Event;
import com.farmapp.rest.service.EventService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @PostMapping()
    public Event createEvent(@RequestBody Event event) {
        return eventService.setEvent(event);
    }

    @GetMapping()
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Optional<Event> getEventById(@PathVariable Long id) {      
        return eventService.getEventById(id);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event event) {
        return eventService.setEvent(event);
    }

    @DeleteMapping()
    public void deleteEvent(@PathVariable Long id){
        eventService.deleteEvent(id);
    }
}
