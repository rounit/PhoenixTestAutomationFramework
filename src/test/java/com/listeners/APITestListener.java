package com.listeners;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class APITestListener implements ITestListener
{
	private static final Logger logger = LogManager.getLogger(APITestListener.class);
	
	public void onTestStart(ITestResult result) {
		logger.info("***************************************");
	    logger.info("Starting the test {}",result.getName());
	    logger.info("Test class {}" , result.getMethod().getTestClass());
	    logger.info("Description {}" , result.getMethod().getDescription());
	    logger.info("Groups {}" , Arrays.toString(result.getMethod().getGroups()));
	    logger.info("***************************************");
	  }
	
	public void onTestSuccess(ITestResult result) 
	{
		long startTime = result.getStartMillis();
		long endTime = result.getEndMillis();
		
		logger.info("Total Duration:{} ms ", endTime - startTime);
		logger.info("{} -- Test Passed!!!", result.getName());
		
	}
	
	public void onTestFailure(ITestResult result) {
	    logger.error("{}- Test FAILED!!!" , result.getName());
	    logger.error("Error Message",result.getThrowable().getMessage());
	    logger.error(result.getThrowable());
	  }
	
	public void onTestSkipped(ITestResult result) {
	    
		logger.info("{}- Test SKIPPED!!!" , result.getName());
	    logger.error(result.getThrowable());
	  }
	
	public void onStart(ITestContext context) {
	    
		logger.info("******* Starting the Phoenix Framework *********");
	  }
	
	public void onFinish(ITestContext context) {
		logger.info("******* FINISH *********");
	  }

}
