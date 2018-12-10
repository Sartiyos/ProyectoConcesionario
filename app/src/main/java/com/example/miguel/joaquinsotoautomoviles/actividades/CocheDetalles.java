package com.example.miguel.joaquinsotoautomoviles.actividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.basededatos.DatabaseAccess;
import com.example.miguel.joaquinsotoautomoviles.clases.Coche;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class CocheDetalles extends AppCompatActivity {

    //Objetos que identifica a los componentes del XML
    private EditText edtMarca;
    private EditText edtModelo;
    private EditText edtPrecio;
    private EditText edtDescripcion;
    private ImageView imgFoto;
    private FloatingActionButton btnFlotSave;

    //ArrayList que contendrá los Coches Nuevos y los Coches de Ocasión
    private ArrayList<Coche> detalleCoche;

    //Enteros donde contendrá el identificador del coche y valor contendrá si es uno nuevo o de
    //ocasión
    private int codigoCoche;
    private int valor;

    //Atributos de la clase Coche
    private String marca;
    private String modelo;
    private String descripcion;
    private int precio;
    private byte[] foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coche_detalles);

        //Creamos un objeto Toolbar y lo vinculamos con el del XML
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Vinculamos el los siguientes objetos con los elementos del XML
        edtMarca        = (EditText) findViewById(R.id.edtMarca);
        edtModelo       = (EditText) findViewById(R.id.edtModelo);
        edtPrecio       = (EditText) findViewById(R.id.edtPrecio);
        edtDescripcion  = (EditText) findViewById(R.id.edtDescripcion);
        imgFoto         = (ImageView) findViewById(R.id.imgFoto);
        btnFlotSave     = (FloatingActionButton) findViewById(R.id.btnFlotSave);

        //Preparamos el paquete que nos hemos traido de la otra actividad
        Intent recibidor = getIntent();
        Bundle paquete = recibidor.getExtras();
        codigoCoche = (int) paquete.getSerializable("codigocoche");
        valor = (int) paquete.getSerializable("entero");

        //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        //Obtenemos todas las Personas de la Tabla Persona
        detalleCoche = databaseAccess.obtenerDatosCoche(codigoCoche, valor);

        //Recogemos los datos del Cliente que queremos modificar
        marca       = detalleCoche.get(0).getMarca();
        modelo      = detalleCoche.get(0).getModelo();
        descripcion = detalleCoche.get(0).getDescripcion();
        precio      = detalleCoche.get(0).getPrecio();
        foto        = detalleCoche.get(0).getFoto();

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        //Cargamos los datos en los campos de texto
        edtMarca.setText(marca);
        edtModelo.setText(modelo);
        edtPrecio.setText(String.valueOf(precio));
        edtDescripcion.setText(descripcion);

        //Convertimos el Array de Bytes en un mapa de bits para poder mostrar la foto
        ByteArrayInputStream imageStream = new ByteArrayInputStream(foto);
        Bitmap imagen = BitmapFactory.decodeStream(imageStream);

        //Cargamos la imagen en el ImageView
        imgFoto.setImageBitmap(imagen);
    }

    //Método para cargar el menú en el Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_detallecoche, menu);
        return true;
    }

    //Método para comprobar si hemos pulsado algún elemento del Menú
    @SuppressLint("RestrictedApi")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.itemDetalleCoche1: //Botón del Menú: Modificar
                edtMarca.setEnabled(true);
                edtMarca.setTextColor(getResources().getColor(R.color.colorPrimary));

                edtModelo.setEnabled(true);
                edtModelo.setTextColor(getResources().getColor(R.color.colorPrimary));

                edtDescripcion.setEnabled(true);
                edtDescripcion.setTextColor(getResources().getColor(R.color.colorPrimary));

                edtPrecio.setEnabled(true);
                edtPrecio.setTextColor(getResources().getColor(R.color.colorPrimary));

                btnFlotSave.setVisibility(View.VISIBLE);
                btnFlotSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newMarca       = edtMarca.getText().toString();
                        String newModelo      = edtModelo.getText().toString();
                        String newDescripcion = edtDescripcion.getText().toString();
                        String newPrecio      = String.valueOf(edtPrecio.getText());

                        Coche newCoche = new Coche(codigoCoche, newMarca, newModelo, newDescripcion, Integer.valueOf(newPrecio));

                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                        databaseAccess.open();

                        databaseAccess.actualizarCoche(newCoche, valor);

                        databaseAccess.close();
                        setResult(RESULT_OK);
                        finish();
                    }
                });
                break;

            case R.id.itemDetalleCoche2: //Botón del Menú: Vista simple
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                databaseAccess.borrarCoche(codigoCoche, valor);
                databaseAccess.close();
                setResult(RESULT_OK);
                finish();
                break;

            case R.id.itemDetalleCoche3: //Botón del Menú: Generar presupuesto
                String nombreCoche = marca + " " + modelo;
                Intent actividadCrearPresupuesto = new Intent(this, CrearPresupuesto.class);
                actividadCrearPresupuesto.putExtra("id", codigoCoche);
                actividadCrearPresupuesto.putExtra("coche", nombreCoche.toString());
                actividadCrearPresupuesto.putExtra("precio", precio);
                startActivity(actividadCrearPresupuesto);
        }
        return true;
    }
}
