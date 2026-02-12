package com.farmapp.rest.entity;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Litter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer bornAlive;
    
    private Integer bornDeceased;
    
    private Integer deceased;
    
    private Integer weaned;
    
    private String note;

    @CreationTimestamp
    private Instant dateCreated;

    @UpdateTimestamp
    private Instant dateModified;

    @ManyToMany
    private List<Event> events;

    @ManyToOne
    @JoinColumn(name="sowId", nullable = false)
    private Sow sow;
}
