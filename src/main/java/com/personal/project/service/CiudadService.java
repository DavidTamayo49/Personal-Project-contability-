package com.personal.project.service;

import com.personal.project.domain.Ciudad;
import com.personal.project.repository.CiudadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CiudadService {

    private CiudadRepository ciudadRepository;

    public CiudadService(CiudadRepository ciudadRepository) {
        this.ciudadRepository = ciudadRepository;
    }

    //Consultar ciudades
    public List<Ciudad> getCities() {
        return ciudadRepository.findAll();
    }

}
