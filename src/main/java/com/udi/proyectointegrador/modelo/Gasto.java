package com.udi.proyectointegrador.modelo;

import java.sql.Date;

public class Gasto {
    private int idGasto;
    private Date fechaGasto;
    private double cantidad;
    private double valorUnitario;
    private double valorTotal;
    private String estadoGasto;
    private int idVisita;
    private int idProducto;
    private Integer idLugarCompra; // Integer para permitir valores nulos si aplica
    private int idFormaAdquisicion;

    public Gasto() {}

    public Gasto(int idGasto, Date fechaGasto, double cantidad, double valorUnitario, double valorTotal, String estadoGasto, int idVisita, int idProducto, Integer idLugarCompra, int idFormaAdquisicion) {
        this.idGasto = idGasto;
        this.fechaGasto = fechaGasto;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
        this.estadoGasto = estadoGasto;
        this.idVisita = idVisita;
        this.idProducto = idProducto;
        this.idLugarCompra = idLugarCompra;
        this.idFormaAdquisicion = idFormaAdquisicion;
    }

    public int getIdGasto() { return idGasto; }
    public void setIdGasto(int idGasto) { this.idGasto = idGasto; }

    public Date getFechaGasto() { return fechaGasto; }
    public void setFechaGasto(Date fechaGasto) { this.fechaGasto = fechaGasto; }

    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }

    public double getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(double valorUnitario) { this.valorUnitario = valorUnitario; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public String getEstadoGasto() { return estadoGasto; }
    public void setEstadoGasto(String estadoGasto) { this.estadoGasto = estadoGasto; }

    public int getIdVisita() { return idVisita; }
    public void setIdVisita(int idVisita) { this.idVisita = idVisita; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public Integer getIdLugarCompra() { return idLugarCompra; }
    public void setIdLugarCompra(Integer idLugarCompra) { this.idLugarCompra = idLugarCompra; }

    public int getIdFormaAdquisicion() { return idFormaAdquisicion; }
    public void setIdFormaAdquisicion(int idFormaAdquisicion) { this.idFormaAdquisicion = idFormaAdquisicion; }
}