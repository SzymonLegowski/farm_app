package com.farmapp.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farmapp.rest.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}