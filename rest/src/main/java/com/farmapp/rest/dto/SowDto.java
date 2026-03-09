package com.farmapp.rest.dto;

import java.time.LocalDate;
import com.farmapp.rest.enums.SowStatus;

public record SowDto(
    Long id,
    Integer number,
    SowStatus status,
    Integer group,
    LocalDate disposalDate,
    String note
) {}

