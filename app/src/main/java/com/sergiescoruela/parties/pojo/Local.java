package com.sergiescoruela.parties.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Local implements Parcelable {

    private String nombre;
    private String descripcion;
    private ArrayList<String> imagen;
    private String hora;
    private String musica;
    private String edad;
    private String direccion;
    private ArrayList<Precio> precio;


    public Local() {
    }

    public Local(String nombre, String descripcion, String direccion ,ArrayList<String> imgagen, String hora, String musica, String edad, ArrayList<Precio> precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.imagen = imgagen;
        this.hora = hora;
        this.musica = musica;
        this.edad = edad;
        this.precio = precio;
    }


    protected Local(Parcel in) {
        nombre = in.readString();
        descripcion = in.readString();
        direccion = in.readString();
        imagen = in.createStringArrayList();
        hora = in.readString();
        musica = in.readString();
        edad = in.readString();
        precio = in.createTypedArrayList(Precio.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(descripcion);
        dest.writeString(direccion);
        dest.writeStringList(imagen);
        dest.writeString(hora);
        dest.writeString(musica);
        dest.writeString(edad);
        dest.writeTypedList(precio);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Local> CREATOR = new Creator<Local>() {
        @Override
        public Local createFromParcel(Parcel in) {
            return new Local(in);
        }

        @Override
        public Local[] newArray(int size) {
            return new Local[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }



    public String getDireccion() {
        return direccion;
    }


    public ArrayList<String> getImagen() {
        return imagen;
    }

    public void setImagen(ArrayList<String> imgagen) {
        this.imagen = imgagen;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMusica() {
        return musica;
    }

    public void setMusica(String musica) {
        this.musica = musica;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public ArrayList<Precio> getListaPrecios() {
        return precio;
    }

    public void setListaPrecios(ArrayList<Precio> precio) {
        this.precio = precio;
    }

    public ArrayList<Precio> getPrecio() {
        return precio;
    }

    public void setPrecio(ArrayList<Precio> precio) {
        this.precio = precio;
    }

    @NonNull
    @Override
    public String toString() {
        return "TAMAÑO PRECIOS: "+imagen.size();
    }
}