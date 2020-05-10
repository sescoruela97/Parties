package com.sergiescoruela.parties.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Precio implements Parcelable {

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

    protected Precio(Parcel in) {
        nombreLocal = in.readString();
        descripcionLocal = in.readString();
        precio = in.readString();
    }

    public static final Creator<Precio> CREATOR = new Creator<Precio>() {
        @Override
        public Precio createFromParcel(Parcel in) {
            return new Precio(in);
        }

        @Override
        public Precio[] newArray(int size) {
            return new Precio[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombreLocal);
        parcel.writeString(descripcionLocal);
        parcel.writeString(precio);
    }
}
