package com.api.tests;

import static com.api.utils.SpecUtils.requestSpecWithAuth;
import static com.api.utils.SpecUtils.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.Assert;
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
import com.database.dao.CustomerAddressDAO;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.JobHeadDao;
import com.database.dao.MapJobProblemDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadModel;
import com.database.model.MapJobProblemModel;

import io.restassured.response.Response;

public class CreateJobAPITestWithDBValidationTest 
{
	
	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct;
	
	@BeforeMethod(description="Creating createjob api request payload")
	public void setup()
	{
		 customer = new Customer("Rounit", "Sharma", "7000298282", "7000299383", "rounitsharma@gmail.com", "rounitsharma@gmail.com");
		 customerAddress = new CustomerAddress("GI1-503", "Green Iconia-1", "Road No-20", "Behind Tulja Ram Temple", "Alkapur Township", "500089"
				, "India", "Telanaga");
		 customerProduct = new CustomerProduct(DateTimeUtil.getTimeWithDaysAgo(10), "70269747447088", "70269747447088", "70269747447088", 
				DateTimeUtil.getTimeWithDaysAgo(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);
		 createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(), Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemList);
	}
	
	@Test(description="Verify if CreateJob API is able to create Inwrranty job", groups= {"api","smoke","regression"})
	public void createJobAPITest() throws IOException
	{
		
		Response response =given()
		.spec(requestSpecWithAuth(Roles.FD, createJobPayload))
		.when()
		.post("/job/create")
		.then()
		.spec(responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
		.body("message",Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id",Matchers.equalTo(1))
		.body("data.job_number",Matchers.startsWith("JOB_"))
		.extract().response();
		System.out.println("----------------------");
		System.out.println();
		
		int customerId = response.then().extract().body().jsonPath().getInt("data.tr_customer_id");
		
		CustomerDBModel customerDataFromDB = CustomerDao.getCustomerInfo(customerId);
		System.out.println(customerDataFromDB);
		
		Assert.assertEquals(customer.first_name(),customerDataFromDB.getFirst_name());
		Assert.assertEquals(customer.last_name(),customerDataFromDB.getLast_name());
		Assert.assertEquals(customer.mobile_number(),customerDataFromDB.getMobile_number());
		Assert.assertEquals(customer.email_id(),customerDataFromDB.getEmail_id());
		Assert.assertEquals(customer.email_id_alt(),customerDataFromDB.getEmail_id_alt());
		Assert.assertEquals(customer.mobile_number_alt(),customerDataFromDB.getMobile_number_alt());
		
		System.out.println();
		CustomerAddressDBModel customerAddressFromDB = CustomerAddressDAO.getCustomerAddressData(customerDataFromDB.getTr_customer_address_id());
		
		Assert.assertEquals(customerAddressFromDB.getFlat_number(), customerAddress.flat_number());
		Assert.assertEquals(customerAddressFromDB.getApartment_name(), customerAddress.apartment_name());
		Assert.assertEquals(customerAddressFromDB.getArea(), customerAddress.area());
		Assert.assertEquals(customerAddressFromDB.getLandmark(), customerAddress.landmark());
		Assert.assertEquals(customerAddressFromDB.getState(), customerAddress.state());
		Assert.assertEquals(customerAddressFromDB.getStreet_name(), customerAddress.street_name());
		Assert.assertEquals(customerAddressFromDB.getCountry(), customerAddress.country());
		Assert.assertEquals(customerAddressFromDB.getPincode(), customerAddress.pincode());
		
		int productId = response.then().extract().body().jsonPath().getInt("data.tr_customer_product_id");
		
		JobHeadModel jobHeadDataFromDB =JobHeadDao.getDataFromJobHead(customerId);
		Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(), createJobPayload.mst_oem_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(), createJobPayload.mst_service_location_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(), createJobPayload.mst_platform_id());
		
		
		int tr_job_head_id = response.then().extract().body().jsonPath().getInt("data.id");
		MapJobProblemModel jobDataFromDB = MapJobProblemDao.getProblemDetails(tr_job_head_id);
		Assert.assertEquals(jobDataFromDB.getMst_problem_id(), createJobPayload.problems().get(0).id());
		Assert.assertEquals(jobDataFromDB.getRemark(), createJobPayload.problems().get(0).remark());
		
        CustomerProductDBModel customerProductDbData = CustomerProductDao.getProductInfo(productId);
		Assert.assertEquals(customerProductDbData.getImei1(), customerProduct.imei1());
		Assert.assertEquals(customerProductDbData.getImei2(), customerProduct.imei2());
		Assert.assertEquals(customerProductDbData.getSerial_number(), customerProduct.serial_number());
		Assert.assertEquals(customerProductDbData.getDop(), customerProduct.dop());
		Assert.assertEquals(customerProductDbData.getPopurl(), customerProduct.popurl());
		Assert.assertEquals(customerProductDbData.getMst_model_id(), customerProduct.mst_model_id());
	}

}
