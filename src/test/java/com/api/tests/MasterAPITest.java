package com.api.tests;

import static com.api.constant.Roles.FD;
import static com.api.utils.SpecUtils.requestSpec;
import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.serverices.MasterService;

@Listeners(com.listeners.APITestListener.class)
public class MasterAPITest extends BaseAPI
{
	
	
    private MasterService masterService;
	
	@BeforeMethod(description="Create the Payload for the login API")
	public void setup()
	{
		 
		masterService = new MasterService();
	}
	
	@Test(description="Verify if Master API response is giving correct response", groups= {"api","smoke","regression"})
	public void masterAPITest() throws IOException
	{
		masterService.master(FD)
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
		  .relaxedHTTPSValidation()
		  .spec(requestSpec())
		  .when()
		  .post("master")
		  .then()
		  .statusCode(401)
		  .log()
		  .all();
	}

}
