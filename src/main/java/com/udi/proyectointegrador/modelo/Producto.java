package com.udi.proyectointegrador.modelo;

public class Producto {
    private int idProducto;
    private String codigoProducto;
    private String nombre;
    private boolean esNecesBasica;
    private int idCategoria;
    private int idUnidadMedida;

    public Producto() {}

    public Producto(int idProducto, String codigoProducto, String nombre, boolean esNecesBasica, int idCategoria, int idUnidadMedida) {
        this.idProducto = idProducto;
        this.codigoProducto = codigoProducto;
        this.nombre = nombre;
        this.esNecesBasica = esNecesBasica;
        this.idCategoria = idCategoria;
        this.idUnidadMedida = idUnidadMedida;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getCodigoProducto() { return codigoProducto; }
    public void setCodigoProducto(String codigoProducto) { this.codigoProducto = codigoProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public boolean isEsNecesBasica() { return esNecesBasica; }
    public void setEsNecesBasica(boolean esNecesBasica) { this.esNecesBasica = esNecesBasica; }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public int getIdUnidadMedida() { return idUnidadMedida; }
    public void setIdUnidadMedida(int idUnidadMedida) { this.idUnidadMedida = idUnidadMedida; }
}