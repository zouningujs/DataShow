package com.ujs.datashow2.notifywin;

import com.ujs.datashow2.R;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
/**
 * 通知弹出对话框
 * @author zouning
 *
 */
public class NotifyDialog2 extends Dialog implements android.view.View.OnClickListener{
    public static String ACTION_NOTIFIDIALOG = "com.ujs.datashow2.notifywin";
    private int notifyId;
    private Context mContext;

    public NotifyDialog2(Context context, int notifyId) {
        super(context);
        // TODO Auto-generated constructor stub
        this.notifyId = notifyId;
        mContext = context;
        setContentView(R.layout.nofify_dialog);
        findViewById(R.id.textView_cancle).setOnClickListener(this);
        
        //设置位置
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.y = -1000;
        onWindowAttributesChanged(layoutParams);
        
        
    }
    
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        if (id == R.id.textView_cancle) {
            dismiss();
            NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.cancel(notifyId);
        }
    }

}
