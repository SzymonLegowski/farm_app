package com.farmapp.rest.dto;

import java.time.LocalDate;
import java.util.List;

public record EventRequest(
    LocalDate date,
    Integer type,
    Integer sowGroup,
    boolean updateSowStatus,
    List<String> sowIdNoteList
) {
}
