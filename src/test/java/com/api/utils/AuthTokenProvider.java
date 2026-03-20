package com.api.utils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Roles;
import com.api.request.model.UserCredentials;
import com.api.serverices.MasterService;

import io.restassured.http.ContentType;

public class AuthTokenProvider {
	
	
	private static Map<Roles,String> tokenCache = new ConcurrentHashMap<Roles,String>();
	private static final Logger LOGGER = LogManager.getLogger(AuthTokenProvider.class);
	
	private AuthTokenProvider()
	{}

	public static String getToken(Roles role) throws IOException 
	{
		
		LOGGER.info("Checking if the token for {} is present in the cache",role);
		if(tokenCache.containsKey(role))
		{
			LOGGER.info("Token found for {}",role);
			return tokenCache.get(role);
		}
		
		LOGGER.info("Token not found making the login request for the role {}",role);
		
		UserCredentials userCredentials=null;
		if(role==Roles.FD)
		{
			userCredentials = new UserCredentials("iamfd","password");
		}
		
		else if(role==Roles.SUP)
		{
			userCredentials = new UserCredentials("iamsup","password");
		}
		
		else if(role==Roles.ENG)
		{
			userCredentials = new UserCredentials("iameng","password");
		}
		
		else if(role==Roles.QC)
		{
			userCredentials = new UserCredentials("iamqc","password");
		}
		
       String token = given()
         .baseUri(ConfigManager.getProperty("BASE_URI"))
         .contentType(ContentType.JSON)
         .body(userCredentials)
         .when()
         .post("login")
         .then()
         .log()
         .ifValidationFails()
         .statusCode(200)
         .body("message",equalTo("Success"))
         .extract()
         .body()
         .jsonPath()
         .getString("data.token");
      
       LOGGER.info("Token cached for future request");
       tokenCache.put(role, token);
       return token;
         
	}

}
