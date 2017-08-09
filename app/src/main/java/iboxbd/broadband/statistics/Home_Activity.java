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

import java.io.IOException;

import iboxbd.broadband.statistics.phone.ServiceCheck;
import iboxbd.broadband.statistics.sqlite.SqliteManager;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;

import static iboxbd.broadband.statistics.sqlite.SqliteBackup.backupDatabase;


public class Home_Activity extends AppCompatActivity {

    private DatabaseHelper _dbHelper;
    private static final int PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_home);
        GetDB();

        try {
            /*String ip = "182.48.78.174";
            String name = "Md. Zahidul Islam";
            if(!_dbHelper.doesIPExist(ip)){
                 _dbHelper.createIP(new IP(ip,name));
               _dbHelper.close();
                Toast.makeText(this,"Exist .....",Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(this,"Do not Exist .....",Toast.LENGTH_LONG).show();
            }*/

            Intent intentService = new Intent(this, ConnectionService.class);
            startService(intentService);

            Toast.makeText(this, Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                //Toast.makeText(this, "Menu item clicked", Toast.LENGTH_LONG).show();
                Intent dbmanager = new Intent(Home_Activity.this, SqliteManager.class);
                startActivity(dbmanager);
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
