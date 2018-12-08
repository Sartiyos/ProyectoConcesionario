package com.example.miguel.joaquinsotoautomoviles.actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.adaptadores.AdaptadorExtras;
import com.example.miguel.joaquinsotoautomoviles.basededatos.DatabaseAccess;
import com.example.miguel.joaquinsotoautomoviles.clases.Extras;

import java.util.ArrayList;

public class CrearResumen extends AppCompatActivity {

    //Objetos necesarios
    private ArrayList<Extras> listaTotalExtras;
    private ArrayList<Extras> listaExtras = new ArrayList<>();
    private AdaptadorExtras adaptadorExtras;
    private int cantidadExtras;

    private LinearLayout layExtras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_resumen);

        //XML
        layExtras = (LinearLayout) findViewById(R.id.layExtras);

        //Obtenemos los Extras que ha marcado el usuario desde la otra Actividad
        boolean[] arrayExtrasPedidos = getIntent().getBooleanArrayExtra("extras");

        //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        //Obtenemos todas los Extras de la Tabla Extras
        listaTotalExtras = databaseAccess.obtenerExtras();

        //Preparamos el AdaptadorExtras
        adaptadorExtras = new AdaptadorExtras(this, listaTotalExtras);

        cantidadExtras = adaptadorExtras.getCount();

        for(int i = 0; i < cantidadExtras; i++) {
            if(arrayExtrasPedidos[i]) {

                //Obtenemos los datos del Extra y lo añadimos al ListView
                listaExtras.add(databaseAccess.obtenerExtra(i+1));

            }
        }

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        //Creamos en el Layout los TextView necesarios
        for (int i = 0; i < listaExtras.size(); i++) {
            String nombre = listaExtras.get(i).getNombre();
            String precio = String.valueOf(listaExtras.get(i).getPrecio());

            TextView textView = new TextView(this);
            textView.setId(i);
            textView.setText(nombre);

            TextView textView2 = new TextView(this);
            textView2.setId(i);
            textView2.setText(precio);

            layExtras.addView(textView);
            layExtras.addView(textView2);
        }

    }
}
