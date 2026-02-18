package com.farmapp.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.service.SowService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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
    public Sow createSow(@RequestBody Sow sow) {
        return sowService.setSow(sow);
    }

    @GetMapping()
    public List<Sow> getAllSows() {
        return sowService.getAllSows();
    }

    @GetMapping("/{id}")
    public Optional<Sow> getSowById(@PathVariable Long id){
        return sowService.getSowById(id);
    }

    @PutMapping("/{id}")
    public Sow updateSow(@PathVariable Long id, @RequestBody Sow sow) {        
        return sowService.setSow(sow);
    }

    @DeleteMapping()
    public void deleteSow(@PathVariable Long id){
        sowService.deleteSow(id);
    }
}
