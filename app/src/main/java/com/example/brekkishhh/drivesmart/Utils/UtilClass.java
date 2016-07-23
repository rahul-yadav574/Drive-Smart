package com.example.brekkishhh.drivesmart.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Brekkishhh on 23-07-2016.
 */
public class UtilClass {

    public static void toastS(Context context,String msg){
        Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
    }
    public static void toastL(Context context,String msg){
        Toast.makeText(context, ""+msg, Toast.LENGTH_LONG).show();
    }
}
