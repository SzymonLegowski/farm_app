package com.farmapp.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farmapp.rest.entity.Sow;

@Repository
public interface SowRepository extends JpaRepository<Sow, Long> {}