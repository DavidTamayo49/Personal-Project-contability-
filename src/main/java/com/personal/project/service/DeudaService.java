package com.personal.project.service;


import com.personal.project.domain.Deuda;
import com.personal.project.repository.DeudaRepository;
import com.personal.project.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeudaService {
    private DeudaRepository deudaRepository;
    private ProveedorRepository proveedorRepository;


    //Consultar las deudas
    public List<Deuda> findAllDebts() {
        return deudaRepository.findAll();
    }

    //Eliminar deuda
    public void deleteDebt(UUID uuid) {
        deudaRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("La oferta que deseas eliminar no existe o ya ha sido eliminada"));
        deudaRepository.deleteById(uuid);
    }

    //Registrar deuda
    public void saveDebt(Deuda deuda) {

        //Asignar id si no tiene uno ya asignado
        if (deuda.getId() == null) {
            deuda.setId(UUID.randomUUID());
        }

        //Verificar que si exista el proveedor al cual se le debe
        if (!proveedorRepository.existsById(deuda.getProveedor().getId())) {
            throw new IllegalArgumentException("El proveedor con ID " + deuda.getProveedor().getId() + " no existe.");

        }

        //No se admiten numeros negativos
        if (deuda.getValor() < 0) {
            throw new IllegalArgumentException("El valor de la deuda no puede ser negativo.");
        }

        deudaRepository.save(deuda);
    }


    //Modificar la deuda
    public void updateDebt(Deuda newDebt) {
        Optional<Deuda> debtOptional = deudaRepository.findById(newDebt.getId());

        if (debtOptional.isPresent()) {
            Deuda debt = debtOptional.get();
            debt.setValor(newDebt.getValor());
            debt.setProveedor(debt.getProveedor());

            deudaRepository.save(debt);
        } else {
            throw new IllegalArgumentException("El producto no fue encontrado");
        }
    }

    //Pagar deuda
    //TODO PENDIENTE DEBIDO A QUE NECESITAMOS PRIMERO EL MOVIMIENTO SERVICE, PORQUE HAY QUE CREAR UN MOVIMIENTO CON EL VALOR DE LA DEUDA




}
