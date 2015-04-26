package com.vz.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vz.forms.MonitorForm;
import com.vz.services.DataHandler;
import com.vz.util.Utils;

public class MonitorAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonitorForm monitorForm = (MonitorForm) form;
		monitorForm.setMessage("Monitoring services started.");
		
		String action="";
		String emKeyWords = "";
		action = request.getParameter("action");
		//System.out.println(action+":"+request.getParameter("ab"));
		if(action == null){
			action="";
		}
		DataHandler handler = new DataHandler();
		
		if(request.getParameter("action")==null){
			//Before Main Page Load - find the maximum event value from DB
			String latestEventTS = "";
			latestEventTS = handler.findLatestEventTS();
			monitorForm.setLatestEventTS(latestEventTS);
			
			//Load Key-words to be checked from properties file.
			
			File f = new File(Utils.getHackathonpropsfilepath());
			 Properties prop = new Properties();
			if(f.exists()){
				try{
					prop.load(new FileInputStream(f));
					emKeyWords = prop.getProperty("emailKeyWords","");
					
					//System.out.println("emKeyWords:"+emKeyWords);
					
				}catch(Exception e){
					System.out.println("caught Exception while loading properties file.\n"+e);
				}
			}
			monitorForm.setEmKeyWords(emKeyWords);
		}
		
		
		if(action.equalsIgnoreCase("checkLog")){
			
			String output = handler.checkError();
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(output);
			out.flush();
			out.close();
			
			return null;
		}else if(action.equalsIgnoreCase("pollLog")){
			String latestTS = "";//latest value every time
			latestTS = request.getParameter("latestTS");
			
			String output = handler.pullLog(latestTS,monitorForm.getEmKeyWords());
			
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(output);
			out.flush();
			out.close();
			
			return null;
		}
		
	return mapping.findForward("success");
	}
	
}
