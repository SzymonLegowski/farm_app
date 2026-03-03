package com.farmapp.rest.entity;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.beans.Transient;
import java.time.LocalDate;

import com.farmapp.rest.enums.EventType;
import com.farmapp.rest.enums.SowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
    
    private Integer groupNumber;
   
    @Nullable
    private LocalDate disposalDate;
    
    private String note;

    @CreationTimestamp
    private LocalDateTime dateCreated;
    
    @UpdateTimestamp
    private LocalDateTime dateModified;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "sow_event",
        joinColumns = @JoinColumn(name = "sow_id"),
        inverseJoinColumns = @JoinColumn(name = "event_id"))
    @JsonIgnoreProperties({"sows", "litters"})
    private Set<Event> events = new HashSet<>();
    
    @OneToMany(mappedBy = "sow", 
               fetch = FetchType.EAGER, 
               cascade = CascadeType.REMOVE, 
               orphanRemoval = true)
    @JsonIgnoreProperties({"sow", "events"})
    private Set<Litter> litters = new HashSet<>();

    public Sow(Integer number, SowStatus status, String note){
        this.number = number;
        this.status = status;
        this.note = note;
    }

    @JsonIgnore
    @Transient
    public Litter resolveLatestOrDefaultLitter(){
        Litter litter = litters.stream()
            .max(Comparator.comparing(Litter::getId))
            .orElseGet(() -> Litter.defaultLitter(this));
        return litter;
    }

    public static SowStatus getStatusByEventType(EventType eventType){
        switch (eventType) {
            case INSEMINATION:
                return SowStatus.INSEMINATED;
            case FARROWING:
                return SowStatus.FARROWED;
            case WEANING:
                return SowStatus.FREE;
            default:
                return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if( !(obj instanceof Sow)) return false;
        Sow other = (Sow) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
