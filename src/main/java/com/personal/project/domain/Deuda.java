package com.personal.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "deuda")
public class Deuda {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int valor;

    @OneToOne
    @JoinColumn(name = "proveedor")
    private Proveedor proveedor;


    public Deuda() {
    }

    public Deuda(UUID id, int valor, Proveedor proveedor) {
        this.id = id;
        this.valor = valor;
        this.proveedor = proveedor;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
