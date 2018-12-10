package com.example.miguel.joaquinsotoautomoviles.actividades;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.miguel.joaquinsotoautomoviles.fragmentos.ConocenosFragment;
import com.example.miguel.joaquinsotoautomoviles.fragmentos.ExtrasFragment;
import com.example.miguel.joaquinsotoautomoviles.fragmentos.NuevoFragment;
import com.example.miguel.joaquinsotoautomoviles.fragmentos.OcasionFragment;
import com.example.miguel.joaquinsotoautomoviles.R;

public class MainActivity extends AppCompatActivity {

    //Objetos con los que identificaremos a los componentes del XML
    private BottomNavigationView btnNavegacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creamos un objeto Toolbar y lo vinculamos con el del XML
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Vinculamos el BottomNavigationView con el del XML
        btnNavegacion = (BottomNavigationView) findViewById(R.id.btnNavegacion);

        //Creamos una escucha para comprobar si se ha pulsado sobre él
        btnNavegacion.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.navegacion_nuevos:
                        fragment = new NuevoFragment();
                        break;

                    case R.id.navegacion_ocasion:
                        fragment = new OcasionFragment();
                        break;

                    case R.id.navegacion_extras:
                        fragment = new ExtrasFragment();
                        break;

                    case R.id.navegacion_conocenos:
                        fragment = new ConocenosFragment();
                        break;
                }
                replaceFragment(fragment);
                return true;
            }
        });

        //Llamamos al método para iniciar el primer fragmento, en este caso el NuevoFragment
        setInitialFragment();
    }

    //Método para iniciar los Fragmentos, en este caso cargará NuevoFragment
    private void setInitialFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.contenedor, new NuevoFragment());
        fragmentTransaction.commit();
    }

    //Método que cambiará el Fragmento
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedor, fragment);
        fragmentTransaction.commit();
    }
}