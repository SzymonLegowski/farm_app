package com.farmapp.rest.service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.farmapp.rest.dto.LitterDto;
import com.farmapp.rest.entity.Event;
import com.farmapp.rest.entity.Litter;
import com.farmapp.rest.exceptions.NotFoundException;
import com.farmapp.rest.repository.LitterRepository;

@Service
public class LitterServiceImpl implements LitterService{

    private final LitterRepository litterRepository;


    public LitterServiceImpl(LitterRepository litterRepository){
        this.litterRepository = litterRepository;
    }

    @Override
    public Litter createLitter(Litter litter) {
        return litterRepository.save(litter);
    }

    @Override
    public LitterDto updateLitter(Long id, LitterDto litterDto) {
        Litter litter = litterRepository.findById(id).orElseThrow(() -> new NotFoundException("Litter not found"));
        litter.setBornAlive(litterDto.bornAlive());
        litter.setBornDeceased(litterDto.bornDeceased());
        litter.setDeceased(litterDto.deceased());
        litter.setWeaned(litterDto.weaned());
        Litter updatedLitter = litterRepository.save(litter);
        return mapToDto(updatedLitter);
    }

    @Override
    public List<Litter> getAllLitters() {
        return litterRepository.findAll();
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

    private LitterDto mapToDto(Litter litter){
        Set<Long> events = litter.getEvents().stream().map(Event::getId).collect(Collectors.toSet());
        LitterDto litterDto = new LitterDto(
            litter.getId(), 
            litter.getBornAlive(),
            litter.getBornDeceased(),
            litter.getDeceased(),
            litter.getWeaned(),
            litter.getNote(),
            litter.getSow().getId(),
            events
        );
        return litterDto;
    }

    // private Litter mapToEntity(LitterDto litterDto){
    //     Litter litter = new Litter();
    //     litter.setBornAlive(litterDto.bornAlive());
    //     litter.setBornDeceased(litterDto.bornDeceased());
    //     litter.setDeceased(litterDto.deceased());
    //     litter.setWeaned(litterDto.weaned());
    //     litter.setNote(litterDto.note());
    //     litter.setSow();
    // }
}
