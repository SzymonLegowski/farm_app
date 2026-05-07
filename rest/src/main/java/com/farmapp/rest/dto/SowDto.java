package com.farmapp.rest.dto;

import java.util.Set;

import com.farmapp.rest.enums.SowStatus;

public record SowDto(
    Integer id,
    Integer number,
    SowStatus status,
    Integer group,
    String disposalDate,
    String note,
    Set<Integer> litterIds
) {}

