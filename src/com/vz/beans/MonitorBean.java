package com.vz.beans;

import java.util.List;

public class MonitorBean {

	private String lastReadEventTS = "";
	private List logData = null; 
	
	public String getLastReadEventTS() {
		return lastReadEventTS;
	}
	public void setLastReadEventTS(String lastReadEventTS) {
		this.lastReadEventTS = lastReadEventTS;
	}
	public List getLogData() {
		return logData;
	}
	public void setLogData(List logData) {
		this.logData = logData;
	}
}
