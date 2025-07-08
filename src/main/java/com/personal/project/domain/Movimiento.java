package com.personal.project.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "movimiento")
public class Movimiento {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date fecha;
    private String descripcion;
    private int valor;

    @OneToOne
    @JoinColumn(name = "mediopago")
    private MedioPago medioPago;

    @OneToOne
    @JoinColumn(name = "tipomovimiento")
    private TipoMovimiento tipoMovimiento;

    @OneToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;
    public Movimiento() {
    }

    public Movimiento(UUID id, Cliente cliente, Date fecha, String descripcion, MedioPago medioPago,
                      int valor, TipoMovimiento tipoMovimiento) {
        this.id = id;
        this.cliente = cliente;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.medioPago = medioPago;
        this.valor = valor;
        this.tipoMovimiento = tipoMovimiento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MedioPago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
