package com.vz.actions;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vz.forms.MonitorForm;
import com.vz.services.DataHandler;

public class MonitorAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonitorForm monitorForm = (MonitorForm) form;
		monitorForm.setMessage("Monitoring services started.");
		
		String action="";
		action = request.getParameter("action");
		System.out.println(action+":"+request.getParameter("ab"));
		if(action == null){
			action="";
		}
		
		if(action.equalsIgnoreCase("checkLog")){
			DataHandler handler = new DataHandler();
			String output = handler.checkError();
			
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
