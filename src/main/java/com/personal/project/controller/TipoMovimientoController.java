package com.personal.project.controller;

import com.personal.project.domain.TipoMovimiento;
import com.personal.project.service.TipoMovimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/tipos-movimiento")
public class TipoMovimientoController {

    private final TipoMovimientoService tipoMovimientoService;

    public TipoMovimientoController(TipoMovimientoService tipoMovimientoService) {
        this.tipoMovimientoService = tipoMovimientoService;
    }

    @GetMapping
    public ResponseEntity<List<TipoMovimiento>> findAllTiposMovimiento() {
        return ResponseEntity.ok(tipoMovimientoService.findTiposMovimiento());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findTipoMovimientoById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(tipoMovimientoService.findTipoMovimientoById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }
}
