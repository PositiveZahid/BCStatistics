/*
package iboxbd.broadband.statistics.sqlite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import softoption.TeamTracker.Configuration.Configuration;
import softoption.TeamTracker.ModelClass.LocationInformation;
import softoption.TeamTracker.ModelClass.LogInformation;
import softoption.TeamTracker.ModelClass.Message;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat log
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME       = "TEAM_TRACKER_SQLITE";

    // Table Names
    private static final String TABLE_LOCATION      = "LOCATION";
    private static final String TABLE_LOG           = "LOG";
    private static final String TABLE_MESSAGE       = "MESSAGE";

    // Location Table
    private static final String LOCATION_ID         = "id";
    private static final String LOCATION_LATITUDE   = "latitude";
    private static final String LOCATION_LONGITUDE  = "longitude";
    private static final String LOCATION_COMMENT    = "comment";
    // 0 = unsynced, 1 = sysnced
    private static final String LOCATION_SYNC_STATUS = "sync_status";
    private static final String KEY_CREATED_AT      = "created_at";

    // Log Table
    private static final String LOG_ID              = "id";
    private static final String LOG_INFORMATION     = "information";
    // 0 = unsynced, 1 = sysnced
    private static final String LOG_SYNC_STATUS     = "sync_status";


    // Message Table - column names
    private static final String MESSAGE_ID          = "id";
    private static final String MESSAGE_DETAIL      = "detail";
    private static final String MESSAGE_SENT_BY     = "sent_by";
    // 0 = unsynced, 1 = sysnced
    private static final String MESSAGE_SYNC_STATUS = "sync_status";




    // Table Create Statements
    // LOCATION table create statement
    private static final String CREATE_TABLE_LOCATION = "CREATE TABLE "
            + TABLE_LOCATION + "(" + LOCATION_ID + " INTEGER PRIMARY KEY,"
            + LOCATION_LATITUDE+ " TEXT,"
            + LOCATION_LONGITUDE + " TEXT,"
            + LOCATION_COMMENT + " TEXT,"
            + LOCATION_SYNC_STATUS + " TEXT,"
            + KEY_CREATED_AT+ " DATETIME" + ")";

    // LOG table create statement
    private static final String CREATE_TABLE_LOG = "CREATE TABLE "
            + TABLE_LOG+ "(" + LOG_ID + " INTEGER PRIMARY KEY,"
            + LOG_INFORMATION + " TEXT,"
            + LOG_SYNC_STATUS + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // MESSAGE table create statement
    private static final String CREATE_TABLE_MESSAGE = "CREATE TABLE "
            + TABLE_MESSAGE + "(" + MESSAGE_ID + " INTEGER PRIMARY KEY,"
            + MESSAGE_SENT_BY + " TEXT,"
            + MESSAGE_DETAIL + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_LOCATION);
        db.execSQL(CREATE_TABLE_LOG);
        db.execSQL(CREATE_TABLE_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);

        // create new tables
        onCreate(db);
    }

    // ------------------------ "location" table methods ----------------//

    */
/**
     * Creating a location
     *//*

    public long createLocation(LocationInformation location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOCATION_LATITUDE, location.getLatitude());
        values.put(LOCATION_LONGITUDE, location.getLongitude());
        values.put(LOCATION_COMMENT, location.getComment());
        values.put(LOCATION_SYNC_STATUS,location.getSyncStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long location_id = db.insert(TABLE_LOCATION, null, values);
        Log.d(Configuration.databaseTAG, String.valueOf(location_id)+" inserted");
        return location_id;
    }

    */
/**
     * get single location
     *//*

    public LocationInformation getLocationById(long location_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION + " WHERE "
                + LOCATION_ID + " = " + location_id;

        Log.d(Configuration.databaseTAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        LocationInformation location = new LocationInformation();
        location.setId(c.getInt(c.getColumnIndex(LOCATION_ID)));
        location.setLatitude((c.getString(c.getColumnIndex(LOCATION_LATITUDE))));
        location.setLongitude((c.getString(c.getColumnIndex(LOCATION_LONGITUDE))));
        location.setComment((c.getString(c.getColumnIndex(LOCATION_COMMENT))));
        location.setSyncStatus((c.getString(c.getColumnIndex(LOCATION_SYNC_STATUS))));
        location.setCreated(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

        return location;
    }

    */
/**
     * getting all locations
     * *//*

    public List<LocationInformation> getAllLocations() {
        List<LocationInformation> locations = new ArrayList<LocationInformation>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION;

        Log.e(Configuration.databaseTAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LocationInformation location = new LocationInformation();
                location.setId(c.getInt(c.getColumnIndex(LOCATION_ID)));
                location.setLatitude((c.getString(c.getColumnIndex(LOCATION_LATITUDE))));
                location.setLongitude((c.getString(c.getColumnIndex(LOCATION_LONGITUDE))));
                location.setComment((c.getString(c.getColumnIndex(LOCATION_COMMENT))));
                location.setSyncStatus((c.getString(c.getColumnIndex(LOCATION_SYNC_STATUS))));
                location.setCreated(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to location list
                locations.add(location);
            } while (c.moveToNext());
        }

        return locations;
    }
    public List<LocationInformation> getAllUnSyncedLocation() {
        List<LocationInformation> locations = new ArrayList<LocationInformation>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATION +" WHERE "+LOCATION_SYNC_STATUS+" = 0";

        Log.e(Configuration.databaseTAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LocationInformation location = new LocationInformation();
                location.setId(c.getInt(c.getColumnIndex(LOCATION_ID)));
                location.setLatitude((c.getString(c.getColumnIndex(LOCATION_LATITUDE))));
                location.setLongitude((c.getString(c.getColumnIndex(LOCATION_LONGITUDE))));
                location.setComment((c.getString(c.getColumnIndex(LOCATION_COMMENT))));
                location.setSyncStatus((c.getString(c.getColumnIndex(LOCATION_SYNC_STATUS))));
                location.setCreated(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to location list
                locations.add(location);
            } while (c.moveToNext());
        }

        return locations;
    }

    */
/**
     * getting location count
     *//*

    public int getLocationCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public int getUnSyncedLocationCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOCATION+" WHERE "+LOCATION_SYNC_STATUS+" = 0";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
    */
/**
     * Updating a location
     *//*

    public int updateLocation(LocationInformation location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOCATION_LATITUDE, location.getLatitude());
        values.put(LOCATION_LONGITUDE, location.getLongitude());
        values.put(LOCATION_COMMENT, location.getComment());
        values.put(LOCATION_SYNC_STATUS, location.getSyncStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // updating row
        return db.update(TABLE_LOCATION, values, LOCATION_ID + " = ?",
                new String[] { String.valueOf(location.getId()) });
    }
    */
/**
     * Updating a location
     *//*

    */
/*public int updateLocationStatus(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOCATION_LATITUDE, location.getLatitude());
        values.put(LOCATION_LONGITUDE, location.getLongitude());
        values.put(LOCATION_COMMENT, location.getComment());
        values.put(KEY_CREATED_AT, getDateTime());

        // updating row
        return db.update(TABLE_LOCATION, values, LOCATION_ID + " = ?",
                new String[] { String.valueOf(location.getId()) });
    }*//*

    */
/**
     * Deleting a Location
     *//*

    public void deleteLocationById(long location_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATION, LOCATION_ID + " = ?",
                new String[] { String.valueOf(location_id) });
    }

    // ------------------------ "Log" table methods ----------------//

    */
/**
     * Creating log
     *//*

    public long createLogInformation(LogInformation log) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOG_INFORMATION, log.getInformation());
        values.put(LOG_SYNC_STATUS, log.getSyncStatus());
        values.put(KEY_CREATED_AT, getDateTime());

        // insert row
        long log_id = db.insert(TABLE_LOG, null, values);

        return log_id;
    }

    */
/**
     * getting all logs
     * *//*

    public List<LogInformation> getAllLog() {
        List<LogInformation> logs = new ArrayList<LogInformation>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LogInformation t = new LogInformation();
                t.setId(c.getInt((c.getColumnIndex(LOG_ID))));
                t.setInformation(c.getString(c.getColumnIndex(LOG_INFORMATION)));
                t.setSyncStatus(c.getString(c.getColumnIndex(LOG_SYNC_STATUS)));
                t.setCreated(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

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

    public int updateLogInformation(LogInformation log) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LOG_INFORMATION, log.getInformation());
        values.put(LOG_SYNC_STATUS, log.getSyncStatus());

        // updating row
        return db.update(TABLE_LOG, values, LOG_ID + " = ?",
                new String[] { String.valueOf(log.getId()) });
    }

    public int getUnSyncedLogCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOG+" WHERE "+LOG_SYNC_STATUS+" = 0";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    public List<LogInformation> getAllUnSyncedLog() {
        List<LogInformation> logs = new ArrayList<LogInformation>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOG +" WHERE "+LOG_SYNC_STATUS+" = 0";

        Log.e(Configuration.databaseTAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                LogInformation t = new LogInformation();
                t.setId(c.getInt((c.getColumnIndex(LOG_ID))));
                t.setInformation(c.getString(c.getColumnIndex(LOG_INFORMATION)));
                t.setSyncStatus(c.getString(c.getColumnIndex(LOG_SYNC_STATUS)));
                t.setCreated(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                logs.add(t);
            } while (c.moveToNext());
        }

        return logs;
    }
    */
/**
     * Deleting a log
     *//*

    public void deleteLogInformation(LogInformation log, boolean should_delete_all_log_todos) {
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
