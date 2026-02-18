package com.farmapp.rest.service;

import java.util.List;
import java.util.Optional;

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
    public Litter setLitter(Litter litter) {
        return litterRepository.save(litter);
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
    public List<Litter> getLittersById(List<Long> ids) {
        return litterRepository.findAllById(ids);
    }

    @Override
    public void deleteLitter(Long id) {
        litterRepository.deleteById(id);
    }

}
