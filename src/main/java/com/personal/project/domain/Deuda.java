package com.personal.project.domain;

import java.util.UUID;

public class Deuda {

    private UUID id;
    private int valor;
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
