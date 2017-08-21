package iboxbd.broadband.statistics.ip;

import java.math.BigDecimal;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;
//import org.apache.commons.io.FileUtils;

/**
 * Created by User on 8/21/2017.
 */

public class SpeedTester implements ISpeedTestListener {

    @Override
    public void onCompletion(SpeedTestReport report) {
        // called when download/upload is complete

        //System.out.println("[COMPLETED] rate in octet/s : " + report.getTransferRateOctet());
        System.out.println("[COMPLETED] rate in bit/s   : " + report.getTransferRateBit());
        //System.out.println("[COMPLETED] rate in mb/s   : " + report.getTransferRateBit().longValue() / ( 8 * 1024)  );
        //android.text.format.Formatter.formatShortFileSize(activityContext, bytes);
        System.out.println("[COMPLETED] rate in mb/s   : " +humanReadableByteCount(report.getTransferRateBit().longValue()/8,true));
        //System.out.println(FileUtils.);
    }

    @Override
    public void onError(SpeedTestError speedTestError, String errorMessage) {
        // called when a download/upload error occur
    }

    @Override
    public void onProgress(float percent, SpeedTestReport report) {
        // called to notify download/upload progress
        System.out.println("[PROGRESS] progress : " + percent + "%");
        //System.out.println("[PROGRESS] rate in octet/s : " + report.getTransferRateOctet());
        //System.out.println("[PROGRESS] rate in bit/s   : " + report.getTransferRateBit());
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
