package com.example.brekkishhh.drivesmart.Utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by Brekkishhh on 24-07-2016.
 */
public class ReverseGeoCoding extends AsyncTask<Void,Void,Address> {

    Geocoder geocoder;
    LatLng userLatLng;
    ReverseGeoCodingCallBack callBack;
    Context context;


    public ReverseGeoCoding(Context context,LatLng userLatLng, ReverseGeoCodingCallBack callBack) {
        this.userLatLng = userLatLng;
        this.callBack = callBack;
        geocoder = new Geocoder(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Address doInBackground(Void... params) {

        try{
            List<Address> addresses = geocoder.getFromLocation(userLatLng.latitude,userLatLng.longitude,5);
            return addresses.isEmpty() ?null:addresses.get(0);
        }catch (IOException e){
            return null;
        }
    }

    @Override
    protected void onPostExecute(Address address) {
        super.onPostExecute(address);

        if (address==null){
            callBack.onReverseGeoCodingFailed();
        }
        else {
            callBack.onReverseGeoCoded(address);
        }


    }
}
