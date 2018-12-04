package com.example.miguel.joaquinsotoautomoviles.fragmentos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.adaptadores.AdaptadorExtras;
import com.example.miguel.joaquinsotoautomoviles.basededatos.DatabaseAccess;
import com.example.miguel.joaquinsotoautomoviles.clases.Extras;

import java.util.ArrayList;

public class ExtrasFragment extends Fragment {

    //Propiedades
    private AdaptadorExtras adaptadorExtras;
    private ArrayList<Extras> listaExtras;
    private View rootView;

    //Objetos para vincularlo con el XML
    private FloatingActionButton btnFlotAdd;
    private ListView lisvExtras;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflamos la Vista rootView para Visualizar el Adaptador personalizado
        rootView = inflater.inflate(R.layout.fragment_extras, container, false);

        //Vinculamos el ListView y el FloatingActionButton con el del XML
        lisvExtras = (ListView) rootView.findViewById(R.id.lisvExtras);
        btnFlotAdd = (FloatingActionButton) rootView.findViewById(R.id.btnFlotAdd);

        //Creamos una escucha para el ListView
        lisvExtras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO ENVIAR A LA ACTIVIDAD DETALLES
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
        listaExtras = databaseAccess.obtenerExtras();

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        adaptadorExtras = new AdaptadorExtras(getActivity(), listaExtras);

        lisvExtras.setAdapter(adaptadorExtras);

        //Devolvemos la Vista
        return rootView;
    }

}
