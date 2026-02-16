package com.farmapp.rest.service;

import java.util.List;

import com.farmapp.rest.entity.Event;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface EventService {
  
    Single<Event> setEvent(Event event);
    Single<List<Event>> getAllEvents();
    Single<List<Event>> getEventsById(List<Long> eventIds);
    Completable deleteEventsById(List<Long> eventIds);
   
}
