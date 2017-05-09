package com.jmk.videoautoboot;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.jmk.videoautoboot.Buttons.InitServiceButton;
import com.jmk.videoautoboot.Buttons.OnBootCheckBox;
import com.jmk.videoautoboot.Buttons.SelectVideoButton;
import com.jmk.videoautoboot.Buttons.StopServiceButton;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Serializable {

    public static int REQUEST_TAKE_GALLERY_VIDEO = 1;

    @BindView(R.id.selectVideo) SelectVideoButton selectVideo;
    @BindView(R.id.iniciarServicio) InitServiceButton iniciarServicio;
    @BindView(R.id.pararServicio) StopServiceButton pararServicio;
    @BindView(R.id.onBootCheck) OnBootCheckBox onBootCheck;

    @BindString(R.string.noEstaElVideo) String noEstaElVideoString;

    @BindViews({R.id.iniciarServicio, R.id.pararServicio, R.id.onBootCheck}) List<View> vistas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        
    }

    private void init() {

        SharedPreferences sharedPreferences = getSharedPreferences("datos",Context.MODE_PRIVATE);
        String pathVideo = sharedPreferences.getString("pathVideo","");

        if(!pathVideo.equals("")){

            File video = new File(pathVideo);
            selectVideo.setText(pathVideo);

            if(video.exists()){

                if(isMyServiceRunning(PersistenceService.class)){
                    ButterKnife.apply(iniciarServicio,ENABLED,false);
                    ButterKnife.apply(pararServicio,ENABLED,true);
                    ButterKnife.apply(onBootCheck,ENABLED,true);

                }else {
                    ButterKnife.apply(iniciarServicio,ENABLED,true);
                    ButterKnife.apply(pararServicio,ENABLED,false);
                    ButterKnife.apply(onBootCheck,ENABLED,true);

                }

            }else{
                /*ButterKnife.apply(iniciarServicio,ENABLED,false);
                ButterKnife.apply(pararServicio,ENABLED,false);
                ButterKnife.apply(onBootCheck,ENABLED,false);*/
                ButterKnife.apply(vistas,ENABLED,false);
                Toast.makeText(this,noEstaElVideoString,Toast.LENGTH_LONG).show();
            }

        }else{
            /*ButterKnife.apply(iniciarServicio,ENABLED,false);
            ButterKnife.apply(pararServicio,ENABLED,false);
            ButterKnife.apply(onBootCheck,ENABLED,false);*/
            ButterKnife.apply(vistas,ENABLED,false);
        }

        pararServicio.setInitServiceButton(iniciarServicio);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {

                    SharedPreferences sharedPreferences = getSharedPreferences("datos",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("pathVideo",selectedImagePath);
                    editor.commit();

                    selectVideo.setText(selectedImagePath);

                    ButterKnife.apply(iniciarServicio,ENABLED,true);
                    ButterKnife.apply(onBootCheck,ENABLED,true);

                }
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    static final ButterKnife.Setter<View, Boolean> ENABLED = new ButterKnife.Setter<View, Boolean>() {
        @Override public void set(View view, Boolean value, int index) {
            view.setEnabled(value);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 2 && grantResults[0] == 0){
            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("video/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("video/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

            startActivityForResult(chooserIntent, REQUEST_TAKE_GALLERY_VIDEO);
        }

    }
}
