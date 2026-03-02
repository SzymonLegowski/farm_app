package com.farmapp.rest.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.farmapp.rest.entity.Litter;
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
    public Litter updateLitter(Litter litterDto) {
        return litterRepository.save(litterDto);
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

    // private LitterDto mapToDto(Litter litter){
    //     LitterDto litterDto = new LitterDto(
    //         litter.getId(), 
    //         litter.getBornAlive(),
    //         litter.getBornDeceased(),
    //         litter.getDeceased(),
    //         litter.getWeaned(),
    //         litter.getNote(),
    //         litter.getSow().getId(),
    //         litter.getEvents().stream().map(Event::getId).collect(Collectors.toList())
    //     );
    //     return litterDto;
    // }

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
