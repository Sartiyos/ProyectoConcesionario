package com.example.miguel.joaquinsotoautomoviles.actividades;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miguel.joaquinsotoautomoviles.dialogos.DialogoCliente;
import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.adaptadores.AdaptadorExtras;
import com.example.miguel.joaquinsotoautomoviles.basededatos.DatabaseAccess;
import com.example.miguel.joaquinsotoautomoviles.clases.Coche;
import com.example.miguel.joaquinsotoautomoviles.clases.Extras;

import java.util.ArrayList;

public class CrearResumen extends AppCompatActivity implements DialogoCliente.interfaceDialogoCliente {

    //Objetos necesarios
    private ArrayList<Extras> listaTotalExtras;
    private ArrayList<Extras> listaExtras = new ArrayList<>();
    private ArrayList<Coche> detalleCoche = new ArrayList<>();
    private AdaptadorExtras adaptadorExtras;
    private int cantidadExtras;

    private LinearLayout layExtras;
    private TextView txvNombreCoche;
    private TextView txvPrecioCoche;
    private FloatingActionButton btnFlotEnviar;

    private int codigoCoche;
    private int precioFinal;
    private String coche;
    private int precioCoche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_resumen);

        //XML
        layExtras = (LinearLayout) findViewById(R.id.layExtras);
        txvNombreCoche = (TextView) findViewById(R.id.txvNombreCoche);
        txvPrecioCoche = (TextView) findViewById(R.id.txvPrecioCoche);

        final Context contexto = this;

        btnFlotEnviar = (FloatingActionButton) findViewById(R.id.btnFlotEnviar);
        btnFlotEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendEmail();
                new DialogoCliente(contexto, CrearResumen.this);

            }
        });

        //Obtenemos los Extras que ha marcado el usuario desde la otra Actividad
        codigoCoche = getIntent().getIntExtra("id", 1);
        boolean[] arrayExtrasPedidos = getIntent().getBooleanArrayExtra("extras");
        coche = getIntent().getStringExtra("coche");
        precioCoche = getIntent().getIntExtra("precio", 0);

        //Sumamos el precio del coche
        precioFinal =+ precioCoche;

        //Le damos el valor a txvNombreCoche
        txvNombreCoche.setText(coche);
        txvPrecioCoche.setText(String.valueOf(precioCoche + " €"));

        //Creamos un objeto DatabaseAccess para tener acceso a la Base de Datos
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();

        //Obtenemos todas los Extras de la Tabla Extras y también nos traemos los datos del coche
        listaTotalExtras = databaseAccess.obtenerExtras();
        detalleCoche = databaseAccess.obtenerDatosCoche(codigoCoche, 1);

        //Preparamos el AdaptadorExtras
        adaptadorExtras = new AdaptadorExtras(this, listaTotalExtras);

        cantidadExtras = adaptadorExtras.getCount();

        for(int i = 0; i < cantidadExtras; i++) {
            if(arrayExtrasPedidos[i]) {

                //Obtenemos los datos del Extra y lo añadimos al ListView
                listaExtras.add(databaseAccess.obtenerExtra(i+1));

            }
        }

        //Cerramos la conexión con la Base de Datos
        databaseAccess.close();

        //Comprobamos si tenemos Extras que añadir
        if(listaExtras.size() != 0) {

            //Creamos un Layout para mostrar en él, todos los extras que se han marcado en la otra
            //actividad
            for (int i = 0; i < listaExtras.size(); i++) {
                String nombre = listaExtras.get(i).getNombre();
                String precio = String.valueOf(listaExtras.get(i).getPrecio());

                precioFinal = precioFinal + Integer.valueOf(precio);

                LinearLayout contenedor = new LinearLayout(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                contenedor.setLayoutParams(params);

                TextView nombreExtra = new TextView(this);
                TextView precioExtra = new TextView(this);

                nombreExtra.setLayoutParams(new TableLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                precioExtra.setLayoutParams(new TableLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 4f));

                nombreExtra.setId(i);
                nombreExtra.setText(nombre);
                nombreExtra.setPadding(5, 5, 5, 5);
                nombreExtra.setGravity(Gravity.LEFT);
                nombreExtra.setTextColor(Color.BLACK);


                precioExtra.setId(i);
                precioExtra.setText(precio + " €");
                precioExtra.setPadding(5, 5, 5, 5);
                precioExtra.setGravity(Gravity.RIGHT);
                precioExtra.setTextColor(Color.BLACK);

                //Agrega vistas al contenedor.
                contenedor.addView(nombreExtra);
                contenedor.addView(precioExtra);

                layExtras.addView(contenedor);
            }
        }

        //Creamos un último layout para mostrar el precio final
        LinearLayout layPrecioFinal = new LinearLayout(this);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        params2.setMargins(0,80,0,0);

        layPrecioFinal.setLayoutParams(params2);

        TextView textoPrecioFinal = new TextView(this);
        TextView valorPrecioFinal = new TextView(this);

        textoPrecioFinal.setLayoutParams(new TableLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        valorPrecioFinal.setLayoutParams(new TableLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 4f));

        textoPrecioFinal.setText("Precio final:");
        textoPrecioFinal.setPadding(5,5,5,5);
        textoPrecioFinal.setGravity(Gravity.RIGHT);
        textoPrecioFinal.setTextColor(Color.BLACK);
        textoPrecioFinal.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        valorPrecioFinal.setText(String.valueOf(precioFinal) + " €");
        valorPrecioFinal.setPadding(5,5,5,5);
        valorPrecioFinal.setGravity(Gravity.RIGHT);
        valorPrecioFinal.setTextColor(Color.BLACK);
        valorPrecioFinal.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

        //Agrega vistas al contenedor.
        layPrecioFinal.addView(textoPrecioFinal);
        layPrecioFinal.addView(valorPrecioFinal);

        layExtras.addView(layPrecioFinal);

    }

    @Override
    public void llamarDialogoCliente(String nombre, String apellidos, int telefono, String email, String poblacion, String direccion) {

        String[] TO = {email};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:" + email));
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

        String descripcion = detalleCoche.get(0).getDescripcion();

        String mensaje = "Estimado " + nombre + " " + apellidos + "\n\n" +
                "Se ha registrado con los siguientes datos:\n" +
                "· Telefono: " + telefono + "\n" +
                "· Dirección: " + direccion + ", " + poblacion + "\n\n" +
                "A continuación le adjuntamos los datos del presupuesto del coche " + coche + "\n" +
                "· Descripción: " + descripcion + "\n" +
                "· Precio coche: " + precioCoche + " €\n";

        if(listaExtras.size() != 0) {

            mensaje = mensaje + "\nCon los siguientes extras:\n\n";

            for (int x = 0; x < listaExtras.size(); x++) {
                mensaje = mensaje + "· " + listaExtras.get(x).getNombre() + " " +
                        "Precio: " + listaExtras.get(x).getPrecio() + " €\n";
            }
        }

        mensaje = mensaje + "\n\nPRECIO FINAL: " + precioFinal;



        // Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Presupuesto del coche" + coche);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    }
}
