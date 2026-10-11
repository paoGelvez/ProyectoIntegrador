package com.udi.proyectointegrador.modelo;

public class Hogar {
    private int idHogar;
    private String nombreFamilia;
    private String direccion;
    private String ciudad;
    private String barrio;
    private String zona;
    private int numeroIntegrantes;
    private String jefeHogar;
    private int numeroTrabajadores;
    private String responsableCompras;
    private double ingresePromMon;
    private double costoNeceBasicas;

    public Hogar() {}

    public Hogar(int idHogar, String nombreFamilia, String direccion, String ciudad, 
                 String barrio, String zona, int numeroIntegrantes, String jefeHogar, 
                 int numeroTrabajadores, String responsableCompras, double ingresePromMon, 
                 double costoNeceBasicas) {
        this.idHogar = idHogar;
        this.nombreFamilia = nombreFamilia;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.barrio = barrio;
        this.zona = zona;
        this.numeroIntegrantes = numeroIntegrantes;
        this.jefeHogar = jefeHogar;
        this.numeroTrabajadores = numeroTrabajadores;
        this.responsableCompras = responsableCompras;
        this.ingresePromMon = ingresePromMon;
        this.costoNeceBasicas = costoNeceBasicas;
    }

    // Getters y Setters
    public int getIdHogar() { return idHogar; }
    public void setIdHogar(int idHogar) { this.idHogar = idHogar; }

    public String getNombreFamilia() { return nombreFamilia; }
    public void setNombreFamilia(String nombreFamilia) { this.nombreFamilia = nombreFamilia; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getBarrio() { return barrio; }
    public void setBarrio(String barrio) { this.barrio = barrio; }

    public String getZona() { return zona; }
    public void setZona(String zona) { this.zona = zona; }

    public int getNumeroIntegrantes() { return numeroIntegrantes; }
    public void setNumeroIntegrantes(int numeroIntegrantes) { this.numeroIntegrantes = numeroIntegrantes; }

    public String getJefeHogar() { return jefeHogar; }
    public void setJefeHogar(String jefeHogar) { this.jefeHogar = jefeHogar; }

    public int getNumeroTrabajadores() { return numeroTrabajadores; }
    public void setNumeroTrabajadores(int numeroTrabajadores) { this.numeroTrabajadores = numeroTrabajadores; }

    public String getResponsableCompras() { return responsableCompras; }
    public void setResponsableCompras(String responsableCompras) { this.responsableCompras = responsableCompras; }

    public double getIngresePromMon() { return ingresePromMon; }
    public void setIngresePromMon(double ingresePromMon) { this.ingresePromMon = ingresePromMon; }

    public double getCostoNeceBasicas() { return costoNeceBasicas; }
    public void setCostoNeceBasicas(double costoNeceBasicas) { this.costoNeceBasicas = costoNeceBasicas; }
}