package iboxbd.broadband.statistics.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import iboxbd.broadband.statistics.ip.IPConverter;
import iboxbd.broadband.statistics.model.Connection;
import iboxbd.broadband.statistics.model.IP;
import iboxbd.broadband.statistics.model.LogData;
import iboxbd.broadband.statistics.model.Wifi;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat log
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "BCStatistics.db";

    // Table Names
    private static final String TABLE_CONNECTION    = "CONNECTION";
    private static final String TABLE_LOG           = "LOG";
    private static final String TABLE_IP            = "IP";
    private static final String TABLE_WIFI          = "WIFI";

    // CONNECTION Table
    private static final String CONNECTION_ID                   = "ID";
    private static final String CONNECTION_IP_ID                = "IP_ID";
    private static final String CONNECTION_ISWIFION             = "ISWIFION";
    private static final String CONNECTION_ISCONNECTTOWIFI      = "ISCONNECTTOWIFI";
    private static final String CONNECTION_WIFIID               = "WIFIID";
    private static final String CONNECTION_ISCONNECTTOINTERNET  = "ISCONNECTTOINTERNET";
    private static final String CONNECTION_ISSYNCED             = "ISSYNCED";
    private static final String CONNECTION_DATETIME             = "DATETIME";

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

    private static final String WIFI_ID         = "ID";
    private static final String WIFI_NAME       = "NAME";
    private static final String WIFI_ISSYNCED   = "ISSYNCED";
    private static final String WIFI_DATETIME   = "DATETIME";


    // Table Create Statements
    // CONNECTION table create statement
    private static final String CREATE_TABLE_CONNECTION = "CREATE TABLE "
            + TABLE_CONNECTION + "(" + CONNECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + CONNECTION_IP_ID + " TEXT,"
            + CONNECTION_ISWIFION + " TEXT,"
            + CONNECTION_ISCONNECTTOWIFI + " TEXT,"
            + CONNECTION_WIFIID + " TEXT,"
            + CONNECTION_ISCONNECTTOINTERNET + " TEXT,"
            + CONNECTION_ISSYNCED + " TEXT,"
            + CONNECTION_DATETIME + " DATETIME" + " )";

    // LOG table create statement
    private static final String CREATE_TABLE_LOG = "CREATE TABLE "
            + TABLE_LOG + "(" + LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + LOG_LOG + " TEXT,"
            + LOG_ISSYNCED + " TEXT,"
            + LOG_DATETIME + " DATETIME" + ")";

    private static final String CREATE_TABLE_IP = "CREATE TABLE "
            + TABLE_IP + "(" + IP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + IP_NO + " TEXT,"
            + IP_ADDRESS + " TEXT,"
            + IP_NAME + " TEXT,"
            + IP_ISSYNCED + " TEXT,"
            + IP_DATETIME + " DATETIME" + ")";

    private static final String CREATE_TABLE_WIFI = "CREATE TABLE "
            + TABLE_WIFI + "(" + WIFI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + WIFI_NAME + " TEXT,"
            + WIFI_ISSYNCED + " TEXT,"
            + WIFI_DATETIME + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_CONNECTION);
        db.execSQL(CREATE_TABLE_LOG);
        db.execSQL(CREATE_TABLE_IP);
        db.execSQL(CREATE_TABLE_WIFI);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONNECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WIFI);
        // create new tables
        onCreate(db);
    }

    // ------------------------ "CONNECTION" table methods ----------------//

    /*      Creating a CONNECTION        */
    public long createConnection(Connection connection) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(CONNECTION_ISWIFION, connection.getIsWifiOn());
        values.put(CONNECTION_ISCONNECTTOWIFI, connection.getIsConnectToWifi());
        values.put(CONNECTION_WIFIID, connection.getWifiId());
        values.put(CONNECTION_ISCONNECTTOINTERNET, connection.getIsConnectToInternet());
        values.put(CONNECTION_IP_ID, connection.getIp_id());
        values.put(CONNECTION_ISSYNCED, connection.getIsSynced());
        values.put(CONNECTION_DATETIME, connection.getDateTime());

        // insert row

        long Connection_id = db.insert(TABLE_CONNECTION, null, values);
        return Connection_id;
    }

    /*      get single CONNECTION           */
    public Connection getConnectionById(long connection_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CONNECTION + " WHERE "
                + CONNECTION_ID + " = " + connection_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Connection connection = new Connection();
        connection.setID(c.getLong(c.getColumnIndex(CONNECTION_ID)));
        connection.setIsWifiOn(c.getString(c.getColumnIndex(CONNECTION_ISWIFION)));
        connection.setIsConnectToWifi(c.getString(c.getColumnIndex(CONNECTION_ISCONNECTTOWIFI)));
        connection.setWifiId(c.getString(c.getColumnIndex(CONNECTION_WIFIID)));
        connection.setIsConnectToInternet(c.getString(c.getColumnIndex(CONNECTION_ISCONNECTTOINTERNET)));
        connection.setIp_id(c.getString(c.getColumnIndex(CONNECTION_IP_ID)));
        connection.setIsSynced(c.getString(c.getColumnIndex(CONNECTION_ISSYNCED)));
        connection.setDateTime(c.getString(c.getColumnIndex(CONNECTION_DATETIME)));

        return connection;
    }

    public List<Connection> customConnection(String custom){
        System.out.println("Yes .........."+custom);
        SQLiteDatabase db = this.getReadableDatabase();
        List<Connection> connections = new ArrayList<Connection>();

        Cursor c = db.rawQuery(custom, null);
            if (c.moveToFirst()) {
                do {
                    Log.d("debug","getting the name from cursor"+ DatabaseUtils.dumpCurrentRowToString(c));
                    //Connection connection = new Connection();
//                    connection.setID(c.getLong(c.getColumnIndex(CONNECTION_ID)));
//                    connection.setIsConnected(c.getString(c.getColumnIndex(CONNECTION_ISCONNECT)));
//                    connection.setIsSynced(c.getString(c.getColumnIndex(CONNECTION_ISSYNCED)));
//                    connection.setDateTime(c.getString(c.getColumnIndex(CONNECTION_DATETIME)));

                    // adding to CONNECTION list
                    //connections.add(connection);
                } while (c.moveToNext());
            }
        return connections;
    }

    public int customInteger(String custom, String column){
        SQLiteDatabase db = this.getReadableDatabase();
        int a = 0;

        Cursor c = db.rawQuery(custom, null);
        if (c.moveToFirst()) {
            a = c.getInt(c.getColumnIndex(column));
        }
        return a;
    }

    /*      getting all CONNECTIONs      */
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
                connection.setIsWifiOn(c.getString(c.getColumnIndex(CONNECTION_ISWIFION)));
                connection.setIsConnectToWifi(c.getString(c.getColumnIndex(CONNECTION_ISCONNECTTOWIFI)));
                connection.setWifiId(c.getString(c.getColumnIndex(CONNECTION_WIFIID)));
                connection.setIsConnectToInternet(c.getString(c.getColumnIndex(CONNECTION_ISCONNECTTOINTERNET)));
                connection.setIp_id(c.getString(c.getColumnIndex(CONNECTION_IP_ID)));
                connection.setIsSynced(c.getString(c.getColumnIndex(CONNECTION_ISSYNCED)));
                connection.setDateTime(c.getString(c.getColumnIndex(CONNECTION_DATETIME)));

                // adding to CONNECTION list
                connections.add(connection);
            } while (c.moveToNext());
        }

        return connections;
    }

    public List<Connection> getConnectionBySyncStatus(String isSynced) {
        List<Connection> connections = new ArrayList<Connection>();

        String selectQuery = "SELECT  * FROM " + TABLE_CONNECTION + " WHERE " + CONNECTION_ISSYNCED + " = " + isSynced;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Connection connection = new Connection();
                connection.setID(c.getLong(c.getColumnIndex(CONNECTION_ID)));
                connection.setIsWifiOn(c.getString(c.getColumnIndex(CONNECTION_ISWIFION)));
                connection.setIsConnectToWifi(c.getString(c.getColumnIndex(CONNECTION_ISCONNECTTOWIFI)));
                connection.setWifiId(c.getString(c.getColumnIndex(CONNECTION_WIFIID)));
                connection.setIsConnectToInternet(c.getString(c.getColumnIndex(CONNECTION_ISCONNECTTOINTERNET)));
                connection.setIp_id(c.getString(c.getColumnIndex(CONNECTION_IP_ID)));
                connection.setIsSynced(c.getString(c.getColumnIndex(CONNECTION_ISSYNCED)));
                connection.setDateTime(c.getString(c.getColumnIndex(CONNECTION_DATETIME)));

                // adding to CONNECTION list
                connections.add(connection);
            } while (c.moveToNext());
        }

        return connections;
    }

    /*      getting CONNECTION count          */
    public int getConnectionCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONNECTION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getConnectionCountBySync(String isSynced) {
        String countQuery = "SELECT  * FROM " + TABLE_CONNECTION + " WHERE " + CONNECTION_ISSYNCED + " = " + isSynced;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /*      Updating a CONNECTION           */
    public int updateConnection(Connection connection) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONNECTION_ISWIFION, connection.getIsWifiOn());
        values.put(CONNECTION_ISCONNECTTOWIFI, connection.getIsConnectToWifi());
        values.put(CONNECTION_WIFIID, connection.getWifiId());
        values.put(CONNECTION_ISCONNECTTOINTERNET, connection.getIsConnectToInternet());
        values.put(CONNECTION_IP_ID, connection.getIp_id());
        values.put(CONNECTION_ISSYNCED, connection.getIsSynced());
        values.put(CONNECTION_DATETIME, connection.getDateTime());

        // updating row
        return db.update(TABLE_CONNECTION, values, CONNECTION_ID + " = ?",
                new String[]{String.valueOf(connection.getID())});
    }

    /*     Updating a CONNECTION     */
    public int updateConnectionStatus(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONNECTION_ISCONNECTTOINTERNET, status);

        // updating row
        return db.update(TABLE_CONNECTION, values, CONNECTION_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    /*      Deleting a CONNECTION     */
    public void deleteConnectionById(long connection_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONNECTION, CONNECTION_ID + " = ?",
                new String[]{String.valueOf(connection_id)});
    }

    public void close() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"message"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            String maxQuery = Query;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (SQLException sqlEx) {
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        } catch (Exception ex) {
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + ex.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }
    }

    // ------------------------ "Log" table methods ----------------//

    /*       Creating log      */
    public long createLOG(LogData log) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOG_LOG, log.getLog());
        values.put(LOG_ISSYNCED, log.getIsSynced());
        values.put(LOG_DATETIME, log.getDateTime());

        // insert row
        long log_id = db.insert(TABLE_LOG, null, values);
        return log_id;
    }

    // ------------------------ "IP" table methods ----------------//

    /*       Creating IP      */
    public long createIP(IP ip) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(IP_NO, IPConverter.convert(ip.getIp()));
        values.put(IP_ADDRESS, ip.getAddress());
        values.put(IP_NAME, ip.getName());
        values.put(IP_ISSYNCED, ip.getIsSynced());
        values.put(IP_DATETIME, ip.getDateTime());

        // insert row
        long log_id = db.insert(TABLE_IP, null, values);
        return log_id;
    }

    /*       Find IP Exist      */
    public boolean doesIPExist(String ip) {
        SQLiteDatabase db   = this.getReadableDatabase();
        boolean ipExist     = false;

        String selectQuery  = "SELECT  * FROM " + TABLE_IP + " WHERE "+ IP_NO + " = "+IPConverter.convert(ip);
        Cursor c            = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0){
            ipExist = true;
        }
        return ipExist;
    }

    public long getIPID(String ip) {
        SQLiteDatabase db   = this.getReadableDatabase();
        long log_id         = 0;

        String selectQuery  = "SELECT  * FROM " + TABLE_IP + " WHERE "+ IP_NO + " = "+IPConverter.convert(ip);
        Cursor c            = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0){
            c.moveToFirst();
            log_id = c.getLong(c.getColumnIndex(IP_ID));
        }
        return log_id;
    }

    public boolean doesWifiExist(String wifiName) {
        SQLiteDatabase db   = this.getReadableDatabase();
        boolean ipExist     = false;

        String selectQuery  = "SELECT  * FROM " + TABLE_WIFI + " WHERE "+ WIFI_NAME + " = '"+wifiName+"'";
        Log.i("Wifi Query",selectQuery);
        Cursor c            = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0){
            ipExist = true;
        }
        return ipExist;
    }

    public long getWIFIID(String wifiName) {
        SQLiteDatabase db   = this.getReadableDatabase();
        long log_id         = 0;

        String selectQuery  = "SELECT  * FROM " + TABLE_WIFI + " WHERE "+ WIFI_NAME + " = "+wifiName;
        Cursor c            = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount()>0){
            c.moveToFirst();
            log_id = c.getLong(c.getColumnIndex(WIFI_ID));
        }
        return log_id;
    }

    public long createWIFI(Wifi wifi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WIFI_NAME,       wifi.getWIFI_NAME());
        values.put(WIFI_ISSYNCED,   wifi.getWIFI_ISSYNCED());
        values.put(WIFI_DATETIME,   wifi.getWIFI_DATETIME());

        // insert row
        long log_id = db.insert(TABLE_WIFI, null, values);
        return log_id;
    }
}
/**
 * getting all logs
 * <p>
 * Updating a log
 * <p>
 * Deleting a log
 * <p>
 * Creating Message
 * <p>
 * getting all logs
 * <p>
 * Updating a log
 * <p>
 * Deleting a log
 * <p>
 * get datetime
 *//*

    public List<LOG_DATA> getAllLog() {
        List<LOG_DATA> logs = new ArrayList<LOG_DATA>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LOG_DATA t = new LOG_DATA();
                t.setId(c.getInt((c.getColumnIndex(LOG_ID))));
                t.setInformation(c.getString(c.getColumnIndex(LOG_LOG)));
                t.setSyncStatus(c.getString(c.getColumnIndex(LOG_ISSYNCED)));
                t.setCreated(c.getString(c.getColumnIndex(LOG_DATETIME)));

                // adding to logs list
                logs.add(t);
            } while (c.moveToNext());
        }
        return logs;
    }

    */
/**
 * Updating a log
 *//*

    public int updateLOG_DATA(LOG_DATA log) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOG_LOG, log.getInformation());
        values.put(LOG_ISSYNCED, log.getSyncStatus());

        // updating row
        return db.update(TABLE_LOG, values, LOG_ID + " = ?",
                new String[] { String.valueOf(log.getId()) });
    }

    public int getUnSyncedLogCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOG+" WHERE "+LOG_ISSYNCED+" = 0";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public List<LOG_DATA> getAllUnSyncedLog() {
        List<LOG_DATA> logs = new ArrayList<LOG_DATA>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG +" WHERE "+LOG_ISSYNCED+" = 0";

        Log.e(Configuration.databaseTAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LOG_DATA t = new LOG_DATA();
                t.setId(c.getInt((c.getColumnIndex(LOG_ID))));
                t.setInformation(c.getString(c.getColumnIndex(LOG_LOG)));
                t.setSyncStatus(c.getString(c.getColumnIndex(LOG_ISSYNCED)));
                t.setCreated(c.getString(c.getColumnIndex(LOG_DATETIME)));

                logs.add(t);
            } while (c.moveToNext());
        }

        return logs;
    }
    */
/**
 * Deleting a log
 *//*

    public void deleteLOG_DATA(LOG_DATA log, boolean should_delete_all_log_todos) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting log
        // check if todos under this log should also be deleted
        if (should_delete_all_log_todos) {
            // get all todos under this log
            */
/*List<Todo> allLogToDos = getAllToDosByLog(log.getLogName());

            // delete all todos
            for (Todo todo : allLogToDos) {
                // delete todo
                deleteToDo(todo.getId());
            }*//*

        }

        // now delete the log
        db.delete(TABLE_LOG, LOG_ID + " = ?",
                new String[] { String.valueOf(log.getId()) });
    }

    // ------------------------ "message" table methods ----------------//

    */
/**
 * Creating Message
 *//*

    public long createMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MESSAGE_DETAIL, message.getMessage());
        values.put(MESSAGE_SENT_BY, message.getSentBy());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long message_id = db.insert(TABLE_MESSAGE, null, values);

        return message_id;
    }

    */
/**
 * getting all logs
 * *//*

    public List<Message> getAllMessage() {
        List<Message> messages = new ArrayList<Message>();
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE+" ORDER BY "+KEY_CREATED_AT+" DESC";

        Log.d(Configuration.databaseTAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Message t = new Message();
                t.setId(c.getInt((c.getColumnIndex(MESSAGE_ID))));
                t.setMessage(c.getString(c.getColumnIndex(MESSAGE_DETAIL)));
                t.setSentBy(c.getString(c.getColumnIndex(MESSAGE_SENT_BY)));
                t.setCreated(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to logs list
                messages.add(t);
            } while (c.moveToNext());
        }
        return messages;
    }

    */
/**
 * Updating a log
 *//*

    public int updateMessage(Message message) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MESSAGE_DETAIL, message.getMessage());
        values.put(MESSAGE_SENT_BY, message.getSentBy());

        // updating row
        return db.update(TABLE_MESSAGE, values, MESSAGE_ID + " = ?",
                new String[] { String.valueOf(message.getId()) });
    }

    */
/**
 * Deleting a log
 *//*

    public void deleteMessage(Message message, boolean should_delete_all_log_todos) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting log
        // check if todos under this log should also be deleted
        if (should_delete_all_log_todos) {
            // get all todos under this log
            */
/*List<Todo> allLogToDos = getAllToDosByLog(log.getLogName());

            // delete all todos
            for (Todo todo : allLogToDos) {
                // delete todo
                deleteToDo(todo.getId());
            }*//*

        }

        // now delete the Message
        db.delete(TABLE_MESSAGE, MESSAGE_ID + " = ?",
                new String[] { String.valueOf(message.getId()) });
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    */
/**
 * get datetime
 * *//*

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}*/
