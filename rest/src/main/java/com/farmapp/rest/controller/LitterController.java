package com.farmapp.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmapp.rest.dto.LitterRequest;
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

    @GetMapping("/sow/{sowId}")
    public ResponseEntity<List<LitterDto>> getLittersBySowId(@PathVariable Long sowId){
        return new ResponseEntity<>(litterService.getLittersBySowId(sowId), HttpStatus.OK);
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<List<LitterDto>> getLittersByMonth(@PathVariable int year, @PathVariable int month){
        return new ResponseEntity<>(litterService.getLittersByMonth(year, month), HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<LitterDto> updateLitter(@PathVariable Long id, @RequestBody LitterRequest litterDto) {
        return new ResponseEntity<>(litterService.updateLitter(id, litterDto), HttpStatus.OK);
    }
}
