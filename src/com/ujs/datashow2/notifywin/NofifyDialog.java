package com.ujs.datashow2.notifywin;

import com.ujs.datashow2.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

/**
 * 一个窗口
 * 
 * @author zouning
 * 
 */
public class NofifyDialog implements OnClickListener {
    private Context mContext;
    private Dialog dialog;
    public NofifyDialog(Activity context) {
        // TODO Auto-generated constructor stub
        mContext = context;
        LayoutInflater inflater = context.getLayoutInflater();
        View layout = inflater.inflate(R.layout.nofify_dialog, null);
        
        layout.findViewById(R.id.textView_cancle).setOnClickListener(this);
        dialog = new AlertDialog.Builder(mContext).setTitle("自定义布局").setView(layout).create();
        //重新设置dialog的位置
        Window window = dialog.getWindow();
        LayoutParams params = window.getAttributes();
        params.y = -1000;//往上移,负数
        dialog.onWindowAttributesChanged(params);
        
        dialog.show();
        
    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        if (id == R.id.textView_cancle) {
            if(dialog != null)
                dialog.dismiss();
        }
    }
    
    

}
