package com.farmapp.rest.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Insemination {
    private LocalDate date;
    private String note;
    
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String breed) {
        this.note = breed;
    }

    
}
