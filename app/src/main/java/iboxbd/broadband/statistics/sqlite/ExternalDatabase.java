package iboxbd.broadband.statistics.sqlite;

/**
 * Created by User on 8/21/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import iboxbd.broadband.statistics.Home_Activity;
import iboxbd.broadband.statistics.model.Connection;

public class ExternalDatabase extends SQLiteOpenHelper {
    //private static final String DATABASE_NAME = "broadband.db3";
    private static final String DATABASE_NAME =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/broadband.db3";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_CONNECTION    = "CONNECTION";

    // CONNECTION Table
    private static final String CONNECTION_ID           = "ID";
    private static final String CONNECTION_IP_ID        = "IP_ID";
    private static final String CONNECTION_ISCONNECT    = "ISCONNECT";
    private static final String CONNECTION_ISSYNCED     = "ISSYNCED";
    private static final String CONNECTION_DATETIME     = "DATETIME";

    Activity context;


    public ExternalDatabase(Activity context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    /*List<Connection> connections = externalDatabase.getAllConnections();

    for(Connection connection:connections){
        if(connection.getIsConnectToInternet().equals("true")){
            _dbHelper.createConnection(new Connection("true","true","1",connection.getIsConnectToInternet(),connection.getIp_id(),connection.getDateTime()));
        }else{
            _dbHelper.createConnection(new Connection("false","false","-",connection.getIsConnectToInternet(),"-",connection.getDateTime()));
        }
        //Log.i("connection.getDateTime()",connection.getDateTime());
    }*/


    public List<Connection> getAllConnections() {
        List<Connection> connections = new ArrayList<Connection>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONNECTION;
        Log.v("Permission","Permission is granted");

        if (ContextCompat.checkSelfPermission(context,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v("Permission is granted","Permission is granted");
            //File write logic here
            //return true;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    Connection connection = new Connection();
                    connection.setID(c.getLong(c.getColumnIndex(CONNECTION_ID)));
                    connection.setIp_id(c.getString(c.getColumnIndex(CONNECTION_IP_ID)));
                    connection.setIsConnectToInternet(c.getString(c.getColumnIndex(CONNECTION_ISCONNECT)));
                    connection.setIsSynced(c.getString(c.getColumnIndex(CONNECTION_ISSYNCED)));
                    connection.setDateTime(c.getString(c.getColumnIndex(CONNECTION_DATETIME)));

                    // adding to CONNECTION list
                    connections.add(connection);
                } while (c.moveToNext());
            }
        }else{
            ActivityCompat.requestPermissions(context, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }


        return connections;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}