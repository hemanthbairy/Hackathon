package com.vz.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.vz.forms.MonitoringForm;

public class MonitoringAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		MonitoringForm monitoringForm = (MonitoringForm) form;
		monitoringForm.setMessage("Monitoring services started.");
		
	return mapping.findForward("success");
	}
	
}
