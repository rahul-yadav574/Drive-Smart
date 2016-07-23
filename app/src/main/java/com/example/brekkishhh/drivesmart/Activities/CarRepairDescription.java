package com.example.brekkishhh.drivesmart.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.brekkishhh.drivesmart.R;
import com.google.android.gms.maps.model.LatLng;

public class CarRepairDescription extends AppCompatActivity {

    private LatLng carRepairLocation;
    private static final String TAG =  "Car Description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_car_repair_description);

        String intentExtra = getIntent().getStringExtra("latlng");
        int x = intentExtra.indexOf(",");
        carRepairLocation = new LatLng(Double.parseDouble(intentExtra.substring(0,x)),Double.parseDouble(intentExtra.substring(x+1,intentExtra.length())));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
