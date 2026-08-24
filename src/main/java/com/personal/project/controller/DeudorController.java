package com.personal.project.controller;

import com.personal.project.domain.Deudor;
import com.personal.project.service.DeudorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/deudor")
public class DeudorController {

    private final DeudorService deudorService;

    public DeudorController(DeudorService deudorService) {
        this.deudorService = deudorService;
    }

    //Consultar todos los deudores
    @GetMapping
    public ResponseEntity<List<Deudor>> findAllDebtors() {
        return ResponseEntity.ok(deudorService.findAllDeptors());
    }

    //Consultar deudor por id
    @GetMapping("/{id}")
    public ResponseEntity<?> findDebtorById(@PathVariable UUID id) {
        return deudorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Registrar deudor
    @PostMapping("/registrar-deudor")
    public ResponseEntity<Object> registerDebtor(@RequestBody Deudor deudor) {
        try {
            deudorService.saveDeptor(deudor);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Deudor registrado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }

    //Modificar deudor
    @PutMapping("/modificar-deudor")
    public ResponseEntity<Object> updateDebtor(@RequestBody Deudor deudor) {
        try {
            deudorService.updateDeptor(deudor);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Deudor modificado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }

    //Eliminar deudor
    @DeleteMapping("/eliminar-deudor/{id}")
    public ResponseEntity<Object> deleteDebtor(@PathVariable UUID id) {
        try {
            deudorService.deleteDebtor(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Deudor eliminado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }
}
