package iboxbd.broadband.statistics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import iboxbd.broadband.statistics.model.LogData;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import iboxbd.broadband.statistics.sqlite.SqliteManager;
import iboxbd.broadband.statistics.utils.DateUtils;

public class RepeatReceiver extends BroadcastReceiver {
    private DatabaseHelper  dbh;
    @Override
    public void onReceive(Context context, Intent intent) {
        dbh = new DatabaseHelper(context);
        System.out.println("Feedback : #005 Repeat Receiver Started");
        Log.i("#005","Repeat Receiver Started");
        Toast.makeText(context,"Repeat Receiver Started",Toast.LENGTH_LONG).show();
        dbh.createLOG(new LogData("#005","false", DateUtils.getDateTime()));
        Toast.makeText(context,"Connection Service Running ........!!! ",Toast.LENGTH_LONG).show();
        dbh.close();
        Intent intentService = new Intent(context,ConnectionService.class);
        context.startService(intentService);
    }
}
