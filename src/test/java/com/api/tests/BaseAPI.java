package com.api.tests;

import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

public class BaseAPI 
{

	    @BeforeMethod(alwaysRun = true)
	    public void resetRestAssured() {
	   
	        RestAssured.requestSpecification = null;
	        
	    }
	}


