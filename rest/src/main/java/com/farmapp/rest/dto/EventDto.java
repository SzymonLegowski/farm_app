package com.farmapp.rest.dto;

import java.time.LocalDate;
import java.util.Set;

import com.farmapp.rest.enums.EventType;

public record EventDto(
    Long id,
    EventType eventType,
    LocalDate date,
    String note,
    Set<Long> litterIds,
    Set<Long> sowIds
) 
{}
