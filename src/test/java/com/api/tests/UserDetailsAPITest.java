package com.api.tests;

import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Roles;
import com.api.serverices.UserServices;
import static com.api.constant.Roles.FD;

public class UserDetailsAPITest 
{
	
	private UserServices userService;
	
	@BeforeMethod(description="Setting up UserService instance")
	public void setup()
	{
		userService = new UserServices();
	}
	
	@Test(description="Verify if the Userdetails API response is shown correctly", groups= {"api","smoke","regression"})
	public void userDetailsAPITest() throws IOException
	{
		userService.userDetails(FD)
		.then()
		  .spec(responseSpec_OK())
		  .and()
		  .body(matchesJsonSchemaInClasspath("response-schema/userDetailsSchema.json"));
		   
	}

}
