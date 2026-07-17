package com.personal.project.controller;

import com.personal.project.domain.Deuda;
import com.personal.project.service.DeudaService;
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
@RequestMapping("api/v1/deuda")
public class DeudaController {

    private final DeudaService deudaService;

    public DeudaController(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    @GetMapping
    public ResponseEntity<List<Deuda>> findAllDebts() {
        return ResponseEntity.ok(deudaService.findAllDebts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findDebtById(@PathVariable UUID id) {
        return deudaService.findDebtById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/registrar-deuda")
    public ResponseEntity<Object> registerDebt(@RequestBody Deuda deuda) {
        try {
            deudaService.saveDebt(deuda);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Deuda registrada correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return unexpectedError();
        }
    }

    @PutMapping("/modificar-deuda")
    public ResponseEntity<Object> updateDebt(@RequestBody Deuda deuda) {
        try {
            deudaService.updateDebt(deuda);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Deuda modificada correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return unexpectedError();
        }
    }

    @PostMapping("/pagar-deuda/{debtId}/medio-pago/{payMethodId}")
    public ResponseEntity<Object> payDebt(@PathVariable UUID debtId,
                                          @PathVariable UUID payMethodId) {
        try {
            deudaService.payDebt(debtId, payMethodId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Deuda pagada correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return unexpectedError();
        }
    }

    @DeleteMapping("/eliminar-deuda/{id}")
    public ResponseEntity<Object> deleteDebt(@PathVariable UUID id) {
        try {
            deudaService.deleteDebt(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Deuda eliminada correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return unexpectedError();
        }
    }

    private ResponseEntity<Object> unexpectedError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
    }
}
