package com.example.brekkishhh.drivesmart.Utils;

import android.location.Address;

/**
 * Created by Brekkishhh on 24-07-2016.
 */
public interface ReverseGeoCodingCallBack {

    void onReverseGeoCoded(Address userAddress);
    void onReverseGeoCodingFailed();
}
