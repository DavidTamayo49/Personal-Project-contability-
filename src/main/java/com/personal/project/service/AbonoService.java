package com.personal.project.service;

import com.personal.project.domain.Abono;
import com.personal.project.domain.Deudor;
import com.personal.project.domain.MedioPago;
import com.personal.project.domain.Movimiento;
import com.personal.project.domain.TipoMovimiento;
import com.personal.project.repository.AbonoRepository;
import com.personal.project.repository.MedioPagoRepository;
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
    private MedioPagoRepository medioPagoRepository;


    public AbonoService(AbonoRepository abonoRepository, MovimientoService movimientoService, MovimientoRepository movimientoRepository,
                        DeudorService deudorService, TipoMovimientoService tipoMovimientoService,
                        MedioPagoRepository medioPagoRepository) {
        this.abonoRepository = abonoRepository;
        this.movimientoService = movimientoService;
        this.movimientoRepository = movimientoRepository;
        this.deudorService = deudorService;
        this.tipoMovimientoService = tipoMovimientoService;
        this.medioPagoRepository = medioPagoRepository;
    }

    private void validateData(Abono abono, boolean requireValor) {
        abono.setId(null);

        if (requireValor && abono.getValorabono() <= 0) {
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
       validateData(abono, true);

       // Recuperar el deudor desde la base de datos
       Deudor deudor = deudorService.findById(abono.getDeudor().getId())
               .orElseThrow(() -> new IllegalArgumentException("Deudor no encontrado"));

       MedioPago medioPago = medioPagoRepository.findById(abono.getMediopago().getId())
               .orElseThrow(() -> new IllegalArgumentException("Medio de pago no encontrado"));

       // Actualizar la deuda del deudor
       int nuevaDeuda = deudor.getValordeuda() - abono.getValorabono();
       if (nuevaDeuda < 0) {
           throw new IllegalArgumentException("El abono no puede ser mayor que la deuda actual.");
       }
       deudor.setValordeuda(nuevaDeuda);
       deudorService.updateDeptor(deudor);

       // Crear y guardar el movimiento
       TipoMovimiento movimientoIngreso = tipoMovimientoService.findTipoMovimientoById(UUID.fromString("ccc4752e-92d0-42d8-934d-56d49f6758b6"));
       Movimiento movimiento = new Movimiento();
       movimiento.setFecha(new Date());
       movimiento.setDescripcion("Abono realizado por el cliente: " + deudor.getCliente().getNombre());
       movimiento.setValor(abono.getValorabono());
       movimiento.setCliente(deudor.getCliente());
       movimiento.setMedioPago(medioPago);
       movimiento.setTipoMovimiento(movimientoIngreso);
       movimientoRepository.save(movimiento);

       abono.setDeudor(deudor);
       abono.setMediopago(medioPago);
       abonoRepository.save(abono);
   }



    //Realizar abono total a la empresa (desde cliente)
    @Transactional
    public void payDebtTotally(Abono abono){
        validateData(abono, false);

        Deudor deudor = deudorService.findById(abono.getDeudor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Deudor no encontrado"));

        //crear movimiento con all valor que debe
        int valorCompleto = deudor.getValordeuda();
        abono.setValorabono(valorCompleto);

        MedioPago medioPago = medioPagoRepository.findById(abono.getMediopago().getId())
                .orElseThrow(() -> new IllegalArgumentException("Medio de pago no encontrado"));

        TipoMovimiento movimientoIngreso = tipoMovimientoService.findTipoMovimientoById(UUID.fromString("bb263257-a81e-4346-b29c-af2e3c64c158"));

        Movimiento movimiento = new Movimiento();
        movimiento.setFecha(new Date());
        movimiento.setDescripcion("Pago completo realizado por el cliente: " + deudor.getCliente().getNombre());
        movimiento.setValor(valorCompleto);
        movimiento.setCliente(deudor.getCliente());
        movimiento.setMedioPago(medioPago);
        movimiento.setTipoMovimiento(movimientoIngreso);

        movimientoService.saveMovement(movimiento);


        //Eliminar deudor porque ya no debe nada
        deudorService.deleteDebtor(abono.getDeudor().getId());

        abono.setDeudor(deudor);
        abono.setMediopago(medioPago);

        // Guardar el abono
        abonoRepository.save(abono);

    }

}
