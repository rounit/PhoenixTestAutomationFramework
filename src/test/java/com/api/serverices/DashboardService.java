package com.api.serverices;

import static com.api.constant.Roles.FD;
import static com.api.utils.SpecUtils.*;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.api.constant.Roles;

import io.restassured.response.Response;

public class DashboardService 
{
	
	public static final String COUNT_ENDPOINT = "/dashboard/count";
	
	public Response count(Roles role) throws IOException
	{
		return given()
		 .spec(requestSpecWithAuth(FD))
		 .when()
		 .get(COUNT_ENDPOINT);
	}
	
	public Response countWithNoAuth() throws IOException
	{
		return given()
				.spec(requestSpec())
		 .when()
		 .get(COUNT_ENDPOINT);
	}

}
