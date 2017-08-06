package iboxbd.broadband.statistics.sqlite;

public class Connection extends DB_BASIC
{
	public String	DateTime;
	public Boolean 	isConnected;
	public Boolean 	isSynced;

	public String getDateTime() {
		return DateTime;
	}

	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}

	public Boolean getConnected() {
		return isConnected;
	}

	public void setConnected(Boolean connected) {
		isConnected = connected;
	}

	public Boolean getSynced() {
		return isSynced;
	}

	public void setSynced(Boolean synced) {
		isSynced = synced;
	}
}
