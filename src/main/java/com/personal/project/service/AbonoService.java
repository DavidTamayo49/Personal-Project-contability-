package com.personal.project.service;

import com.personal.project.domain.Abono;
import com.personal.project.domain.Deudor;
import com.personal.project.domain.Movimiento;
import com.personal.project.domain.TipoMovimiento;
import com.personal.project.repository.AbonoRepository;
import com.personal.project.repository.DeudorRepository;
import com.personal.project.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AbonoService {

    private AbonoRepository abonoRepository;
    private MovimientoService movimientoService;
    private DeudorService deudorService;
    private TipoMovimientoService tipoMovimientoService;


    public AbonoService(AbonoRepository abonoRepository, MovimientoService movimientoService,
                        DeudorService deudorService, TipoMovimientoService tipoMovimientoService) {
        this.abonoRepository = abonoRepository;
        this.movimientoService = movimientoService;

        this.deudorService = deudorService;
        this.tipoMovimientoService = tipoMovimientoService;
    }

    public void validateData(Abono abono) {

        if (abono.getId() == null) {
            abono.setId(UUID.randomUUID());
        }

        if (abono.getValorabono() <= 0) {
            throw new IllegalArgumentException("El valor del abono debe ser mayor que cero.");
        }

        if (abono.getMediopago() == null) {
            throw new IllegalArgumentException("Los campos son obligatorios.");
        }

        if (abono.getDeudor() == null || abono.getDeudor().getId() == null) {
            throw new IllegalArgumentException("El deudor es obligatorio.");
        }


    }

   //Realizar abono parcial a la empresa (desde cliente)
    public void payDebtParcially(Abono abono) {

        validateData(abono);

        Deudor deudor = deudorService.findById(abono.getDeudor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Deudor no encontrado"));



        // Actualizar la deuda del deudor
        int nuevaDeuda = deudor.getValordeuda() - abono.getValorabono();
        if (nuevaDeuda < 0) {
            throw new IllegalArgumentException("El abono no puede ser mayor que la deuda actual.");
        }

        deudor.setValordeuda(nuevaDeuda);

        deudorService.updateDeptor(deudor);

        TipoMovimiento movimientoIngreso = tipoMovimientoService.findTipoMovimientoById(UUID.fromString("f8b1c2d3-4e5f-6a7b-8c9d-e0f1g2h3i4j5"));


        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(new Date());
        movimiento.setDescripcion("Abono realizado por el cliente: " + deudor.getCliente().getNombre());
        movimiento.setValor(abono.getValorabono());
        movimiento.setCliente(deudor.getCliente());
        movimiento.setMedioPago(abono.getMediopago());
        movimiento.setTipoMovimiento(movimientoIngreso);

        movimientoService.saveMovement(movimiento);

        // Guardar el abono
        abonoRepository.save(abono);
    }



    //Realizar abono total a la empresa (desde cliente)
    public void payDebtTotally(Abono abono){
        validateData(abono);

        Deudor deudor = deudorService.findById(abono.getDeudor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Deudor no encontrado"));

        //crear movimiento con all valor que debe
        int valorCompleto = deudor.getValordeuda();

        TipoMovimiento movimientoIngreso = tipoMovimientoService.findTipoMovimientoById(UUID.fromString("f8b1c2d3-4e5f-6a7b-8c9d-e0f1g2h3i4j5"));

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(new Date());
        movimiento.setDescripcion("Pago completo realizado por el cliente: " + deudor.getCliente().getNombre());
        movimiento.setValor(valorCompleto);
        movimiento.setCliente(deudor.getCliente());
        movimiento.setMedioPago(abono.getMediopago());
        movimiento.setTipoMovimiento(movimientoIngreso);

        movimientoService.saveMovement(movimiento);


        //Eliminar deudor porque ya no debe nada
        deudorService.deleteDebtor(abono.getDeudor().getId());

        // Guardar el abono
        abonoRepository.save(abono);

    }

}
