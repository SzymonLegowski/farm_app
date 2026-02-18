package com.farmapp.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farmapp.rest.entity.Litter;
import com.farmapp.rest.entity.Sow;

import java.util.List;


@Repository
public interface LitterRepository extends JpaRepository<Litter, Long> {
    List<Litter> findBySow(Sow sow);
}