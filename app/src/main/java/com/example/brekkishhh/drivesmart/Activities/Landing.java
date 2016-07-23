package com.example.brekkishhh.drivesmart.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.brekkishhh.drivesmart.R;
import com.example.brekkishhh.drivesmart.Utils.Tracking;
import com.example.brekkishhh.drivesmart.Utils.UtilClass;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Landing extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {


    private MapFragment mainMap;
    private Tracking tracking;
    private static final String TAG = "Landing Activity";
    private GoogleMap googleMap;

    private int radiusOfSearch = 20;     //this radius is in kms.
    private LatLng userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        mainMap = MapFragment.newInstance();         //getting instance of the map object
        mainMap.getMapAsync(this);         //registering onMapReadyCallback
        this.addMapToLayout();                   //this method adds the map to the layout
        tracking = new Tracking(Landing.this);


    }

    private void addMapToLayout() {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mapContainer, mainMap).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        userLocation = tracking.fetchUserLocation();


        if (userLocation == null) {
            return;
        }
        googleMap.addMarker(new MarkerOptions()
                .position(userLocation)
                .title("I am Stuck Here")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        zoomToMarker(userLocation);

        getNearbyCarServices(userLocation);
    }

    public void getNearbyCarServices(LatLng userLatLang) {

        String placeSearchApiUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + userLatLang.latitude + "," + userLatLang.longitude + "&radius=" + radiusOfSearch + "000&type=car_repair&key=" + getString(R.string.place_search_api_key);
        OkHttpClient httpClient = new OkHttpClient();
        final List<LatLng> responses = new ArrayList<>();

        //Log.d(TAG,placeSearchApiUrl);

        httpClient.newCall(new Request.Builder()
                .url(placeSearchApiUrl)
                .build()).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UtilClass.toastL(Landing.this, "Unable to get nearby car repair services");
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {
                JsonArray locationArray = null;
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(response.body().charStream()).getAsJsonObject();

                locationArray = jsonObject.getAsJsonArray("results");
                for (int objects = 0; objects < locationArray.size(); objects++) {
                    JsonObject geometry = locationArray.get(objects).getAsJsonObject().getAsJsonObject("geometry");
                    JsonObject location = geometry.getAsJsonObject("location");
                    double latitude = location.get("lat").getAsDouble();
                    double longitude = location.get("lng").getAsDouble();
                    responses.add(new LatLng(latitude, longitude));
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateMapWithNewCentres(responses);
                    }
                });

            }
        });
    }

    private void updateMapWithNewCentres(List<LatLng> carServices) {

        //Calculating Bounds For Making Camera TO Fit for all markers
        LatLngBounds.Builder boundBuilder = new LatLngBounds.Builder();

        for (LatLng pos : carServices) {
            boundBuilder.include(pos);
            googleMap.addMarker(new MarkerOptions()
                    .title("I am here to help you")
                    .position(pos)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }

        if (carServices.size() != 0) {
            LatLngBounds bounds = boundBuilder.build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 100, 100, 5);
            googleMap.animateCamera(cameraUpdate);
        }
    }

    private void zoomToMarker(LatLng marker) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(marker)
                .zoom(10)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_landing, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.changeRadius) {

            final EditText editText = new EditText(Landing.this);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);

            AlertDialog dialog = new AlertDialog.Builder(Landing.this)
                    .setMessage("Input Radius For The Search in kms.")
                    .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (editText.getText().toString().length() == 0 || editText.getText().toString().equals("")) {
                                return;
                            }

                            radiusOfSearch = Integer.parseInt(editText.getText().toString());
                            getNearbyCarServices(userLocation);
                        }
                    }).create();

            dialog.setView(editText);

            dialog.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {



        LatLng markerLocation = marker.getPosition();

        String extra = markerLocation.latitude+","+markerLocation.longitude;
        Log.d(TAG,extra);
        Intent showDescription = new Intent(Landing.this,CarRepairDescription.class);
        showDescription.putExtra("latlng",extra);
        startActivity(showDescription);
        return true;
    }
}
