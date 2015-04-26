package com.vz.beans;

public class LogBean {

	private String event_date;
	private String logLevel;
	private String message;
	private String logger ;
	private String logger_line_num ;
	private String ex_short;
	private String ex_class_name;
	private String ex_file_name;
	private String ex_line_num;
	private String ex_method;
	private String ex_localized_msg;
	private String client_id;
	
	
	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getLogger() {
		return logger;
	}
	public void setLogger(String logger) {
		this.logger = logger;
	}
	public String getLogger_line_num() {
		return logger_line_num;
	}
	public void setLogger_line_num(String logger_line_num) {
		this.logger_line_num = logger_line_num;
	}
	public String getEx_short() {
		return ex_short;
	}
	public void setEx_short(String ex_short) {
		this.ex_short = ex_short;
	}
	public String getEx_class_name() {
		return ex_class_name;
	}
	public void setEx_class_name(String ex_class_name) {
		this.ex_class_name = ex_class_name;
	}
	public String getEx_file_name() {
		return ex_file_name;
	}
	public void setEx_file_name(String ex_file_name) {
		this.ex_file_name = ex_file_name;
	}
	public String getEx_line_num() {
		return ex_line_num;
	}
	public void setEx_line_num(String ex_line_num) {
		this.ex_line_num = ex_line_num;
	}
	public String getEx_method() {
		return ex_method;
	}
	public void setEx_method(String ex_method) {
		this.ex_method = ex_method;
	}
	public String getEx_localized_msg() {
		return ex_localized_msg;
	}
	public void setEx_localized_msg(String ex_localized_msg) {
		this.ex_localized_msg = ex_localized_msg;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	
}
