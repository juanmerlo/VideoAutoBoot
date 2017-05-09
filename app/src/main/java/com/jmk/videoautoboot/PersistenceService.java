package com.jmk.videoautoboot;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class PersistenceService extends Service {

    TimerTask timerTask;
    Timer timer = new Timer();

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(!VideoActivity.active) {
                    Intent videoIntent = new Intent(PersistenceService.this, VideoActivity.class);
                    videoIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(videoIntent);
                    Log.v("Aplicacion", "Servicio On, abriendo video");
                }else{
                    Log.v("Aplicacion", "Servicio On, video funcinando");

                }
            }
        };

        startWithDelay(intent.getIntExtra("delay",0));


        return START_STICKY;
    }

    private void startWithDelay(int delay){

        timer.scheduleAtFixedRate(timerTask,delay,3000);

    }

    @Override
    public void onDestroy() {
        timerTask.cancel();
        timer.cancel();
        timer.purge();
        stopSelf();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
