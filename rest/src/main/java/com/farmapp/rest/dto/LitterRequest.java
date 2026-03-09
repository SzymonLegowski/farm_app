package com.farmapp.rest.dto;

import java.time.LocalDate;

public record LitterRequest(
    Long id,
    LocalDate farrowing,
    LocalDate weaning,
    Integer bornAlive,
    Integer bornDeceased,
    Integer deceased,
    Integer weaned,
    String note,
    boolean updateSowStatus,
    Long sowId
) {}
