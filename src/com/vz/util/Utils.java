package com.vz.util;

public class Utils {
	
	public static final String hackathonPropsFilePath = "C://Properties//hackathon.properties";
	
	public static String putNbsp(String s){
		if((s==null)||(s.trim().equals("")||(s.equals("null")))){
			s="&nbsp;";
		}
		return s;
	}

	public static String getHackathonpropsfilepath() {
		return hackathonPropsFilePath;
	}


}
