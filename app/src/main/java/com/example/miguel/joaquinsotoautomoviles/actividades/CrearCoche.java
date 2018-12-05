package com.example.miguel.joaquinsotoautomoviles.actividades;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.basededatos.DatabaseAccess;
import com.example.miguel.joaquinsotoautomoviles.clases.Coche;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CrearCoche extends AppCompatActivity {

    //Objetos que identifica a los componentes del XML
    private EditText edtMarca;
    private EditText edtModelo;
    private EditText edtPrecio;
    private EditText edtDescripcion;
    private ImageView imgFoto;
    private FloatingActionButton btnFlotCrear;

    private byte[] imagenCoche;
    private Uri urlImagen;

    private ArrayList<Coche> cocheNuevo;

    private int valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_coche);

        //Creamos un objeto Toolbar y lo vinculamos con el del XML
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Preparamos el paquete que nos hemos traido de la otra actividad
        Intent recibidor = getIntent();
        Bundle paquete = recibidor.getExtras();
        valor = (int) paquete.getSerializable("entero");

        //Vinculamos el los siguientes objetos con los elementos del XML
        edtMarca        = (EditText) findViewById(R.id.edtNewMarca);
        edtModelo       = (EditText) findViewById(R.id.edtNewModelo);
        edtPrecio       = (EditText) findViewById(R.id.edtNewPrecio);
        edtDescripcion  = (EditText) findViewById(R.id.edtNewDescripcion);
        imgFoto         = (ImageView) findViewById(R.id.imgNewFoto);
        btnFlotCrear     = (FloatingActionButton) findViewById(R.id.btnFlotCrear);

        //Creamos una escucha del FloatingActionButton
        btnFlotCrear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String marca        = edtMarca.getText().toString();
                String modelo       = edtModelo.getText().toString();
                String precio       = edtPrecio.getText().toString();
                String descripcion  = edtDescripcion.getText().toString();

                if(marca.isEmpty() || modelo.isEmpty() || precio.isEmpty() || descripcion.isEmpty() || imagenCoche == null) {
                    Snackbar.make(v, "Debe rellenar todos los campos", Snackbar.LENGTH_LONG).show();
                }

                else {
                    int precioNuevo = Integer.valueOf(precio);

                    Coche coche = new Coche(marca, modelo, descripcion, precioNuevo, imagenCoche);

                    //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                    databaseAccess.open();

                    //Guardamos el nuevo coche
                    databaseAccess.crearCoche(coche, valor);

                    //Cerramos la conexión con la Base de Datos
                    databaseAccess.close();

                    setResult(RESULT_OK);
                    finish();

                }
            }
        });

    }

    //Método para cargar el menú en el Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crearcoche, menu);
        return true;
    }

    //Método para comprobar si hemos pulsado algún elemento del Menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.idItemCamara: //Botón del Menú: Cámara
                if (comprobarPermisosCamara()) {
                    Intent abrirCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(abrirCamara, 0);
                }
                break;

            case R.id.idItemGaleria: //Botón del Menú: Galeria
                Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                galeria.setType("image/*");
                startActivityForResult(galeria, 1);
                break;
        }

        return true;
    }

    //Método para comprobar si tenemos permisos para ejecutar la cámara
    private boolean comprobarPermisosCamara() {
        int comprobarPermisos = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (comprobarPermisos != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No tiene permisos para usar la cámara");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 225);
            return false;
        } else {
            Log.i("Mensaje", "Tiene permisos para usar la cámara");
            return true;
        }
    }


    //Método para transformar la imagen en un Array de Bytes de salida
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Si es 0 viene de la cámara
        if(requestCode == 0) {
            //Guardamos los datos obtenidos en un mapa de bits
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            //Lo colocamos en el ImageView
            imgFoto.setImageBitmap(bitmap);

            //Creamos un Array de Bytes de salida
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            //Comprimimos en PNG
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagenCoche = stream.toByteArray();
        }
        else {}

        //Si es 1 viene de la Galería
        if(requestCode == 1){
            Uri imageURI = data.getData();
            imgFoto.setImageURI(imageURI);

            imgFoto.buildDrawingCache();
            Bitmap bitmap = imgFoto.getDrawingCache();

            Bitmap bitmapfinal = Bitmap.createScaledBitmap(bitmap,400,195,false);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapfinal.compress(Bitmap.CompressFormat.PNG, 100, stream);

            imagenCoche = stream.toByteArray();

        }
    }

}
