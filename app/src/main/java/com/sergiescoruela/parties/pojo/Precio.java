package com.sergiescoruela.parties.pojo;

public class Precio {

    String nombreLocal;
    String descripcionLocal;
    String precio;

    public Precio() {
    }

    public Precio(String nombreLocal, String descripcionLocal, String precio) {
        this.nombreLocal = nombreLocal;
        this.descripcionLocal = descripcionLocal;
        this.precio = precio;
    }

    public String getNombreLocal() {
        return nombreLocal;
    }

    public void setNombreLocal(String nombreLocal) {
        this.nombreLocal = nombreLocal;
    }

    public String getDescripcionLocal() {
        return descripcionLocal;
    }

    public void setDescripcionLocal(String descripcionLocal) {
        this.descripcionLocal = descripcionLocal;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
