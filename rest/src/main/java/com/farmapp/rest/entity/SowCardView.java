package com.farmapp.rest.entity;

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
@Table(name = "sow_history_json")
public class SowCardView {
    @Id
    @Column(name = "sow_id")
    private Integer id;
    @Column(name = "max_ins_cnt")
    private Integer maxInsCnt;
    @JsonRawValue
    @Column(name = "history_data")
    private String litters;
}
