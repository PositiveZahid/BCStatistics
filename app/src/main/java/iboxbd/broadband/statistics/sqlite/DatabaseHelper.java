package iboxbd.broadband.statistics.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import iboxbd.broadband.statistics.model.Connection;
import iboxbd.broadband.statistics.model.LogData;

import static iboxbd.broadband.statistics.utils.DateUtils.getDateTime;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat log
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "BCStatistics";

    // Table Names
    private static final String TABLE_CONNECTION    = "CONNECTION";
    private static final String TABLE_LOG           = "LOG";

    // CONNECTION Table
    private static final String CONNECTION_ID = "ID";
    private static final String CONNECTION_ISCONNECT = "ISCONNECT";
    private static final String CONNECTION_ISSYNCED = "ISSYNCED";
    private static final String CONNECTION_DATETIME = "DATETIME";

    // Log Table
    private static final String LOG_ID = "ID";
    private static final String LOG_LOG = "LOG";
    private static final String LOG_ISSYNCED = "ISSYNCED";
    private static final String LOG_DATETIME = "DATETIME";


    // Table Create Statements
    // CONNECTION table create statement
    private static final String CREATE_TABLE_CONNECTION = "CREATE TABLE "
            + TABLE_CONNECTION + "(" + CONNECTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + CONNECTION_ISCONNECT + " TEXT,"
            + CONNECTION_ISSYNCED + " TEXT,"
            + CONNECTION_DATETIME + " DATETIME" + " )";

    // LOG table create statement
    private static final String CREATE_TABLE_LOG = "CREATE TABLE "
            + TABLE_LOG + "(" + LOG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + LOG_LOG + " TEXT,"
            + LOG_ISSYNCED + " TEXT,"
            + LOG_DATETIME + " DATETIME" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_CONNECTION);
        db.execSQL(CREATE_TABLE_LOG);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONNECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOG);
        // create new tables
        onCreate(db);
    }

    // ------------------------ "CONNECTION" table methods ----------------//

    /*      Creating a CONNECTION        */
    public long createConnection(Connection connection) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONNECTION_ISCONNECT, connection.getIsConnected());
        values.put(CONNECTION_ISSYNCED, connection.getIsSynced());
        values.put(CONNECTION_DATETIME, getDateTime());

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
        connection.setIsConnected(c.getString(c.getColumnIndex(CONNECTION_ISCONNECT)));
        connection.setIsConnected(c.getString(c.getColumnIndex(CONNECTION_ISSYNCED)));
        connection.setIsConnected(c.getString(c.getColumnIndex(CONNECTION_DATETIME)));

        return connection;
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
                connection.setIsConnected(c.getString(c.getColumnIndex(CONNECTION_ISCONNECT)));
                connection.setIsConnected(c.getString(c.getColumnIndex(CONNECTION_ISSYNCED)));
                connection.setIsConnected(c.getString(c.getColumnIndex(CONNECTION_DATETIME)));

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
                connection.setIsConnected(c.getString(c.getColumnIndex(CONNECTION_ISCONNECT)));
                connection.setIsConnected(c.getString(c.getColumnIndex(CONNECTION_ISSYNCED)));
                connection.setIsConnected(c.getString(c.getColumnIndex(CONNECTION_DATETIME)));

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
        values.put(CONNECTION_ISSYNCED, connection.getIsSynced());
        values.put(CONNECTION_ISCONNECT, connection.getIsConnected());
        values.put(CONNECTION_DATETIME, connection.getDateTime());

        // updating row
        return db.update(TABLE_CONNECTION, values, CONNECTION_ID + " = ?",
                new String[]{String.valueOf(connection.getID())});
    }

    /*     Updating a CONNECTION     */
    public int updateConnectionStatus(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONNECTION_ISCONNECT, status);

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
