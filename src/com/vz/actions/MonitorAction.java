package com.vz.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vz.forms.MonitorForm;

public class MonitorAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonitorForm monitorForm = (MonitorForm) form;
		monitorForm.setMessage("Monitoring services started.");
		
	return mapping.findForward("success");
	}
	
}
