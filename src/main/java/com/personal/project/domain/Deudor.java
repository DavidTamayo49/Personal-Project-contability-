package com.personal.project.domain;

import java.util.UUID;

public class Deudor {

    private UUID id;
    private Cliente cliente;
    private int valordeuda;


    public Deudor() {
        //Is empty because JPA implementation
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getValordeuda() {
        return valordeuda;
    }

    public void setValordeuda(int valordeuda) {
        this.valordeuda = valordeuda;
    }
}
