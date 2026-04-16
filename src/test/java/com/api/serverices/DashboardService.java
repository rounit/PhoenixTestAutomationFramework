package com.api.serverices;

import static com.api.constant.Roles.FD;
import static com.api.utils.SpecUtils.*;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Roles;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class DashboardService 
{
	
	public static final String COUNT_ENDPOINT = "/dashboard/count";
	
	public static final String DETAIL_COUNT = "/dashboard/details";
	
	private static final Logger LOGGER = LogManager.getLogger(DashboardService.class);
	
	
	@Step("Making Count API Request  for the role")
	public Response count(Roles role) throws IOException
	{
		LOGGER.info("Making request to the {} for the role {}",COUNT_ENDPOINT,role);
		return given()
		 .spec(requestSpecWithAuth(role))
		 .when()
		 .get(COUNT_ENDPOINT);
	}
	
	@Step("Making Count API Request without Auth token")
	public Response countWithNoAuth() throws IOException
	{
		LOGGER.info("Making request to the {} with no Auth Token",COUNT_ENDPOINT);
		return given()
				.spec(requestSpec())
		 .when()
		 .get(COUNT_ENDPOINT);
	}
	
	@Step("Making Details API Request")
	public Response details(Roles role,Object payload) throws IOException
	{
		LOGGER.info("Making request to the {} with role {} and the payload {}",DETAIL_COUNT,role,payload);
		return given()
		 .spec(requestSpecWithAuth(role))
		 .body(payload)
		 .when()
		 .post(DETAIL_COUNT);
	}

}
