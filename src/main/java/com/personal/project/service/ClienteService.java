package com.personal.project.service;


import com.personal.project.domain.Cliente;
import com.personal.project.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ClienteService {
    private EmpresaService empresaService;
    private final ClienteRepository clienteRepository;
    private static final Pattern DOC_PATTERN =
            Pattern.compile("^[1-9][0-9]{5,9}$");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^\\S+@\\S+\\.\\S+$");

    public ClienteService(ClienteRepository clienteRepository, EmpresaService empresaService) {
        this.clienteRepository = clienteRepository;
        this.empresaService = empresaService;
    }


    private void validateClient(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getCorreo() == null || cliente.getDocumento() == null) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        //Formato numero de documento
        if (!DOC_PATTERN.matcher(cliente.getDocumento()).matches()) {
            throw new IllegalArgumentException("El documento no tiene un formato valido");
        }

        //Formato correo
        if (!EMAIL_PATTERN.matcher(cliente.getCorreo()).matches()) {
            throw new IllegalArgumentException("El correo electronico no tiene un formato valido");
        }

        if (cliente.getEmpresa() == null || cliente.getEmpresa().getId() == null) {
            throw new IllegalArgumentException("La empresa debe ser obligatoria");
        }
    }


    //Registar cliente
    public void saveClient(Cliente cliente) {

        validateClient(cliente);

        cliente.setEmpresa(empresaService.findEnterpriseById(cliente.getEmpresa().getId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa no fue encontrada")));

        clienteRepository.save(cliente);

    }


    //Consultar los clientes
    public List<Cliente> findAllClients() {
        return clienteRepository.findAll();
    }

    //Consultar cliente por id
    public Optional<Cliente> findClientById(UUID id) {
        return clienteRepository.findById(id);
    }
    
    //Modificar cliente
    public void updateClient(Cliente newClient) {
        Optional<Cliente> clientOptional = clienteRepository.findById(newClient.getId());

        if (clientOptional.isPresent()) {
            validateClient(newClient);

            Cliente client = clientOptional.get();
            client.setNombre(newClient.getNombre());
            client.setCorreo(newClient.getCorreo());
            client.setDocumento(newClient.getDocumento());
            client.setEmpresa(empresaService.findEnterpriseById(newClient.getEmpresa().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Empresa no fue encontrada")));
            clienteRepository.save(client);


        } else {
            throw new IllegalArgumentException("Cliente no fue encontrado");
        }
    }



}
