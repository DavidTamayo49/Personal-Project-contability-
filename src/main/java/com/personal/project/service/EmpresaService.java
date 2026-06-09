package com.personal.project.service;

import com.personal.project.domain.Empresa;
import com.personal.project.repository.CiudadRepository;
import com.personal.project.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class EmpresaService {

    private EmpresaRepository empresaRepository;
    private static final Pattern NIT_PATTERN = Pattern.compile("^[0-9]{5,10}(-[0-9])?$");
    private CiudadService ciudadService;

    public EmpresaService(EmpresaRepository empresaRepository, CiudadService ciudadService) {
        this.empresaRepository = empresaRepository;
        this.ciudadService = ciudadService;
    }

    //Consultar empresa
    public Optional<Empresa> findEnterpriseById(UUID id) {
        return empresaRepository.findById(id);
    }

    //Registrar empresa
    public void saveEmpresa(Empresa empresa) {


        //Verificar que no sean vacios
        if (empresa.getNombre() == null || empresa.getNit() == null  ) {
            throw new IllegalArgumentException("Los datos de la empresa  no pueden ser nulos");
        }

        //Formato de nit
        if (!NIT_PATTERN.matcher(empresa.getNit()).matches()) {
            throw new IllegalArgumentException("El NIT no es valido");
        }


        if (empresa.getCiudad() == null || empresa.getCiudad().getId() == null) {
            throw new IllegalArgumentException("La ciudad debe ser obligatoria");
        }

        empresa.setCiudad(ciudadService.findById(empresa.getCiudad().getId()));

        empresaRepository.save(empresa);
    }



}
