package com.personal.project.domain;

import java.util.UUID;

public class Proveedor {

    private UUID id;
    private String nit;
    private String nombre;
    private Ciudad ciudad;

    public Proveedor() {

    }

    public Proveedor(UUID id, String nit, String nombre, Ciudad ciudad) {
        this.id = id;
        this.nit = nit;
        this.nombre = nombre;
        this.ciudad = ciudad;
    }


}
