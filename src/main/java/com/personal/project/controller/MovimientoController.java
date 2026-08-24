package com.personal.project.controller;

import com.personal.project.domain.Movimiento;
import com.personal.project.service.MovimientoService;
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
@RequestMapping("api/v1/movimiento")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    //Consultar todos los movimientos
    @GetMapping
    public ResponseEntity<List<Movimiento>> findAllMovements() {
        return ResponseEntity.ok(movimientoService.findAllMovements());
    }

    //Consultar movimiento por id
    @GetMapping("/{id}")
    public ResponseEntity<?> findMovementById(@PathVariable UUID id) {
        return movimientoService.findMovementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Registrar movimiento
    @PostMapping("/registrar-movimiento")
    public ResponseEntity<Object> registerMovement(@RequestBody Movimiento movimiento) {
        try {
            movimientoService.saveMovement(movimiento);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Movimiento registrado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }

    //Modificar movimiento
    @PutMapping("/modificar-movimiento")
    public ResponseEntity<Object> updateMovement(@RequestBody Movimiento movimiento) {
        try {
            movimientoService.updateMovement(movimiento);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Movimiento modificado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }

    //Eliminar movimiento
    @DeleteMapping("/eliminar-movimiento/{id}")
    public ResponseEntity<Object> deleteMovement(@PathVariable UUID id) {
        try {
            movimientoService.deleteMovement(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Movimiento eliminado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }
}
