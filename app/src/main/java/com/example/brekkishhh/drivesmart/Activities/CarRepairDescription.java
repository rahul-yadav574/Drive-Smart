package com.example.brekkishhh.drivesmart.Activities;

import android.location.Address;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.brekkishhh.drivesmart.R;
import com.example.brekkishhh.drivesmart.Utils.ReverseGeoCoding;
import com.example.brekkishhh.drivesmart.Utils.ReverseGeoCodingCallBack;
import com.example.brekkishhh.drivesmart.Utils.UtilClass;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.okhttp.internal.Util;

public class CarRepairDescription extends AppCompatActivity implements ReverseGeoCodingCallBack {

    private LatLng carRepairLocation;
    private static final String TAG =  "Car Description";
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_car_repair_description);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        String intentExtra = getIntent().getStringExtra("latlng");
        int x = intentExtra.indexOf(",");
        carRepairLocation = new LatLng(Double.parseDouble(intentExtra.substring(0,x)),Double.parseDouble(intentExtra.substring(x+1,intentExtra.length())));

        new ReverseGeoCoding(CarRepairDescription.this,carRepairLocation,this).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReverseGeoCoded(Address userAddress) {
        progressBar.setVisibility(View.GONE);

        //TODO: Here we have To show the info to user in a proper manner with image if available
    }

    @Override
    public void onReverseGeoCodingFailed() {
        UtilClass.toastL(CarRepairDescription.this,"Unable To Fetch Info");
        this.finish();
    }
}
