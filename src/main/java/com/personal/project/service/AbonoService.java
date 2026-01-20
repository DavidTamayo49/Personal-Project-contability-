package com.personal.project.service;

import com.personal.project.domain.Abono;
import com.personal.project.domain.Deudor;
import com.personal.project.domain.Movimiento;
import com.personal.project.domain.TipoMovimiento;
import com.personal.project.repository.AbonoRepository;
import com.personal.project.repository.MovimientoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import java.util.Date;
import java.util.UUID;

@Service
public class AbonoService {

    private AbonoRepository abonoRepository;
    private MovimientoService movimientoService;
    private MovimientoRepository movimientoRepository;
    private DeudorService deudorService;
    private TipoMovimientoService tipoMovimientoService;


    public AbonoService(AbonoRepository abonoRepository, MovimientoService movimientoService, MovimientoRepository movimientoRepository,
                        DeudorService deudorService, TipoMovimientoService tipoMovimientoService) {
        this.abonoRepository = abonoRepository;
        this.movimientoService = movimientoService;
        this.movimientoRepository = movimientoRepository;
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
   @Transactional
   public void payDebtParcially(Abono abono) {
       validateData(abono);

       // Recuperar el deudor desde la base de datos
       Deudor deudor = deudorService.findById(abono.getDeudor().getId())
               .orElseThrow(() -> new IllegalArgumentException("Deudor no encontrado"));

       // Sincronizar el abono con la base de datos
       Abono existingAbono = abonoRepository.findById(abono.getId())
               .orElse(abono); // Si no existe, usar el nuevo abono

       // Actualizar la deuda del deudor
       int nuevaDeuda = deudor.getValordeuda() - abono.getValorabono();
       if (nuevaDeuda < 0) {
           throw new IllegalArgumentException("El abono no puede ser mayor que la deuda actual.");
       }
       deudor.setValordeuda(nuevaDeuda);
       deudorService.updateDeptor(deudor);

       // Crear y guardar el movimiento
       TipoMovimiento movimientoIngreso = tipoMovimientoService.findTipoMovimientoById(UUID.fromString("bb263257-a81e-4346-b29c-af2e3c64c158"));
       Movimiento movimiento = new Movimiento();
       movimiento.setFecha(new Date());
       movimiento.setDescripcion("Abono realizado por el cliente: " + deudor.getCliente().getNombre());
       movimiento.setValor(abono.getValorabono());
       movimiento.setCliente(deudor.getCliente());
       movimiento.setMedioPago(abono.getMediopago());
       movimiento.setTipoMovimiento(movimientoIngreso);
       movimientoRepository.save(movimiento);

       // Guardar el abono sincronizado
       abonoRepository.save(existingAbono);
   }



    //Realizar abono total a la empresa (desde cliente)
    public void payDebtTotally(Abono abono){
        validateData(abono);

        Deudor deudor = deudorService.findById(abono.getDeudor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Deudor no encontrado"));

        //crear movimiento con all valor que debe
        int valorCompleto = deudor.getValordeuda();

        TipoMovimiento movimientoIngreso = tipoMovimientoService.findTipoMovimientoById(UUID.fromString("bb263257-a81e-4346-b29c-af2e3c64c158"));

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
