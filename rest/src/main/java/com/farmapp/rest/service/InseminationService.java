package com.farmapp.rest.service;

import java.util.List;

import com.farmapp.rest.dto.InseminationDto;
import com.farmapp.rest.dto.InseminationRequest;
import com.farmapp.rest.entity.Insemination;

public interface InseminationService {
  
    Insemination createInsemination(InseminationRequest inseminationRequest);
    Insemination updateInsemination(InseminationRequest inseminationRequest, Long id);
    List<InseminationDto> getAllInseminations();
    List<Insemination> getInseminationsByMonth(int year, int month);
    InseminationDto getInseminationById(Long id);
    List<InseminationDto> getInseminationsById(List<Long> ids);
    void deleteInsemination(Long id);
   
}
