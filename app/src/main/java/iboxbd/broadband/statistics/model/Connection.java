package iboxbd.broadband.statistics.model;

import iboxbd.broadband.statistics.sqlite.DB_BASIC;

public class Connection extends DB_BASIC
{
	public String 	isConnected;
	public String 	isSynced;
	public String	DateTime;

	public Connection(){

	}

	public Connection(String isConnected, String isSynced, String DateTime ){
		this.isConnected = isConnected;
		this.isSynced = isSynced;
		this.DateTime = DateTime;
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
