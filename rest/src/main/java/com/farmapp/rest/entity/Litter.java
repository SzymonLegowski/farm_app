package com.farmapp.rest.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Litter {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private LocalDate farrowing;
    
    @Column(nullable = true)
    private LocalDate weaning;

    private Integer bornAlive;
    
    private Integer bornDeceased;
    
    private Integer deceased;
    
    private Integer weaned;
    
    private String note;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime dateModified;

    @ManyToMany(mappedBy = "litters", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"litters", "sows"})
    private Set<Insemination> inseminations = new HashSet<>(); 

    @ManyToOne
    @JoinColumn(name = "sowId", nullable = false)
    @JsonIgnoreProperties({"inseminations", "litters"})
    private Sow sow;

    public static Litter defaultLitter(Sow sow){
        Litter litter = new Litter();
        litter.setBornAlive(0);
        litter.setBornDeceased(0);
        litter.setDeceased(0);
        litter.setWeaned(0);
        litter.setNote("");
        litter.setSow(sow);
        return litter;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if( !(obj instanceof Litter)) return false;
        Litter other = (Litter) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
