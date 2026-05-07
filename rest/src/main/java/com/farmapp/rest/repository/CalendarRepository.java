package com.farmapp.rest.repository;

import java.time.LocalDate;
import java.util.List;

import com.farmapp.rest.entity.CalendarView;

public interface CalendarRepository extends ViewRepository<CalendarView, Integer> {
    List<CalendarView> findByDateBetween(LocalDate start, LocalDate end);
}
