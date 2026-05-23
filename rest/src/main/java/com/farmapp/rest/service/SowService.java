package com.farmapp.rest.service;

import java.util.List;
import java.util.Set;

import com.farmapp.rest.dto.EventRequest;
import com.farmapp.rest.dto.SowDto;
import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.entity.SowCardView;

public interface SowService {

    SowDto createSow(SowDto sowDto);
    SowDto updateSow(SowDto sowDto, Integer id);
    void createEvent(EventRequest eventRequest);
    List<SowDto> getAllSows();
    List<SowDto> getActiveSows();
    SowDto getSowById(Integer id);
    SowCardView getSowHistoryById(Integer id);
    List<SowDto> getSowsById(Set<Integer> ids);
    Set<Sow> getSowEntitiesById(Set<Integer> ids);
    void deleteSow(Integer id);

}
