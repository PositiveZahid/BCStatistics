package iboxbd.broadband.statistics;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import iboxbd.broadband.statistics.model.Connection;
import iboxbd.broadband.statistics.phone.ServiceCheck;
import iboxbd.broadband.statistics.phone.Storage;
import iboxbd.broadband.statistics.sqlite.ExternalDatabase;
import iboxbd.broadband.statistics.sqlite.SqliteManager;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import iboxbd.broadband.statistics.sqlite.SqliteStorage;

import static iboxbd.broadband.statistics.sqlite.SqliteBackup.backupDatabase;

public class Home_Activity extends AppCompatActivity {

    private DatabaseHelper _dbHelper;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private ExternalDatabase readExternalDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        readExternalDatabase = new ExternalDatabase(this);
        setContentView(R.layout.activity_home);
        GetDB();

        try {
            //new NetworkCall(getApplicationContext()).execute();
            //new SpeedCall(Home_Activity.this).execute();
            //Toast.makeText(this, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), Toast.LENGTH_LONG).show();
            File file = Storage.getStorageDir("broadband.db3");
            if(file.exists()){
                Toast.makeText(this, "Yes .......... ", Toast.LENGTH_LONG).show();
                System.out.println("Yes "+file);
            }else{
                Toast.makeText(this, "NO .......... ", Toast.LENGTH_LONG).show();
                System.out.println("NO "+file);
            }

            List<Connection> connections = new ArrayList<Connection>();
            connections = readExternalDatabase.getAllConnections();

            for (Connection connection: connections) {
                //connection.getIsConnected();
                //connection.getDateTime();
                //connection.setIp_id("1");
                //_dbHelper.createConnection(connection);
                Log.i(connection.getDateTime(),connection.getIsConnected());
            }


            if (ServiceCheck.isMyServiceRunning(Home_Activity.this, ConnectionService.class)) {
                Toast.makeText(this, "Connection Service Running ........!!! ", Toast.LENGTH_LONG).show();
            }
            //String androidId = ;
            //Toast.makeText(this, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), Toast.LENGTH_LONG).show();
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


}
