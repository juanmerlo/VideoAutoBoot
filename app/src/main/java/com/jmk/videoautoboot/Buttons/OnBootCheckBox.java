package com.jmk.videoautoboot.Buttons;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.jmk.videoautoboot.MainActivity;

/**
 * Created by juanmartin on 24/4/2017.
 */

public class OnBootCheckBox extends android.support.v7.widget.AppCompatCheckBox implements CompoundButton.OnCheckedChangeListener {

    MainActivity context;
    public OnBootCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = (MainActivity) context;

        SharedPreferences sharedPreferences = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        boolean onBoot = sharedPreferences.getBoolean("onBoot",false);

        setChecked(onBoot);

        setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("datos",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(isChecked()){
            editor.putBoolean("onBoot",true);
        }else {
            editor.putBoolean("onBoot",false);
        }
        editor.commit();
    }
}
