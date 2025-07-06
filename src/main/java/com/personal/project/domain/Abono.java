package com.personal.project.domain;

import java.util.UUID;

public class Abono {

    private UUID id;
    private Deudor deudor;
    private int valorabono;

    public Abono() {
    }

    public Abono(UUID id, Deudor deudor, int valorabono) {
        this.id = id;
        this.deudor = deudor;
        this.valorabono = valorabono;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Deudor getDeudor() {
        return deudor;
    }

    public void setDeudor(Deudor deudor) {
        this.deudor = deudor;
    }

    public int getValorabono() {
        return valorabono;
    }

    public void setValorabono(int valorabono) {
        this.valorabono = valorabono;
    }
}
