package com.api.tests;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.models.UserCredentials;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;


public class LoginAPITest 
{
	@Test
	public void loginAPITest()
	
	{
		UserCredentials userCred = new UserCredentials("iamfd","password");
		
		
		given()
		  .baseUri("http://64.227.160.186:9000/v1")
		  .and()
		  .contentType(ContentType.JSON)
		  .and()
		  .accept(ContentType.JSON)
		  .and()
		  .body(userCred)
		  .log().uri()
		  .log().method()
		  .log().headers()
		  .log().body()
		.when()
		   .post("login")
		.then()
		   .log().all()
		   .statusCode(200)
		   .time(lessThan(1500L))
		   .and()
		   .body("message",equalTo("Success"))
		   .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));
		   
	}

}
