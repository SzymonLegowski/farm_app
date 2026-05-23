package com.farmapp.rest.service;

import com.farmapp.rest.repository.CalendarRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Service;

import com.farmapp.rest.dto.LitterRequest;
import com.farmapp.rest.dto.EventsDto;
import com.farmapp.rest.dto.LitterDto;
import com.farmapp.rest.entity.CalendarView;
import com.farmapp.rest.entity.Insemination;
import com.farmapp.rest.entity.Litter;
import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.exceptions.NotFoundException;
import com.farmapp.rest.repository.LitterRepository;
import com.farmapp.rest.repository.SowRepository;

import jakarta.transaction.Transactional;

@Service
public class LitterServiceImpl implements LitterService{

    private final CalendarRepository calendarRepository;
    private final LitterRepository litterRepository;
    private final SowRepository sowRepository;

    public LitterServiceImpl(LitterRepository litterRepository, SowRepository sowRepository, CalendarRepository calendarRepository){
        this.litterRepository = litterRepository;
        this.sowRepository = sowRepository;
        this.calendarRepository = calendarRepository;
    }

    @Override
    public Litter createLitter(Litter litter) {
        return litterRepository.save(litter);
    }

    @Override
    @Transactional
    public List<LitterDto> updateLitters(List<LitterRequest> litterRequests){
        List<LitterDto> litterDtos = new ArrayList<>();
        litterRequests.forEach(lr -> litterDtos.add(updateLitter(lr.litterDto().id(), lr)));
        return litterDtos;
    }

    @Override
    @Transactional
    public LitterDto updateLitter(Integer id, LitterRequest litterRequest) {
        Integer sowStatus = -1;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LitterDto litterDto = litterRequest.litterDto();
        Litter litter = litterRepository.findById(id).orElseThrow(() -> new NotFoundException("Litter not found"));
        List<Insemination> inseminations = new ArrayList<>();
        litterDto.inseminations().forEach(insDto -> {
            if(insDto.date() == null || insDto.date().isBlank()) return;
            LocalDate date = LocalDate.parse(insDto.date(), formatter);
            Insemination insemination = new Insemination(date, insDto.note());
            inseminations.add(insemination);
        });
        if(inseminations.isEmpty()){
            litterRepository.delete(litter);
            return null;
        }

        if(litter.getInseminations().size() < inseminations.size())
            sowStatus = 1;

        litter.setInseminations(inseminations);

        if(litterDto.bornAlive() != null)
            litter.setBornAlive(litterDto.bornAlive());
        else
            litter.setBornAlive(0);

        if(litterDto.bornDeceased() != null)
            litter.setBornDeceased(litterDto.bornDeceased());
        else
            litter.setBornDeceased(0);

        if(litterDto.deceased() != null)
            litter.setDeceased(litterDto.deceased());
            else 
            litter.setDeceased(0);

        if(litterDto.weaned() != null)
            litter.setWeaned(litterDto.weaned());
        else
            litter.setWeaned(0);

        if(litterDto.farrowing() != null){
            sowStatus = 3;
            LocalDate farrowing = LocalDate.parse(litterDto.farrowing(), formatter);
            litter.setFarrowing(farrowing);
        }else
            litter.setFarrowing(null);

        if(litterDto.weaning() != null){
            sowStatus = 0;
            LocalDate weaning = LocalDate.parse(litterDto.weaning(), formatter);
            litter.setWeaning(weaning);
        }else
            litter.setWeaning(null);
        
        if(litterRequest.updateSowStatus()){
            Sow sow = sowRepository.findById(litter.getSow().getId()).get();
            sow.updateStatus(sowStatus);
            sowRepository.save(sow);
        }

        Litter updatedLitter = litterRepository.save(litter);
        return updatedLitter.toDto();
    }

    @Override
    public List<Litter> getAllLitters() {
        return litterRepository.findAll();
    }

    @Override
    public List<LitterDto> getLittersBySowId(Integer sowId){
        List<Litter> litters = litterRepository.findBySowId(sowId);
        return litters.stream().map(Litter::toDto).toList();
    }

    @Override
    public Optional<Litter> getLitterById(Integer id) {
        return litterRepository.findById(id);
    }

    @Override
    public Set<Litter> getLittersById(Set<Integer> ids) {
        List<Litter> litters = litterRepository.findAllById(ids);
        return new LinkedHashSet<>(litters);
    }

    @Override
    public EventsDto getLittersEventsInPeriod(LocalDate start, LocalDate end) {
        List<CalendarView> calendarView = calendarRepository.findByDateBetween(start, end);
        Integer latestSowGroup = sowRepository.getLatestSowGroup();
        EventsDto eventsDto = new EventsDto(calendarView, latestSowGroup);
        return eventsDto;
    }

    @Override
    public void deleteLitter(Integer id) {
        litterRepository.deleteById(id);
    }


}
