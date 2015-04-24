package com.vz.services;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ValidationService {
	private static final Logger logger = LogManager.getLogger(ParentService.class);
	
	public void validateRequest(){
		logger.info("UserName and User Password are present.");
		logger.info("validationg completed : Success");
	}
	public void validateRequest(String expFlag){
		logger.warn("User have not given password.");
		//logger.error("Validation Failed.",new Exception("Mobile digit number is not 10"));
		logger.error("Exception happened while getting DataBase connection.",new Exception("Unable to get DataBase connection. DataBase might be down."));
	}
	//have exception in its one of the methods
}
