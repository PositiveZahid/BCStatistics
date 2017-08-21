package iboxbd.broadband.statistics.ip;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import fr.bmartel.speedtest.SpeedTestSocket;
import iboxbd.broadband.statistics.model.Connection;
import iboxbd.broadband.statistics.model.IP;
import iboxbd.broadband.statistics.model.LogData;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;

/**
 * Created by User on 8/21/2017.
 */

public class SpeedCall extends AsyncTask<String, String, String> {
    DatabaseHelper dbh;
    Context context;
    SpeedTester speedTester;
    SpeedTestSocket speedTestSocket = new SpeedTestSocket();

    public SpeedCall(Context context) {
        this.context = context;
        this.dbh = new DatabaseHelper(context);
        speedTester = new SpeedTester();
        speedTestSocket.addSpeedTestListener(speedTester);
    }

    @Override
    protected String doInBackground(String... strings) {

        String ipInformation = new String();
        try  {
            speedTestSocket.startDownload("http://2.testdebit.info/fichiers/1Mo.dat");
            //speedTestSocket.startDownload("http://2.testdebit.info/fichiers/1Mo.dat");
        }  catch (Exception e) {
            //dbh.createLOG(new LogData("#905"));
            //dbh.close();
            Log.i("#905", "Network call Error!! " + e.getMessage());
        }


        return ipInformation;

    }

    protected void onPostExecute(String result) {
    }
}