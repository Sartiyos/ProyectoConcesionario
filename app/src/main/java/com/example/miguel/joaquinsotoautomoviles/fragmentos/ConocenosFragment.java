package com.example.miguel.joaquinsotoautomoviles.fragmentos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miguel.joaquinsotoautomoviles.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ConocenosFragment extends Fragment {

    private SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_maps, container, false);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng empresa = new LatLng(37.4003359, -4.7536589);
                    googleMap.addMarker(new MarkerOptions().position(empresa).title("Joaquín Soto Automoviles"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(empresa, 15));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(empresa));
                }
            });
        }

        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        return rootView;
    }
}
