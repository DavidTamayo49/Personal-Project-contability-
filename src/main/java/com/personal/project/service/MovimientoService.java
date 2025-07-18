package com.personal.project.service;


import com.personal.project.domain.Movimiento;
import com.personal.project.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovimientoService {
    private MovimientoRepository movimientoRepository;

    //Consultar movimientos
    public List<Movimiento> findAllMovements() {
        return movimientoRepository.findAll();
    }


    //Consultar movimiento por id
    public Optional<Movimiento> findMovementById(UUID id) {
        return movimientoRepository.findById(id);
    }

    //Registrar movimiento
    public void saveMovement(Movimiento movimiento) {
        if (movimiento.getId() == null) {
            movimiento.setId(UUID.randomUUID());
        }

        if (movimiento.getFecha() == null || movimiento.getMedioPago() == null || movimiento.getTipoMovimiento() == null
                || movimiento.getDescripcion() == null || movimiento.getCliente() == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        if (movimiento.getFecha().after(new Date())) {
            throw new IllegalArgumentException("La fecha del movimiento no puede ser mayor a la fecha actual");
        }

        if (movimiento.getValor() <= 0) {
            throw new IllegalArgumentException("El valor del movimiento debe ser mayor a 0");
        }

        movimientoRepository.save(movimiento);
    }

    // Actualizar movimiento
    public void updateMovement(Movimiento newMovement) {
        Optional<Movimiento> movementOptional = movimientoRepository.findById(newMovement.getId());

        if (movementOptional.isPresent()) {
            Movimiento movement = movementOptional.get();
            movement.setFecha(newMovement.getFecha());
            movement.setDescripcion(newMovement.getDescripcion());
            movement.setValor(newMovement.getValor());
            movement.setMedioPago(newMovement.getMedioPago());
            movement.setTipoMovimiento(newMovement.getTipoMovimiento());
            movement.setCliente(newMovement.getCliente());
            movimientoRepository.save(movement);
        } else {
            throw new IllegalArgumentException("Movimiento no fue encontrado");
        }
    }


    // Eliminar movimiento
    public void deleteMovement(UUID id) {
        if (!movimientoRepository.existsById(id)) {
            throw new IllegalArgumentException("El movimiento con el ID especificado no existe");
        }

        movimientoRepository.deleteById(id);
    }
}
