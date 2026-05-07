package com.farmapp.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.farmapp.rest.dto.SowDto;
import com.farmapp.rest.entity.SowCardView;
import com.farmapp.rest.service.SowService;

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
@RequestMapping("/api/sows")
public class SowController {

    private final SowService sowService;
    
    public SowController(SowService sowService){
        this.sowService = sowService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SowDto> createSow(@RequestBody SowDto sow) {
        return new ResponseEntity<>(sowService.createSow(sow), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SowDto>> getAllSows() {
        return new ResponseEntity<>(sowService.getAllSows(), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<SowDto>> getActiveSows() {
        return new ResponseEntity<>(sowService.getActiveSows(), HttpStatus.OK);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<SowDto> getSowById(@PathVariable Integer id){
        return new ResponseEntity<>(sowService.getSowById(id), HttpStatus.OK);
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<SowCardView> getSowHistoryById(@PathVariable Integer id){
        return new ResponseEntity<>(sowService.getSowHistoryById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SowDto> updateSow(@PathVariable Integer id, @RequestBody SowDto sow) {        
        return new ResponseEntity<>(sowService.updateSow(sow, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSow(@PathVariable Integer id){
        sowService.deleteSow(id);
        return new ResponseEntity<>("Sow delete", HttpStatus.OK);
    }
}
