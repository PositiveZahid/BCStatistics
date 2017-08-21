package iboxbd.broadband.statistics.model;

import iboxbd.broadband.statistics.utils.DateUtils;

/**
 * Created by User on 8/9/2017.
 */

public class IP {
    public String   ip;
    public String   address;
    public String   name;
    public String 	isSynced;
    public String	DateTime;

    public IP(){
    }

    public IP(String ip, String address, String name){
        this.ip         = ip;
        this.address    = address;
        this.name       = name;
        this.isSynced   = "false";
        this.DateTime   = DateUtils.getDateTime();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
