package iboxbd.broadband.statistics;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import iboxbd.broadband.statistics.ip.NetworkCall;
import iboxbd.broadband.statistics.ip.Speed;
import iboxbd.broadband.statistics.model.Connection;
import iboxbd.broadband.statistics.model.TimeCounting;
import iboxbd.broadband.statistics.phone.Connectivity;
import iboxbd.broadband.statistics.sqlite.ExternalDatabase;
import iboxbd.broadband.statistics.sqlite.SqliteManager;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import iboxbd.broadband.statistics.sqlite.SqliteStorage;
import iboxbd.broadband.statistics.utils.DateUtils;

import static iboxbd.broadband.statistics.sqlite.SqliteBackup.backupDatabase;

public class Home_Activity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static long tureSeconds     =0;
    private static long falseSeconds    = 0;
    private static float truePercentage = 0;
    private static float falsePercentage = 0;
    private DatabaseHelper _dbHelper;
    private ExternalDatabase externalDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        externalDatabase = new ExternalDatabase(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.home_info);

        GetDB();

        try {
            table();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                //Toast.makeText(this, "Menu item clicked", Toast.LENGTH_LONG).show();
                Intent dbmanager = new Intent(Home_Activity.this, SqliteManager.class);
                startActivity(dbmanager);
                return true;
            case R.id.item2:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermission()) {
                        // Code for above or equal 23 API Oriented Device.  Your Permission granted already .Do next code
                        try {
                            backupDatabase(this);
                        } catch (IOException e) {
                            Toast.makeText(this, "Backup error ........!!! ", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        requestPermission(); // Code for permission
                    }
                }
                return true;
            case R.id.item3:
                Intent backupPlanner = new Intent(Home_Activity.this, SqliteStorage.class);
                startActivity(backupPlanner);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(Home_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(Home_Activity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(Home_Activity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(Home_Activity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.i("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public void table() {

        SimpleDateFormat parseTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat displayTime = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat parseDate = new SimpleDateFormat("yyyy-MM-dd");

        Date todayDate = new Date();

        String dayStartTime     = parseDate.format(todayDate)+" 00:00:00";
        String dayEndTime       = parseDate.format(todayDate)+" 23:59:59";

        String TRUE     = "SELECT * FROM connection where DATETIME > Datetime('"+dayStartTime+"') and DATETIME < Datetime('"+dayEndTime+"')";
        List<Connection> connects = _dbHelper.customConnection(TRUE);
        List<TimeCounting> timeCount = new ArrayList();

        System.out.println("Result Count "+connects.size());

        if(connects.size()!=0) {
            try {

                int counter = 0;

                String startTime    = "";
                String endTime      = "";
                String startWifi    = "";
                int falseCounter    = 0;


                for (Connection c : connects) {
                    System.out.println("Time "+c.getDateTime()+" Status "+ c.getIsConnectToInternet());
                    if (Boolean.parseBoolean(c.getIsConnectToInternet()) == true) {
                        if (counter == 0) {
                            startTime = c.getDateTime();
                            startWifi = c.getWifiId();
                        } else {
                            endTime = c.getDateTime();
                        }
                        counter++;
                        falseCounter = 0;
                    } else {
                        if (falseCounter > 0) {

                        } else {
                            counter = 0;
                            TimeCounting tc = new TimeCounting();
                            tc.setStartTime(startTime);
                            tc.setEndTime(endTime);
                            tc.setWifiId(startWifi);
                            timeCount.add(tc);
                        }
                        falseCounter++;
                    }
                }

                TimeCounting tc = new TimeCounting();
                tc.setStartTime(startTime);
                tc.setEndTime(endTime);
                tc.setWifiId(startWifi);
                timeCount.add(tc);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Something Wrong ........!!! ", Toast.LENGTH_LONG).show();
            }
        }

        int i = 1;


        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        stk.setColumnStretchable(0, true);
        stk.setColumnStretchable(1, true);
        stk.setColumnStretchable(2, true);
        stk.setGravity(Gravity.CENTER);


        TableRow tbrow0 = new TableRow(this);
        //tbrow0.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView tv0 = new TextView(this);
        tv0.setText(" Sl.No ");
        tv0.setTextColor(Color.WHITE);
        //tv0.setGravity(Gravity.CENTER_HORIZONTAL);
        //tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Start ");
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);

        TextView tv2 = new TextView(this);
        tv2.setText(" End ");
        tv2.setTextColor(Color.WHITE);
        tv2.setGravity(Gravity.CENTER);
        tbrow0.addView(tv2);

        TextView tv3 = new TextView(this);
        tv3.setText(" Wifi ");
        tv3.setTextColor(Color.WHITE);
        tv3.setGravity(Gravity.CENTER);

        tbrow0.addView(tv3);
        stk.addView(tbrow0);



        try{
            for (TimeCounting t : timeCount) {

                if(t.getStartTime().equals("") || t.getStartTime().equals(null) || t.getEndTime().equals("") || t.getEndTime().equals(null)){

                }else{
                    TableRow tbrow = new TableRow(this);
                    TextView t1v = new TextView(this);

                    t1v.setText("" + i);
                    t1v.setTextColor(Color.WHITE);
                    t1v.setGravity(Gravity.CENTER);
                    //tbrow.addView(t1v);

                    TextView t2v = new TextView(this);
                    t2v.setText(displayTime.format(parseTime.parse(t.getStartTime())));
                    t2v.setTextColor(Color.WHITE);
                    t2v.setGravity(Gravity.CENTER);
                    tbrow.addView(t2v);

                    TextView t3v = new TextView(this);
                    t3v.setText(displayTime.format(parseTime.parse(t.getEndTime())));
                    t3v.setTextColor(Color.WHITE);
                    t3v.setGravity(Gravity.CENTER);
                    tbrow.addView(t3v);

                    TextView t4v = new TextView(this);
                    t4v.setText(t.getWifiId());
                    t4v.setTextColor(Color.WHITE);
                    t4v.setGravity(Gravity.CENTER);

                    tbrow.addView(t4v);
                    stk.addView(tbrow);
                    //calendar.add(Calendar.DATE, 1);
                    i++;
                    //}
                }
            }
        }catch (Exception e){
            //e.printStackTrace();
            Toast.makeText(this, "Something Wrong ........!!! ", Toast.LENGTH_LONG).show();
        }

    }
}
