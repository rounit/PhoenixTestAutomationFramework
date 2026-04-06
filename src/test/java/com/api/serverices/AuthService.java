package com.api.serverices;

import static com.api.utils.SpecUtils.requestSpec;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dataproviders.api.bean.UserBean;

import io.restassured.response.Response;

public class AuthService 
{
	private static final String LOGIN_ENDPOINT = "login";
	private static final Logger LOGGER = LogManager.getLogger(AuthService.class);
	
	public Response login(Object userCredentials) throws IOException
	{
		LOGGER.info("Making loging request for the payload {} ",((UserBean)userCredentials).getUsername());
		Response response = given()
		.spec(requestSpec(userCredentials))
		.when()
		.post(LOGIN_ENDPOINT);
		
		return response;
	}

}
