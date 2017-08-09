package iboxbd.broadband.statistics.ip;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import iboxbd.broadband.statistics.model.Connection;
import iboxbd.broadband.statistics.model.IP;
import iboxbd.broadband.statistics.model.LogData;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;

/**
 * Created by User on 8/8/2017.
 */

/*
*       By calling this url : http://ip-api.com/json we get a response like
*
*       {"as":"AS63969 Race Online Limited","city":"Dhaka","country":"Bangladesh","countryCode":"BD","isp":"Earth Telecommunication ( pvt ) Limited","lat":23.7916,"lon":90.4152,"org":"Race Online Limited","query":"182.48.78.174","region":"C","regionName":"Dhaka Division","status":"success","timezone":"Asia/Dhaka","zip":"1212"}
*
*       We would love to parse isp
* */


public class NetworkCall extends AsyncTask<String, String, String> {
    DatabaseHelper      dbh;
    Context             context;

    public NetworkCall(Context context){
        this.context    = context;
        this.dbh        = new DatabaseHelper(context);
    }
    @Override
    protected String doInBackground(String... strings) {

        String ipInformation = new String();
        try (java.util.Scanner s = new java.util.Scanner(new java.net.URL("http://ip-api.com/json").openStream(), "UTF-8").useDelimiter("\\A")) {
            ipInformation = s.next();
        } catch (java.io.IOException e) {
            dbh.createLOG(new LogData("#905"));
            dbh.close();
            Log.i("#905","Network call Error!! : IOException "+e.getMessage());
        } catch (Exception e){
            dbh.createLOG(new LogData("#905"));
            dbh.close();
            Log.i("#905","Network call Error!! "+e.getMessage());
        }
        return ipInformation;
    }
    protected void onPostExecute(String result) {
        try {
            JSONObject ipObject      = new JSONObject(result);
            String ip                = ipObject.getString("query");
            String name              = ipObject.getString("isp");

            if(!dbh.doesIPExist(ip)){
                dbh.createIP(new IP(ip,name));
                dbh.close();
            }else{
                Log.i("#905","IP already Exist inside local database");
            }

            dbh.createConnection(new Connection("true"));
            dbh.close();
        } catch (JSONException e) {
            dbh.createLOG(new LogData("#905"));
            dbh.close();
            Log.i("#905","Network call Error!! : JSONException "+e.getMessage());
        } catch (Exception e ){
            dbh.createLOG(new LogData("#905"));
            dbh.close();
            Log.i("#905","Network call data Error!! "+e.getMessage());
        }

    }
}