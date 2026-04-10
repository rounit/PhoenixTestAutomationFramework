package com.api.tests.datadriven;

import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Roles;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.api.serverices.JobService;
import com.api.utils.DateTimeUtil;

@Listeners(com.listeners.APITestListener.class)
public class CreateJobAPIDataDrivenTest {
	
	private JobService jobService;
	
	@BeforeMethod(description="Creating createjob api request payload")
	public void setup()
	{
		jobService = new JobService();
	}

	@Test(description = "Verify if CreateJob API is able to create Inwrranty job", groups = { "api", "smoke",
			"regression","datadriven","csv" },

			dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "CreateJobAPIDataProvider"

	)
	public void createJobAPITest(CreateJobPayload createJobPayload) throws IOException {

		jobService.createJob(Roles.FD, createJobPayload).
		then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", Matchers.equalTo("Job created successfully. "))
				.body("data.mst_service_location_id", Matchers.equalTo(1))
				.body("data.job_number", Matchers.startsWith("JOB_"));

	}

}
