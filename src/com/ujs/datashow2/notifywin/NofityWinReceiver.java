package com.ujs.datashow2.notifywin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NofityWinReceiver extends BroadcastReceiver{
    
    private static final String tag = "NofityWinReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.d(tag, "onReceive");
        if (!NotifyDialog2.ACTION_NOTIFIDIALOG.equals(intent.getAction())) {
            Log.d(tag, "action not filter:" + intent.getAction());
            return;
        }
        if(context instanceof Activity){
            Log.d(tag, "context is activity");
            NotifyDialog2 dialog2 = new NotifyDialog2((Activity)context, 0);
            dialog2.show();
        }else {
            NotifyDialog2 dialog2 = new NotifyDialog2(context.getApplicationContext(), 0);
            dialog2.show();
            Log.w(tag, "context isn't activity");
        }
        
        
        
    }

}
