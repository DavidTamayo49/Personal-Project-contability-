package com.personal.project.service;

import com.personal.project.domain.Abono;
import com.personal.project.repository.AbonoRepository;
import com.personal.project.repository.DeudorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AbonoService {

    private AbonoRepository abonoRepository;
    private DeudorRepository deudorRepository;


    public AbonoService(AbonoRepository abonoRepository, DeudorRepository deudorRepository) {
        this.abonoRepository = abonoRepository;
        this.deudorRepository = deudorRepository;
    }

   //Realizar abono total


    //Realizar abono parcial







}
