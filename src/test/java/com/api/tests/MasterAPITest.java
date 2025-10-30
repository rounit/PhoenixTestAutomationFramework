package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Roles;

import io.restassured.module.jsv.JsonSchemaValidator;

import static com.api.utils.AuthTokenProvider.*;

import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

import java.io.IOException;

public class MasterAPITest 
{
	@Test
	public void masterAPITest() throws IOException
	{
		given()
		  .baseUri(getProperty("BASE_URI"))
		  .and()
		  .header("Authorization",getToken(Roles.FD))
		  .and()
		  .contentType("")
		  .log().all()
		  .when()
		  .post("master")
		  .then()
		  .log().all()
		  .statusCode(200)
		  .time(Matchers.lessThan(1000L))
		  .body("message",Matchers.equalTo("Success"))
		  .body("data",Matchers.notNullValue())
		  .body("data",Matchers.hasKey("mst_oem"))
		  .body("$",Matchers.hasKey("message"))
		  .body("$",Matchers.hasKey("data"))
		  .body("data.mst_oem.size()",Matchers.greaterThan(0))
		  .body("data.mst_model.size()",Matchers.greaterThan(0))
		  .body("data.mst_oem.id",Matchers.everyItem(Matchers.notNullValue()))
		  .body("data.mst_oem.name",Matchers.everyItem(Matchers.notNullValue()))
		  .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));	  
	}
	
	@Test
	public void invalidTokenForMasterAPITest() throws IOException
	{
		given()
		  .baseUri(getProperty("BASE_URI"))
		  .and()
		  .header("Authorization","")
		  .and()
		  .contentType("")
		  .log().all()
		  .when()
		  .post("master")
		  .then()
		  .log().all()
		  .statusCode(401);
	}

}
