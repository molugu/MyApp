package com.example.incresol_078.myapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Contacts;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    static int countPowerOff = 0;
    private Activity activity = null;

public MyReceiver(){
        super();
    }

    public MyReceiver(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v("onReceive", "Power button is pressed.");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            countPowerOff++;
        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            if (countPowerOff == 3) {
                Log.i("onReceive", "insdie screen on");

                    Intent map_intent=new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0"));
                    map_intent.setClassName("com.google.android.apps.maps","com.google.android.maps.MapsActivity");
                    activity.startActivity(map_intent);
            }
        }

    }
}