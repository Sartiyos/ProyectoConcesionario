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

    //Objetos con los que identificaremos a los componentes del XML
    private LinearLayout layExtras;
    private TextView txvNombreCoche;
    private TextView txvPrecioCoche;
    private FloatingActionButton btnFlotEnviar;

    //ArrayList donde guardamos todos los extras de la base de datos
    private ArrayList<Extras> listaTotalExtras;

    //ArrayList donde guardamos los extras que han sido marcados
    private ArrayList<Extras> listaExtras = new ArrayList<>();

    //ArrayList donde guardamos los datos del coche
    private ArrayList<Coche> detalleCoche = new ArrayList<>();

    //Adaptador con el que mostraremos los extras a en el ListView
    private AdaptadorExtras adaptadorExtras;

    //Entero donde guardaremos la cantidad de Extras que han sido marcados
    private int cantidadExtras;

    //Variables que nos hemos traido desde la otra actividad
    private int codigoCoche;
    private int precioFinal;
    private int precioCoche;
    private String coche;

    //Array de booleanos donde guardaremos cuales son los extras marcados
    private boolean[] arrayExtrasPedidos;

    //Valor sirve para identificar si es un coche nuevo o de ocasión
    private int valor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_resumen);

        //XML
        layExtras      = (LinearLayout) findViewById(R.id.layExtras);
        txvNombreCoche = (TextView) findViewById(R.id.txvNombreCoche);
        txvPrecioCoche = (TextView) findViewById(R.id.txvPrecioCoche);
        btnFlotEnviar  = (FloatingActionButton) findViewById(R.id.btnFlotEnviar);
        btnFlotEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogoCliente(getApplicationContext(), CrearResumen.this);
            }
        });

        //Obtenemos los Extras que ha marcado el usuario desde la otra Actividad
        valor       = getIntent().getIntExtra("tipo", 1);
        codigoCoche = getIntent().getIntExtra("id", 1);
        coche       = getIntent().getStringExtra("coche");
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
        detalleCoche = databaseAccess.obtenerDatosCoche(codigoCoche, valor);

        //En caso de ser coche nuevo se ejecutará lo siguiente
        if(valor == 1) {
            arrayExtrasPedidos = getIntent().getBooleanArrayExtra("extras");

            //Preparamos el AdaptadorExtras
            adaptadorExtras = new AdaptadorExtras(this, listaTotalExtras);

            cantidadExtras = adaptadorExtras.getCount();

            for (int i = 0; i < cantidadExtras; i++) {
                if (arrayExtrasPedidos[i]) {

                    //Obtenemos los datos del Extra y lo añadimos al ListView
                    listaExtras.add(databaseAccess.obtenerExtra(i + 1));

                }
            }

            //Cerramos la conexión con la Base de Datos
            databaseAccess.close();

            //Comprobamos si tenemos Extras que añadir
            if (listaExtras.size() != 0) {

                //Creamos un Layout para mostrar en él, todos los extras que se han marcado en la
                //otra actividad
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

    //Método donde mostraremos un diálogo para recoger los datos del cliente y posteriormente
    //enviarlo por correo
    @Override
    public void llamarDialogoCliente(String nombre, String apellidos, int telefono, String email, String poblacion, String direccion, String fecha) {

        String[] TO = {email};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:" + email));
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

        String descripcion = detalleCoche.get(0).getDescripcion();

        String mensaje = "Estimado " + nombre + " " + apellidos + "\n\n" +
                "Se ha registrado con los siguientes datos:\n" +
                "· Fecha de Nacimiento: " + fecha + "\n" +
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
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Presupuesto del coche " + coche);
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
