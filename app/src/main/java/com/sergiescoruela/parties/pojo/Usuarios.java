package com.sergiescoruela.parties.pojo;

import java.util.Date;

public class Usuarios {


    String nombre;
    String id;
    String contraseña;
    String correo;
    String fechaNacimiento;
    String imagen;

    public Usuarios() {
    }

    public Usuarios(String nombre,  String correo, String contraseña, String fechaNacimiento) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;

    }

    public Usuarios(String nombre, String id, String contraseña, String correo, String fechaNacimiento, String imagen) {
        this.nombre = nombre;
        this.id = id;
        this.contraseña = contraseña;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
