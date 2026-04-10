package com.api.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

@Listeners(com.listeners.APITestListener.class)
public class BaseAPI 
{

	    @BeforeMethod(alwaysRun = true)
	    public void resetRestAssured() {
	   
	        RestAssured.requestSpecification = null;
	        
	    }
	}


