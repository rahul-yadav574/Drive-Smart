package com.example.brekkishhh.drivesmart.Services;

import android.app.IntentService;
import android.content.Intent;

import com.example.brekkishhh.drivesmart.Utils.UtilClass;

/**
 * Created by Brekkishhh on 24-07-2016.
 */
public class MessageService extends IntentService {

    public MessageService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String contactName = intent.getStringExtra("contactName");
        UtilClass.toastL(this,"We Have Informed : "+ contactName);

    }
}
