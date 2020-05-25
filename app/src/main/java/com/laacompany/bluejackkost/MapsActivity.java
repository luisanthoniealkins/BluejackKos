package com.laacompany.bluejackkost;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String EXTRA_LAT = "extra_latitude";
    private static final String EXTRA_LNG = "extra_longitude";
    private static final String EXTRA_NAME = "extra_name";

    public static Intent newIntent(Context packageContext, double latitude, double longitude, String name){
        Intent intent = new Intent(packageContext, MapsActivity.class);
        intent.putExtra(EXTRA_LAT, latitude);
        intent.putExtra(EXTRA_LNG, longitude);
        intent.putExtra(EXTRA_NAME, name);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double latitude = getIntent().getDoubleExtra(EXTRA_LAT,0);
        double longitude = getIntent().getDoubleExtra(EXTRA_LNG, 0);
        String name = getIntent().getStringExtra(EXTRA_NAME);
        LatLng bHouse = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(bHouse).title(name));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(bHouse));
        googleMap.setMinZoomPreference(14);
    }

}
