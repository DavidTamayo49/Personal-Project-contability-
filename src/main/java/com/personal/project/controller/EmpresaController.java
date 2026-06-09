package com.personal.project.controller;

import com.personal.project.domain.Empresa;
import com.personal.project.service.EmpresaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/empresa")
public class EmpresaController {

    private EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/registrar-empresa")
    public ResponseEntity<Object> registerEnterprise(@RequestBody Empresa empresa) {
        try {
            empresaService.saveEmpresa(empresa);
            return ResponseEntity.
                    status(HttpStatus.OK).body(Map.of("mensaje", "Empresa registrada exitosamente"));
        } catch (Exception e) {
             e.printStackTrace();
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("mensaje", e.getMessage()));
}
    }

    //Consultar informacion de la empresa
    @GetMapping("/{id}")
    public ResponseEntity<?> getEnterpriseById(@PathVariable UUID id) {
        return empresaService.findEnterpriseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}






