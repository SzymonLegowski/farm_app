package com.farmapp.rest.dto;

import java.util.Set;

public record LitterDto(
    Long id,
    Integer bornAlive,
    Integer bornDeceased,
    Integer deceased,
    Integer weaned,
    String note,
    Long sowId,
    Set<Long> eventIds
) {}
