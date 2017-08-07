package iboxbd.broadband.statistics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import iboxbd.broadband.statistics.model.LogData;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import iboxbd.broadband.statistics.utils.DateUtils;

public class RepeatReceiver extends BroadcastReceiver {
    private DatabaseHelper  dbh;
    @Override
    public void onReceive(Context context, Intent intent) {
        dbh = new DatabaseHelper(context);
        System.out.print("Feedback : #004 Repeat Receiver Started");
        Toast.makeText(context,"BCS Started",Toast.LENGTH_LONG).show();
        dbh.createLOG(new LogData("#004","false", DateUtils.getDateTime()));
        Toast.makeText(context,"Connection Service Running ........!!! ",Toast.LENGTH_LONG).show();
    }
}
