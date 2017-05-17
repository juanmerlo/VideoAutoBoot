package com.jmk.videoautoboot.Buttons;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.jmk.videoautoboot.MainActivity;
import com.jmk.videoautoboot.PersistenceService;
import com.jmk.videoautoboot.R;

/**
 * Created by juanmartin on 24/4/2017.
 */

public class StopServiceButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener{

    MainActivity context;
    InitServiceButton initServiceButton;

    public StopServiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        this.context = (MainActivity) context;
    }

    @Override
    public void onClick(View v) {
        context.stopService(new Intent(context,PersistenceService.class));
        setEnabled(false);
        initServiceButton.setEnabled(true);
        Toast.makeText(context,getResources().getString(R.string.servicioDetenido),Toast.LENGTH_SHORT).show();
    }

    public void setInitServiceButton(InitServiceButton initServiceButton){
        this.initServiceButton = initServiceButton;
    }
}
