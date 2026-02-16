package com.farmapp.rest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.repository.SowRepository;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import lombok.NonNull;

@Service
public class SowServiceImpl implements SowService{

    private final SowRepository sowRepository;

    public SowServiceImpl(SowRepository sowRepository) {
        this.sowRepository = sowRepository;
    }

    @Override
    public Single<Sow> setSow(@NonNull Sow sow) {
        return Single.just(sowRepository.saveAndFlush(sow));
    }

    @Override
    public Single<List<Sow>> getAllSows() {
        return Single.just(sowRepository.findAll());        
    }

    @Override
    public Single<List<Sow>> getSowsById(@NonNull List<Long> sowIds) {
        return Single.just(sowRepository.findAllById(sowIds));
    }

    @Override
    public Completable deleteSowsById(List<Long> sowIds) {
        return Completable.fromAction(() -> sowRepository.deleteAllById(sowIds));
    }

}
