package com.farmapp.rest.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.enums.SowStatus;

@Repository
public interface SowRepository extends JpaRepository<Sow, Long> {
    List<Sow> findByStatusIsIn(Set<SowStatus> statuses);
}