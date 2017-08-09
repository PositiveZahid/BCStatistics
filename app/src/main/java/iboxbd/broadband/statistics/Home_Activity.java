package iboxbd.broadband.statistics;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

            Intent intentService = new Intent(this,ConnectionService.class);
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
                try {
                    backupDatabase(this);
                } catch (IOException e) {
                    Toast.makeText(this, "Backup error ........!!! ", Toast.LENGTH_LONG).show();
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

}
