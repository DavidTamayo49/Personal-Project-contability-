package com.personal.project.service;


import com.personal.project.domain.Deudor;
import com.personal.project.repository.DeudorRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeudorService {
    private DeudorRepository deudorRepository;


    //Registrar Deudor
    public void saveDeptor(Deudor deptor){

        if(deptor.getId() == null){
            deptor.setId(UUID.randomUUID());
        }

        if(deptor.getCliente() == null || deptor.getCliente().getId() == null){
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        deudorRepository.save(deptor);

    }

    //Modificar deudor


    //Conusultar deudores

    //Consultar deudor por id

    //Eliminar deudor

}
