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

public class ModificarExtra extends AppCompatActivity {

    //Objetos con los que identificaremos a los componentes del XML
    private EditText edtNombre;
    private EditText edtDescripcion;
    private EditText edtPrecio;
    private FloatingActionButton btnFlotSave;

    //Objeto de tipo Extra donde se almacenará los datos de un Extra
    private Extras extra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_extra);

        //Recogemos de la otra Actividad el ID del Extra a modificar
        final int idExtra = getIntent().getIntExtra("id", 1);

        //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        //Llamamos al método para obtener los datos del extra
        extra = databaseAccess.obtenerExtra(idExtra);

        //Vinculamos los objetos con los elementos del XML
        edtNombre = (EditText) findViewById(R.id.edtNombreExtra);
        edtDescripcion = (EditText) findViewById(R.id.edtDescripcionExtra);
        edtPrecio = (EditText) findViewById(R.id.edtPrecioExtra);
        btnFlotSave = (FloatingActionButton) findViewById(R.id.btnFlotSave);

        //Rellenamos los datos en cada elemento del XML
        edtNombre.setText(extra.getNombre());
        edtDescripcion.setText(extra.getDescripcion());
        edtPrecio.setText(String.valueOf(extra.getPrecio()));

        //Creamos una escucha del FloatingActionButton
        btnFlotSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Obtenemos los valores que contenga los EditText
                String nombre      = edtNombre.getText().toString();
                String descripcion = edtDescripcion.getText().toString();
                String precioExtra = edtPrecio.getText().toString();

                //Comprobamos si estan los campos vacios
                if (nombre.isEmpty() || descripcion.isEmpty() || precioExtra.isEmpty()) {
                    Snackbar.make(v, "Debe rellenar todos los campos", Snackbar.LENGTH_LONG).show();
                }

                //En caso de no estar vacios guardamos los datos en la base de datos
                else {
                    //Convertimos el string de precio en entero
                    int precio = Integer.parseInt(precioExtra);

                    //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();

                    //Actualizamos el Extra en la Base de Datos
                    Extras nuevoExtra = new Extras(idExtra, nombre, descripcion, precio);
                    databaseAccess.actualizarExtra(nuevoExtra);

                    //Cerramos la conexión con la Base de Datos
                    databaseAccess.close();

                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
