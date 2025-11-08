package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Roles;
import com.api.utils.SpecUtils;

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
		  .spec(SpecUtils.requestSpecWithAuth(Roles.FD))
		  .when()
		  .post("master")
		  .then()
		  .log().all()
		  .spec(SpecUtils.responseSpec_OK())
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
		  .spec(SpecUtils.requestSpec())
		  .when()
		  .post("master")
		  .then()
		  .spec(SpecUtils.responseSpec(401));
	}

}
