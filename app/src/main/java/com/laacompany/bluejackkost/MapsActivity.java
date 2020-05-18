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

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double latitude = getIntent().getDoubleExtra(EXTRA_LAT,0);
        double longitude = getIntent().getDoubleExtra(EXTRA_LNG, 0);
        String name = getIntent().getStringExtra(EXTRA_NAME);
        // Add a marker in Sydney and move the camera
        LatLng bHouse = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(bHouse).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bHouse));
        mMap.setMinZoomPreference(13);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }

}
