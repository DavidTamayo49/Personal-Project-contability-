package com.personal.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nit;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "ciudad")
    private Ciudad ciudad;

    public Empresa() {

    }

    public Ciudad getCiudad() {
        return ciudad;
    }


    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }



    public Empresa(UUID id, String nit, String nombre, Ciudad ciudad) {
        this.id = id;
        this.nit = nit;
        this.nombre = nombre;
    }

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
