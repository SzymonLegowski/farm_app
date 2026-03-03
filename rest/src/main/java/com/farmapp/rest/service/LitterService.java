package com.farmapp.rest.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.farmapp.rest.dto.LitterDto;
import com.farmapp.rest.entity.Litter;

public interface LitterService {

    Litter createLitter(Litter l);
    LitterDto updateLitter(Long id, LitterDto litterDto);
    List<Litter> getAllLitters();
    Optional<Litter> getLitterById(Long id);
    Set<Litter> getLittersById(Set<Long> ids);
    void deleteLitter(Long id);
}
