package com.personal.project.controller;


import com.personal.project.domain.Cliente;
import com.personal.project.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //Consultar todas los clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> findAllClients() {
        return ResponseEntity.ok(clienteService.findAllClients());
    }

    @PostMapping("/registrar-cliente")
    public ResponseEntity<Object> registerClient(@RequestBody Cliente cliente) {
        try {
            clienteService.saveClient(cliente);
            return ResponseEntity.
                    status(HttpStatus.OK).body(Map.of("mensaje", "cliente registrado correctamente"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", e.getMessage()));
        }
    }

    //Modificar cliente
    @PutMapping("/modificar-cliente")
    public ResponseEntity<Object> updateClient(@RequestBody Cliente cliente) {
        try {
            clienteService.updateClient(cliente);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Map.of("mensaje", "cliente modificado correctamente"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", e.getMessage()));
        }
    }

}
