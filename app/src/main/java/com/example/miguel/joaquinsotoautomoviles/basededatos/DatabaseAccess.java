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

    //Atributos de la clase
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

    //METODOS PARA LOS COCHES

    //Método que devuelve Coches Nuevos o de Ocasion dependiendo del entero
    public ArrayList<Coche> obtenerCoches(int valor) {
        Cursor c;
        ArrayList<Coche> listadoCoches = new ArrayList<>();

        //Coches Nuevos
        if(valor == 1) {
            //Consultamos a la Base de Datos y guardamos el resultado en el Cursor
            c = database.rawQuery("SELECT * FROM CochesNuevos", null);

            if(c.moveToFirst()) { //Nos colocamos al principio del Cursor

                do { //Vamos añadiendo al ArrayList los Coches con sus valores
                    listadoCoches.add(new Coche(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getInt(4),
                            c.getBlob(5)));
                } while(c.moveToNext());
            }
        }

        //Coches Ocasión
        else {
            //Consultamos a la Base de Datos y guardamos el resultado en el Cursor
            c = database.rawQuery("SELECT * FROM CochesOcasion", null);

            if(c.moveToFirst()) { //Nos colocamos al principio del Cursor

                do { //Vamos añadiendo al ArrayList los Coches con sus valores
                    listadoCoches.add(new Coche(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getInt(4),
                            c.getBlob(5)));
                } while(c.moveToNext());
            }
        }

        c.close(); //Cerramos el Cursor
        return listadoCoches; //Devolvemos la lista de Coches
    }

    //Método que devuelve un Coche, ya sea Nuevo o de Ocasión a través del entero valor
    public ArrayList<Coche> obtenerDatosCoche(int codigoCoche, int valor) {
        Cursor c;

        String codCoche[] = new String[]{String.valueOf(codigoCoche)}; //Converitmos en String el codigoCoche

        ArrayList<Coche> listaCoche = new ArrayList<>(); //Guardamos los datos del coche

        //Coche nuevos
        if(valor == 1) {
            //Consultamos a la Base de Datos y guardamos el resultado en el Cursor
            c = database.rawQuery("SELECT * FROM CochesNuevos WHERE ID_CocheNuevo = ?", codCoche);

            //Nos colocamos al principio del Cursor
            if(c.moveToFirst()) {

                do { //Vamos añadiendo al ArrayList los Coches con sus valores
                    listaCoche.add(new Coche(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getInt(4),
                            c.getBlob(5)));
                } while(c.moveToNext());
            }
        }

        //Coche ocasión
        else {
            //Consultamos a la Base de Datos y guardamos el resultado en el Cursor
            c = database.rawQuery("SELECT * FROM CochesOcasion WHERE ID_CocheOcasion = ?", codCoche);

            //Nos colocamos al principio del Cursor
            if(c.moveToFirst()) {

                do {
                    //Vamos añadiendo detalles del coche de ocasion
                    listaCoche.add(new Coche(
                            c.getInt(0),
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getInt(4),
                            c.getBlob(5)));
                } while(c.moveToNext());
            }
        }
        c.close(); //Cerramos el Cursor
        return listaCoche; //Devolvemos la lista de Coches
    }

    //Método que borra un Coche
    public void borrarCoche(int ID_Coche, int valor) {
        if(valor == 1) {//Borramos en caso de ser un coche nuevo
            String where = "ID_CocheNuevo = ?";
            String[] whereArgs = new String[]{ID_Coche + ""};

            database.delete("CochesNuevos", where, whereArgs);
        }
        else {//Borramos en caso de ser un coche ocasion
            String where = "ID_CocheOcasion = ?";
            String[] whereArgs = new String[]{ID_Coche + ""};

            database.delete("CochesOcasion", where, whereArgs);
        }
    }

    //Método que modifica un Coche
    public void actualizarCoche(Coche coche, int valor) {
        ContentValues datos = new ContentValues();

        if(valor == 1) {//Actualizamos el coche en caso de ser Nuevo
            String ID_CocheNuevo = String.valueOf(coche.getID_Coche());

            datos.put("Marca", coche.getMarca());
            datos.put("Modelo", coche.getModelo());
            datos.put("Descripcion", coche.getDescripcion());
            datos.put("Precio", coche.getPrecio());

            String where = "ID_CocheNuevo = ?";
            String[] whereArgs = new String[]{ID_CocheNuevo + ""};

            database.update("CochesNuevos", datos, where, whereArgs);
        }

        else {//Actualizamos el coche en caso de ser de Ocasión
            int ID_CocheOcasion = coche.getID_Coche();

            datos.put("Marca", coche.getMarca());
            datos.put("Modelo", coche.getModelo());
            datos.put("Descripcion", coche.getDescripcion());
            datos.put("Precio", coche.getPrecio());

            String where = "ID_CocheOcasion = ?";
            String[] whereArgs = new String[]{ID_CocheOcasion + ""};

            database.update("CochesOcasion", datos, where, whereArgs);
        }
    }

    //Método que crea un Coche
    public void crearCoche(Coche coche, int valor) {
        ContentValues datos = new ContentValues();

        if(valor == 1) {//Creamos el coche en caso de ser Nuevo

            datos.put("Marca", coche.getMarca());
            datos.put("Modelo", coche.getModelo());
            datos.put("Descripcion", coche.getDescripcion());
            datos.put("Precio", coche.getPrecio());
            datos.put("Imagen", coche.getFoto());

            database.insert("CochesNuevos", null, datos);
        }

        else {//Creamos el coche en caso de ser de Ocasión
            datos.put("Marca", coche.getMarca());
            datos.put("Modelo", coche.getModelo());
            datos.put("Descripcion", coche.getDescripcion());
            datos.put("Precio", coche.getPrecio());
            datos.put("Imagen", coche.getFoto());

            database.insert("CochesOcasion", null, datos);
        }
    }


    //METODOS PARA LOS EXTRAS

    //Método para obtener un Extra de la Base de Datos
    public Extras obtenerExtra(int ID_Extra) {

        Cursor c;

        String idExtra[] = new String[]{String.valueOf(ID_Extra)}; //Converitmos en String del ID_Extras

        Extras extra = new Extras(); //Guardamos los datos del extra

        //Consultamos a la Base de Datos y guardamos el resultado en el Cursor
        c = database.rawQuery("SELECT * FROM Extras WHERE ID_Extra = ?", idExtra);

        //Nos colocamos al principio del Cursor
        if(c.moveToFirst()) {

            do { //Vamos añadiendo al ArrayList los Coches con sus valores
                extra = new Extras(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3));
            } while(c.moveToNext());
        }
        return extra;
    }

    //Método que devuelve todos los Extras de la Tabla Extras
    public ArrayList<Extras> obtenerExtras() {
        Cursor c;

        //Creamos un ArrayList para guardar todos los datos de los Extras
        ArrayList<Extras> listadoExtras = new ArrayList<>();

        //Consultamos a la Base de Datos y guardamos el resultado en el Cursor
        c = database.rawQuery("SELECT * FROM Extras", null);

        //Nos colocamos al principio del Cursor
        if(c.moveToFirst()) {

            do { //Vamos añadiendo Extras al ArrayList
                listadoExtras.add(new Extras(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getInt(3)));
            } while(c.moveToNext());

        }
        c.close(); //Cerramos el Cursor
        return listadoExtras; //Devolvemos la lista de Extras que hay en la Tabla Extras
    }

    //Método para guardar un nuevo Extra en la Base de Datos
    public void guardarExtra(Extras extra) {
        ContentValues datos = new ContentValues();
        datos.put("Nombre", extra.getNombre());
        datos.put("Descripcion", extra.getDescripcion());
        datos.put("Precio", extra.getPrecio());

        database.insert("Extras", null, datos);
        database.close();
    }

    //Método para borrar un Extra de la Base de Datos
    public void borrarExtra(int ID_Extra) {

        String where = "ID_Extra = ?";
        String[] whereArgs = new String[]{ID_Extra + ""};

        database.delete("Extras", where, whereArgs);
    }

    //Método que modifica un Extra
    public void actualizarExtra(Extras extra) {
        ContentValues datos = new ContentValues();

        String ID_Extra = String.valueOf(extra.getID_Extra());

        datos.put("Nombre",      extra.getNombre());
        datos.put("Descripcion", extra.getDescripcion());
        datos.put("Precio",      extra.getPrecio());

        String where = "ID_Extra = ?";
        String[] whereArgs = new String[]{ID_Extra + ""};

        database.update("Extras", datos, where, whereArgs);
    }
}