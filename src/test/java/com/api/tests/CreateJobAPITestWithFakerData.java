package com.api.tests;

import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.hamcrest.Matchers;
import org.testng.Assert;
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
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDAO;
import com.database.dao.CustomerDao;
import com.database.dao.JobHeadDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.JobHeadModel;
import com.github.javafaker.Faker;

@Listeners(com.listeners.APITestListener.class)
public class CreateJobAPITestWithFakerData 
{
	private JobService jobService;
	private CreateJobPayload createJobPayload;
	private final static String COUNTRY = "India";
	
	@BeforeMethod(description="Creating createjob api request payload")
	public void setup()
	{
		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();
		jobService = new JobService();
	}
	
	@Test(description="Verify if CreateJob API is able to create Inwrranty job", groups= {"api","smoke","regression"})
	public void createJobAPITest() throws IOException
	{
		
		int customerId = jobService.createJob(Roles.FD, createJobPayload)
				.then()
		.spec(responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.body("message",Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id",Matchers.equalTo(1))
		.body("data.job_number",Matchers.startsWith("JOB_"))
		.extract().body().jsonPath().getInt("data.tr_customer_id");
		Customer expectedCustomerData = createJobPayload.customer();
		CustomerDBModel actualCustomerDataInDB = CustomerDao.getCustomerInfo(customerId);
		Assert.assertEquals(actualCustomerDataInDB.getFirst_name(),expectedCustomerData.first_name());
		Assert.assertEquals(actualCustomerDataInDB.getLast_name(),expectedCustomerData.last_name());
		Assert.assertEquals(actualCustomerDataInDB.getMobile_number(),expectedCustomerData.mobile_number());
		Assert.assertEquals(actualCustomerDataInDB.getEmail_id(),expectedCustomerData.email_id());
		Assert.assertEquals(actualCustomerDataInDB.getEmail_id_alt(),expectedCustomerData.email_id_alt());
		Assert.assertEquals(actualCustomerDataInDB.getMobile_number_alt(),expectedCustomerData.mobile_number_alt());
		
CustomerAddressDBModel customerAddressFromDB = CustomerAddressDAO.getCustomerAddressData(actualCustomerDataInDB.getTr_customer_address_id());
		
		Assert.assertEquals(customerAddressFromDB.getFlat_number(), createJobPayload.customer_address().flat_number());
		Assert.assertEquals(customerAddressFromDB.getApartment_name(), createJobPayload.customer_address().apartment_name());
		Assert.assertEquals(customerAddressFromDB.getArea(), createJobPayload.customer_address().area());
		Assert.assertEquals(customerAddressFromDB.getLandmark(), createJobPayload.customer_address().landmark());
		Assert.assertEquals(customerAddressFromDB.getState(), createJobPayload.customer_address().state());
		Assert.assertEquals(customerAddressFromDB.getStreet_name(), createJobPayload.customer_address().street_name());
		Assert.assertEquals(customerAddressFromDB.getCountry(), createJobPayload.customer_address().country());
		Assert.assertEquals(customerAddressFromDB.getPincode(), createJobPayload.customer_address().pincode());
		
		JobHeadModel jobHeadDataFromDB =JobHeadDao.getDataFromJobHead(customerId);
		Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(), createJobPayload.mst_oem_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(), createJobPayload.mst_service_location_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(), createJobPayload.mst_platform_id());
	}

}
