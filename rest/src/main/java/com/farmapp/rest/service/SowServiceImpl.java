package com.farmapp.rest.service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farmapp.rest.dto.SowDto;
import com.farmapp.rest.entity.Event;
import com.farmapp.rest.entity.Litter;
import com.farmapp.rest.entity.Sow;
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
        if(sowDto.sowStatus() != null)
            sow.setStatus(sowDto.sowStatus());
        if(sowDto.number() != null)
            sow.setNumber(sowDto.number());
        if(sowDto.note() != null)
            sow.setNote(sowDto.note());
        if(sowDto.disposalDate() != null)
            sow.setDisposalDate(sowDto.disposalDate());
        sow.setGroup(null);
        Sow updatedSow = sowRepository.save(sow);
        return mapToDto(updatedSow);
    }

    @Override
    public List<SowDto> getAllSows() {
        List<Sow> sows = sowRepository.findAll();        
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
        sow.setStatus(sowDto.sowStatus());
        if(sowDto.note() != null)
            sow.setNote(sowDto.note());
        else
            sow.setNote("");
        sow.setGroup(sowDto.group());
        return sow;
    }

    private SowDto mapToDto(Sow sow){
        Set<Long> eventIds = new HashSet<>();
        if(sow.getEvents() != null)
            eventIds = sow.getEvents().stream().map(Event::getId).collect(Collectors.toSet());
        Set<Long> litterIds = new HashSet<>();
        if(sow.getLitters() != null)
            litterIds = sow.getLitters().stream().map(Litter::getId).collect(Collectors.toSet());
        SowDto sowDto = new SowDto(
            sow.getId(),
            sow.getNumber(),
            sow.getStatus(),
            sow.getGroup(), 
            sow.getDisposalDate(),
            sow.getNote(),
            eventIds,
            litterIds
        );
        return sowDto;
    }

}
