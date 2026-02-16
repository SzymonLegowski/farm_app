package com.farmapp.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmapp.rest.entity.Sow;
import com.farmapp.rest.service.SowService;

import io.reactivex.rxjava3.core.Single;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api")
public class SowController {

    private final SowService sowService;
    
    public SowController(SowService sowService){
        this.sowService = sowService;
    }

    @PostMapping("/sows")
    public Single<Sow> createSow(@RequestBody Sow sow) {
        return sowService.setSow(sow);
    }

    @GetMapping("/sows")
    public Single<List<Sow>> getAllSows() {
        return sowService.getAllSows();
    }

    @PutMapping("/sows/{id}")
    public Single<Sow> putMethodName(@PathVariable Long id, @RequestBody Sow sow) {        
        return sowService.setSow(sow);
    }
    
    
}
