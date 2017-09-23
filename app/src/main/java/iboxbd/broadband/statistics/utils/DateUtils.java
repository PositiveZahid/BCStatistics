package iboxbd.broadband.statistics.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    public static Calendar now                      = Calendar.getInstance();
    //public static Calendar dayStart 	            = new Calendar(now.get(Calendar.YEAR),now.get(Calendar.MONTH), now.get(Calendar.DATE), 00,00,00);
    //public static Calendar dayEnd 		            = new Calendar(now.get(Calendar.YEAR),now.get(Calendar.MONTH), now.get(Calendar.DATE), 23,59,59);

    public static final SimpleDateFormat FULL_DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static Date ValueToDate(String val) throws ParseException {
        Date resultDate = null;
        if (val != null && !val.equals(StringUtils.EMPTY_STRING)) {
            resultDate = DateUtils.FULL_DATE_TIME_FORMAT.parse(val.toString());
        }
        return resultDate;
    }

    public static String DateToValue(Object date) throws ParseException {
        return DateUtils.FULL_DATE_TIME_FORMAT.format(date);
    }

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static String convertSecondsToString(long second){

        int seconds = (int) second;
        int sec = seconds % 60;
        int min = (seconds / 60)%60;
        int hours = (seconds/60)/60;

        return hours + ":" + min + ":" + sec;
    }
}
