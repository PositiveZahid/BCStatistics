package iboxbd.broadband.statistics.model;

import iboxbd.broadband.statistics.sqlite.DB_BASIC;
import iboxbd.broadband.statistics.utils.DateUtils;

public class Connection extends DB_BASIC {
    public String ip_id;
    public String isWifiOn;
    public String isConnectToWifi;
    public String wifiId;
    public String isConnectToInternet;
    public String isSynced;
    public String dateTime;


    public Connection() {

    }

    public Connection(String isConnectToInternet, String ip_id) {
        this.isConnectToInternet = isConnectToInternet;
        this.ip_id = ip_id;
        this.isSynced = "false";
        this.dateTime = DateUtils.getDateTime();
    }

    public Connection( String isWifiOn, String isConnectToWifi, String wifiId, String isConnectToInternet,String ip_id) {
        this.isWifiOn               = isWifiOn;
        this.isConnectToWifi        = isConnectToWifi;
        this.wifiId                 = wifiId;
        this.isConnectToInternet    = isConnectToInternet;
        this.ip_id                  = ip_id;
        this.isSynced               = "false";
        this.dateTime               = DateUtils.getDateTime();
    }

    public Connection( String isWifiOn, String isConnectToWifi, String wifiId, String isConnectToInternet,String ip_id, String dateTime) {
        this.isWifiOn               = isWifiOn;
        this.isConnectToWifi        = isConnectToWifi;
        this.wifiId                 = wifiId;
        this.isConnectToInternet    = isConnectToInternet;
        this.ip_id                  = ip_id;
        this.isSynced               = "false";
        this.dateTime               = dateTime;
    }
    public String getIp_id() {
        return ip_id;
    }

    public void setIp_id(String ip_id) {
        this.ip_id = ip_id;
    }

    public String getIsWifiOn() {
        return isWifiOn;
    }

    public void setIsWifiOn(String isWifiOn) {
        this.isWifiOn = isWifiOn;
    }

    public String getIsConnectToWifi() {
        return isConnectToWifi;
    }

    public void setIsConnectToWifi(String isConnectToWifi) {
        this.isConnectToWifi = isConnectToWifi;
    }

    public String getWifiId() {
        return wifiId;
    }

    public void setWifiId(String wifiId) {
        this.wifiId = wifiId;
    }

    public String getIsConnectToInternet() {
        return isConnectToInternet;
    }

    public void setIsConnectToInternet(String isConnectToInternet) {
        this.isConnectToInternet = isConnectToInternet;
    }

    public String getIsSynced() {
        return isSynced;
    }

    public void setIsSynced(String isSynced) {
        this.isSynced = isSynced;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "ip='" + ip_id + '\'' +
                ", Wifi='" + isWifiOn + '\'' +
                ", wifi='" + isConnectToWifi + '\'' +
                ", wifiId='" + wifiId + '\'' +
                ", internet='" + isConnectToInternet + '\'' +
                ", Synced='" + isSynced + '\'' +
                ", Time='" + dateTime + '\'' +
                '}';
    }
}
