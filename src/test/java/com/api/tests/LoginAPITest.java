package com.api.tests;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.testng.annotations.Test;

import com.api.models.UserCredentials;
import com.api.utils.SpecUtils;

import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	@Test
	public void loginAPITest() throws IOException

	{

		UserCredentials userCred = new UserCredentials("iamfd", "password");

		given().spec(SpecUtils.requestSpec(userCred)).when().post("login").then().spec(SpecUtils.responseSpec_OK())
				.body("message", equalTo("Success")).and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));

	}

}
