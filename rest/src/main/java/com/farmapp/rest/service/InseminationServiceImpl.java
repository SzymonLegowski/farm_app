package com.farmapp.rest.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farmapp.rest.dto.InseminationDto;
import com.farmapp.rest.dto.InseminationRequest;
import com.farmapp.rest.entity.Insemination;
import com.farmapp.rest.entity.Litter;
import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.enums.SowStatus;
import com.farmapp.rest.exceptions.NoAssignedEntitiesException;
import com.farmapp.rest.exceptions.NoFieldsException;
import com.farmapp.rest.exceptions.NotFoundException;
import com.farmapp.rest.repository.InseminationRepository;

import jakarta.transaction.Transactional;

@Service
public class InseminationServiceImpl implements InseminationService{

    private final InseminationRepository inseminationRepository;
    private final SowService sowService;
    private final LitterService litterService;

    public InseminationServiceImpl(InseminationRepository inseminationRepository, LitterService litterService, SowService sowService){
        this.inseminationRepository = inseminationRepository;
        this.sowService = sowService;
        this.litterService = litterService;
    }

    @Override
    @Transactional
    public Insemination createInsemination(InseminationRequest inseminationRequest) {
        InseminationDto inseminationDto = inseminationRequest.inseminationDto();
        Set<Long> sowIds = inseminationDto.sowIds() != null ? inseminationDto.sowIds() : new HashSet<>();
        Set<Long> litterIds = inseminationDto.litterIds() != null ? inseminationDto.litterIds() : new HashSet<>();
        StringBuilder missingFields = new StringBuilder();
        if(sowIds.isEmpty() && litterIds.isEmpty()) 
            throw new NoAssignedEntitiesException("Insemination must have at least 1 sow or litter assigned!");        

        if(inseminationDto.date() == null) missingFields.append("date, ");
        if(inseminationDto.breed() == null) missingFields.append("breed, ");
        if(inseminationRequest.sowGroup() == null) missingFields.append("sow group, ");
        if(!missingFields.isEmpty()) {
            missingFields.setLength(missingFields.length() - 2);
            throw new NoFieldsException("Missing fields: " + missingFields);
        }

        Insemination insemination = mapToEntity(inseminationDto);
        Set<Litter> litters = litterService.getLittersById(litterIds);        
        Set<Sow> sows = sowService.getSowEntitiesById(sowIds);
        
        if(inseminationRequest.updateSowStatus())
            sows.forEach(sow -> sow.setStatus(SowStatus.INSEMINATED));

        sows.forEach(sow -> sow.setGroupNumber(inseminationRequest.sowGroup()));

            sows.forEach(sow -> {
                if(litters.stream().noneMatch(l -> l.getSow().getId().equals(sow.getId()))){
                    Optional<Litter> litter = sow.resolveLatestLitter();
                    if(litter.isEmpty())
                        litters.add(Litter.defaultLitter(sow));    
                    else if(litter.get().getFarrowing() != null)
                        litters.add(Litter.defaultLitter(sow));
                    else
                        litters.add(litter.get());
            }});

        insemination.addLitters(litters);
        insemination.addSows(sows);
        
        return inseminationRepository.save(insemination);
    }

    @Override
    public Insemination updateInsemination(InseminationRequest inseminationRequest, Long id) {
        InseminationDto inseminationDto = inseminationRequest.inseminationDto();
        Insemination editedInsemination = inseminationRepository.findById(id).orElseThrow(() -> new NotFoundException("insemination not found"));
        Set<Long> sowIds = inseminationDto.sowIds() != null ? inseminationDto.sowIds() : new HashSet<>();
        Set<Long> litterIds = inseminationDto.litterIds() != null ? inseminationDto.litterIds() : new HashSet<>();
        
        if(sowIds.isEmpty() && litterIds.isEmpty())
            throw new NoAssignedEntitiesException("Insemination must have at least 1 sow or litter assigned!");

        if(inseminationDto.breed() != null)
            editedInsemination.setBreed(inseminationDto.breed());
        if(inseminationDto.date() != null)    
            editedInsemination.setDate(inseminationDto.date());
        if(inseminationDto.note() != null)
            editedInsemination.setNote(inseminationDto.note());

        editedInsemination.getSows().removeIf(sow -> !sowIds.contains(sow.getId()));
        editedInsemination.getLitters().removeIf(litter -> !litterIds.contains(litter.getId()) || !sowIds.contains(litter.getSow().getId()));

        Set<Long> existingSowIds = editedInsemination.getSows().stream()
                .map(Sow::getId)
                .collect(Collectors.toSet());

        Set<Long> existingLitterIds = editedInsemination.getLitters().stream()
                .map(Litter::getId)
                .collect(Collectors.toSet());

        sowIds.removeIf(existingSowIds::contains);
        litterIds.removeIf(existingLitterIds::contains);

        Set<Sow> sows = sowService.getSowEntitiesById(sowIds);
        Set<Litter> litters = litterService.getLittersById(litterIds);

        editedInsemination.addSows(sows);
        editedInsemination.addLitters(litters);
        
        return inseminationRepository.save(editedInsemination);
    }

    @Override
    public List<InseminationDto> getAllInseminations() {
        List<Insemination> inseminations = inseminationRepository.findAll();
        return inseminations.stream().map(this::mapToDto).toList();
    }

    @Override
    public List<Insemination> getInseminationsOfMonth(int year, int month){
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = start.plusMonths(1).minusDays(1);
        return inseminationRepository.findByDateBetween(start, end);
    }

    @Override
    public InseminationDto getInseminationById(Long id) {
        Insemination insemination = inseminationRepository.findById(id).orElseThrow(() -> new NotFoundException("Insemination not found"));
        return mapToDto(insemination);
    }

    @Override
    public List<InseminationDto> getInseminationsById(List<Long> ids) {
        List<Insemination> inseminations = inseminationRepository.findAllById(ids);
        return inseminations.stream().map(this::mapToDto).toList();
    }

    @Override
    public void deleteInsemination(Long id) {
        inseminationRepository.deleteById(id);      
    }

    private Insemination mapToEntity(InseminationDto inseminationDto){
        Insemination insemination = new Insemination();
        insemination.setDate(inseminationDto.date());
        if(inseminationDto.note() != null)
            insemination.setNote(inseminationDto.note());
        else
            insemination.setNote("");
        return insemination;
    }

    private InseminationDto mapToDto(Insemination insemination){
        Set<Long> sowIds = new HashSet<>();
        Set<Long> litterIds = new HashSet<>();
        if(insemination.getSows() != null){
            sowIds = insemination.getSows().stream().map(Sow::getId).collect(Collectors.toSet());
            if(insemination.getLitters() != null)
                litterIds = insemination.getLitters().stream().map(Litter::getId).collect(Collectors.toSet());
        }
        InseminationDto inseminationDto = new InseminationDto(
            insemination.getId(),
            insemination.getDate(),
            insemination.getBreed(),
            insemination.getNote(),
            litterIds,
            sowIds
        );
        return inseminationDto;
    }

}
