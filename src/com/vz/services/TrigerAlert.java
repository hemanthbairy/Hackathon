package com.vz.services;

import java.util.Properties;

import javax.mail.Session;

import com.vz.util.EmailUtil;

public class TrigerAlert implements Runnable{
	
	private String subject;
	private String body;
	
	public TrigerAlert (String subject, String body){
		this.subject = subject;
		this.body = body;
	}
	
	public void run(){
		System.out.println("SimpleEmail Start");
        
        String smtpHostServer = "mail.hackathon.com";//Pull from Properties file
        String emailID = "pavneet@mail.hackathon.com";//Pull from Properties file
         
        Properties props = System.getProperties();
 
        props.put("mail.smtp.host", smtpHostServer);
 
        Session session = Session.getInstance(props, null);
         
        EmailUtil.sendEmail(session, emailID,subject, body);
	}

}
