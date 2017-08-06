package iboxbd.broadband.statistics;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import iboxbd.broadband.statistics.sqlite.Connection;

/**
 * Created by User on 8/6/2017.
 */

public class ServiceStarter extends BroadcastReceiver {
    private AlarmManager    manager;
    private Intent          alarmIntent;
    private PendingIntent   pendingIntent;
    private int             interval;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.print("Feedback : #002 Service Receiver Started");
        Toast.makeText(context,"BCS Started",Toast.LENGTH_LONG).show();
        //context.startService(new Intent(context, ConnectionService.class));
        manager         = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmIntent     = new Intent(context, ConnectionService.class);
        pendingIntent   = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        interval        = 5 * 60 * 1000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
    }
}