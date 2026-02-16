package com.farmapp.rest.service;

import java.util.List;

import com.farmapp.rest.entity.Event;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class EventServiceImpl implements EventService{

    @Override
    public Single<Event> setEvent(Event event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEvent'");
    }

    @Override
    public Single<List<Event>> getAllEvents() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllEvents'");
    }

    @Override
    public Single<List<Event>> getEventsById(List<Long> eventIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEventsById'");
    }

    @Override
    public Completable deleteEventsById(List<Long> eventIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteEventsById'");
    }

}
