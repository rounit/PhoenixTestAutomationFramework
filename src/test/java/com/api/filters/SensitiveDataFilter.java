package com.api.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {

	
	private static final Logger logger = LogManager.getLogger(SensitiveDataFilter.class);
	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		System.out.println("--------- HELLO from the Filter!!! ---------");
		redactPayload(requestSpec);
		Response response = ctx.next(requestSpec, responseSpec);
		System.out.println("------------ I got the respone in Filter ------------");
		
		redactResponseBody(response);
		return response;
	}
	
	private void redactResponseBody(Response response) {
		
		String responseBody = response.asPrettyString();
		responseBody = responseBody.replaceAll("\"token\"\s*:\s*\"[^\"]+\"", "\"token\":\"[REDACTED]\"");
		logger.info("RESPONSE BODY : {}",responseBody);
		
	}

	public void redactPayload(FilterableRequestSpecification requestSpec)
	{
		String requestPayload = requestSpec.getBody().toString();
		requestPayload = requestPayload.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\":\"[REDACTED]\"");
		logger.info("REQUEST PAYLOAD : {}",requestPayload);
		
	}

}
