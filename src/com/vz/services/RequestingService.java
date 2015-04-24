package com.vz.services;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class RequestingService {
	private static final Logger logger = LogManager.getLogger(ParentService.class);
	public void ProcessingRequest(int id){
		logger.info("Entering RequestingService : ProcessingRequest()");
		ValidationService valService= new ValidationService();
		for(int i=100; i>0 ; i--){
			logger.info("Request received from "+id+""+i+" user id.");
			for(int j=100;j>0;j--){
				//nothing just time gap
			}
			if(i==50 && id==10){
				valService.validateRequest("50");
			}else{
				valService.validateRequest();
			}
			logger.info("User Request processed");
		}
			
		
		logger.info("Exiting RequestingService : ProcessingRequest()");
	}
	
}
