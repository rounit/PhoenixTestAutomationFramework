package com.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo {

	
	
	private static Logger logger = LogManager.getLogger(Demo.class);
	
	public static void main(String[] args) 
	{
		System.out.println("Inside the Main Method");
		logger.info("Inside the Main Method");
		int a=10;
		logger.info("Value of a {} ", a);
		
		int b=0;
		if(b==0)
		{
			logger.warn("Value of a {} ", b);
		}
		else
		{
			logger.warn("Value of a {} ", b);
		}
		
		try
		{
		int result =a/b;
		logger.warn("Value of result {} ", result);
		}
		catch(ArithmeticException e)
		{
			logger.error("Operation cannot happen" , e);
		}
		
		

	}

}
