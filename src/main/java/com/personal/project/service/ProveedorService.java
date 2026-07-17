package com.personal.project.service;


import com.personal.project.domain.Proveedor;
import com.personal.project.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ProveedorService {

    private ProveedorRepository proveedorRepository;
    private CiudadService ciudadService;
    private static final Pattern NIT_PATTERN =
            Pattern.compile("^[0-9]{5,10}(-[0-9])?$");

    //Constructor
    public ProveedorService(ProveedorRepository proveedorRepository, CiudadService ciudadService) {
        this.proveedorRepository = proveedorRepository;
        this.ciudadService = ciudadService;
    }

    private void validateProvider(Proveedor proveedor) {
        if (proveedor.getNombre() == null || proveedor.getNit() == null) {
            throw new IllegalArgumentException("Los datos del proveedor no pueden ser nulos");
        }

        if (!NIT_PATTERN.matcher(proveedor.getNit()).matches()) {
            throw new IllegalArgumentException("El NIT no es valido");
        }

        if (proveedor.getCiudad() == null || proveedor.getCiudad().getId() == null) {
            throw new IllegalArgumentException("La ciudad debe ser obligatoria");
        }
    }

    //Consultar proveedores
    public List<Proveedor> getProviders() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> findProviderById(UUID id) {
        return proveedorRepository.findById(id);
    }


    //Eliminar proveedor
    public void deleteProvider(UUID uuid) {
        proveedorRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("El proveedor que deseas eliminar no existe o ya ha sido eliminado"));
        proveedorRepository.deleteById(uuid);
    }

    //Registrar proveedor
    public void saveProvider(Proveedor proveedor) {
        validateProvider(proveedor);

        proveedor.setCiudad(ciudadService.findById(proveedor.getCiudad().getId()));
        proveedorRepository.save(proveedor);
    }

    //Modificar proveedor
    public void updateProvider(Proveedor newProvider) {
        Optional<Proveedor> providerOptional = proveedorRepository.findById(newProvider.getId());

        if (providerOptional.isPresent()) {
            validateProvider(newProvider);

            Proveedor provider = providerOptional.get();
            provider.setNombre(newProvider.getNombre());
            provider.setNit(newProvider.getNit());
            provider.setCiudad(ciudadService.findById(newProvider.getCiudad().getId()));
            proveedorRepository.save(provider);
        } else {
            throw new IllegalArgumentException("Proveedor no fue encontrado");
        }
    }

}
