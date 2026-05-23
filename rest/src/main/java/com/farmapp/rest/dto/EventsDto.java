package com.farmapp.rest.dto;

import java.util.List;

import com.farmapp.rest.entity.CalendarView;

public record EventsDto(
    List<CalendarView> calendarData,
    Integer latestGroup 
) {}
