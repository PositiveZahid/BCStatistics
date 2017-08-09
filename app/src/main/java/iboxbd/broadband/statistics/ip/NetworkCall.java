package iboxbd.broadband.statistics.ip;

import android.os.AsyncTask;

/**
 * Created by User on 8/8/2017.
 */

/*
*
* */


public class NetworkCall extends AsyncTask {
    @Override
    protected Object doInBackground(Object... arg0) {
        try (java.util.Scanner s = new java.util.Scanner(new java.net.URL("http://ip-api.com/json").openStream(), "UTF-8").useDelimiter("\\A")) {
            System.out.println("My current IP address is " + s.next());
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}