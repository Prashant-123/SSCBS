package com.cbs.sscbs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Tanya on 12/22/2017.
 */

public class AlertReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper =new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannel1Notification("ok","there");
        notificationHelper.getManager().notify(1,nb.build());
    }
}
