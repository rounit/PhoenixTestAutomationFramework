package com.api.filters;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {

	
	private static final Logger logger = LogManager.getLogger(SensitiveDataFilter.class);
	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		
		logger.info("************** REQUEST DETAILS **************");
		logger.info("BASE URI: {}",requestSpec.getURI());
		logger.info("HTTP METHOD: {}",requestSpec.getMethod());
		redactHeader(requestSpec);
		
		redactPayload(requestSpec);
		Response response = ctx.next(requestSpec, responseSpec);
		logger.info("************** RESPONSE DETAILS **************");
		logger.info("STATUS : {}",response.getStatusLine());
		logger.info("RESPONSE TIME ms: {}",response.timeIn(TimeUnit.MILLISECONDS));
		logger.info("RESPONSE HEADERS: \n {}",response.getHeaders());
		
		
		redactResponseBody(response);
		return response;
	}
	
	private void redactHeader(FilterableRequestSpecification requestSpec) {
		
		List<Header> headerList = requestSpec.getHeaders().asList();
		logger.info("REQUEST HEADERS: \n {}",requestSpec.getHeaders());
		for(Header h : headerList)
		{
			if(h.getName().equalsIgnoreCase("Authorization"))
			{
			   logger.info("HEADER {} : {}",h.getName() , "\"[REDACTED]\"");
			}
			else
			{
				logger.info("HEADER {} : {}", h.getName(),h.getValue());
			}
		}
		
	}

	private void redactResponseBody(Response response) {
		
		String responseBody = response.asPrettyString();
		responseBody = responseBody.replaceAll("\"token\"\s*:\s*\"[^\"]+\"", "\"token\":\"[REDACTED]\"");
		logger.info("RESPONSE BODY : \n {}",responseBody);
		
	}

	public void redactPayload(FilterableRequestSpecification requestSpec)
	{
		if(requestSpec.getBody()!=null) {
			
		String requestPayload = requestSpec.getBody().toString();
		requestPayload = requestPayload.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\":\"[REDACTED]\"");
		logger.info("REQUEST PAYLOAD : \n {}",requestPayload);
		}
		
	}

}
