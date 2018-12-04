package com.example.miguel.joaquinsotoautomoviles.clases;

public class Coche {

    //Atributos de la Clase Coche
    private int ID_CocheNuevo;
    private String marca;
    private String modelo;
    private String descripcion;
    private int precio;
    private byte[] foto;

    //Constructor
    public Coche(int ID_CocheNuevo, String marca, String modelo, String descripcion, int precio, byte[] foto) {
        this.ID_CocheNuevo = ID_CocheNuevo;
        this.marca = marca;
        this.modelo = modelo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.foto = foto;
    }

    public int getID_CocheNuevo() {
        return ID_CocheNuevo;
    }

    public void setID_CocheNuevo(int ID_CocheNuevo) {
        this.ID_CocheNuevo = ID_CocheNuevo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}