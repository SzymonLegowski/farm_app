package com.farmapp.rest.service;

import java.util.List;
import java.util.Set;

import com.farmapp.rest.dto.SowDto;
import com.farmapp.rest.entity.Sow;

public interface SowService {

    SowDto createSow(SowDto sowDto);
    SowDto updateSow(SowDto sowDto, Long id);
    List<SowDto> getAllSows();
    List<SowDto> getActiveSows();
    SowDto getSowById(Long id);
    List<SowDto> getSowsById(Set<Long> ids);
    Set<Sow> getSowEntitiesById(Set<Long> ids);
    void deleteSow(Long id);

}
