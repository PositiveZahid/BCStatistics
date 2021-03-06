package iboxbd.broadband.statistics.sqlite;

/**
 * Created by ZahiD on 07-Aug-17.
 */


import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class SqliteBackup {

    public static void backupDatabase(Context context) throws IOException {

        // https://stackoverflow.com/questions/19194576/how-do-i-view-the-sqlite-database-on-an-android-device
        // https://stackoverflow.com/questions/14052824/where-is-sqlite-database-in-android-tablet
        // https://github.com/sanathp/DatabaseManager_For_Android

        // Open your local db as the input stream
        //File sdCard = Environment.getExternalStorageDirectory();
        //File root = new File(sdCard.getAbsolutePath() + "/");
        try {
            String inFileName = "/data/data/iboxbd.broadband.statistics/databases/BCStatistics.db";
            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            File outputDirectory = new File(Environment.getExternalStorageDirectory() + "/");
            outputDirectory.mkdirs();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // append date time to db name

            String backupFileName = "/MyApp_" + sdf.format(new Date()) + ".db3";
            String outFileName = outputDirectory + backupFileName;

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            // Close the streams
            output.flush();
            output.close();
            fis.close();

            Toast.makeText(context, "Database backup complete", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            //Log.i("Error : ", " "+ e.getStackTrace());
            e.printStackTrace();
            Toast.makeText(context, "Backup Error : "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}