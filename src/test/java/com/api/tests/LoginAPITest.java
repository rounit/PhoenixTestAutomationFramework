package com.api.tests;

import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.serverices.AuthService;

import io.restassured.response.Response;

public class LoginAPITest {
	
	private UserCredentials userCred ;
	
	private AuthService authService;
	
	@BeforeMethod(description="Create the Payload for the login API")
	public void setup()
	{
		 userCred = new UserCredentials("iamfd", "password");
		 authService = new AuthService();
	}
	
	@Test(description="Verifying if login api os working for FD user", groups= {"api","regression","smoke"})
	public void loginAPITest() throws IOException

	{
		authService.login(userCred)
		.then().spec(responseSpec_OK())
		.body("message", equalTo("Success"))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));

	}

}
