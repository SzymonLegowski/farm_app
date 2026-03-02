package com.farmapp.rest.dto;

public record EventRequest(
    EventDto eventDto,
    Boolean updateSowStatus,
    Integer sowGroup
) {}
