package com.farmapp.rest.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.farmapp.rest.enums.EventType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Event {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private EventType eventType;
    
    private LocalDate date;
    
    private String note;
    
    @CreationTimestamp
    private Instant dateCreated;
    
    @UpdateTimestamp
    private Instant dateModified;

    @ManyToMany
    private List<Sow> sows;
    
    @ManyToMany
    private List<Litter> litters;
}
