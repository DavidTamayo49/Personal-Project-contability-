package com.personal.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "deudor")
public class Deudor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "cliente")
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
