package iboxbd.broadband.statistics;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import iboxbd.broadband.statistics.ip.NetworkCall;
import iboxbd.broadband.statistics.model.LogData;
import iboxbd.broadband.statistics.phone.Connectivity;
import iboxbd.broadband.statistics.model.Connection;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import iboxbd.broadband.statistics.utils.DateUtils;

public class ConnectionService extends Service {
    DatabaseHelper dbh;
    String wifiId;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        dbh     = new DatabaseHelper(getApplicationContext());
        wifiId  = "";

        try{

            if(Connectivity.checkWifiOn(getApplicationContext())){                                  // Checking Wifi
                if(Connectivity.isRegisteredWithWifi(getApplicationContext())){                     // Check Registered With Wifi
                    if(dbh.doesWifiExist(Connectivity.wifiName(getApplicationContext()))){
                        wifiId = String.valueOf(dbh.getWIFIID(Connectivity.wifiName(getApplicationContext())));
                    }
                    Connectivity.isInternetAvailable("http://www.google.com",new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg.what != 1) { // code if not connected
                                Log.i("#004","Server/Internet Problem");
                                //dbh.createConnection(new Connection("false","-"));
                                dbh.createConnection(new Connection("true","true",wifiId,"false","-"));
                                dbh.close();
                            } else { // code if connected
                                Log.i("#004","Internet Connected");
                                new NetworkCall(getApplicationContext()).execute();
                            }
                        }
                    },2000);
                }else{
                    Log.i("#004","Wifi enabled but not connected to a network");
                    //dbh.createConnection(new Connection("false","-"));
                    dbh.createConnection(new Connection("true","false","-","false","-"));
                    dbh.close();
                }
            }else{
                Log.i("#004","Wifi disabled");
                dbh.createConnection(new Connection("false","false","-","false","-"));
                dbh.close();
            }

        }catch (Exception e){
            dbh.createLOG(new LogData("#904"));
            dbh.close();
            Log.i("#904","Connection Checking Error!! "+e.getMessage());
            Toast.makeText(getApplicationContext(),"Connection Checking problem occurred!! .. ",Toast.LENGTH_LONG).show();
        }


        return Service.START_STICKY_COMPATIBILITY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}



