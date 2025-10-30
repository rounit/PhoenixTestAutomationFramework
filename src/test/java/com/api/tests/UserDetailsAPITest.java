package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Roles;

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
		
		Header authHeader = new Header("Authorization",getToken(Roles.FD));
		
		given()
		  .baseUri(getProperty("BASE_URI"))
		  .and()
		  .header(authHeader)
		  .and()
		  .accept(ContentType.JSON)
		  .log().uri()
		  .log().method()
		  .log().body()
		  .log().headers()
		.when()
		   .get("userdetails")
		.then()
		  .log().all()
		  .statusCode(200)
		  .time(Matchers.lessThan(2500L))
		  .and()
		  .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/userDetailsSchema.json"));
		   
	}

}
