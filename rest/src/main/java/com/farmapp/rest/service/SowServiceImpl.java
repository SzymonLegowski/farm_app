package com.farmapp.rest.service;

import com.farmapp.rest.repository.LitterRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.farmapp.rest.dto.EventRequest;
import com.farmapp.rest.dto.SowDto;
import com.farmapp.rest.entity.Insemination;
import com.farmapp.rest.entity.Litter;
import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.entity.SowCardView;
import com.farmapp.rest.enums.SowStatus;
import com.farmapp.rest.exceptions.NotFoundException;
import com.farmapp.rest.repository.SowCardRepository;
import com.farmapp.rest.repository.SowRepository;

import jakarta.transaction.Transactional;

@Service
public class SowServiceImpl implements SowService{

    private final LitterRepository litterRepository;
    private final SowRepository sowRepository;
    private final SowCardRepository sowCardRepository;

    public SowServiceImpl(SowRepository sowRepository, SowCardRepository sowCardRepository, LitterRepository litterRepository) {
        this.sowRepository = sowRepository;
        this.sowCardRepository = sowCardRepository;
        this.litterRepository = litterRepository;
    }
    

    @Override
    public SowDto createSow(SowDto sowDto) {
        Sow sow = new Sow();
        sow.fromDto(sowDto);
        Sow newSow = sowRepository.save(sow);
        return newSow.toDto();
    }

    @Override
    @Transactional
    public void createEvent(EventRequest eventRequest){
        Map<Integer, String> sowIdNoteMap = new HashMap<>();
        eventRequest.sowIdNoteList().forEach(s -> {
            String[] data = s.split(",");
            sowIdNoteMap.putIfAbsent(Integer.parseInt(data[0]), data[1]);
        });
        List<Sow> selectedSows = sowRepository.findAllById(sowIdNoteMap.keySet());
        if(eventRequest.type() == 0){
            selectedSows.forEach(s -> {
                if(eventRequest.sowGroup() != null)
                    s.setGroupNr(eventRequest.sowGroup());
                Litter litter;
                Insemination newInsemination = new Insemination(eventRequest.date(), sowIdNoteMap.get(s.getId()));
                if(s.getStatus() != SowStatus.INSEMINATED){
                    litter = Litter.defaultLitter(s);
                    Optional<Litter> previousLitter = s.resolveLatestLitter();
                    litter.setNumber(previousLitter.isPresent() ? previousLitter.get().getNumber() + 1 : 1);
                }else{
                    litter = s.resolveLatestLitter().get();
                }
                Optional<Insemination> duplicate = litter.getInseminations().stream().filter(ins -> ins.getDate().equals(newInsemination.getDate())).findFirst();
                if(duplicate.isPresent()){
                    litter.getInseminations().remove(duplicate.get());
                    litter.getInseminations().add(newInsemination);
                }
                if(eventRequest.updateSowStatus()){
                    s.setStatus(SowStatus.INSEMINATED);
                }
                litterRepository.save(litter);
                sowRepository.save(s);
            });
        }else{
            selectedSows.forEach(s -> {
                Litter litter = s.resolveLatestLitter().get();
                if(eventRequest.type() == 1){
                    litter.setFarrowing(eventRequest.date());
                    if(eventRequest.updateSowStatus()){
                        s.setStatus(SowStatus.FARROWED);
                        sowRepository.save(s);
                    }
                }
                if(eventRequest.type() == 2){
                    litter.setWeaning(eventRequest.date());
                    if(eventRequest.updateSowStatus()){
                        s.setStatus(SowStatus.FREE);
                        sowRepository.save(s);
                    }
                }
                litterRepository.save(litter);
            });   
        }
    }

    @Override
    public SowDto updateSow(SowDto sowDto, Integer id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        Sow sow = sowRepository.findById(id).orElseThrow(() -> new NotFoundException("Can't find sow to update"));
        if(sowDto.status() != null)
            sow.setStatus(sowDto.status());
        if(sowDto.number() != null)
            sow.setNumber(sowDto.number());
        if(sowDto.note() != null)
            sow.setNote(sowDto.note());
        if(sowDto.disposalDate() != null)
            sow.setDisposed(LocalDate.parse(sowDto.disposalDate(),formatter));
        else
            sow.setDisposed(null);
        sow.setGroupNr(sowDto.group());
        Sow updatedSow = sowRepository.save(sow);
        return updatedSow.toDto();
    }

    @Override
    public List<SowDto> getAllSows() {
        List<Sow> sows = sowRepository.findAll();        
        return sows.stream().map(Sow::toDto).toList();
    }

    @Override
    public List<SowDto> getActiveSows() {
        Set<SowStatus> activeStatuses = Set.of(SowStatus.FREE, SowStatus.INSEMINATED, SowStatus.FARROWED);
        List<Sow> sows = sowRepository.findByStatusIsIn(activeStatuses);
        return sows.stream().map(Sow::toDto).toList();
    }

    @Override
    public SowDto getSowById(Integer id) {
        Sow sow = sowRepository.findById(id).orElseThrow(() -> new NotFoundException("Sow not found"));
        return sow.toDto();
    }

    @Override
    public List<SowDto> getSowsById(Set<Integer> ids) {
        List<Sow> sows = sowRepository.findAllById(ids);
        return sows.stream().map(Sow::toDto).toList();
    }

    @Override
    public Set<Sow> getSowEntitiesById(Set<Integer> ids){
        List<Sow> sows = sowRepository.findAllById(ids);
        return new LinkedHashSet<>(sows);
    }    

    @Override
    public SowCardView getSowHistoryById(Integer id) {
        SowCardView sowCardView = sowCardRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Sow with id " + id + " not found"));
        return sowCardView;
    }


    @Override
    public void deleteSow(Integer id) {
        sowRepository.deleteById(id);
    }
}
