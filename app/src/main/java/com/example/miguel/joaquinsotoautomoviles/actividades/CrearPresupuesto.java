package com.example.miguel.joaquinsotoautomoviles.actividades;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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
    private FloatingActionButton btnFlotGenerar;

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
        btnFlotGenerar = (FloatingActionButton) findViewById(R.id.btnFlotGenerar);

        txvPrecioCoche.setText(String.valueOf(precio) + " €");

        adaptadorExtras();

        //Array de Booleanos para guardar que extras estan marcados y cuales no
        final boolean[] arrayExtras = new boolean[adaptadorExtras.getCount()];

        lisvExtras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(arrayExtras[i] == false) {
                    view.setBackgroundColor(getResources().getColor(R.color.colorAccent)); //Coloreamos el elemento marcado
                    arrayExtras[i] = true;
                }

                else if (arrayExtras[i] == true) {
                    view.setBackgroundColor(Color.alpha(0));  //Quitamos el color al elemento
                    arrayExtras[i] = false;
                }
            }
        });

        //Creamos una escucha para el btnFlotAdd
        btnFlotGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividadCrearExtra = new Intent(getApplicationContext(), CrearResumen.class);
                actividadCrearExtra.putExtra("extras", arrayExtras);
                startActivity(actividadCrearExtra);
            }
        });

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
