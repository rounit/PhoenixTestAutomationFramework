package com.api.utils;

import java.util.Locale;

import com.github.javafaker.Faker;

public class FakerDemo 
{
	public static void main(String[] args) 
	{
		Faker faker = new Faker(new Locale("en-IND"));
		String fName = faker.name().firstName();
		String lastName = faker.name().lastName();
		System.out.println(fName);
		System.out.println(lastName);
		
		
		String buildingNumber = faker.address().buildingNumber();
		System.out.println(buildingNumber);
		
		
		System.out.println(faker.number().digits(10));
		
		System.out.println(faker.numerify("91+70########"));
		System.out.println(faker.numerify("91+70########"));
		System.out.println(faker.numerify("91+70########"));
		
		
		System.out.println(faker.internet().emailAddress());
		
		System.out.println(faker.phoneNumber().cellPhone());
		
		
		
	}

	

}
