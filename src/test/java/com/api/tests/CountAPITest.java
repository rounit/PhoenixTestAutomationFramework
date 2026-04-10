package com.api.tests;

import static com.api.constant.Roles.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.serverices.DashboardService;
import com.api.serverices.UserServices;

import static com.api.utils.SpecUtils.*;

@Listeners(com.listeners.APITestListener.class)
public class CountAPITest 
{
	
private DashboardService dashboardService;
	
	@BeforeMethod(description="Setting up Dashboard instance")
	public void setup()
	{
		dashboardService = new DashboardService();
	}
	
	@Test(description="Verify if Count API response is giving correct response", groups= {"api","smoke","regression"})
	public void verifyAPIResponse() throws IOException
	{
		dashboardService.count(FD)
		 .then()
		 .spec(responseSpec_OK())
		 .body("message",Matchers.equalTo("Success"))
		 .time(Matchers.lessThan(1000L))
		 .body("data",Matchers.notNullValue())
		 
		 .body("data.size()",Matchers.equalTo(3))
		 .body("data.count",Matchers.everyItem(Matchers.greaterThanOrEqualTo(0)))
		 .body("data.label",Matchers.everyItem(Matchers.not(Matchers.blankOrNullString())))
		 .body("data.key",Matchers.containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
		 .body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));
	}
	
	@Test(description="Verify if Count API response is giving correct status code for invalid token", groups= {"api","negative","smoke","regression"})
	public void countAPITest_MissingAuthToken() throws IOException
	{
		dashboardService.countWithNoAuth()
		 .then()
		 .spec(responseSpec_TEXT(401));
	}
	
	
	
	
	
	

}
