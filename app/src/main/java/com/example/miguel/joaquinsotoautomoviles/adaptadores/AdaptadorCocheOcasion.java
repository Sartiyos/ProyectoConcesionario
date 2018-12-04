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

public class AdaptadorCocheOcasion extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Coche> items;

    public AdaptadorCocheOcasion(Activity activity, ArrayList<Coche> items) {
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
            v = inf.inflate(R.layout.items_ocasion, null);
        }

        Coche listaCochesOcasion = items.get(position);

        ByteArrayInputStream imageStream = new ByteArrayInputStream(listaCochesOcasion.getFoto());
        Bitmap imagen= BitmapFactory.decodeStream(imageStream);

        ImageView imgFoto = (ImageView) v.findViewById(R.id.imgFotoNuevo);
        imgFoto.setImageBitmap(imagen);

        TextView txvMarca = (TextView) v.findViewById(R.id.txvMarca);
        txvMarca.setText(listaCochesOcasion.getMarca());

        TextView txvModelo = (TextView) v.findViewById(R.id.txvModelo);
        txvModelo.setText(listaCochesOcasion.getModelo());

        TextView txvPrecio = (TextView) v.findViewById(R.id.txvPrecio);
        txvPrecio.setText(String.valueOf(listaCochesOcasion.getPrecio()));

        return v;
    }

}
