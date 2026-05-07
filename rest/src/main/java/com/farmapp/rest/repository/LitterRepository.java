package com.farmapp.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farmapp.rest.entity.Litter;



@Repository
public interface LitterRepository extends JpaRepository<Litter, Integer> {
    List<Litter> findBySowId(Integer sowId);
}