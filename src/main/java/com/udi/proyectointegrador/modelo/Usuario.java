package com.udi.proyectointegrador.modelo;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String identificacion;
    private String rol;
    private String contrasena;

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String apellido, String identificacion, String rol, String contrasena) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.rol = rol;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}