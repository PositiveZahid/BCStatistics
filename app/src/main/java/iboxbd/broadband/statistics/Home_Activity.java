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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import iboxbd.broadband.statistics.ip.NetworkCall;
import iboxbd.broadband.statistics.ip.Speed;
import iboxbd.broadband.statistics.model.Connection;
import iboxbd.broadband.statistics.phone.Connectivity;
import iboxbd.broadband.statistics.sqlite.ExternalDatabase;
import iboxbd.broadband.statistics.sqlite.SqliteManager;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import iboxbd.broadband.statistics.sqlite.SqliteStorage;

import static iboxbd.broadband.statistics.sqlite.SqliteBackup.backupDatabase;

public class Home_Activity extends AppCompatActivity {

    private DatabaseHelper _dbHelper;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private ExternalDatabase externalDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        externalDatabase = new ExternalDatabase(this);
        setContentView(R.layout.activity_home);
        GetDB();
        //new Speed(Home_Activity.this).execute();
        //new NetworkCall(getApplicationContext()).execute();
        //_dbHelper.doesWifiExist(Connectivity.wifiName(this));
        //Log.i("Wifi Name", _dbHelper.doesWifiExist(Connectivity.wifiName(this))+"");


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

        //Adding table based on class TEST reflection
        //_dbHelper.CreateTable(new Connection());
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
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), 1);
        int month = calendar.get(Calendar.MONTH);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        int i = 1;


        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText(" Sl.No ");
        tv0.setTextColor(Color.WHITE);
        //tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Date ");
        tv1.setTextColor(Color.WHITE);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Connected ");
        tv2.setTextColor(Color.WHITE);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Disconnected ");
        tv3.setTextColor(Color.WHITE);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        while (calendar.get(Calendar.MONTH) == month) {

            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat countingFormat = new SimpleDateFormat("yyyy-MM-dd");

            String TRUE     = "SELECT COUNT(*) as count FROM connection WHERE ISCONNECTTOINTERNET = 'true' and DATETIME >= Datetime('"+countingFormat.format(calendar.getTime())+" 00:00:00') and DATETIME <= Datetime('"+countingFormat.format(calendar.getTime())+" 23:59:59')";
            String FALSE    = "SELECT COUNT(*) as count FROM connection WHERE ISCONNECTTOINTERNET = 'false' and DATETIME >= Datetime('"+countingFormat.format(calendar.getTime())+" 00:00:00') and DATETIME <= Datetime('"+countingFormat.format(calendar.getTime())+" 23:59:59')";

            int countTrue   = _dbHelper.customInteger(TRUE,"count");
            int countFalse  = _dbHelper.customInteger(FALSE,"count");


            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText("" + i);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            //tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(format1.format(calendar.getTime()));
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText("" + countTrue);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText("" + countFalse);
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);
            calendar.add(Calendar.DATE, 1);
            i++;
        }
    }
}
