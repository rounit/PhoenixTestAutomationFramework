package com.api.serverices;

import static com.api.utils.SpecUtils.requestSpec;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.api.request.model.UserCredentials;

import io.restassured.response.Response;

public class AuthService 
{
	private static final String LOGIN_ENDPOINT = "login";
	
	public Response login(Object userCredentials) throws IOException
	{
		Response response = given()
		.spec(requestSpec(userCredentials))
		.when()
		.post(LOGIN_ENDPOINT);
		
		return response;
	}

}
