package com.farmapp.rest.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
public class Insemination {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate date;
    
    private String breed;

    private String note;
    
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime dateCreated;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime dateModified;

    @ManyToMany(mappedBy = "inseminations", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"inseminations", "litters"})
    private Set<Sow> sows = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
        name = "insemination_litter",
        joinColumns = @JoinColumn(name = "insemination_id"),
        inverseJoinColumns = @JoinColumn(name = "litter_id"))
    @JsonIgnoreProperties({"sow","inseminations"})
    private Set<Litter> litters = new HashSet<>();

    public void addLitters(Set<Litter> litters){
        this.litters.addAll(litters);
        litters.forEach(litter -> litter.getInseminations().add(this));
    }

    public void addSows(Set<Sow> sows){
        this.sows.addAll(sows);
        sows.forEach(sow -> sow.getInseminations().add(this));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if( !(obj instanceof Sow)) return false;
        Insemination other = (Insemination) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
