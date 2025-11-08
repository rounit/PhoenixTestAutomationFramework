package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.utils.SpecUtils;

import static com.api.constant.Roles.*;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class CountAPITest 
{
	
	@Test
	public void verifyAPIResponse() throws IOException
	{
		given()
		 .spec(SpecUtils.requestSpecWithAuth(FD))
		 .when()
		 .get("/dashboard/count")
		 .then()
		 .spec(SpecUtils.responseSpec_OK())
		 .body("message",Matchers.equalTo("Success"))
		 .time(Matchers.lessThan(1000L))
		 .body("data",Matchers.notNullValue())
		 
		 .body("data.size()",Matchers.equalTo(3))
		 .body("data.count",Matchers.everyItem(Matchers.greaterThanOrEqualTo(0)))
		 .body("data.label",Matchers.everyItem(Matchers.not(Matchers.blankOrNullString())))
		 .body("data.key",Matchers.containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"))
		 .body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"));
	}
	
	@Test
	public void countAPITest_MissingAuthToken() throws IOException
	{
		given()
		 .spec(SpecUtils.requestSpec())
		 .when()
		 .get("/dashboard/count")
		 .then()
		 .spec(SpecUtils.responseSpec_TEXT(401));
	}
	
	
	
	
	
	

}
