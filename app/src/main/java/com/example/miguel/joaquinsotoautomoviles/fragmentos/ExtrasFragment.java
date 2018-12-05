package com.example.miguel.joaquinsotoautomoviles.fragmentos;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.actividades.CrearExtra;
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

    //Objeto para vincular el botón del Toolbar
    MenuItem itemMenuBorrar;

    //Controlar
    private boolean itemPulsado = false; //Comprobamos si hemos pulsado algo en el ListView
    private View ultimoView; //Guardamos el valor del ultimo View para colorear la zona
    private int id_extra; //Posicion del elemento marcado del ListView

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Indicamos que este fragmento debe rellenar el menú
        setHasOptionsMenu(true);

        //Inflamos la Vista rootView para Visualizar el Adaptador personalizado
        rootView = inflater.inflate(R.layout.fragment_extras, container, false);

        //Vinculamos el ListView y el FloatingActionButton con el del XML
        lisvExtras = (ListView) rootView.findViewById(R.id.lisvExtras);
        btnFlotAdd = (FloatingActionButton) rootView.findViewById(R.id.btnFlotAdd);

        //Llamamos a la función para mostrar el adaptador
        adaptadorExtras();

        //Creamos una escucha para el ListView
        lisvExtras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(itemPulsado == false) {
                    view.setBackgroundColor(getResources().getColor(R.color.colorAccent)); //Coloreamos el elemento marcado
                    ultimoView = view;              //Guardamos cual es el último elemento pulsado
                    itemPulsado = true;             //Indicamos que ha sido pulsado un elemento
                    id_extra = listaExtras.get(i).getID_Extra(); //Obtenemos la ID del Extra
                    itemMenuBorrar.setVisible(true); //Mostramos el botón de Borrar en el Toolbar
                }

                else {
                    ultimoView.setBackgroundColor(Color.alpha(0));  //Quitamos el color al último elemento
                    view.setBackgroundColor(getResources().getColor(R.color.colorAccent));   //Al nuevo elemento lo coloreamos
                    ultimoView = view; //Guardamos este elemento como el último
                    id_extra = listaExtras.get(i).getID_Extra();  //Obtenemos la ID del Extra
                }
            }
        });

        //Creamos una escucha para el btnFlotAdd
        btnFlotAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent actividadCrearExtra = new Intent(getActivity(), CrearExtra.class);
                startActivityForResult(actividadCrearExtra, 1);
            }
        });

        //Devolvemos la Vista
        return rootView;
    }

    //Método para cargar el menú en el Toolbar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_extras, menu);
        itemMenuBorrar = menu.findItem(R.id.itemExtras1);
        itemMenuBorrar.setVisible(false);
    }

    //Método para comprobar el resultado de las otras actividades
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 1) && (resultCode == CrearExtra.RESULT_OK)) {
            adaptadorExtras(); //Llamamos al método del adaptador para que se actualice
            itemPulsado = false; //Ponemos en false el booleano para comprobar si tenemos algo pulsado
            itemMenuBorrar.setVisible(false); //Ocultamos el botón de Borrar en el Toolbar
        }
        else if((requestCode == 1) && (resultCode == CrearExtra.RESULT_CANCELED)) {
            if(ultimoView != null) {
                ultimoView.setBackgroundColor(Color.alpha(0));
            }
            itemPulsado = false; //Ponemos en false el booleano para comprobar si tenemos algo pulsado
            itemMenuBorrar.setVisible(false); //Mostramos el botón de Borrar en el Toolbar
        }
    }

    //Método para actualizar el adaptador cuando sea necesario
    public void adaptadorExtras() {
        //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();

        //Obtenemos todas las Personas de la Tabla Persona
        listaExtras = databaseAccess.obtenerExtras();

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        adaptadorExtras = new AdaptadorExtras(getActivity(), listaExtras);

        lisvExtras.setAdapter(adaptadorExtras);
    }

    //Método para comprobar si hemos pulsado algún elemento del Menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(itemPulsado) {

            switch (id) {
                case R.id.itemExtras1: //Botón del Menú: Nuevo cliente
                    //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getContext());
                    databaseAccess.open();

                    //Obtenemos todas las Personas de la Tabla Persona
                    databaseAccess.borrarExtra(id_extra);
                    listaExtras = databaseAccess.obtenerExtras();

                    //Cerramos la conexión con la Base de Datos
                    databaseAccess.close();

                    adaptadorExtras = new AdaptadorExtras(getActivity(), listaExtras);

                    lisvExtras.setAdapter(adaptadorExtras);
                    Snackbar.make(getView(), "Se ha eliminado correctamente", Snackbar.LENGTH_LONG).show();
                    itemPulsado = false;
                    itemMenuBorrar.setVisible(false);
                    break;
            }
            return true;

        }
        Snackbar.make(getView(), "Debe marcar un extra", Snackbar.LENGTH_LONG).show();
        return false;
    }
}
