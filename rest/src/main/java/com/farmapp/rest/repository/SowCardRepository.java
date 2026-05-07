package com.farmapp.rest.repository;

import java.util.Optional;

import com.farmapp.rest.entity.SowCardView;

public interface SowCardRepository extends ViewRepository<SowCardView, Integer>{
    Optional<SowCardView> findById(Integer id);
}
