package com.udi.proyectointegrador.modelo;

public class FormaAdquisicion {
    private int idFormaAdquisicion;
    private String nombre;
    private String descripcion;

    public FormaAdquisicion() {}

    public FormaAdquisicion(int idFormaAdquisicion, String nombre, String descripcion) {
        this.idFormaAdquisicion = idFormaAdquisicion;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdFormaAdquisicion() { return idFormaAdquisicion; }
    public void setIdFormaAdquisicion(int idFormaAdquisicion) { this.idFormaAdquisicion = idFormaAdquisicion; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}