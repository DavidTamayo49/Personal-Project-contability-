package com.personal.project.controller;


import com.personal.project.domain.Abono;
import com.personal.project.service.AbonoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/deudores/abono")
public class AbonoController {

    private final AbonoService abonoService;

    public AbonoController(AbonoService abonoService) {
        this.abonoService = abonoService;
    }


    @PostMapping("/pagar-deuda-parcialmente")
    public ResponseEntity<Object> payDebtParcially(@RequestBody Abono abono) {
        try{
            abonoService.payDebtParcially(abono);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Se ha realizado el abono parcial exitosamente "));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
        }



    @PostMapping("/pagar-deuda-totalmente")
    public ResponseEntity<Object> payDebtTotally(@RequestBody Abono abono) {
        try{
            abonoService.payDebtTotally(abono);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Se ha realizado el abono total exitosamente "));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }


}






