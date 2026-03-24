package com.farmapp.rest.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farmapp.rest.entity.Insemination;

@Repository
public interface InseminationRepository extends JpaRepository<Insemination, Long> {
    
    List<Insemination> findByDateBetween(LocalDate start, LocalDate end);
    
}