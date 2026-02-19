package com.database.dao;

import java.sql.SQLException;

import org.testng.Assert;

import com.api.request.model.Customer;
import com.database.model.CustomerDBModel;

public class DemoRunnerDao {

	public static void main(String[] args) throws SQLException 
	{
		CustomerDBModel customerDBData = CustomerDao.getCustomerInfo();
		System.out.println(customerDBData);
		System.out.println(customerDBData.getFirst_name());
		System.out.println(customerDBData.getEmail_id_alt());
		System.out.println(customerDBData.getMobile_number());
		Customer cust = new Customer("Johann", "Sharma", "7000299384","", "ronny@gmail.com", "");
		System.out.println(cust.first_name());
		Assert.assertEquals(customerDBData.getFirst_name(), cust.first_name());
		

	}

}
