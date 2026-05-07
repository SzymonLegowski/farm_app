package com.farmapp.rest.entity;

import java.time.LocalDate;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonRawValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Immutable
@Data
@Table(name = "cal_events_json")
public class CalendarView {
    @Id
    private Integer id;
    private LocalDate date;
    @JsonRawValue
    private String events;
}
