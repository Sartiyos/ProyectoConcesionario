package com.example.miguel.joaquinsotoautomoviles.adaptadores;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.example.miguel.joaquinsotoautomoviles.clases.Extras;

import java.util.ArrayList;

public class AdaptadorExtras extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Extras> items;

    public AdaptadorExtras(Activity activity, ArrayList<Extras> items) {
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
            v = inf.inflate(R.layout.items_extras, null);
        }

        Extras listaExtras = items.get(position);

        TextView txvNombre = (TextView) v.findViewById(R.id.txvNombre);
        txvNombre.setText(listaExtras.getNombre());

        TextView txvPrecio = (TextView) v.findViewById(R.id.txvPrecio);
        txvPrecio.setText(String.valueOf(listaExtras.getPrecio()));

        return v;
    }

}
