package iboxbd.broadband.statistics.model;

import iboxbd.broadband.statistics.sqlite.DB_BASIC;
import iboxbd.broadband.statistics.utils.DateUtils;

public class Connection extends DB_BASIC
{
	public String 	isConnected;
	public String 	isSynced;
	public String	DateTime;
	public String  	ip_id;



    public Connection(){

	}

	public Connection(String isConnected, String ip_id){
		this.isConnected 	= isConnected;
        this.ip_id          = ip_id;
		this.isSynced   	= "false";
		this.DateTime   	= DateUtils.getDateTime();
	}

	public Connection(String isConnected, String ip_id, String dateTime){
		this.isConnected 	= isConnected;
		this.ip_id          = ip_id;
		this.isSynced   	= "false";
		this.DateTime   	= dateTime;
	}
    public String getIp_id() {
        return ip_id;
    }

    public void setIp_id(String ip_id) {
        this.ip_id = ip_id;
    }
	public String getIsConnected() {
		return isConnected;
	}

	public void setIsConnected(String isConnected) {
		this.isConnected = isConnected;
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
