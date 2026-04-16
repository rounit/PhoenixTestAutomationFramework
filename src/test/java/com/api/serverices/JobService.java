package com.api.serverices;

import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constant.Roles;
import com.api.request.model.CreateJobPayload;
import com.api.utils.SpecUtils;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class JobService 
{
	
	public static final String CREATE_JOB_ENDPOINT = "/job/create";
	
	public static final String SEARCH_JOB_ENDPOINT = "/job/search";
	
	private static final Logger LOGGER = LogManager.getLogger(JobService.class);
	
	@Step("Creating inwarranty Job with Create Job API")
	public Response createJob(Roles role,CreateJobPayload createJobPayload) throws IOException
	{
		LOGGER.info("Making request to  {} with the role {} and payload {}",CREATE_JOB_ENDPOINT,role,createJobPayload);
		return given()
		.spec(requestSpecWithAuth(Roles.FD, createJobPayload))
		.when()
		.post(CREATE_JOB_ENDPOINT);
	}
	
	@Step("Making Search API Request")
	public Response createJob(Roles role,Object payload) throws IOException
	{
		LOGGER.info("Making request to  {} with the role {} and payload {}",SEARCH_JOB_ENDPOINT,role,payload);
		return given()
		.spec(SpecUtils.requestSpecWithAuth(role))
		.body(payload)
		.when()
		.post(SEARCH_JOB_ENDPOINT);
	}

}
