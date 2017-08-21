package iboxbd.broadband.statistics.sqlite;

/**
 * Created by User on 8/21/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import iboxbd.broadband.statistics.model.Connection;

public class ExternalDatabase extends SqliteAsset {
    private static final String DATABASE_NAME = "broadband.db3";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_CONNECTION    = "CONNECTION";
    private static final String TABLE_LOG           = "LOG";
    private static final String TABLE_IP            = "IP";


    // CONNECTION Table
    private static final String CONNECTION_ID           = "ID";
    private static final String CONNECTION_IP_ID        = "IP_ID";
    private static final String CONNECTION_ISCONNECT    = "ISCONNECT";
    private static final String CONNECTION_ISSYNCED     = "ISSYNCED";
    private static final String CONNECTION_DATETIME     = "DATETIME";

    // Log Table
    private static final String LOG_ID          = "ID";
    private static final String LOG_LOG         = "LOG";
    private static final String LOG_ISSYNCED    = "ISSYNCED";
    private static final String LOG_DATETIME    = "DATETIME";

    private static final String IP_ID           = "ID";
    private static final String IP_NO           = "NO";
    private static final String IP_ADDRESS      = "ADDRESS";
    private static final String IP_NAME         = "NAME";
    private static final String IP_ISSYNCED     = "ISSYNCED";
    private static final String IP_DATETIME     = "DATETIME";

    public ExternalDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<Connection> getAllConnections() {
        List<Connection> connections = new ArrayList<Connection>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONNECTION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Connection connection = new Connection();
                connection.setID(c.getLong(c.getColumnIndex(CONNECTION_ID)));
                //connection.setIp_id(c.getString(c.getColumnIndex(CONNECTION_IP_ID)));
                connection.setIsConnected(c.getString(c.getColumnIndex(CONNECTION_ISCONNECT)));
                connection.setIsSynced(c.getString(c.getColumnIndex(CONNECTION_ISSYNCED)));
                connection.setDateTime(c.getString(c.getColumnIndex(CONNECTION_DATETIME)));

                // adding to CONNECTION list
                connections.add(connection);
            } while (c.moveToNext());
        }

        return connections;
    }
}