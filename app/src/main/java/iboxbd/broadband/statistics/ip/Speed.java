package iboxbd.broadband.statistics.ip;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by User on 8/23/2017.
 */

public class Speed extends AsyncTask<Void, Void, Void> {
    private long startTime;
    private long endTime;
    private long takenTime;
    private Context context;
    private long size;
    private long dataSize;
    private BigDecimal second;
    private BigDecimal speed;

    public Speed(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        startTime = System.currentTimeMillis();
        size = 0;
        second = new BigDecimal(0.0);

        URL url = null;
        try {
            url = new URL("http://gis.ess.washington.edu/data/raster/kmtiles/README.TXT");
            //url = new URL("https://www.ncl.ucar.edu/Applications/Data/asc/string1.txt");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("User-Agent", "Mozilla/4.76");
            size = conn.getContentLength();

            if (size < 0) {
                System.out.println("File not found");
            } else {
                System.out.println("File size in Bytes: " + size);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        endTime = System.currentTimeMillis();
        return null;
    }

    @Override
    protected void onPostExecute(Void string) {
        try {
            dataSize    = size / 1024;
            takenTime   = endTime - startTime;
            second      = new BigDecimal(takenTime).divide(new BigDecimal(1000),2, RoundingMode.HALF_UP);
            speed       = new BigDecimal(dataSize).divide(second,2, RoundingMode.HALF_UP);

            Toast.makeText(context, "" + speed + " kbps", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Issue .. "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}