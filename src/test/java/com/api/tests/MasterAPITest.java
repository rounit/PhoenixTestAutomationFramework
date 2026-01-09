package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Roles;
import static com.api.utils.SpecUtils.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class MasterAPITest 
{
	@Test(description="Verify if Master API response is giving correct response", groups= {"api","smoke","regression"})
	public void masterAPITest() throws IOException
	{
		given()
		  .spec(requestSpecWithAuth(Roles.FD))
		  .when()
		  .post("master")
		  .then()
		  .log().all()
		  .spec(responseSpec_OK())
		  .body("message",Matchers.equalTo("Success"))
		  .body("data",Matchers.notNullValue())
		  .body("data",Matchers.hasKey("mst_oem"))
		  .body("$",Matchers.hasKey("message"))
		  .body("$",Matchers.hasKey("data"))
		  .body("data.mst_oem.size()",Matchers.greaterThan(0))
		  .body("data.mst_model.size()",Matchers.greaterThan(0))
		  .body("data.mst_oem.id",Matchers.everyItem(Matchers.notNullValue()))
		  .body("data.mst_oem.name",Matchers.everyItem(Matchers.notNullValue()))
		  .body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"));	  
	}
	
	@Test(description="Verify if Master API response is giving correct status code for invalid token", groups= {"api","negative","smoke","regression"})
	public void invalidTokenForMasterAPITest() throws IOException
	{
		given()
		  .spec(requestSpec())
		  .when()
		  .post("master")
		  .then()
		  .spec(responseSpec(401));
	}

}
