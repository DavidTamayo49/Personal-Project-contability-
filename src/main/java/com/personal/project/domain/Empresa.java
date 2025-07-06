package com.personal.project.domain;

import java.util.UUID;

public class Empresa {

    private UUID id;
    private String nit;
    private String nombre;

    public Empresa() {
    }

    public Empresa(UUID id, String nit, String nombre, Ciudad ciudad) {
        this.id = id;
        this.nit = nit;
        this.nombre = nombre;
        this.ciudad = ciudad;
    }


    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    private Ciudad ciudad;
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
