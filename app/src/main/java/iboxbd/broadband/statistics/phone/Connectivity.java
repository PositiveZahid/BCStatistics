package iboxbd.broadband.statistics.phone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZahiD on 05-Apr-16.
 */
public class Connectivity {
    public static boolean isConnect = false;
    public static NetworkInfo activeNetwork;
    public static ConnectivityManager connection;
    public static Thread thread;
    public static OkHttpClient okHttpClient;

    public static boolean isConnected(Context context) {

        connection = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connection.getActiveNetworkInfo();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    try {
                        URL url = new URL("http://www.google.com/");
                        HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                        urlc.setRequestProperty("User-Agent", "test");
                        urlc.setRequestProperty("Connection", "close");
                        urlc.setConnectTimeout(9000); // mTimeout is in seconds
                        urlc.connect();
                        if (urlc.getResponseCode() == 200) {
                            isConnect = true;
                        } else {
                            isConnect = false;
                        }
                    } catch (IOException e) {
                        Log.i("Error : #001", "Error checking internet connection", e);
                        isConnect = false;
                    }
                }
                isConnect = false;
            }
        });
        thread.start();
        return isConnect;
    }

    public static boolean wifiOrMobileData(Context context) {
        connection = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connection.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isConnected()) {
            isConnect = true;
        } else {
            isConnect = false;
        }
        return isConnect;
    }

    public static void isNetworkAvailable(final String url, final Handler handler, final int timeout) {
        // ask fo message '0' (not connected) or '1' (connected) on 'handler'
        // the answer must be send before before within the 'timeout' (in milliseconds)

        new Thread() {
            private boolean responded = false;

            @Override
            public void run() {
                // set 'responded' to TRUE if is able to connect with google mobile (responds fast)
                new Thread() {
                    @Override
                    public void run() {
                        okHttpClient = new OkHttpClient();

                        Request request = new Request.Builder().url(url).build();

                        try {
                            Response response = okHttpClient.newCall(request).execute();
                            if (!response.isSuccessful()) {
                                //responseBack        = "NotSuccessFull : Response Code "+response;
                                //throw new IOException("Unexpected code " + response);
                                Log.i("Feedback : #001", "Not Success ! " + " " + "Unexpected code " + response);
                            } else {
                                //responseBack        = ""+response.body().string();
                                Log.i("Feedback : #001", "Success ! Connection");
                                responded = true;
                            }
                        } catch (IOException e) {
                            //responseBack            = "IOException";
                            //throw new IOException("IOException");
                        }
                    }
                }.start();

                try {
                    int waited = 0;
                    while (!responded && (waited < timeout)) {
                        sleep(100);
                        if (!responded) {
                            waited += 100;
                        }
                    }
                } catch (InterruptedException e) {
                } // do nothing
                finally {
                    if (!responded) {
                        handler.sendEmptyMessage(0);
                    } else {
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        }.start();
    }

    public static boolean checkWifiOn(Context context) {
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON
            return true; // Connected to an access point
        } else {
            return false; // Wi-Fi adapter is OFF
        }
    }

    public static boolean isRegisteredWithWifi(Context context){
        boolean isWifi = false;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = connManager.getActiveNetworkInfo();
        isWifi = current != null && current.getType() == ConnectivityManager.TYPE_WIFI;
        return isWifi;
    }
}
