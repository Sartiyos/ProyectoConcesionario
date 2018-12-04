package com.example.miguel.joaquinsotoautomoviles.basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.miguel.joaquinsotoautomoviles.clases.Coche;
import com.example.miguel.joaquinsotoautomoviles.clases.Extras;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

public class DatabaseAccess {

    private SQLiteAssetHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    //Constructor
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    //Método para obtener la estancia
    public static DatabaseAccess getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    //Método para abrir la conexión con la Base de Datos
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    //Método para cerrar la conexión con la Base de Datos
    public void close() {
        if(database != null) {
            this.database.close();
        }
    }


    //Método que devuelve todos las Personas de la Tabla persona
    public ArrayList<Coche> obtenerCochesNuevos() {
        Cursor c;

        //Creamos un ArrayList para guardar todos los datos de las Personas
        ArrayList<Coche> listadoCochesNuevos = new ArrayList<>();

        //Consultamos a la Base de Datos y guardamos el resultado en el Cursor
        c = database.rawQuery("SELECT * FROM CochesNuevos", null);

        //Nos colocamos al principio del Cursor
        if(c.moveToFirst()) {

            do {
                //Vamos añadiendo Personas al ArrayList
                listadoCochesNuevos.add(new Coche(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getInt(4),
                        c.getBlob(5)));
            } while(c.moveToNext());

        }

        //Cerramos el Cursor
        c.close();

        //Devolvemos la lista de Personas que hay en la Tabla Persona
        return listadoCochesNuevos;
    }


    //Método que devuelve todos las Personas de la Tabla persona
    public ArrayList<Coche> obtenerCochesOcasion() {
        Cursor c;

        //Creamos un ArrayList para guardar todos los datos de las Personas
        ArrayList<Coche> listadoCochesOcasion = new ArrayList<>();

        //Consultamos a la Base de Datos y guardamos el resultado en el Cursor
        c = database.rawQuery("SELECT * FROM CochesOcasion", null);

        //Nos colocamos al principio del Cursor
        if(c.moveToFirst()) {

            do {
                //Vamos añadiendo Personas al ArrayList
                listadoCochesOcasion.add(new Coche(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getInt(4),
                        c.getBlob(5)));
            } while(c.moveToNext());

        }

        //Cerramos el Cursor
        c.close();

        //Devolvemos la lista de Personas que hay en la Tabla Persona
        return listadoCochesOcasion;
    }

    //Método que devuelve todos las Personas de la Tabla persona
    public ArrayList<Extras> obtenerExtras() {
        Cursor c;

        //Creamos un ArrayList para guardar todos los datos de las Personas
        ArrayList<Extras> listadoExtras = new ArrayList<>();

        //Consultamos a la Base de Datos y guardamos el resultado en el Cursor
        c = database.rawQuery("SELECT * FROM Extras", null);

        //Nos colocamos al principio del Cursor
        if(c.moveToFirst()) {

            do {
                //Vamos añadiendo Personas al ArrayList
                listadoExtras.add(new Extras(
                        c.getInt(0),
                        c.getString(1),
                        c.getInt(2)));
            } while(c.moveToNext());

        }

        //Cerramos el Cursor
        c.close();

        //Devolvemos la lista de Personas que hay en la Tabla Persona
        return listadoExtras;
    }

    //Método que devuelve todos las Personas de la Tabla persona
    public ArrayList<Coche> obtenerDatosCocheNuevo(int codigoCoche) {

        //Convertimos el entero en String
        String codCoche[] = new String[]{String.valueOf(codigoCoche)};

        Cursor c;

        //Creamos un ArrayList para guardar todos los datos de las Personas
        ArrayList<Coche> listadoCochesNuevos = new ArrayList<>();

        //Consultamos a la Base de Datos y guardamos el resultado en el Cursor
        c = database.rawQuery("SELECT * FROM CochesNuevos WHERE ID_CocheNuevo = ?", codCoche);

        //Nos colocamos al principio del Cursor
        if(c.moveToFirst()) {

            do {
                //Vamos añadiendo Personas al ArrayList
                listadoCochesNuevos.add(new Coche(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getInt(4),
                        c.getBlob(5)));
            } while(c.moveToNext());
        }

        //Cerramos el Cursor
        c.close();

        //Devolvemos la lista de Personas que hay en la Tabla Persona
        return listadoCochesNuevos;
    }

    //Método que devuelve los datos de un coche de ocasion
    public ArrayList<Coche> obtenerDatosCocheOcasion(int codigoCoche) {

        //Convertimos el entero en String
        String codCoche[] = new String[]{String.valueOf(codigoCoche)};

        Cursor c;

        //Creamos un ArrayList para guardar todos los datos del coche de ocasion
        ArrayList<Coche> detallesCocheOcasion = new ArrayList<>();

        //Consultamos a la Base de Datos y guardamos el resultado en el Cursor
        c = database.rawQuery("SELECT * FROM CochesOcasion WHERE ID_CocheOcasion = ?", codCoche);

        //Nos colocamos al principio del Cursor
        if(c.moveToFirst()) {

            do {
                //Vamos añadiendo detalles del coche de ocasion
                detallesCocheOcasion.add(new Coche(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getInt(4),
                        c.getBlob(5)));
            } while(c.moveToNext());
        }

        //Cerramos el Cursor
        c.close();

        //Devolvemos la lista que contiene los detalles del coche
        return detallesCocheOcasion;
    }

    //Método para guardar un nuevo Extra en la Base de Datos
    public void guardarExtra(Extras extra) {
        ContentValues datos = new ContentValues();
        datos.put("Nombre", extra.getNombre());
        datos.put("Precio", extra.getPrecio());

        database.insert("Extras", null, datos);
        database.close();
    }
}