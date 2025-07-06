package com.personal.project.domain;

import java.util.UUID;

public class Ciudad {

    private UUID id;
    private String nombre;

    public Ciudad(UUID id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Ciudad() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
