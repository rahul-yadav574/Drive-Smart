package com.example.brekkishhh.drivesmart.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.nfc.Tag;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Toast;

import com.example.brekkishhh.drivesmart.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brekkishhh on 23-07-2016.
 */
public class Tracking {

    private Context context;
    private LocationManager locationManager;
    private static final String TAG = "Tracking";


    public Tracking(Context context) {
        this.context =context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public LatLng fetchUserLocation(){


        Location networkLocation = null;
        Location gpsLocation = null;

        if (!isGpsAvailable() && !isNetworkAvailable()){
            openLocationSettingsDialog().show();

        }else {
            if (isGpsAvailable()){
                try {
                    gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Log.d(TAG,gpsLocation.toString());

                }catch (SecurityException ex){
                    Log.e(TAG,"Error : "+ ex.getMessage());
                }
            }

            if (isNetworkAvailable()){
                try {
                    networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    Log.d(TAG,networkLocation.toString());

                }catch (SecurityException ex){
                    Log.e(TAG,"Error : "+ ex.getMessage());
                }
            }

            if (gpsLocation==null && networkLocation!=null){
                return new LatLng(networkLocation.getLatitude(),networkLocation.getLongitude());
            }
            else if (networkLocation == null && gpsLocation!=null){
                return new LatLng(gpsLocation.getLatitude(),gpsLocation.getLongitude());
            }
            else {
                return new LatLng(12.34,34.12);
            }
        }

        Toast.makeText(context, "Unable To Get User Location", Toast.LENGTH_SHORT).show();
        return new LatLng(0,0);

    }

    private boolean isGpsAvailable() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean isNetworkAvailable() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    private AlertDialog openLocationSettingsDialog(){

        return new AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.gps_not_enabled))
                .setTitle(context.getString(R.string.title_error))
                .setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent settingsIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(settingsIntent);
                    }
                })
                .create();
    }



}
