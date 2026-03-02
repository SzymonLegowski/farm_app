package com.farmapp.rest.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.farmapp.rest.entity.Litter;

public interface LitterService {

    Litter createLitter(Litter l);
    Litter updateLitter(Litter l);
    List<Litter> getAllLitters();
    Optional<Litter> getLitterById(Long id);
    Set<Litter> getLittersById(Set<Long> ids);
    void deleteLitter(Long id);

}
