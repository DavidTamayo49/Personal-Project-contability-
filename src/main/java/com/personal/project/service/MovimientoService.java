package com.personal.project.service;


import com.personal.project.domain.Cliente;
import com.personal.project.domain.MedioPago;
import com.personal.project.domain.Movimiento;
import com.personal.project.domain.TipoMovimiento;
import com.personal.project.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final ClienteService clienteService;
    private final MedioPagoService medioPagoService;
    private final TipoMovimientoService tipoMovimientoService;

    public MovimientoService(MovimientoRepository movimientoRepository, ClienteService clienteService,
                             MedioPagoService medioPagoService, TipoMovimientoService tipoMovimientoService) {
        this.movimientoRepository = movimientoRepository;
        this.clienteService = clienteService;
        this.medioPagoService = medioPagoService;
        this.tipoMovimientoService = tipoMovimientoService;
    }

    private void validateMovement(Movimiento movimiento) {
        if (movimiento.getDescripcion() == null || movimiento.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        if (movimiento.getCliente() == null || movimiento.getCliente().getId() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }

        if (movimiento.getMedioPago() == null || movimiento.getMedioPago().getId() == null) {
            throw new IllegalArgumentException("El medio de pago es obligatorio");
        }

        if (movimiento.getTipoMovimiento() == null || movimiento.getTipoMovimiento().getId() == null) {
            throw new IllegalArgumentException("El tipo de movimiento es obligatorio");
        }

        if (movimiento.getValor() <= 0) {
            throw new IllegalArgumentException("El valor del movimiento debe ser mayor a 0");
        }
    }

    private void validateMovementDate(Movimiento movimiento) {
        if (movimiento.getFecha() == null) {
            throw new IllegalArgumentException("La fecha del movimiento es obligatoria");
        }

        if (movimiento.getFecha().after(new Date())) {
            throw new IllegalArgumentException("La fecha del movimiento no puede ser mayor a la fecha actual");
        }
    }

    private void setMovementRelations(Movimiento movimiento) {
        Cliente cliente = clienteService.findClientById(movimiento.getCliente().getId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no fue encontrado"));

        MedioPago medioPago = medioPagoService.findById(movimiento.getMedioPago().getId())
                .orElseThrow(() -> new IllegalArgumentException("Medio de pago no fue encontrado"));

        TipoMovimiento tipoMovimiento = tipoMovimientoService
                .findTipoMovimientoById(movimiento.getTipoMovimiento().getId());

        movimiento.setCliente(cliente);
        movimiento.setMedioPago(medioPago);
        movimiento.setTipoMovimiento(tipoMovimiento);
    }

    //Consultar movimientos
    public List<Movimiento> findAllMovements() {
        return movimientoRepository.findAll();
    }


    //Consultar movimiento por id
    public Optional<Movimiento> findMovementById(UUID id) {
        return movimientoRepository.findById(id);
    }

    //Registrar movimiento
    @Transactional
    public void saveMovement(Movimiento movimiento) {
        validateMovement(movimiento);
        movimiento.setFecha(new Date());
        setMovementRelations(movimiento);
        movimientoRepository.save(movimiento);
    }

    // Actualizar movimiento
    public void updateMovement(Movimiento newMovement) {
        if (newMovement.getId() == null) {
            throw new IllegalArgumentException("El id del movimiento es obligatorio");
        }

        Optional<Movimiento> movementOptional = movimientoRepository.findById(newMovement.getId());

        if (movementOptional.isPresent()) {
            validateMovement(newMovement);
            validateMovementDate(newMovement);
            setMovementRelations(newMovement);

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
