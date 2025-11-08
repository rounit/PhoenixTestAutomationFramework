package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.api.constant.Roles.*;
import com.api.utils.SpecUtils;

import static com.api.utils.AuthTokenProvider.*;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPITest 
{
	@Test
	public void userDetailsAPITest() throws IOException
	{
		
		
		
		given()
		  .spec(SpecUtils.requestSpecWithAuth(FD))
		.when()
		   .get("userdetails")
		.then()
		  .spec(SpecUtils.responseSpec_OK())
		  .and()
		  .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/userDetailsSchema.json"));
		   
	}

}
