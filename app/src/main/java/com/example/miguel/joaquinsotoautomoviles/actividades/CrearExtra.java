package com.example.miguel.joaquinsotoautomoviles.actividades;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.basededatos.DatabaseAccess;
import com.example.miguel.joaquinsotoautomoviles.clases.Extras;

public class CrearExtra extends AppCompatActivity {

    //Objetos que identifica a los componentes del XML
    private EditText edtNombre;
    private EditText edtPrecio;
    private FloatingActionButton btnFlotSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_extra);

        //Vinculamos el los siguientes objetos con los elementos del XML
        edtNombre = (EditText) findViewById(R.id.edtNombreExtra);
        edtPrecio = (EditText) findViewById(R.id.edtPrecioExtra);
        btnFlotSave = (FloatingActionButton) findViewById(R.id.btnFlotSave);

        //Creamos una escucha del FloatingActionButton
        btnFlotSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Obtenemos los valores que contenga los EditText
                String nombre = edtNombre.getText().toString();
                String precioExtra = edtPrecio.getText().toString();

                //Comprobamos si estan los campos vacios
                if ((nombre.isEmpty()) || (precioExtra.isEmpty())) {
                    Snackbar.make(v, "Debe rellenar todos los campos", Snackbar.LENGTH_LONG).show();
                }

                //En caso de no estar vacios guardamos los datos en la base de datos
                else {
                    //Convertimos el string de precio en entero
                    int precio = Integer.parseInt(precioExtra);

                    //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();

                    //Creamos un objeto de tipo Extras y lo añadimos a la base de datos
                    Extras nuevoExtra = new Extras(nombre, precio);
                    databaseAccess.guardarExtra(nuevoExtra);

                    //Cerramos la conexión con la Base de Datos
                    databaseAccess.close();

                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
