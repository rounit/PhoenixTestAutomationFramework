package com.api.tests;

import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;

public class BaseAPI 
{

	    @BeforeMethod(alwaysRun = true)
	    public void resetRestAssured() {
	        RestAssured.reset();
	        RestAssured.defaultParser = null;
	    }
	}


