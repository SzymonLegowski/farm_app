package com.farmapp.rest.dto;

import java.time.LocalDate;
import java.util.Set;

import com.farmapp.rest.enums.SowStatus;

public record SowDto(
    Long id,
    Integer number,
    SowStatus sowStatus,
    Integer group,
    LocalDate disposalDate,
    String note,
    Set<Long> eventIds,
    Set<Long> litterIds
) {}

