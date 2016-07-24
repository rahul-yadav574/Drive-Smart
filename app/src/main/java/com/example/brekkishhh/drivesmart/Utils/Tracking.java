package com.example.brekkishhh.drivesmart.Utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.brekkishhh.drivesmart.Activities.Landing;
import com.example.brekkishhh.drivesmart.R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.model.LatLng;

import java.security.Permission;

/**
 * Created by Brekkishhh on 23-07-2016.
 */
public class Tracking {

    private Context context;
    private LocationManager locationManager;
    private static final String TAG = "Tracking";
    private LocationListener gpsLocationListener;
    private LocationListener networkLocationListener;


    public Tracking(Context context) {
        this.context =context;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        gpsLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG,"Gps new Location is : "+location.getLatitude()+" "+location.getLongitude());
               // locationManager.removeUpdates(gpsLocationListener);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        networkLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG,"Gps new Location is : "+location.getLatitude()+" "+location.getLongitude());


              //  locationManager.removeUpdates(networkLocationListener);

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


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
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 10, gpsLocationListener);
//                    Log.d(TAG,gpsLocation.toString());

                }catch (SecurityException ex){
                    Log.e(TAG,"Error : "+ ex.getMessage());
                }
            }

            if (isNetworkAvailable()){
                try {
                    networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 200, 10, networkLocationListener);
                  //  Log.d(TAG,networkLocation.toString());

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

        UtilClass.toastS(context,"Unable To Get User Location");
        return null;

    }

    private boolean isGpsAvailable() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private boolean isNetworkAvailable() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    private AlertDialog openLocationSettingsDialog(){

        return new AlertDialog.Builder(context)
                .setCancelable(false)
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
