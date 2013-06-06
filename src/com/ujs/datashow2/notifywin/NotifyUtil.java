package com.ujs.datashow2.notifywin;

import com.ujs.datashow2.DataShow2Activity;
import com.ujs.datashow2.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class NotifyUtil {
    public static void notify(Activity context){
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notificion = new Notification();
        notificion.icon = R.drawable.ic_launcher;
        
        notificion.defaults = Notification.DEFAULT_ALL;
        
        /*
        Intent intent = new Intent(NotifyDialog2.ACTION_NOTIFIDIALOG);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 101, intent, 0);
        notificion.setLatestEventInfo(context, "title", "text", pendingIntent);
        nm.notify(0, notificion);
       
        //方案2
        NotifyDialog2 dialog2 = new NotifyDialog2((Context)context,0);
        dialog2.show();
        */
        
        Intent intent2 = new Intent(context,NotifyDialog3.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 101, intent2, 0);
        notificion.setLatestEventInfo(context, "title", "text", pendingIntent);
        nm.notify(0,notificion);
        
        context.startActivity(intent2);
        
    }
}
