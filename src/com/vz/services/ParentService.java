package com.vz.services;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



public class ParentService {
	private static final Logger logger = LogManager.getLogger(ParentService.class);
	public void startServices(){
		
		
		logger.info("Entering ParentService : startServices()");
		
		RequestingService reqService = new RequestingService();
		
		for(int i=0; i<40;i++){
			logger.info("Sending request");
			reqService.ProcessingRequest(i);
			logger.info("Request received for Parent id"+i);		
		}
		
		logger.info("Exiting ParentService : startServices()");
	}
}
