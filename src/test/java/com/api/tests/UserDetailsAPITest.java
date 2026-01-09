package com.api.tests;

import static com.api.constant.Roles.FD;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtils.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class UserDetailsAPITest 
{
	@Test(description="Verify if the Userdetails API response is shown correctly", groups= {"api","smoke","regression"})
	public void userDetailsAPITest() throws IOException
	{
		
		
		
		given()
		  .spec(requestSpecWithAuth(FD))
		.when()
		   .get("userdetails")
		.then()
		  .spec(responseSpec_OK())
		  .and()
		  .body(matchesJsonSchemaInClasspath("response-schema/userDetailsSchema.json"));
		   
	}

}
