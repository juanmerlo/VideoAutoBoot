package com.jmk.videoautoboot.Buttons;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;

import com.jmk.videoautoboot.MainActivity;
import com.jmk.videoautoboot.PersistenceService;

/**
 * Created by juanmartin on 24/4/2017.
 */

public class InitServiceButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener{

    MainActivity context;

    public InitServiceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context =(MainActivity) context;
        setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent service = new Intent(context,PersistenceService.class);
        context.startService(service);
        context.finish();
    }

}

