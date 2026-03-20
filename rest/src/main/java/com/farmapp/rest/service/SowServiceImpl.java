package com.farmapp.rest.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.farmapp.rest.dto.SowDto;
import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.enums.SowStatus;
import com.farmapp.rest.exceptions.NotFoundException;
import com.farmapp.rest.repository.SowRepository;

@Service
public class SowServiceImpl implements SowService{

    private final SowRepository sowRepository;

    public SowServiceImpl(SowRepository sowRepository) {
        this.sowRepository = sowRepository;
    }

    @Override
    public SowDto createSow(SowDto sowDto) {
        Sow sow = mapToEntity(sowDto);
        Sow newSow = sowRepository.save(sow);
        return mapToDto(newSow);
    }

    @Override
    public SowDto updateSow(SowDto sowDto, Long id) {
        Sow sow = sowRepository.findById(id).orElseThrow(() -> new NotFoundException("Can't find sow to update"));
        if(sowDto.status() != null)
            sow.setStatus(sowDto.status());
        if(sowDto.number() != null)
            sow.setNumber(sowDto.number());
        if(sowDto.note() != null)
            sow.setNote(sowDto.note());
        if(sowDto.disposalDate() != null)
            sow.setDisposalDate(sowDto.disposalDate());
        else
            sow.setDisposalDate(null);
        sow.setGroupNumber(sowDto.group());
        Sow updatedSow = sowRepository.save(sow);
        return mapToDto(updatedSow);
    }

    @Override
    public List<SowDto> getAllSows() {
        List<Sow> sows = sowRepository.findAll();        
        return sows.stream().map(this::mapToDto).toList();
    }

    @Override
    public List<SowDto> getActiveSows() {
        Set<SowStatus> activeStatuses = Set.of(SowStatus.FREE, SowStatus.INSEMINATED, SowStatus.FARROWED);
        List<Sow> sows = sowRepository.findByStatusIsIn(activeStatuses);
        return sows.stream().map(this::mapToDto).toList();
    }

    @Override
    public SowDto getSowById(Long id) {
        Sow sow = sowRepository.findById(id).orElseThrow(() -> new NotFoundException("Sow not found"));
        return mapToDto(sow);
    }

    @Override
    public List<SowDto> getSowsById(Set<Long> ids) {
        List<Sow> sows = sowRepository.findAllById(ids);
        return sows.stream().map(this::mapToDto).toList();
    }

    @Override
    public Set<Sow> getSowEntitiesById(Set<Long> ids){
        List<Sow> sows = sowRepository.findAllById(ids);
        return new LinkedHashSet<>(sows);
    }

    @Override
    public void deleteSow(Long id) {
        sowRepository.deleteById(id);
    }

    private Sow mapToEntity(SowDto sowDto){
        Sow sow = new Sow();
        sow.setNumber(sowDto.number());
        if(sowDto.disposalDate() != null)
            sow.setDisposalDate(sowDto.disposalDate());
        sow.setStatus(sowDto.status());
        if(sowDto.note() != null)
            sow.setNote(sowDto.note());
        else
            sow.setNote("");
        sow.setGroupNumber(sowDto.group());
        return sow;
    }

    private SowDto mapToDto(Sow sow){
        SowDto sowDto = new SowDto(
            sow.getId(),
            sow.getNumber(),
            sow.getStatus(),
            sow.getGroupNumber(), 
            sow.getDisposalDate(),
            sow.getNote()
        );
        return sowDto;
    }

}
