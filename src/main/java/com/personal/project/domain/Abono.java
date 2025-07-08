package com.personal.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "abono")
public class Abono {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private int valorabono;

    @ManyToOne
    @JoinColumn(name = "deudor")
    private Deudor deudor;

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
