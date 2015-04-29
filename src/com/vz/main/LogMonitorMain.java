package com.vz.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.util.Properties;
import java.util.StringTokenizer;

import com.vz.util.Utils;
import com.vz.services.*;

public class LogMonitorMain {

	private static String emKeyWords="";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Reading Properties File for keyWords
		//String emKeyWords = "";
		String propsFilePath = "";
		if(args.length >0 && args[0] != null){
			propsFilePath=args[0];
		}else{
			propsFilePath=Utils.getHackathonpropsfilepath();
		}
		File f = new File(propsFilePath);//get this value in arguments
		Properties prop = new Properties();
		if(f.exists()){
			try{
				prop.load(new FileInputStream(f));
				emKeyWords = prop.getProperty("emailKeyWords","");
				
				//System.out.println("emKeyWords:"+emKeyWords);
				
			}catch(Exception e){
				System.out.println("caught Exception while loading properties file.\n"+e);
			}
		}else{
			System.out.println("Shutting Down : Properties file not found at "+propsFilePath);
			System.exit(0);
		}
		System.out.println("===========================================");
		System.out.println("Properties file loaded from :"+propsFilePath);
		System.out.println("Monitoring for keywords:"+emKeyWords);
		System.out.println("===========================================");
		
		//get LogFilePath
		String logFilePath = "C:\\Logs\\WebAppLogs\\logfile.log";
		if(prop.getProperty("logFilePath") != null){
			logFilePath = prop.getProperty("logFilePath");
		}
		
		File file =  new File(logFilePath);
		if(!file.exists()){
			System.out.println("Shutting Down : LogFile not found at "+logFilePath);
			System.exit(0);
		}
		if(emKeyWords.isEmpty()){
			System.out.println("Shutting Down : No KeyWords found in "+logFilePath);
			System.exit(0);
		}
		//File exists for below code
		System.out.println("LogFile found at "+logFilePath);
		System.out.println("=====================Monitoring======================");
		
		try{
			RandomAccessFile r = new RandomAccessFile(file,"r");
			
			long startPosition = r.length();
			long lastReadPosition = startPosition-1;//Default value
			
			while(true){
				if(startPosition > lastReadPosition){
					lastReadPosition = checkLogs(lastReadPosition,file,r);
					
				}else{
					//Waiting
				}
				r=null;
				r = new RandomAccessFile(file,"r");
				startPosition = r.length();
			}
			
		}catch(Exception e){
			System.out.println("Exception caught in main()");
			e.printStackTrace();
		}
		
		
		
	}
	
	public static long checkLogs(long startPos, File file, RandomAccessFile r) throws Exception{
		long lastRead = startPos;
		r.seek(lastRead);
		String logLine = "";
		while((logLine = r.readLine()) != null){
			
			//check for KeyWords
			keyWordAlert(emKeyWords,logLine);
			//System.out.println(logLine);
		}
		lastRead = r.getFilePointer();
		return lastRead;
	}
	
	public static void keyWordAlert(String keyWords, String logMessage){
		Boolean isKeyWordPresent = false;
		StringTokenizer st = new StringTokenizer(keyWords);
		//String token = "";
		String subject="";
		String body="";
		try{
			while(st.hasMoreTokens()){
				//System.out.println("::"+st.nextToken());
				String token = st.nextToken();
				if(logMessage.toUpperCase().contains(token.toUpperCase())){
					isKeyWordPresent = true;
					System.out.println("Alert !! "+token.toUpperCase());
					
					//Email - subject formation
					subject=token.toUpperCase()+": HIT ";
					
					//Email - Body formation
					body = logMessage;
					
					//Trigger email when keyword found.
					DataHandler dh = new DataHandler();
					dh.sendEmail(subject,body);
					
				}
				
			}
		}catch(Exception e){
			System.out.println("Exception happened in checking keywords.");
			e.printStackTrace();
		}
		
						
	}

}
