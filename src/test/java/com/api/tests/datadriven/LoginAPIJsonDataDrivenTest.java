package com.api.tests.datadriven;

import static com.api.utils.SpecUtils.requestSpec;
import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;
import com.api.serverices.AuthService;
import com.dataproviders.api.bean.UserBean;

public class LoginAPIJsonDataDrivenTest {
	
	private AuthService authService;
	
	@BeforeMethod(description="Setting up the AuthService reference")
	public void setup()
	{
		 authService = new AuthService();
	}
	
	@Test(description="Verifying if login api os working for FD user", groups= {"api","regression","datadriven"}
	,dataProviderClass = com.dataproviders.DataProviderUtils.class
	,dataProvider = "LoginAPIJsonDataProvider")
	
	
	public void loginAPITest(UserCredentials userCredentials) throws IOException 

	{
		authService.login(userCredentials)
		.then().spec(responseSpec_OK())
		.body("message", equalTo("Success"))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));

	}

}
