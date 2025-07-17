package com.personal.project.service;


import com.personal.project.domain.MedioPago;
import com.personal.project.repository.MedioPagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedioPagoService {

    MedioPagoRepository medioPagoRepository;

    //Consultar medio de pago
    public List<MedioPago> findAllPayMetods() {
        return medioPagoRepository.findAll();
    }


    //Consuultar medio de pago por id
    public Optional<MedioPago> findById(UUID id) {
        return medioPagoRepository.findById(id);
    }



}
