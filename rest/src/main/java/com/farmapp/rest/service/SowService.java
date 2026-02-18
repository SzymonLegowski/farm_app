package com.farmapp.rest.service;

import java.util.List;
import java.util.Optional;

import com.farmapp.rest.entity.Sow;

public interface SowService {

    Sow setSow(Sow sow);
    List<Sow> getAllSows();
    Optional<Sow> getSowById(Long id);
    List<Sow> getSowsById(List<Long> ids);
    void deleteSow(Long id);

}
