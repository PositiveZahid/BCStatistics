package iboxbd.broadband.statistics;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import iboxbd.broadband.statistics.phone.InternetConnection;

public class ConnectionLog extends IntentService {
    public ConnectionLog(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Handler connectionTest = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what != 1) { // code if not connected

                    Log.d("Feedback : #002","Server/Internet Problem");
                    //LogInformation log          = new LogInformation("Location sync issue, Server Connection Problem","0");
                    //database.createLogInformation(log);
                    //database.closeDB();
                } else { // code if connected
                    //for (LocationInformation location:unSyncedLocation){
                    //    new SyncLocation(getApplicationContext()).execute(String.valueOf(location.getId()), location.getLatitude(), location.getLongitude(),location.getComment(),location.getCreated());
                    //}
                }
            }
        };

        InternetConnection.isNetworkAvailable("http://www.google.com",connectionTest,5000);

    }
}
