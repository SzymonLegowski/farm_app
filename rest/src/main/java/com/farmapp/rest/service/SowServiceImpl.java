package com.farmapp.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.repository.SowRepository;

@Service
public class SowServiceImpl implements SowService{

    private final SowRepository sowRepository;

    public SowServiceImpl(SowRepository sowRepository) {
        this.sowRepository = sowRepository;
    }

    @Override
    public Sow setSow(Sow sow) {
        return sowRepository.saveAndFlush(sow);
    }

    @Override
    public List<Sow> getAllSows() {
        return sowRepository.findAll();        
    }

    @Override
    public Optional<Sow> getSowById(Long id) {
        return sowRepository.findById(id);
    }

    @Override
    public List<Sow> getSowsById(List<Long> ids) {
        return sowRepository.findAllById(ids);
    }

    @Override
    public void deleteSow(Long id) {
        sowRepository.deleteById(id);
    }

}
