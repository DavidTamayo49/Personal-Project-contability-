package com.personal.project.controller;

import com.personal.project.domain.MedioPago;
import com.personal.project.service.MedioPagoService;
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
@RequestMapping("api/v1/medio-pago")
public class MedioPagoController {

    private final MedioPagoService medioPagoService;

    public MedioPagoController(MedioPagoService medioPagoService) {
        this.medioPagoService = medioPagoService;
    }

    //Consultar todos los medios de pago
    @GetMapping
    public ResponseEntity<List<MedioPago>> findAllPayMethods() {
        return ResponseEntity.ok(medioPagoService.findAllPayMetods());
    }

    //Consultar medio de pago por id
    @GetMapping("/{id}")
    public ResponseEntity<?> findPayMethodById(@PathVariable UUID id) {
        return medioPagoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Registrar medio de pago
    @PostMapping("/registrar-medio-pago")
    public ResponseEntity<Object> registerPayMethod(@RequestBody MedioPago medioPago) {
        try {
            medioPagoService.savePayMethod(medioPago);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Medio de pago registrado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }

    //Modificar medio de pago
    @PutMapping("/modificar-medio-pago")
    public ResponseEntity<Object> updatePayMethod(@RequestBody MedioPago medioPago) {
        try {
            medioPagoService.updatePayMethod(medioPago);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Medio de pago modificado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }

    //Eliminar medio de pago
    @DeleteMapping("/eliminar-medio-pago/{id}")
    public ResponseEntity<Object> deletePayMethod(@PathVariable UUID id) {
        try {
            medioPagoService.deletePayMethod(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Medio de pago eliminado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }
}
