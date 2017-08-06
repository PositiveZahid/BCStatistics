package iboxbd.broadband.statistics;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import iboxbd.broadband.statistics.phone.InternetConnection;
import iboxbd.broadband.statistics.sqlite.Connection;
import iboxbd.broadband.statistics.sqlite.DatabaseHelper;
import iboxbd.broadband.statistics.utils.DateUtils;

public class ConnectionService extends Service {
    Connection connection;
    DatabaseHelper database;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.print("Feedback : #003 Connection Service Started");
        connection = new Connection();
        Handler connectionTest = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what != 1) { // code if not connected
                    Log.d("Feedback : #002","Server/Internet Problem");
                    connection.setDateTime(DateUtils.getDateTime());
                    connection.setIsConnected("false");
                    connection.setIsSynced("false");
                    database.createConnection(connection);
                    database.close();
                } else { // code if connected

                    try (java.util.Scanner s = new java.util.Scanner(new java.net.URL("https://api.ipify.org").openStream(), "UTF-8").useDelimiter("\\A")) {
                        System.out.println("My current IP address is " + s.next());
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                    connection.setDateTime(DateUtils.getDateTime());
                    connection.setIsConnected("true");
                    connection.setIsSynced("false");
                    database.createConnection(connection);
                    database.close();
                }
            }
        };

        InternetConnection.isNetworkAvailable("http://www.google.com",connectionTest,2000);

        return null;
    }
}
