package com.example.miguel.joaquinsotoautomoviles.actividades;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.clases.Coche;

import java.util.ArrayList;

public class CrearCocheNuevo extends AppCompatActivity {

    //Objetos que identifica a los componentes del XML
    private EditText edtMarca;
    private EditText edtModelo;
    private EditText edtPrecio;
    private EditText edtDescripcion;
    private ImageView imgFoto;
    private FloatingActionButton btnFlotSave;

    private ArrayList<Coche> cocheNuevo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_coche_nueco);

        //Vinculamos el los siguientes objetos con los elementos del XML
        edtMarca        = (EditText) findViewById(R.id.edtMarca);
        edtModelo       = (EditText) findViewById(R.id.edtModelo);
        edtPrecio       = (EditText) findViewById(R.id.edtPrecio);
        edtDescripcion  = (EditText) findViewById(R.id.edtDescripcion);
        imgFoto         = (ImageView) findViewById(R.id.imgFoto);
        btnFlotSave     = (FloatingActionButton) findViewById(R.id.btnFlotSave);

        //Creamos una escucha del FloatingActionButton
        btnFlotSave.setOnClickListener(new View.OnClickListener() {

            String marca        = edtMarca.getText().toString();
            String modelo       = edtModelo.getText().toString();
            String precio       = edtPrecio.getText().toString();
            String descripcion  = edtDescripcion.getText().toString();

            @Override
            public void onClick(View v) {
                if((marca.isEmpty()) || (modelo.isEmpty()) || (precio.isEmpty()) || (descripcion.isEmpty())) {
                    Snackbar.make(v, "Debe rellenar todos los campos", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }
}
