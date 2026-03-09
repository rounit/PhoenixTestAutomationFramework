package com.api.tests;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Roles;
import com.api.request.model.Detail;
import com.api.serverices.DashboardService;
import com.api.utils.SpecUtils;

public class DetailsAPITest 
{
	private DashboardService dashboardService;
	private Detail detailPayload;
	
	
	@BeforeMethod(description="Instantiating the Dashboard service and creating detail payload")
	public void setup()
	{
		dashboardService = new DashboardService();
		detailPayload = new Detail("created today");
	}
	
	@Test(description="Verify if Details API is working properly",groups = {"api","smoke","e2e"})
	public void detailAPITest() throws IOException
	{
		dashboardService.details(Roles.FD, detailPayload)
		.then()
		.spec(SpecUtils.responseSpec_OK())
		.body("message", Matchers.equalTo("Success"));
	}

}
