package com.sergiescoruela.parties.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Precio implements Parcelable {

    private String tipoEntrada;
    private String descripcionEntrada;
    private String precio;

    public Precio() {
    }

    public Precio(String tipoEntrada, String descripcionEntrada, String precio) {
        this.tipoEntrada = tipoEntrada;
        this.descripcionEntrada = descripcionEntrada;
        this.precio = precio;
    }

    protected Precio(Parcel in) {
        tipoEntrada = in.readString();
        descripcionEntrada = in.readString();
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

    public String getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(String tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public String getDescripcionEntrada() {
        return descripcionEntrada;
    }

    public void setDescripcionEntrada(String descripcionEntrada) {
        this.descripcionEntrada = descripcionEntrada;
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
        parcel.writeString(tipoEntrada);
        parcel.writeString(descripcionEntrada);
        parcel.writeString(precio);
    }
}
