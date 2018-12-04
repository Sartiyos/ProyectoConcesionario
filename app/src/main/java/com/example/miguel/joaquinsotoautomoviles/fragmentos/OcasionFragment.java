package com.example.miguel.joaquinsotoautomoviles.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.actividades.CocheDetalles;
import com.example.miguel.joaquinsotoautomoviles.adaptadores.AdaptadorCocheNuevo;
import com.example.miguel.joaquinsotoautomoviles.basededatos.DatabaseAccess;
import com.example.miguel.joaquinsotoautomoviles.clases.Coche;

import java.util.ArrayList;

public class OcasionFragment extends Fragment {

    //Propiedades
    private AdaptadorCocheNuevo adaptadorCocheOcasion;
    private ArrayList<Coche> listaCochesOcasion;
    private View rootView;

    //Objetos para vincularlo con el XML
    private FloatingActionButton btnFlotAdd;
    private ListView lisvCochesOcasion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflamos la Vista rootView para Visualizar el Adaptador personalizado
        rootView = inflater.inflate(R.layout.fragment_ocasion, container, false);

        //Vinculamos el ListView y el FloatingActionButton con el del XML
        lisvCochesOcasion = (ListView) rootView.findViewById(R.id.lisvCochesOcasion);
        btnFlotAdd = (FloatingActionButton) rootView.findViewById(R.id.btnFlotAdd);

        //Creamos una escucha para el ListView
        lisvCochesOcasion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView txvIdCoche = (TextView) view.findViewById(R.id.txvIdCoche);
                int nuevo = 2;
                String codigoCoche = txvIdCoche.getText().toString();

                Intent ModificarCoche = new Intent(getActivity(), CocheDetalles.class);
                Bundle enviarCoche = new Bundle();
                enviarCoche.putSerializable("codigocoche", Integer.valueOf(codigoCoche));
                enviarCoche.putSerializable("entero", nuevo);
                ModificarCoche.putExtras(enviarCoche);
                startActivityForResult(ModificarCoche, 1);
            }
        });

        //Creamos una escucha para el btnFlotAdd
        btnFlotAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Tocaste el FAB", Snackbar.LENGTH_LONG).show();
            }
        });

        //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();

        //Obtenemos todas las Personas de la Tabla Persona
        listaCochesOcasion = databaseAccess.obtenerCochesOcasion();

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        adaptadorCocheOcasion = new AdaptadorCocheNuevo(getActivity(), listaCochesOcasion);

        lisvCochesOcasion.setAdapter(adaptadorCocheOcasion);

        //Devolvemos la Vista
        return rootView;
    }
}