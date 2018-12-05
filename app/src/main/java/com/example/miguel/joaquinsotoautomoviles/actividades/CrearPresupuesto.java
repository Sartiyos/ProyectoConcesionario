package com.example.miguel.joaquinsotoautomoviles.actividades;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.adaptadores.AdaptadorExtras;
import com.example.miguel.joaquinsotoautomoviles.basededatos.DatabaseAccess;
import com.example.miguel.joaquinsotoautomoviles.clases.Extras;

import java.util.ArrayList;

public class CrearPresupuesto extends AppCompatActivity {

    //Objetos que identifica a los componentes del XML
    private ListView lisvExtras;
    private TextView txvPrecioCoche;

    //Objetos necesarios
    private ArrayList<Extras> listaExtras;
    private AdaptadorExtras adaptadorExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_presupuesto);

        String titulo = getIntent().getStringExtra("coche");
        int precio = getIntent().getIntExtra("precio", 0);

        //Creamos un objeto Toolbar y lo vinculamos con el del XML
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(titulo);//Le ponemos de titulo el nombre del coche
        setSupportActionBar(toolbar);

        //Vinculamos con el XML
        txvPrecioCoche = (TextView) findViewById(R.id.txvPrecioCoche);
        lisvExtras = (ListView) findViewById(R.id.lisvExtras);

        txvPrecioCoche.setText(String.valueOf(precio));

        adaptadorExtras();

    }

    public void adaptadorExtras() {
        //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        //Obtenemos todas las Personas de la Tabla Persona
        listaExtras = databaseAccess.obtenerExtras();

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        adaptadorExtras = new AdaptadorExtras(this, listaExtras);

        lisvExtras.setAdapter(adaptadorExtras);
    }
}
