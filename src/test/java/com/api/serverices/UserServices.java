package com.api.serverices;

import static com.api.constant.Roles.FD;
import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.api.constant.Roles;

import io.restassured.response.Response;

public class UserServices 
{
private static final String USERDETAILS_ENDPOINT = "/userdetails";
	
	public Response userDetails(Roles role) throws IOException
	{
		Response response = given()
		  .spec(requestSpecWithAuth(FD))
		.when()
		   .get(USERDETAILS_ENDPOINT);
		
		return response;
	}
}
