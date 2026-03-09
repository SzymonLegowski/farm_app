package com.farmapp.rest.dto;

public record InseminationRequest(
    InseminationDto inseminationDto,
    Boolean updateSowStatus,
    Integer sowGroup
) {}
