package com.farmapp.rest.dto;

public record LitterRequest(
    LitterDto litterDto,
    Boolean updateSowStatus
) {}
