package com.personal.project.service;


import com.personal.project.domain.Movimiento;
import com.personal.project.repository.BalanceRepository;
import com.personal.project.repository.MedioPagoRepository;
import com.personal.project.repository.MovimientoRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BalanceService {

    private MedioPagoRepository medioPagoRepository;
    private MovimientoRepository movimientoRepository;

    public BalanceService(MedioPagoRepository medioPagoRepository, MovimientoRepository movimientoRepository) {
        this.medioPagoRepository = medioPagoRepository;
        this.movimientoRepository = movimientoRepository;
    }



    //Consultar balance por medio de pago
    public double getBalanceByPayMethod(UUID medioPagoId) {
        // Obtener el nombre del medio de pago a partir de su UUID
        String nombreMedioPago = medioPagoRepository.findById(medioPagoId)
                .map(medioPago -> medioPago.getNombre())
                .orElseThrow(() -> new IllegalArgumentException("Medio de pago no encontrado"));

        // Reutilizar el métod existente para calcular balances por medio de pago
        Map<String, Double> balances = makeBalanceByPayMethod();

        // Retornar el balance del medio de pago especificado, o 0.0 si no existe
        return balances.getOrDefault(nombreMedioPago, 0.0);
    }

    //Consultar balance global
    public double getGlobalBalance() {
        return makeGlobalBalance();
    }

    //Realizar balance por medio de pago
     public Map<String, Double> makeBalanceByPayMethod() {
         String[] mediosDePago = {"Nequi", "Efectivo", "Transferencia Bancaria", "Tarjeta"};

         List<Movimiento> movimientos = movimientoRepository.findAll();

         // Usar streams para calcular los balances por medio de pago
         Map<String, Double> balances = movimientos.stream()
             // Filtrar solo los movimientos que correspondan a los medios de pago relevantes
             .filter(movimiento ->
                 movimiento.getMedioPago() != null &&
                 Arrays.asList(mediosDePago).contains(movimiento.getMedioPago().getNombre())
             )
             // Agrupar los movimientos por el nombre del medio de pago
             .collect(Collectors.groupingBy(
                 movimiento -> movimiento.getMedioPago().getNombre(),
                 // Sumar los valores de los movimientos, sumando para ingresos y restando para egresos
                 Collectors.summingDouble(movimiento ->
                     movimiento.getTipoMovimiento().getNombre().equalsIgnoreCase("ingreso")
                         ? movimiento.getValor()
                         : -movimiento.getValor()
                 )
             ));

         // Asegurarse de que todos los medios de pago relevantes estén presentes en el mapa
         // Si algún medio de pago no tiene movimientos, se inicializa con balance 0.0
         for (String medio : mediosDePago) {
             balances.putIfAbsent(medio, 0.0);
         }

         return balances;
     }



    //Realizar balance global
    public double makeGlobalBalance() {
        List<Movimiento> movimientos = movimientoRepository.findAll();

        // Usar streams para calcular el balance global
        return movimientos.stream()
            // Sumar los valores de los movimientos, sumando para ingresos y restando para egresos
            .mapToDouble(movimiento ->
                movimiento.getTipoMovimiento().getNombre().equalsIgnoreCase("ingreso")
                    ? movimiento.getValor()
                    : -movimiento.getValor()
            )
            .sum(); // Sumar todos los valores calculados
    }




}
