package com.vz.services;

import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vz.beans.LogBean;
import com.vz.beans.MonitorBean;
import com.vz.util.*;

public class DataHandler {
	
	public void  sendEmail(String subject, String body){
		TrigerAlert trigger = new TrigerAlert(subject,body);
		Thread triggerThread = new Thread(trigger);
		triggerThread.start();
	}
	
	public void keyWordAlert(String keyWords, String logMessage, LogBean logBean){
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
					//System.out.println(token.toUpperCase()+":::FOUND.");
					
					//Email - subject formation
					subject=token.toUpperCase()+": FOUND : "+logBean.getClient_id()+" : "+logBean.getEvent_date();
					
					//Email - Body formation
					Gson gson = new Gson();
					Type type = new TypeToken<LogBean>(){}.getType();
					body = gson.toJson(logBean,type);
					
					//Trigger email when keyword found.
					sendEmail(subject,body);
					
					
				}
				
			}
		}catch(Exception e){
			System.out.println("Exception happened in checking keywords.");
			e.printStackTrace();
		}
		
						
	} 
	
	public String pullLog(String eventTS, String emKeyWords){
		String jsonData = "";
		String lastReadEventTS = eventTS;
		MonitorBean mBean = new MonitorBean();
		LogBean logBean = null;
		List<LogBean> logData = new ArrayList<LogBean>();
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		//String sql = "select to_char(max(event_date),'MM/DD/YYYY HH:MI:SS PM') event_date from app_log_table where to_char(event_date,'MM/DD/YYYY HH:MI:SS PM') > '"+eventTS+"'  order by 1 desc";
		StringBuffer sql = new StringBuffer();
		sql.append("select to_char(event_date,'MM/DD/YYYY HH:MI:SS PM') event_date ");
		sql.append(" , log_level, message,logger,logger_line_num , ex_short, ex_class_name, ex_file_name, ex_line_num,ex_method,ex_localized_msg ");
		sql.append(" ,ex_full,client_id ");
		sql.append(" from app_log_table where to_char(event_date,'MM/DD/YYYY HH:MI:SS PM') > '"+eventTS+"'  order by 1 asc");
		//System.out.println("pullLog:"+sql);
		try{
			 con = ConnectionFactory.getDatabaseConnection();
		}catch(Exception e){
			System.out.println(e);
		}
		if(con != null){
			try{
				st=con.createStatement();
				rs = st.executeQuery(sql.toString());
				rs.setFetchSize(500);
				while(rs.next()){
					lastReadEventTS = rs.getString("event_date");
					logBean = new LogBean();
					logBean.setEvent_date(Utils.putNbsp(rs.getString("event_date")));
					logBean.setLogLevel(Utils.putNbsp(rs.getString("log_level")));
					logBean.setMessage(Utils.putNbsp(rs.getString("message")));
					logBean.setLogger(Utils.putNbsp(rs.getString("logger")));
					logBean.setLogger_line_num(Utils.putNbsp(rs.getString("logger_line_num")));
					logBean.setEx_short(Utils.putNbsp(rs.getString("ex_short")));
					logBean.setEx_class_name(Utils.putNbsp(rs.getString("ex_class_name")));
					logBean.setEx_file_name(Utils.putNbsp(rs.getString("ex_file_name")));
					logBean.setEx_line_num(Utils.putNbsp(rs.getString("ex_line_num")));
					logBean.setEx_method(Utils.putNbsp(rs.getString("ex_method")));
					logBean.setEx_localized_msg(Utils.putNbsp(rs.getString("ex_localized_msg")));
					logBean.setClient_id(Utils.putNbsp(rs.getString("client_id")));
					//System.out.println(Utils.putNbsp(rs.getString("event_date")));
					
					//Check for Keyword's presence and alert if present
					keyWordAlert(emKeyWords,rs.getString("message"),logBean);
					
					logData.add(logBean);
					
				}
				mBean.setLastReadEventTS(lastReadEventTS);
				mBean.setLogData(logData);
				
				//Making JSON Object
				Gson gson = new Gson();
				Type type = new TypeToken<MonitorBean>(){}.getType();
				jsonData=gson.toJson(mBean,type);
				
				
			}catch(Exception e){
				//logger.error();
				System.out.println(e);
			}finally{
				try{
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
				if(con != null)
					con.close();
				}catch(Exception e){
					System.out.println("Exception while closing connection."+e);
				}
			}
			}
			System.out.println("Exiting pullLog method : "+jsonData);
			
		return jsonData;
	}
	
	public String findLatestEventTS(){
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		String sql = "select to_char(max(event_date),'MM/DD/YYYY HH:MI:SS PM') event_date from app_log_table ";
		String latestEventTS = "";
		
		try{
			 con = ConnectionFactory.getDatabaseConnection();
		}catch(Exception e){
			System.out.println(e);
		}
		
		if(con != null){
			try{
				st=con.createStatement();
				rs = st.executeQuery(sql);
				//rs.setFetchSize(500);
				while(rs.next()){
					latestEventTS = rs.getString(1);
				}
				
			}catch(Exception e){
				//logger.error();
				System.out.println(e);
			}finally{
				try{
				if(rs != null)
					rs.close();
				if(st != null)
					st.close();
				if(con != null)
					con.close();
				}catch(Exception e){
					System.out.println("Exception while closing connection."+e);
				}
			}
			}
			System.out.println("Exiting findLatestTS method : "+latestEventTS);
			return  latestEventTS;
	} 
	
	public String checkError(){
		String exp="";
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		Boolean expExists = false;
		String subject="ERROR: ";
		String body="ERROR";
		String clobAsString="ERROR EMAIL";
		
		//pass date as input
		String sql = "select * from app_log_table where upper(log_level) = upper('ERROR')   order by 1 desc";
		
		try{
			 con = ConnectionFactory.getDatabaseConnection();
		}catch(Exception e){
			System.out.println(e);
		}
		
		if(con != null){
		try{
			st=con.createStatement();
			rs = st.executeQuery(sql);
			rs.setFetchSize(500);
			while(rs.next()){
				expExists = true;
				exp=rs.getString(2)+":"+rs.getString("ex_short");
				
				subject+=rs.getString("location");
				
				if(rs.getClob("ex_full") != null){
					try{
						InputStream in = rs.getClob("ex_full").getAsciiStream();
						StringWriter w = new StringWriter();
						IOUtils.copy(in, w);
						 clobAsString = w.toString();
					}catch(Exception e){
						System.out.println("Exception occcured while converting clob to String");
						clobAsString = rs.getString("ex_short");
					}
					}
				else{
					System.out.println("CLOB is null, setting short Dtails of Exception.");
					clobAsString = rs.getString("ex_short");
				}
				body=clobAsString;
				//Trigger email for each error encountered.
				TrigerAlert trigger = new TrigerAlert(subject,body);
				Thread triggerThread = new Thread(trigger);
				triggerThread.start();
				
			}
			
		}catch(Exception e){
			//logger.error();
			System.out.println(e);
		}finally{
			try{
			if(rs != null)
				rs.close();
			if(st != null)
				st.close();
			if(con != null)
				con.close();
			}catch(Exception e){
				System.out.println("Exception while closing connection."+e);
			}
		}
		}
		
		return  exp;
	}

}
