package com.api.serverices;

import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static io.restassured.RestAssured.given;

import java.io.IOException;

import com.api.constant.Roles;
import com.api.request.model.CreateJobPayload;
import com.api.utils.SpecUtils;

import io.restassured.response.Response;

public class JobService 
{
	
	public static final String CREATE_JOB_ENDPOINT = "/job/create";
	
	public static final String SEARCH_JOB_ENDPOINT = "/job/search";
	
	public Response createJob(Roles role,CreateJobPayload createJobPayload) throws IOException
	{
		return given()
		.spec(requestSpecWithAuth(Roles.FD, createJobPayload))
		.when()
		.post(CREATE_JOB_ENDPOINT);
	}
	
	public Response createJob(Roles role,Object payload) throws IOException
	{
		return given()
		.spec(SpecUtils.requestSpecWithAuth(role))
		.body(payload)
		.when()
		.post(SEARCH_JOB_ENDPOINT);
	}

}
