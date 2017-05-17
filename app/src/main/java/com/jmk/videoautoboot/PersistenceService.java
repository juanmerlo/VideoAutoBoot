package com.jmk.videoautoboot;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class PersistenceService extends Service {

    TimerTask timerTask;
    Timer timer = new Timer();
    NotificationManager mNotifyManager;
    NotificationCompat.Builder builder;
    int id = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Se construye la notificación
        builder = new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        builder.setSmallIcon(android.R.drawable.ic_media_play);
        builder.setContentTitle(getResources().getString(R.string.reproduciendo));
        builder.setTicker(getResources().getString(R.string.reproduciendo));
        startForeground(id,builder.build());
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

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
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
