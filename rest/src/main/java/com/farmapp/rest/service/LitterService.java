package com.farmapp.rest.service;

import java.util.List;
import java.util.Optional;

import com.farmapp.rest.entity.Litter;

public interface LitterService {

    Litter setLitter(Litter litter);
    List<Litter> getAllLitters();
    Optional<Litter> getLitterById(Long id);
    List<Litter> getLittersById(List<Long> ids);
    void deleteLitter(Long id);

}
