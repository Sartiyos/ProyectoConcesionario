package com.example.miguel.joaquinsotoautomoviles.basededatos;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseOpenHelper extends SQLiteAssetHelper {

    //Nombre de la Base de Datos que vamos a trabajar
    private static final String DATABASE_NAME = "concesionario.db";

    //Versión de la Base de Datos
    private static final int DATABASE_VERSION = 1;

    //Constructor
    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}