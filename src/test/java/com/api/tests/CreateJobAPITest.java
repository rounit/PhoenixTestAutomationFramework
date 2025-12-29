package com.api.tests;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Roles;
import com.api.models.CreateJobPayload;
import com.api.models.Customer;
import com.api.models.CustomerAddress;
import com.api.models.CustomerProduct;
import com.api.models.Problems;
import com.api.utils.SpecUtils;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest 
{
	
	
	@Test
	public void createJobAPITest() throws IOException
	{
		
		Customer customer = new Customer("Rounit", "Sharma", "7000298282", "7000299383", "rounitsharma@gmail.com", "rounitsharma@gmail.com");
		CustomerAddress customerAddress = new CustomerAddress("GI1-503", "Green Iconia-1", "Road No-20", "Behind Tulja Ram Temple", "Alkapur Township", "500089"
				, "India", "Telanaga");
		CustomerProduct customerProduct = new CustomerProduct("2025-04-06T18:30:00.000Z", "36269747447006", "36269747447006", "36269747447006", 
				"2025-04-06T18:30:00.000Z", 1, 1);
		Problems problems = new Problems(1, "Battery Issue");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		
		
		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
		
		given()
		.spec(SpecUtils.requestSpecWithAuth(Roles.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(SpecUtils.responseSpec_OK())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.body("message",Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id",Matchers.equalTo(1))
		.body("data.job_number",Matchers.startsWith("JOB_"));
	}

}
