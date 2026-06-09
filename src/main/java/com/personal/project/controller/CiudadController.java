package com.personal.project.controller;


import com.personal.project.domain.Ciudad;
import com.personal.project.service.CiudadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/city")
public class CiudadController {

    private CiudadService ciudadService;

    public CiudadController(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    @GetMapping
    public ResponseEntity<List<Ciudad>> findAllCities() {
        return ResponseEntity.ok(ciudadService.getCities());
    }
}
