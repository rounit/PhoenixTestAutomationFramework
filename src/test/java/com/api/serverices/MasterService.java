package com.api.serverices;

import static com.api.constant.Roles.FD;
import static com.api.utils.SpecUtils.requestSpec;
import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.api.constant.Roles;

import io.restassured.response.Response;

public class MasterService 
{
public static final String MASTER_ENDPOINT = "master";
	
	public Response master(Roles role) throws IOException
	{
		return given()
		 .spec(requestSpecWithAuth(FD))
		 .when()
		 .get(MASTER_ENDPOINT);
	}
	
	

}


