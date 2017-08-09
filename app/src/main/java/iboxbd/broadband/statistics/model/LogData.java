package iboxbd.broadband.statistics.model;

import iboxbd.broadband.statistics.sqlite.DB_BASIC;
import iboxbd.broadband.statistics.utils.DateUtils;

/**
 * Created by User on 8/7/2017.
 */

public class LogData extends DB_BASIC {
    public String 	log;
    public String 	isSynced;
    public String	DateTime;

    public LogData(){

    }

    public LogData(String log){
        this.log            = log;
        this.isSynced   	= "false";
        this.DateTime   	= DateUtils.getDateTime();
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(String isSynced) {
        this.isSynced = isSynced;
    }

    public String getDateTime() {
        return DateTime;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }
}
