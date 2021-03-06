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

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.actividades.CocheDetalles;
import com.example.miguel.joaquinsotoautomoviles.actividades.CrearCoche;
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

    private final int valor = 2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflamos la Vista rootView para Visualizar el Adaptador personalizado
        rootView = inflater.inflate(R.layout.fragment_ocasion, container, false);

        //Vinculamos el ListView y el FloatingActionButton con el del XML
        lisvCochesOcasion = (ListView) rootView.findViewById(R.id.lisvCochesOcasion);
        btnFlotAdd = (FloatingActionButton) rootView.findViewById(R.id.btnFlotAdd);

        //Llamamos al método adaptadorOcasion para adaptar los datos al ListView
        adaptadorOcasion();

        //Creamos una escucha para el ListView
        lisvCochesOcasion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String codigoCoche = String.valueOf(listaCochesOcasion.get(i).getID_Coche());

                Intent ModificarCoche = new Intent(getActivity(), CocheDetalles.class);
                Bundle enviarCoche = new Bundle();
                enviarCoche.putSerializable("codigocoche", Integer.valueOf(codigoCoche));
                enviarCoche.putSerializable("entero", valor);
                ModificarCoche.putExtras(enviarCoche);
                startActivityForResult(ModificarCoche, 1);
            }
        });

        //Creamos una escucha para el btnFlotAdd
        btnFlotAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividadCrearCocheNuevo = new Intent(getActivity(), CrearCoche.class);
                Bundle paquete = new Bundle();
                paquete.putSerializable("entero", valor);
                actividadCrearCocheNuevo.putExtras(paquete);
                startActivityForResult(actividadCrearCocheNuevo, 2);
            }
        });

        //Devolvemos la Vista
        return rootView;
    }

    //Método para comprobar el resultado de las otras actividades
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 1) && (resultCode == CrearCoche.RESULT_OK)) {
            adaptadorOcasion(); //Llamamos al método del adaptador para que se actualice
        }

        if((requestCode == 2) && (resultCode == CrearCoche.RESULT_OK)) {
            adaptadorOcasion(); //Llamamos al método del adaptador para que se actualice
        }
    }

    //Método para llamar al adaptador
    public void adaptadorOcasion() {
        //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();

        //Obtenemos todas los Coches de la Tabla Coches de Ocasion
        listaCochesOcasion = databaseAccess.obtenerCoches(2);

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        adaptadorCocheOcasion = new AdaptadorCocheNuevo(getActivity(), listaCochesOcasion);

        lisvCochesOcasion.setAdapter(adaptadorCocheOcasion);
    }
}