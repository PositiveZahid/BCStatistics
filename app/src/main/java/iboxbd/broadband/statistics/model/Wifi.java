package iboxbd.broadband.statistics.model;

import iboxbd.broadband.statistics.utils.DateUtils;

/**
 * Created by User on 8/26/2017.
 */

public class Wifi {
    private String WIFI_ID;
    private String WIFI_NAME;
    private String WIFI_ISSYNCED;
    private String WIFI_DATETIME;

    public Wifi(String WIFI_NAME) {
        this.WIFI_NAME = WIFI_NAME;
        this.WIFI_ISSYNCED = "false";
        this.WIFI_DATETIME = DateUtils.getDateTime();

    }

    public String getWIFI_ID() {
        return WIFI_ID;
    }

    public void setWIFI_ID(String WIFI_ID) {
        this.WIFI_ID = WIFI_ID;
    }

    public String getWIFI_NAME() {
        return WIFI_NAME;
    }

    public void setWIFI_NAME(String WIFI_NAME) {
        this.WIFI_NAME = WIFI_NAME;
    }

    public String getWIFI_ISSYNCED() {
        return WIFI_ISSYNCED;
    }

    public void setWIFI_ISSYNCED(String WIFI_ISSYNCED) {
        this.WIFI_ISSYNCED = WIFI_ISSYNCED;
    }

    public String getWIFI_DATETIME() {
        return WIFI_DATETIME;
    }

    public void setWIFI_DATETIME(String WIFI_DATETIME) {
        this.WIFI_DATETIME = WIFI_DATETIME;
    }
}
