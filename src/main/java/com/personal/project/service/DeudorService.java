package com.personal.project.service;



import com.personal.project.domain.Deudor;
import com.personal.project.repository.DeudorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeudorService {
    private DeudorRepository deudorRepository;

    public DeudorService(DeudorRepository deudorRepository) {
        this.deudorRepository = deudorRepository;
    }

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
    public void updateDeptor(Deudor newDeptor) {
        Optional<Deudor> deptorOptional = deudorRepository.findById(newDeptor.getId());

        if (deptorOptional.isPresent()) {
            Deudor deptor = deptorOptional.get();
            deptor.setCliente(newDeptor.getCliente());
            deptor.setValordeuda(newDeptor.getValordeuda());

            deudorRepository.save(deptor);


        } else {
            throw new IllegalArgumentException("Cliente no fue encontrado");
        }
    }


    //Conusultar deudores
    public List<Deudor> findAllDeptors() {
        return deudorRepository.findAll();
    }



    //Consultar deudor por id
    public Optional<Deudor> findById(UUID id) {
        return deudorRepository.findById(id);
    }

    //Eliminar deudor
    public void deleteDebtor(UUID uuid) {
        deudorRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("La oferta que deseas eliminar no existe o ya ha sido eliminada"));
        deudorRepository.deleteById(uuid);
    }
}
