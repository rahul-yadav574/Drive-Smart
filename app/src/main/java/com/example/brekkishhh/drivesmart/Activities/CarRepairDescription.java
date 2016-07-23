package com.example.brekkishhh.drivesmart.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.brekkishhh.drivesmart.R;
import com.google.android.gms.maps.model.LatLng;

public class CarRepairDescription extends AppCompatActivity {

    private LatLng carRepairLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_repair_description);

        String intentExtra = getIntent().getStringExtra("latlng");
        int x = intentExtra.indexOf(",");
        double lat = Double.parseDouble(intentExtra.substring(0,x-1));
        double lng = Double.parseDouble(intentExtra.substring(x+1,intentExtra.length()-1));

        carRepairLocation = new LatLng(lat,lng);

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
