package com.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDemo2 
{
	private final static String COUNTRY = "India";
	public static void main(String[] args) 
	{
		Faker faker = new Faker(new Locale("en-IND"));
		
		String fname = faker.name().firstName();
		String lname = faker.name().lastName();
		String mobileNumber = faker.numerify("70########");
		String alternatemobileNumber = faker.numerify("70########");
		String customerEmailAddress = faker.internet().emailAddress();
		String altcustomerEmailAddress = faker.internet().emailAddress();
		
		Customer customer = new Customer(fname, lname, mobileNumber, customerEmailAddress, alternatemobileNumber, altcustomerEmailAddress);
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
		
		CreateJobPayload payload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, custProduct, problemList);
		
		System.out.println(payload);
		
	}

	

}
