package com.udi.proyectointegrador.modelo;

public class LugarCompra {
    private int idLugarCompra;
    private String nombre;
    private String tipo;

    public LugarCompra() {}

    public LugarCompra(int idLugarCompra, String nombre, String tipo) {
        this.idLugarCompra = idLugarCompra;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public int getIdLugarCompra() { return idLugarCompra; }
    public void setIdLugarCompra(int idLugarCompra) { this.idLugarCompra = idLugarCompra; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}