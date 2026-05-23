package com.farmapp.rest.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.farmapp.rest.dto.LitterRequest;
import com.farmapp.rest.dto.EventsDto;
import com.farmapp.rest.dto.LitterDto;
import com.farmapp.rest.entity.Litter;

public interface LitterService {

    Litter createLitter(Litter l);
    LitterDto updateLitter(Integer id, LitterRequest litterDto);
    List<Litter> getAllLitters();
    List<LitterDto> getLittersBySowId(Integer sowId);
    EventsDto getLittersEventsInPeriod(LocalDate start, LocalDate end);
    Optional<Litter> getLitterById(Integer id);
    Set<Litter> getLittersById(Set<Integer> ids);
    void deleteLitter(Integer id);
    List<LitterDto> updateLitters(List<LitterRequest> litterRequests);
}
