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
import org.testng.annotations.BeforeMethod;
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
import com.api.utils.DateTimeUtil;
import com.github.javafaker.Faker;

public class CreateJobAPITest2 
{
	
	private CreateJobPayload createJobPayload;
	private final static String COUNTRY = "India";
	
	@BeforeMethod(description="Creating createjob api request payload")
	public void setup()
	{
Faker faker = new Faker(new Locale("en-IND"));
		
		String fname = faker.name().firstName();
		String lname = faker.name().lastName();
		String mobileNumber = faker.numerify("70########");
		String alternatemobileNumber = faker.numerify("70########");
		String customerEmailAddress = faker.internet().emailAddress();
		String altcustomerEmailAddress = faker.internet().emailAddress();
		
		Customer customer = new Customer(fname, lname, mobileNumber, alternatemobileNumber, customerEmailAddress, altcustomerEmailAddress);
        System.out.println(customer);
        
        
        String flatNumber = faker.numerify("###");
        String apartmentNumber = faker.address().streetName();
        String streetName = faker.address().streetName();
        String landmark = faker.address().streetName();
        String area = faker.address().streetName();
        String pincode = faker.numerify("######");
        
        String state = faker.address().state();
        
        
        
        CustomerAddress customerAddress = new CustomerAddress(flatNumber, apartmentNumber, streetName, landmark, area, pincode, COUNTRY, state);
        
        System.out.println(customerAddress);
        
        String dop = DateTimeUtil.getTimeWithDaysAgo(10);
        String imeiSerialNumber = faker.numerify("###############");
        String popUrl = faker.internet().url();
        CustomerProduct custProduct = new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber, popUrl, 1, 1);
        System.out.println(custProduct);
        
        
        String fakeRemark = faker.lorem().sentence(5);
        
        Random random = new Random();
        int problemId = random.nextInt(26)+1;
        
        Problems problems = new Problems(problemId, fakeRemark);
        System.out.println(problems);
		
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		
		createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, custProduct, problemList);
		
		System.out.println(createJobPayload);

	}
	
	@Test(description="Verify if CreateJob API is able to create Inwrranty job", groups= {"api","smoke","regression"})
	public void createJobAPITest() throws IOException
	{
		
		given()
		.spec(requestSpecWithAuth(Roles.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.body("message",Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id",Matchers.equalTo(1))
		.body("data.job_number",Matchers.startsWith("JOB_"));
		
	}

}
