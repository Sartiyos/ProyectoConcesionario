package com.example.miguel.joaquinsotoautomoviles.clases;

public class Extras {

    private int ID_Extra;
    private String nombre;
    private int precio;

    public Extras(int ID_Extra, String nombre, int precio) {
        this.ID_Extra = ID_Extra;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Extras(String nombre, int precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Extras() {

    }

    public int getID_Extra() {
        return ID_Extra;
    }

    public void setID_Extra(int ID_Extra) {
        this.ID_Extra = ID_Extra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
