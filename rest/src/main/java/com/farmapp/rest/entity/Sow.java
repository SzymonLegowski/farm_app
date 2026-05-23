package com.farmapp.rest.entity;

import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.farmapp.rest.dto.SowDto;
import com.farmapp.rest.enums.SowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Sow {
    
    @Id
    @SequenceGenerator(name="sow_id_seq", sequenceName = "sow_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sow_id_seq")
    private Integer id;
    private Integer number;
    private SowStatus status;
    @Column(name = "group_nr")
    private Integer groupNr;
    @Nullable
    private LocalDate disposed;
    private String note;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime created;
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modified;

    @OneToMany(
    mappedBy = "sow",
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties({"sow", "events"})
    private Set<Litter> litters = new HashSet<>();
    public Sow(Integer number, SowStatus status, String note){
        this.number = number;
        this.status = status;
        this.note = note;
    }

    @JsonIgnore
    @Transient
    public Optional<Litter> resolveLatestLitter(){
        Optional<Litter> litter = litters.stream()
            .max(Comparator.comparing(Litter::getId));
        return litter;
    }

    @JsonIgnore
    public void updateStatus(Integer status){
        switch (status) {
            case 0:
                this.status = SowStatus.FREE;        
                break;
            case 1:
                this.status = SowStatus.INSEMINATED;
                break;
            case 3:
                this.status = SowStatus.FARROWED;
                break;
            default:
                break;
        }
    }

    @JsonIgnore
    public SowDto toDto(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String disposalDate = "";
        if(this.disposed != null)
            disposalDate = disposed.format(formatter).toString();
        Set<Integer> litterIds = new HashSet<>();
        if(litters != null)
            litterIds = litters.stream().map(Litter::getId).collect(Collectors.toSet());
        SowDto sowDto = new SowDto(
            id,
            number,
            status,
            groupNr, 
            disposalDate,
            note,
            litterIds
        );
        return sowDto;
    }

    @JsonIgnore
    public void fromDto(SowDto dto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        number = dto.number();
        if(dto.disposalDate() != null)
            disposed = LocalDate.parse(dto.disposalDate(), formatter);
        status = dto.status();
        if(dto.note() != null)
            note = dto.note();
        else
            note = "";
        groupNr = dto.group();
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
