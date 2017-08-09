package iboxbd.broadband.statistics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import iboxbd.broadband.statistics.model.LogData;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;

public class RepeatReceiver extends BroadcastReceiver {
    private DatabaseHelper  dbh;
    @Override
    public void onReceive(Context context, Intent intent) {
        dbh = new DatabaseHelper(context);
        try{
            Intent intentService = new Intent(context,ConnectionService.class);
            context.startService(intentService);
            Log.i("#003","Repeat Receiver Started");
        }catch (Exception e){
            dbh.createLOG(new LogData("#903"));
            dbh.close();
            Log.i("#903","Repeat Receiver Error!! "+e.getMessage());
            Toast.makeText(context,"Repetition problem occurred!! .. ",Toast.LENGTH_LONG).show();
        }
    }
}
