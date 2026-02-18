package com.farmapp.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmapp.rest.entity.Litter;
import com.farmapp.rest.service.LitterService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/litters")
public class LitterController {
    
    private final LitterService litterService;

    public LitterController(LitterService litterService){
        this.litterService = litterService;
    }

    @GetMapping()
    public List<Litter> getAllLitters() {
        return litterService.getAllLitters();
    }
    
    @PutMapping("/{id}")
    public Litter updateLitter(@PathVariable String id, @RequestBody Litter litter) {
        return litterService.setLitter(litter);
    }
}
