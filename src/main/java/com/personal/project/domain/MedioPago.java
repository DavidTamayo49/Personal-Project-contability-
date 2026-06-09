package com.personal.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
@Table(name = "mediopago")
public class MedioPago {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private String nombre;

    public MedioPago() {
    }

    public MedioPago(UUID id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
