package com.vz.forms;

import org.apache.struts.action.ActionForm;

public class MonitorForm extends ActionForm  {
	
	private String message;
	private String latestEventTS; 
	private String emKeyWords;
	
	public String getLatestEventTS() {
		return latestEventTS;
	}

	public void setLatestEventTS(String latestEventTS) {
		this.latestEventTS = latestEventTS;
	}

	public String getMessage() {
		return message;
	}
	 
	public void setMessage(String message) {
		this.message = message;
	}

	public String getEmKeyWords() {
		return emKeyWords;
	}

	public void setEmKeyWords(String emKeyWords) {
		this.emKeyWords = emKeyWords;
	}

}
