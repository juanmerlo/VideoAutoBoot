package com.jmk.videoautoboot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootUpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        boolean bootOn = sharedPreferences.getBoolean("onBoot",false);

        if(bootOn){
            Intent service = new Intent(context, PersistenceService.class);
            service.putExtra("delay",60000);
            context.startService(service);
        }

    }
}
