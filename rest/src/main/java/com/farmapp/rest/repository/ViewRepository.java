package com.farmapp.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface ViewRepository<T, K> extends Repository<T ,K> {

    List<T> findAll();
    Optional<T> findById(Integer id);

}
