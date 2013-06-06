package com.ujs.datashow2.jni;

import com.ujs.datashow2.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MyJni extends Activity{

    private TextView tvJni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_jni);
        
        tvJni = (TextView)findViewById(R.id.tv_jni_show);
        tvJni.setText(stringFromJni());
    }
    
    public native String stringFromJni();
    
    static{
        System.loadLibrary("module1");
    }
}
