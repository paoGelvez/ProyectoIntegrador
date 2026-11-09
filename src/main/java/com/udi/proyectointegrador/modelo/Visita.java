package com.udi.proyectointegrador.modelo;

import java.sql.Date;

public class Visita {
    private int idVisita;
    private int numeroDia;
    private Date fechaVisita;
    private String estadoVisita;
    private String observaciones;
    private int idEncuesta;

    public Visita() {}

    public Visita(int idVisita, int numeroDia, Date fechaVisita, String estadoVisita, String observaciones, int idEncuesta) {
        this.idVisita = idVisita;
        this.numeroDia = numeroDia;
        this.fechaVisita = fechaVisita;
        this.estadoVisita = estadoVisita;
        this.observaciones = observaciones;
        this.idEncuesta = idEncuesta;
    }

    // Getters y Setters
    public int getIdVisita() { return idVisita; }
    public void setIdVisita(int idVisita) { this.idVisita = idVisita; }

    public int getNumeroDia() { return numeroDia; }
    public void setNumeroDia(int numeroDia) { this.numeroDia = numeroDia; }

    public Date getFechaVisita() { return fechaVisita; }
    public void setFechaVisita(Date fechaVisita) { this.fechaVisita = fechaVisita; }

    public String getEstadoVisita() { return estadoVisita; }
    public void setEstadoVisita(String estadoVisita) { this.estadoVisita = estadoVisita; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public int getIdEncuesta() { return idEncuesta; }
    public void setIdEncuesta(int idEncuesta) { this.idEncuesta = idEncuesta; }
}