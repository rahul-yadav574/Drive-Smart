package com.example.brekkishhh.drivesmart.Activities;

import android.location.LocationManager;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.brekkishhh.drivesmart.R;
import com.example.brekkishhh.drivesmart.Utils.Tracking;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Landing extends AppCompatActivity implements OnMapReadyCallback{


    private MapFragment mainMap;
    private Tracking tracking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        mainMap = MapFragment.newInstance();         //getting instance of the map object
        mainMap.getMapAsync(this);         //registering onMapReadyCallback
        this.addMapToLayout();                   //this method adds the map to the layout
        tracking = new Tracking(Landing.this);


    }

    private void addMapToLayout(){

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mapContainer,mainMap).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       // googleMap.addMarker(new MarkerOptions().position(new LatLng(28.1881,76.6115)).title("Hey Map Is Working"));
        LatLng userLatLng = tracking.fetchUserLocation();
        googleMap.addMarker(new MarkerOptions().position(userLatLng).title("Hey I am Getting User's Position"));


    }
}
