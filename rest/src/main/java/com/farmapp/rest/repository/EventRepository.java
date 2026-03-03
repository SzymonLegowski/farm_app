package com.farmapp.rest.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farmapp.rest.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByDateBetween(LocalDate start, LocalDate end);
}