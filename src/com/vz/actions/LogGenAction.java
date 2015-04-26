package com.vz.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vz.forms.LogGenForm;

import org.apache.logging.log4j.ThreadContext;

public class LogGenAction extends Action {

	
	
@Override
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	LogGenForm logGenForm = (LogGenForm) form;
	logGenForm.setMessage("Message");

	//Setting UserIP
	ThreadContext.put("ipUser","sw07.verizon.com");//HardCoded client user name for logging purposes
	//ThreadContext.clearAll();


return mapping.findForward("success");
}
	
	
	
}
