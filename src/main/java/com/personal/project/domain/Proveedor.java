package com.personal.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
@Table(name = "provedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nit;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "ciudad")
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
