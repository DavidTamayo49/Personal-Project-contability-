package com.personal.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
@Table(name = "balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "mediopago")
    private MedioPago medioPago;

    private int valor;

    public Balance(UUID id, int valor, MedioPago medioPago) {
        this.id = id;
        this.valor = valor;
        this.medioPago = medioPago;
    }

    public Balance() {
    }

}
