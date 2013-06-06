package com.ujs.datashow2.notifywin;

import com.ujs.datashow2.R;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

/**
 * android:theme="@android:style/Theme.Dialog"
 * 
 * @author zouning
 * 
 */
public class NotifyDialog3 extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nofify_dialog);
        findViewById(R.id.textView_cancle).setOnClickListener(this);
        
        // 设置位置
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.y = -1000;
        onWindowAttributesChanged(layoutParams);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        if (id == R.id.textView_cancle) {
            
            this.finish();
        }
    }

}
