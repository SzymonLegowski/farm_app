package com.farmapp.rest.dto;

import java.time.LocalDate;
import java.util.Set;


public record InseminationDto(
    Long id,
    LocalDate date,
    String breed,
    String note,
    Set<Long> litterIds,
    Set<Long> sowIds
) 
{}
