package com.api.serverices;

import static com.api.constant.Roles.FD;
import static com.api.utils.SpecUtils.requestSpec;
import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Roles;

import io.restassured.response.Response;

public class MasterService 
{
public static final String MASTER_ENDPOINT = "master";

private static final Logger LOGGER = LogManager.getLogger(MasterService.class);
	
	public Response master(Roles role) throws IOException
	{
		LOGGER.info("Making request to {} for the role {} ",MASTER_ENDPOINT,role);
		return given()
		 .spec(requestSpecWithAuth(FD))
		 .when()
		 .get(MASTER_ENDPOINT);
	}
	
	

}


