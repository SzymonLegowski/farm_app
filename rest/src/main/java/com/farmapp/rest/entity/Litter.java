package com.farmapp.rest.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.farmapp.rest.dto.InseminationDto;
import com.farmapp.rest.dto.LitterDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
    @SequenceGenerator(name="litter_id_seq", sequenceName = "litter_id_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "litter_id_seq")
    private Integer id;

    private Integer number;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private List<Insemination> inseminations = new ArrayList<>();

    private LocalDate farrowing;

    private LocalDate weaning;

    @Column(name = "born_alive")
    private Integer bornAlive;

    @Column(name = "born_deceased")
    private Integer bornDeceased;
    
    private Integer deceased;
    
    private Integer weaned;
    
    private String note;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modified;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sow_id", nullable = false)
    @JsonIgnoreProperties({"events", "litters"})
    private Sow sow;

    public LitterDto toDto(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        List<InseminationDto> insDtos = new ArrayList<>();
        Insemination latestInsemination = inseminations.stream().max(Comparator.comparing(Insemination::getDate)).orElse(null);
        String predictedFarrowing = "";
        if(latestInsemination != null){
         
        }
        inseminations.forEach(ins -> {
            String date = ins.getDate().format(formatter).toString();
            InseminationDto insDto = new InseminationDto(date, ins.getNote());
            insDtos.add(insDto);
        });
        String farrowingDate = "";
        String weaningDate = "";
        if(farrowing != null)
            farrowingDate = farrowing.format(formatter).toString();
        if(weaning != null)
            weaningDate = weaning.format(formatter).toString();
        LitterDto dto = new LitterDto(
            id, 
            insDtos, 
            predictedFarrowing,
            farrowingDate,
            weaningDate,
            bornAlive,
            bornDeceased,
            deceased,
            weaned,
            note   
        );
        return dto;
    }

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
