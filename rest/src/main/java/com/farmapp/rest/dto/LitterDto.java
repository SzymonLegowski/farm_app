package com.farmapp.rest.dto;

import java.util.List;

public record LitterDto(
    Integer id,
    List<InseminationDto> inseminations,
    String predictedFarrowing,
    String farrowing,
    String weaning,
    Integer bornAlive,
    Integer bornDeceased,
    Integer deceased,
    Integer weaned,
    String note
) {}
