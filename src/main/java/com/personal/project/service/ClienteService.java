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
    private ClienteRepository clienteRepository;
    private static final Pattern DOC_PATTERN =
            Pattern.compile("^[1-9][0-9]{5,9}$");

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //Registar cliente
    public void saveClient(Cliente cliente) {

        if (cliente.getId() == null){
            cliente.setId(UUID.randomUUID());
        }

        if (cliente.getNombre() == null || cliente.getCorreo() == null || cliente.getDocumento() == null){
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        //Formato numero de documento
        if (!DOC_PATTERN.matcher(cliente.getDocumento()).matches()){
            throw new IllegalArgumentException("El documento no tiene un formato valido");
        }

        clienteRepository.save(cliente);

    }


    //Consultar los clientes
    public List<Cliente> findAllClients() {
        return clienteRepository.findAll();
    }
    
    //Modificar cliente
    public void updateClient(Cliente newClient) {
        Optional<Cliente> clientOptional = clienteRepository.findById(newClient.getId());

        if (clientOptional.isPresent()) {
            Cliente client = clientOptional.get();
            client.setNombre(newClient.getNombre());
            client.setCorreo(newClient.getCorreo());
            client.setDocumento(newClient.getDocumento());
            client.setEmpresa(newClient.getEmpresa());
            clienteRepository.save(client);


        } else {
            throw new IllegalArgumentException("Cliente no fue encontrado");
        }
    }

}
