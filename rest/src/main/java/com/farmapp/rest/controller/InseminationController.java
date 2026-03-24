package com.farmapp.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.farmapp.rest.dto.InseminationDto;
import com.farmapp.rest.dto.InseminationRequest;
import com.farmapp.rest.entity.Insemination;
import com.farmapp.rest.service.InseminationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/inseminations")
public class InseminationController {
    private final InseminationService inseminationService;

    public InseminationController(InseminationService inseminationService){
        this.inseminationService = inseminationService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Insemination> createInsemination(@RequestBody InseminationRequest inseminationRequest) {
        return new ResponseEntity<>(inseminationService.createInsemination(inseminationRequest), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<InseminationDto>> getAllInseminations() {
        return new ResponseEntity<>(inseminationService.getAllInseminations(), HttpStatus.OK);
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<Insemination>> getInseminationsByMonth(@PathVariable int year, @PathVariable int month){
        return new ResponseEntity<>(inseminationService.getInseminationsByMonth(year, month), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InseminationDto> getInseminationById(@PathVariable Long id) {      
        return new ResponseEntity<>(inseminationService.getInseminationById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Insemination> updateInsemination(@PathVariable Long id, @RequestBody InseminationRequest inseminationRequest) {
        return new ResponseEntity<>(inseminationService.updateInsemination(inseminationRequest, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInsemination(@PathVariable Long id){
        inseminationService.deleteInsemination(id);
        return new ResponseEntity<>("Insemination delete", HttpStatus.OK);
    }
}
