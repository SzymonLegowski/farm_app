package com.farmapp.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmapp.rest.dto.LitterDto;
import com.farmapp.rest.entity.Litter;
import com.farmapp.rest.service.LitterService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;




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
    public ResponseEntity<LitterDto> updateLitter(@PathVariable Long id, @RequestBody LitterDto litterDto) {
        return new ResponseEntity<>(litterService.updateLitter(id, litterDto), HttpStatus.OK);
    }
}
