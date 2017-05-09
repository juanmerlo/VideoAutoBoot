package com.jmk.videoautoboot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {

    @BindView(R.id.video) VideoView videoView;

    String pathVideo;

    static boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);


        initPathVideo();
        initVideo();
        avisoInicio();

    }



    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }


    @Override
    public void onBackPressed() {
        stopService(new Intent(this, PersistenceService.class));
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    private void avisoInicio(){
        Toast.makeText(this,getResources().getString(R.string.backParaSalir),Toast.LENGTH_LONG).show();
    }

    private void initPathVideo() {


        SharedPreferences sharedPreferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        pathVideo = sharedPreferences.getString("pathVideo","");

        File video = new File(pathVideo);
        if(!video.exists()){
            Toast.makeText(VideoActivity.this,getResources().getString(R.string.noEstaElVideo),Toast.LENGTH_LONG).show();
            finish();
        }

    }

    private void initVideo(){

        String uriPath = pathVideo;
        final Uri uri = Uri.parse(uriPath);
        MediaController mediaController = new MediaController(this);


        videoView.setMediaController(mediaController);

        try {
            videoView.setVideoURI(uri);
            videoView.requestFocus();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();

            }
        });

        //Para Android 4.4.2 por bug
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                videoView.setVideoURI(uri);
                videoView.start();
            }
        });

    }






}
