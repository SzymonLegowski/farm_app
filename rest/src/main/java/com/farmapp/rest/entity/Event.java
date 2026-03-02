package com.farmapp.rest.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.farmapp.rest.enums.EventType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private EventType eventType;
    
    private LocalDate date;
    
    private String note;
    
    @CreationTimestamp()
    @Column(updatable = false, nullable = false)
    private LocalDateTime dateCreated;
    
    @UpdateTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime dateModified;

    @ManyToMany(mappedBy = "events", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"events", "litters"})
    private Set<Sow> sows = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "event_litter",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "litter_id"))
    @JsonIgnoreProperties({"sow","events"})
    private Set<Litter> litters = new HashSet<>();

    public void addLitters(Set<Litter> litters){
        this.litters.addAll(litters);
        litters.forEach(litter -> litter.getEvents().add(this));
    }

    public void addSows(Set<Sow> sows){
        this.sows.addAll(sows);
        sows.forEach(sow -> sow.getEvents().add(this));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if( !(obj instanceof Sow)) return false;
        Event other = (Event) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
