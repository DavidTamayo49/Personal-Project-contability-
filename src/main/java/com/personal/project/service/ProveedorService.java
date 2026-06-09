package com.personal.project.service;


import com.personal.project.domain.Ciudad;
import com.personal.project.domain.Proveedor;
import com.personal.project.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ProveedorService {

    private ProveedorRepository proveedorRepository;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[0-9]{5,10}(-[0-9])?$");


    //Constructor
    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    //Consultar proveedor
    public List<Proveedor> getProviders() {
        return proveedorRepository.findAll();
    }

    //Eliminar proveedor
    public void deleteProvider(UUID uuid) {
        proveedorRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("El proveedor que deseas eliminar no existe o ya ha sido eliminado"));
        proveedorRepository.deleteById(uuid);
    }

    //Registrar proveedor
    public void saveProvider(Proveedor proveedor) {
        //Verificar que no sean vacios
        if (proveedor.getNombre() == null || proveedor.getNit() == null  ) {
            throw new IllegalArgumentException("Los datos del proveedor  no pueden ser nulos");
        }

        //Formato de nit
        if (!EMAIL_PATTERN.matcher(proveedor.getNit()).matches()) {
            throw new IllegalArgumentException("El NIT no es valido");
        }

        //Si el id esta vacio asignar uno
        if (proveedor.getId() == null){
            proveedor.setId(UUID.randomUUID());
        }

        //Que la ciudad no este vacio
        if (proveedor.getCiudad() == null){
            throw new IllegalArgumentException("La ciudad debe ser obligatoria ");
        }


        proveedorRepository.save(proveedor);
    }



}
