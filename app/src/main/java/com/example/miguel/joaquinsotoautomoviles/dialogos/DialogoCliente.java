package com.example.miguel.joaquinsotoautomoviles.dialogos;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.miguel.joaquinsotoautomoviles.R;

public class DialogoCliente {

    public interface interfaceDialogoCliente {
        void llamarDialogoCliente(String nombre, String apellidos, int telefono, String email, String poblacion, String direccion);
    }

    private interfaceDialogoCliente interfaz;

    public DialogoCliente(Context context, interfaceDialogoCliente actividad) {

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
                int telefono     = Integer.parseInt(edtTelefono.getText().toString());
                String email     = edtEmail.getText().toString();
                String poblacion = edtPoblacion.getText().toString();
                String direccion = edtDireccion.getText().toString();

                interfaz.llamarDialogoCliente(nombre, apellidos, telefono, email, poblacion, direccion);
                dialogo.dismiss();
            }
        });

        dialogo.show();
    }

}
