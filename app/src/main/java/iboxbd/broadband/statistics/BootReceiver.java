package iboxbd.broadband.statistics;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import iboxbd.broadband.statistics.model.LogData;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import static android.content.Context.ALARM_SERVICE;

/**
 * Created by User on 8/6/2017.
 */

public class BootReceiver extends BroadcastReceiver {
    private AlarmManager    alarmManager;
    private Intent          alarmIntent;
    private PendingIntent   pendingIntent;
    private long            interval;
    private DatabaseHelper  dbh;

    @Override
    public void onReceive(Context context, Intent intent) {
        interval            = 5 * 60 * 1000;
        dbh                 = new DatabaseHelper(context);
        try{
            alarmIntent             = new Intent(context, RepeatReceiver.class);
            pendingIntent           = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
            alarmManager            = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

            dbh.createLOG(new LogData("#002"));
            dbh.close();
            Log.i("#002", "Boot Receiver Started");
        }catch (Exception e){
            dbh.createLOG(new LogData("#902"));
            dbh.close();
            Log.i("#902", "Boot Receiver Error!! "+e.getMessage());
            Toast.makeText(context,"Problem Loading BCStatistics.... ",Toast.LENGTH_LONG).show();
        }
    }
}