package com.example.miguel.joaquinsotoautomoviles.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.clases.Coche;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class AdaptadorCocheNuevo extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Coche> items;

    public AdaptadorCocheNuevo(Activity activity, ArrayList<Coche> items) {
        this.activity = activity;
        this.items    = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.items_nuevo, null);
        }

        //Obtenemos los datos del coche sobre que hemos pulsado
        Coche listaCochesNuevo = items.get(position);

        //Recogemos el ID del Coche
        TextView txvIdCoche = (TextView) v.findViewById(R.id.txvIdCoche);
        txvIdCoche.setText(String.valueOf(listaCochesNuevo.getID_CocheNuevo()));

        //Convertimos la imagen de byte a un Array de bytes
        ByteArrayInputStream imageStream = new ByteArrayInputStream(listaCochesNuevo.getFoto());
        Bitmap imagen = BitmapFactory.decodeStream(imageStream);

        //Mostramos la imagen
        ImageView imgFoto = (ImageView) v.findViewById(R.id.imgFotoNuevo);
        imgFoto.setImageBitmap(imagen);

        //Mostramos la marca del coche
        TextView txvMarca = (TextView) v.findViewById(R.id.txvMarca);
        txvMarca.setText(listaCochesNuevo.getMarca());

        //Mostramos el modelo del coche
        TextView txvModelo = (TextView) v.findViewById(R.id.txvModelo);
        txvModelo.setText(listaCochesNuevo.getModelo());

        //Mostramos el precio del coche
        TextView txvPrecio = (TextView) v.findViewById(R.id.txvPrecio);
        txvPrecio.setText(String.valueOf(listaCochesNuevo.getPrecio()));

        return v;
    }
}