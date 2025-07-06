package com.personal.project.domain;

import java.util.Date;

public class Movimiento {

    private Cliente cliente;
    private Date fecha;
    private String descripcion;
    private MedioPago medioPago;
    private int valor;
    private TipoMovimiento tipoMovimiento;

    public Movimiento() {
    }

    public Movimiento(Cliente cliente, Date fecha, String descripcion, MedioPago medioPago,
                      int valor, TipoMovimiento tipoMovimiento) {
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
}
