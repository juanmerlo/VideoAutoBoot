package com.jmk.videoautoboot.Buttons;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.jmk.videoautoboot.MainActivity;
import com.jmk.videoautoboot.R;

/**
 * Created by juanmartin on 24/4/2017.
 */

public class SelectVideoButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener{

    MainActivity context;

    public SelectVideoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =(MainActivity) context;
        setOnClickListener(this);

        setText(getResources().getString(R.string.select_video));
    }

    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            context.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);

        }else {

            Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
            getIntent.setType("video/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("video/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

            context.startActivityForResult(chooserIntent, context.REQUEST_TAKE_GALLERY_VIDEO);
        }
    }



}
