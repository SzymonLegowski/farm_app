package com.farmapp.rest.entity;

import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;

import com.farmapp.rest.enums.SowStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sow {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private Integer number;
    
    private SowStatus status;
    
    @Nullable
    private LocalDate disposalDate;
    
    private String note;

    @CreationTimestamp
    private Instant dateCreated;
    
    @UpdateTimestamp
    private Instant dateModified;
    
    @ManyToMany
    @JsonIgnoreProperties({"litters"})
    private List<Event> events;
    
    @OneToMany(mappedBy="sow")
    @JsonIgnoreProperties({"events"})
    private List<Litter> litters;

    public Sow(Integer number, SowStatus status, String note){
        this.number = number;
        this.status = status;
        this.note = note;
    }
}
