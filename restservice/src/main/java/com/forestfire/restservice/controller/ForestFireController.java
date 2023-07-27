package com.forestfire.restservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forestfire.restservice.dto.ForestFireConfigDto;
import com.forestfire.restservice.service.ForestFireService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/forestfire")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class ForestFireController {

    private final ForestFireService forestFireService;

    @GetMapping("init")
    public String[][] init(ForestFireConfigDto config) throws Exception {
        return forestFireService.initForestFire(config);
    }

    @PostMapping("next")
    public String[][] nextStep(ForestFireConfigDto config, @RequestBody String[][] fireForestActual) throws Exception {
        return forestFireService.calculateNextStep(config, fireForestActual);
    }

}