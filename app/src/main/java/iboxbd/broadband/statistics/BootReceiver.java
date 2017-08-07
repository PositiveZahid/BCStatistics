package iboxbd.broadband.statistics;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

import iboxbd.broadband.statistics.model.LogData;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import iboxbd.broadband.statistics.utils.DateUtils;

/**
 * Created by User on 8/6/2017.
 */

public class BootReceiver extends BroadcastReceiver {
    private AlarmManager    manager;
    private Intent          alarmIntent;
    private PendingIntent   pendingIntent;
    private int             interval;
    private DatabaseHelper  dbh;

    @Override
    public void onReceive(Context context, Intent intent) {
        dbh = new DatabaseHelper(context);
        System.out.print("Feedback : #002 Service Receiver Started");
        Toast.makeText(context,"BCS Started",Toast.LENGTH_LONG).show();
        dbh.createLOG(new LogData("#002","false", DateUtils.getDateTime()));
        //context.startService(new Intent(context, ConnectionService.class));
        /*manager         = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmIntent     = new Intent(context, ConnectionService.class);
        pendingIntent   = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        interval        = 5 * 60 * 1000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);*/

        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent i= new Intent(context,RepeatReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 1);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), calendar.getTimeInMillis(), pi);
    }
}