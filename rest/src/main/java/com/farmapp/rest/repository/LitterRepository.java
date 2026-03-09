package com.farmapp.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farmapp.rest.entity.Litter;

import java.util.List;
import java.time.LocalDate;



@Repository
public interface LitterRepository extends JpaRepository<Litter, Long> {
    List<Litter> findBySowId(Long sowId);
    List<Litter> findByFarrowingBetween(LocalDate start, LocalDate end);
    List<Litter> findByWeaningBetween(LocalDate start, LocalDate end);
}