package com.personal.project.controller;

import com.personal.project.domain.Proveedor;
import com.personal.project.service.ProveedorService;
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
@RequestMapping("api/v1/proveedor")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    //Consultar todos los proveedores
    @GetMapping
    public ResponseEntity<List<Proveedor>> findAllProviders() {
        return ResponseEntity.ok(proveedorService.getProviders());
    }

    //Registrar proveedor
    @PostMapping("/registrar-proveedor")
    public ResponseEntity<Object> registerProvider(@RequestBody Proveedor proveedor) {
        try {
            proveedorService.saveProvider(proveedor);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Proveedor registrado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }

    //Modificar proveedor
    @PutMapping("/modificar-proveedor")
    public ResponseEntity<Object> updateProvider(@RequestBody Proveedor proveedor) {
        try {
            proveedorService.updateProvider(proveedor);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Proveedor modificado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }

    //Eliminar proveedor
    @DeleteMapping("/eliminar-proveedor/{id}")
    public ResponseEntity<Object> deleteProvider(@PathVariable UUID id) {
        try {
            proveedorService.deleteProvider(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "Proveedor eliminado correctamente"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensaje", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "Ha ocurrido un error inesperado"));
        }
    }
}
