package com.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo2 {

	
	
	private static Logger logger = LogManager.getLogger(Demo2.class);
	
	public static void main(String[] args) 
	{
		System.out.println("Inside the Main Method");
		logger.info("Inside the Main Method");
		int a=10;
		System.out.println("Value of a is " + a);
		logger.info("Value of a {} ",a);
		int b=20;
		System.out.println("Value of a is " + b	);
		logger.info("Value of a {} ",b);
		
		int result = a+b;
		System.out.println("result of addition " + result	);
		logger.info("Final Result {} ",result);
		
		System.out.println("result is " + result);
		
		System.out.println("Programm ended !!!");
		logger.info("Programm ended !!!");

	}

}
