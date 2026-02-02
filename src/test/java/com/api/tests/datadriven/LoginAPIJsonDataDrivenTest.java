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
import com.dataproviders.api.bean.UserBean;

public class LoginAPIJsonDataDrivenTest {
	
	private UserCredentials userCred ;
	
	@BeforeMethod(description="Create the Payload for the login API")
	public void setup()
	{
		 userCred = new UserCredentials("iamfd", "password");
	}
	
	@Test(description="Verifying if login api os working for FD user", groups= {"api","regression","datadriven"}
	,dataProviderClass = com.dataproviders.DataProviderUtils.class
	,dataProvider = "LoginAPIJsonDataProvider")
	
	
	public void loginAPITest(UserCredentials userCredentials) throws IOException 

	{
		given()
		.spec(requestSpec(userCredentials))
		.when()
		.post("login")
		.then().spec(responseSpec_OK())
		.body("message", equalTo("Success"))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));

	}

}
