package com.personal.project.service;



import com.personal.project.domain.Cliente;
import com.personal.project.domain.Deudor;
import com.personal.project.repository.DeudorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeudorService {
    private final DeudorRepository deudorRepository;
    private final ClienteService clienteService;

    public DeudorService(DeudorRepository deudorRepository, ClienteService clienteService) {
        this.deudorRepository = deudorRepository;
        this.clienteService = clienteService;
    }

    private void validateDebtor(Deudor deptor) {
        if (deptor.getCliente() == null || deptor.getCliente().getId() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }

        if (deptor.getValordeuda() <= 0) {
            throw new IllegalArgumentException("El valor de la deuda debe ser mayor que cero");
        }
    }

    //Registrar Deudor
    public void saveDeptor(Deudor deptor){
        validateDebtor(deptor);

        Cliente cliente = clienteService.findClientById(deptor.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no fue encontrado"));

        deptor.setCliente(cliente);
        deudorRepository.save(deptor);

    }

    //Modificar deudor
    public void updateDeptor(Deudor newDeptor) {
        if (newDeptor.getId() == null) {
            throw new IllegalArgumentException("El id del deudor es obligatorio");
        }

        Optional<Deudor> deptorOptional = deudorRepository.findById(newDeptor.getId());

        if (deptorOptional.isPresent()) {
            validateDebtor(newDeptor);

            Cliente cliente = clienteService.findClientById(newDeptor.getCliente().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no fue encontrado"));

            Deudor deptor = deptorOptional.get();
            deptor.setCliente(cliente);
            deptor.setValordeuda(newDeptor.getValordeuda());

            deudorRepository.save(deptor);


        } else {
            throw new IllegalArgumentException("Deudor no fue encontrado");
        }
    }


    //Conusultar deudores
    public List<Deudor> findAllDeptors() {
        return deudorRepository.findAll();
    }



    //Consultar deudor por id
    public Optional <Deudor> findById(UUID id) {
        return deudorRepository.findById(id);
    }


    //Eliminar deudor
    public void deleteDebtor(UUID uuid) {
        deudorRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("El deudor que deseas eliminar no existe o ya ha sido eliminado"));
        deudorRepository.deleteById(uuid);
    }
}
