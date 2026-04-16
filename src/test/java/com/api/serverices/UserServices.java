package com.api.serverices;

import static com.api.constant.Roles.FD;
import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Roles;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserServices 
{
private static final String USERDETAILS_ENDPOINT = "/userdetails";

private static final Logger LOGGER = LogManager.getLogger(UserServices.class);
	


    @Step("Making UserDetails API Request")
	public Response userDetails(Roles role) throws IOException
	{
		LOGGER.info("Making request to {} for the role {} ",USERDETAILS_ENDPOINT,role);
		Response response = given()
		  .spec(requestSpecWithAuth(FD))
		.when()
		   .get(USERDETAILS_ENDPOINT);
		
		return response;
	}
}
