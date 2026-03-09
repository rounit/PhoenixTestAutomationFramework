package com.api.tests;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Roles;
import com.api.request.model.Search;
import com.api.serverices.JobService;
import com.api.utils.SpecUtils;

public class SearchAPITest 
{
	private JobService jobService;
	private static final String JOB_NUMBER="JOB_193923";
	private Search searchPayload;
	
	@BeforeMethod(description="Instantiating the JobService and creating the search payload")
	public void setup()
	{
		jobService = new JobService();
		searchPayload = new Search(JOB_NUMBER);
	}
	
	@Test(description="Verify if search api is working properly",groups={"e2e","smoke"})
	public void searchAPITest() throws IOException
	{
		jobService.createJob(Roles.FD, searchPayload)
		.then()
		.spec(SpecUtils.responseSpec_OK())
		.body("message",Matchers.equalTo("Success"));
		
	}
	
	

}
