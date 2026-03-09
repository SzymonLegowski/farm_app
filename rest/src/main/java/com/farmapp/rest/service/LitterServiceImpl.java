package com.farmapp.rest.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farmapp.rest.dto.LitterRequest;
import com.farmapp.rest.dto.LitterDto;
import com.farmapp.rest.entity.Insemination;
import com.farmapp.rest.entity.Litter;
import com.farmapp.rest.enums.SowStatus;
import com.farmapp.rest.exceptions.NotFoundException;
import com.farmapp.rest.repository.LitterRepository;
import com.farmapp.rest.repository.SowRepository;

@Service
public class LitterServiceImpl implements LitterService{

    private final LitterRepository litterRepository;
    private final SowRepository sowRepository;

    public LitterServiceImpl(LitterRepository litterRepository, SowRepository sowRepository){
        this.litterRepository = litterRepository;
        this.sowRepository = sowRepository;
    }

    @Override
    public Litter createLitter(Litter litter) {
        return litterRepository.save(litter);
    }

    @Override
    public LitterDto updateLitter(Long id, LitterRequest litterDto) {
        Litter litter = litterRepository.findById(id).orElseThrow(() -> new NotFoundException("Litter not found"));
        
        if(litterDto.bornAlive() != null)
            litter.setBornAlive(litterDto.bornAlive());
        if(litterDto.bornDeceased() != null)
            litter.setBornDeceased(litterDto.bornDeceased());
        if(litterDto.deceased() != null)
            litter.setDeceased(litterDto.deceased());
        if(litterDto.weaned() != null)
            litter.setWeaned(litterDto.weaned());
        if(litterDto.farrowing() != null){
            if(litterDto.updateSowStatus())
                litter.getSow().setStatus(SowStatus.FARROWED);
            litter.setFarrowing(litterDto.farrowing());
        }
        if(litterDto.weaning() != null){
            if(litterDto.updateSowStatus())
                litter.getSow().setStatus(SowStatus.FREE);
            litter.setWeaning(litterDto.weaning());
        }
        
        if(litterDto.updateSowStatus())
            sowRepository.save(litter.getSow());
        
        Litter updatedLitter = litterRepository.save(litter);
        return mapToLitterDto(updatedLitter);
    }

    @Override
    public List<Litter> getAllLitters() {
        return litterRepository.findAll();
    }

    @Override
    public List<LitterDto> getLittersBySowId(Long sowId){
        List<Litter> litters = litterRepository.findBySowId(sowId);
        return litters.stream().map(this::mapToLitterDto).toList();
    }

    public List<LitterDto> getLittersByMonth(int year, int month){
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        List<Litter> litters = litterRepository.findByFarrowingBetween(start, end);
        litters.addAll(litterRepository.findByWeaningBetween(start, end));
        return litters.stream().map(this::mapToLitterDto).toList();
    }

    @Override
    public Optional<Litter> getLitterById(Long id) {
        return litterRepository.findById(id);
    }

    @Override
    public Set<Litter> getLittersById(Set<Long> ids) {
        List<Litter> litters = litterRepository.findAllById(ids);
        return new LinkedHashSet<>(litters);
    }

    @Override
    public void deleteLitter(Long id) {
        litterRepository.deleteById(id);
    }

    private LitterDto mapToLitterDto(Litter litter){
        List<LocalDate> inseminations = litter.getInseminations().stream()
            .map(Insemination::getDate)
            .sorted(Comparator.naturalOrder())
            .collect(Collectors.toList());
        
        LocalDate predictedFarrowing = Collections.max(inseminations).plusDays(114);
        LitterDto dto = new LitterDto(
            litter.getId(),
            inseminations,
            predictedFarrowing,
            litter.getFarrowing(),
            litter.getWeaning(),
            litter.getBornAlive(),
            litter.getBornAlive(),
            litter.getDeceased(),
            litter.getWeaned(),
            litter.getNote(),
            litter.getSow().getId(),
            litter.getSow().getNumber()
        );
        return dto;
    }

}
