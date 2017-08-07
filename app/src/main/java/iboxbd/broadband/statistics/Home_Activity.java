package iboxbd.broadband.statistics;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        try{

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


            _dbHelper.close();
        }catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        Button databaseButton   = (Button) findViewById(R.id.database);
        Button startAlarm       = (Button) findViewById(R.id.alarmStart);
        Button stopAlarm        = (Button) findViewById(R.id.alarmStop);

        databaseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent dbmanager = new Intent(Home_Activity.this,SqliteManager.class);
                startActivity(dbmanager);
            }
        });

        startAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartAlarm(getApplicationContext());
            }
        });

        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StopAlarm(getApplicationContext());
            }
        });

        if(ServiceCheck.isMyServiceRunning(Home_Activity.this,ConnectionService.class)){
            Toast.makeText(this,"Connection Service Running ........!!! ",Toast.LENGTH_LONG).show();
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

    public void StartAlarm(Context context)    {
        /*AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, RepeatReceiver.class);
        //intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 5 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60  , pi);*/
        _dbHelper = new DatabaseHelper(context);
        System.out.print("Feedback : #004 Repeat Receiver Started");
        Toast.makeText(context,"Feedback : #004 Repeat Receiver Started",Toast.LENGTH_LONG).show();
        _dbHelper.createLOG(new LogData("#004","false", DateUtils.getDateTime()));

        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, RepeatReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +                        60 * 1000, alarmIntent);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,System.currentTimeMillis() , 60 * 1000 , alarmIntent);
        //alarmMgr.setInexactRepeating();

    }

    public void StopAlarm(Context context)    {
        Intent intent = new Intent(context, RepeatReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

}
