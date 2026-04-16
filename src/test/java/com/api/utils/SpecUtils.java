package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;

import com.api.constant.Roles;
import com.api.filters.SensitiveDataFilter;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtils {

	@Step("Setting up the BaseURI , Content Type as Application/JSOn and attaching the SensitiveData Filter")
	// GET-DEL
	public static RequestSpecification requestSpec() throws IOException {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				// .setAccept(ContentType.JSON)
				.addFilter(new SensitiveDataFilter())
				.addFilter(new AllureRestAssured())
				

				.build();
		return requestSpecification;

	}

	@Step("Setting up the BaseURI , Content Type as Application/JSOn and attaching the SensitiveData Filter")
	public static RequestSpecification requestSpec(Object userCreds) throws IOException {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				// .setAccept(ContentType.JSON)
				.setBody(userCreds)
				.addFilter(new SensitiveDataFilter())
				.addFilter(new AllureRestAssured())

				.build();
		return requestSpecification;

	}

	@Step("Setting up the BaseURI , Content Type as Application/JSOn and attaching the SensitiveData Filter for a role")
	public static RequestSpecification requestSpecWithAuth(Roles role) throws IOException {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				// .setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role))
				.addFilter(new SensitiveDataFilter())
				.addFilter(new AllureRestAssured())

				.build();
		return requestSpecification;
	}

	@Step("Setting up the BaseURI , Content Type as Application/JSOn and attaching the SensitiveData Filter for a role and attaching payload")
	public static RequestSpecification requestSpecWithAuth(Roles role, Object payload) throws IOException {
		RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON)
				// .setAccept(ContentType.JSON)
				.addHeader("Authorization", AuthTokenProvider.getToken(role)).setBody(payload)
				.addFilter(new SensitiveDataFilter())
				.addFilter(new AllureRestAssured())

				.build();
		return requestSpecification;
	}

	@Step("Expecting the response to have Content type as Application/JSON,Status 200 and response time less than 1000ms ")
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
				// .expectContentType(ContentType.JSON)
				.expectStatusCode(200).expectResponseTime(Matchers.lessThan(2000L))

				.build();

		return responseSpecification;
	}

	@Step("Expecting the response to have Content type as Application/JSON,Status 200 and response time less than 1000ms and status code")
	public static ResponseSpecification responseSpec(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()
				// .expectContentType(ContentType.JSON)
				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(1000L))

				.build();

		return responseSpecification;
	}

	@Step("Expecting the response to have Content type as text and Response time less than 1000ms and status code")
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder()

				.expectStatusCode(statusCode).expectResponseTime(Matchers.lessThan(2000L))

				.build();

		return responseSpecification;

	}

}
