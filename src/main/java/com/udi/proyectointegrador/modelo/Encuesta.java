package com.udi.proyectointegrador.modelo;

import java.sql.Date;

public class Encuesta {
    private int idEncuesta;
    private String codigoEncuesta;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    private int idHogar;
    private int idUsuario;

    public Encuesta() {}

    public Encuesta(int idEncuesta, String codigoEncuesta, Date fechaInicio, Date fechaFin, String estado, int idHogar, int idUsuario) {
        this.idEncuesta = idEncuesta;
        this.codigoEncuesta = codigoEncuesta;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.idHogar = idHogar;
        this.idUsuario = idUsuario;
    }

    // Getters y Setters
    public int getIdEncuesta() { return idEncuesta; }
    public void setIdEncuesta(int idEncuesta) { this.idEncuesta = idEncuesta; }

    public String getCodigoEncuesta() { return codigoEncuesta; }
    public void setCodigoEncuesta(String codigoEncuesta) { this.codigoEncuesta = codigoEncuesta; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public int getIdHogar() { return idHogar; }
    public void setIdHogar(int idHogar) { this.idHogar = idHogar; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
}