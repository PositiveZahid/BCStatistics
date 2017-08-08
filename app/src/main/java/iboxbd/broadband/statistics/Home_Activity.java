package iboxbd.broadband.statistics;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import iboxbd.broadband.statistics.model.LogData;
import iboxbd.broadband.statistics.phone.ServiceCheck;
import iboxbd.broadband.statistics.sqlite.SqliteManager;
import iboxbd.broadband.statistics.model.Connection;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import iboxbd.broadband.statistics.utils.DateUtils;


public class Home_Activity extends AppCompatActivity {

    private DatabaseHelper _dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_home);
        GetDB();

        //Connection d = new Connection();
        try {

            Connection c = new Connection();
            c.setDateTime(DateUtils.getDateTime());
            c.setIsConnected("false");
            c.setIsSynced("false");
            //_dbHelper.createConnection(c);

            Connection c1 = new Connection();
            c1.setDateTime(DateUtils.getDateTime());
            c1.setIsConnected("false");
            c1.setIsSynced("false");
            //_dbHelper.createConnection(c1);

            //List<Connection> dataList = new ArrayList<Connection>();
            //dataList.addAll(_dbhandler.GetTableData(Connection.class));
            //dataList = _dbHelper.getAllConnections();


            //for (Connection temp : _dbHelper.getAllConnections()) {
            //    Toast.makeText(this,temp.getID().toString(),Toast.LENGTH_LONG).show();
            //temp.ID
            //}
            //dd = _dbhandler.GetValue()


            //_dbHelper.close();
            Toast.makeText(this, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), Toast.LENGTH_LONG).show();
            //Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Button databaseButton = (Button) findViewById(R.id.database);
        //Button startAlarm       = (Button) findViewById(R.id.alarmStart);
        //Button stopAlarm        = (Button) findViewById(R.id.alarmStop);

        /*databaseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(Home_Activity.this, SqliteManager.class);
                startActivity(dbmanager);
            }
        });*/

        /*startAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //StartAlarm(getApplicationContext());
                startAlert();
            }
        });*/

        /*stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopAlarm(getApplicationContext());
            }
        });*/

        if (ServiceCheck.isMyServiceRunning(Home_Activity.this, ConnectionService.class)) {
            Toast.makeText(this, "Connection Service Running ........!!! ", Toast.LENGTH_LONG).show();
        }
    }

    public DatabaseHelper GetDB() {
        if (_dbHelper == null) {
            InitDB();
        }
        return _dbHelper;
    }

    private void InitDB() {
        //Initialize DB handler
        _dbHelper = new DatabaseHelper(this);
        //Adding table based on class TEST reflection
        //_dbHelper.CreateTable(new Connection());
    }

    public void StartAlarm(Context context) {
        /*AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, RepeatReceiver.class);
        //intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 5 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60  , pi);*/
        _dbHelper = new DatabaseHelper(context);
        System.out.print("Feedback : #004 Repeat Receiver Started");
        Log.i("#004", "Repeat Receiver Started");
        Toast.makeText(context, "Feedback : #004 Repeat Receiver Started", Toast.LENGTH_LONG).show();
        _dbHelper.createLOG(new LogData("#004", "false", DateUtils.getDateTime()));

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, RepeatReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +                        60 * 1000, alarmIntent);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, System.currentTimeMillis(), 60 * 1000, alarmIntent);
        //alarmMgr.setInexactRepeating();

    }

    public void StopAlarm(Context context) {
        Intent intent = new Intent(context, RepeatReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    public void startAlert() {
        //EditText text = (EditText) findViewById(R.id.time);
        /*int i = Integer.parseInt("10");
        Intent intent = new Intent(this, RepeatReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), pendingIntent);*/

        Intent broadcastIntent = new Intent(Home_Activity.this, RepeatReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(this, 0, broadcastIntent, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeInterval = 2 * 60 * 1000;

        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), timeInterval, pi);
        //am.

        Toast.makeText(this, "Alarm set in " + timeInterval + " seconds", Toast.LENGTH_LONG).show();
        _dbHelper = new DatabaseHelper(this.getApplicationContext());
        System.out.println("Feedback : #004 Home Button Clicked");
        Log.i("#004", "Home Button Clicked");
        Toast.makeText(this.getApplicationContext(), "Feedback : #004 Home Button Clicked", Toast.LENGTH_LONG).show();
        _dbHelper.createLOG(new LogData("#004", "false", DateUtils.getDateTime()));
        _dbHelper.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Menu item cliecked", Toast.LENGTH_LONG).show();
                Intent dbmanager = new Intent(Home_Activity.this, SqliteManager.class);
                startActivity(dbmanager);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

}
