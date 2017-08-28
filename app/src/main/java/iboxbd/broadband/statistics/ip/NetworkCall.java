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
import iboxbd.broadband.statistics.model.Wifi;
import iboxbd.broadband.statistics.phone.Connectivity;
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
            String ip_id             = "";
            String wifiName          = Connectivity.wifiName(context);
            String wifiId               = "";

            if(dbh.doesWifiExist(wifiName)){
                wifiId = String.valueOf(dbh.getWIFIID(wifiName));
                dbh.close();
                Log.i("#905","Wifi name already exist local database");
            }else{
                wifiId = String.valueOf(dbh.createWIFI(new Wifi(wifiName)));
                dbh.close();
            }

            if(dbh.doesIPExist(ip)){
                ip_id = String.valueOf(dbh.getIPID(ip));
                dbh.close();
                Log.i("#905","IP already exist inside local database");
            }else{
                ip_id = String.valueOf(dbh.createIP(new IP(ip,ip,name)));
                dbh.close();
            }

            dbh.createConnection(new Connection("true","true",wifiId,"true",ip_id));
            dbh.close();
        } catch (JSONException e) {
            dbh.createLOG(new LogData("#905"));
            dbh.close();
            e.printStackTrace();
            Log.i("#905","Network call Error!! : JSONException "+e.getMessage());
        } catch (Exception e ){
            dbh.createLOG(new LogData("#905"));
            dbh.close();
            e.printStackTrace();
            Log.i("#905","Network call data Error!! "+e.getMessage());
        }

    }
}