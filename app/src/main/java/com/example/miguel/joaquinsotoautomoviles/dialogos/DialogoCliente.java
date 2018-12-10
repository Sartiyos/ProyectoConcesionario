package com.example.miguel.joaquinsotoautomoviles.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miguel.joaquinsotoautomoviles.R;

public class DialogoCliente {

    public interface interfaceDialogoCliente {
        void llamarDialogoCliente(String nombre, String apellidos, int telefono, String email, String poblacion, String direccion, String fecha);
    }

    private interfaceDialogoCliente interfaz;

    public DialogoCliente(final Context context, interfaceDialogoCliente actividad) {

        interfaz = actividad;

        final Dialog dialogo = new Dialog(context);
        dialogo.getWindow();
        dialogo.setContentView(R.layout.layout_dialogo);

        final EditText edtNombre    = (EditText)dialogo.findViewById(R.id.edtNombre);
        final EditText edtApellidos = (EditText)dialogo.findViewById(R.id.edtApellidos);
        final EditText edtTelefono  = (EditText)dialogo.findViewById(R.id.edtTelefono);
        final EditText edtEmail     = (EditText) dialogo.findViewById(R.id.edtEmail);
        final EditText edtPoblacion = (EditText)dialogo.findViewById(R.id.edtPoblacion);
        final EditText edtDireccion = (EditText)dialogo.findViewById(R.id.edtDireccion);
        final EditText edtFecha     = (EditText)dialogo.findViewById(R.id.edtFecha);

        Button btnCancelar = (Button)dialogo.findViewById(R.id.btnCancelar);
        Button btnAceptar  = (Button)dialogo.findViewById(R.id.btnAceptar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre    = edtNombre.getText().toString();
                String apellidos = edtApellidos.getText().toString();
                String telefono  = edtTelefono.getText().toString();
                String email     = edtEmail.getText().toString();
                String poblacion = edtPoblacion.getText().toString();
                String direccion = edtDireccion.getText().toString();
                String fecha     = edtFecha.getText().toString();

                if(nombre.isEmpty() || apellidos.isEmpty() || telefono.isEmpty() ||
                        email.isEmpty() || poblacion.isEmpty() || direccion.isEmpty() ||
                        fecha.isEmpty()) {
                    Snackbar.make(view, "Debe rellenar todos los campos", Snackbar.LENGTH_LONG)
                            .show();
                }
                else {
                    interfaz.llamarDialogoCliente(nombre, apellidos, Integer.parseInt(telefono),
                            email, poblacion, direccion, fecha);
                    dialogo.dismiss();
                }
            }
        });

        dialogo.show();
    }

}
