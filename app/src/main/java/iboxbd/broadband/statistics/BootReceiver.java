package iboxbd.broadband.statistics;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import iboxbd.broadband.statistics.model.LogData;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import iboxbd.broadband.statistics.utils.DateUtils;

import static android.content.Context.ALARM_SERVICE;

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
        System.out.println("Feedback : #002 Boot Receiver Started");
        Toast.makeText(context,"Boot Receiver Started",Toast.LENGTH_LONG).show();
        dbh.createLOG(new LogData("#002","false", DateUtils.getDateTime()));
        //context.startService(new Intent(context, ConnectionService.class));
        /*manager         = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmIntent     = new Intent(context, ConnectionService.class);
        pendingIntent   = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
        interval        = 5 * 60 * 1000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent i= new Intent(context,RepeatReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 1);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), calendar.getTimeInMillis(), pi);*/


        Intent broadcastIntent = new Intent(context, RepeatReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(context, 0, broadcastIntent, 0);

        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);

        long timeInterval =5 * 60 * 1000;

        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timeInterval, pi);
        //am.

        /*Toast.makeText(this, "Alarm set in " + timeInterval + " seconds",Toast.LENGTH_LONG).show();
        _dbHelper = new DatabaseHelper(this.getApplicationContext());
        System.out.println("Feedback : #004 Home Button Clicked");
        Log.i("#004","Home Button Clicked");
        Toast.makeText(this.getApplicationContext(),"Feedback : #004 Home Button Clicked",Toast.LENGTH_LONG).show();
        _dbHelper.createLOG(new LogData("#004","false", DateUtils.getDateTime()));
        _dbHelper.close();*/
    }
}