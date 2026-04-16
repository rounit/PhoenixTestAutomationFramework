package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

import io.qameta.allure.Step;

public class CustomerDao 
{
	
	private static final Logger LOGGER = LogManager.getLogger(CustomerDao.class);
	private static final String CUSTOMER_DETAIL_QUERY=
			"""
			SELECT * FROM tr_customer where id = ?
			
			""" ;
	
	private CustomerDao()
	{}
	
	@Step("Retriving the Customer Information from DB for the specific customer id")
	public static CustomerDBModel getCustomerInfo(int customerID) 
	{
		CustomerDBModel customerDBModel = null;
		try 
		{
			LOGGER.info("Getting the Connection from the Database Manager");
		Connection conn = DatabaseManager.getConnection();
		LOGGER.info("Executing ther SQL Query {}", CUSTOMER_DETAIL_QUERY );
		PreparedStatement preparedStatement = conn.prepareStatement(CUSTOMER_DETAIL_QUERY);
		preparedStatement.setInt(1, customerID);
		ResultSet rs = preparedStatement.executeQuery();
		
		
		while(rs.next())
		{
			System.out.println(rs.getString("first_name"));
			
			customerDBModel = new CustomerDBModel(rs.getInt("id"),rs.getString("first_name"), rs.getString("last_name"), rs.getString("mobile_number"), rs.getString("mobile_number_alt"), rs.getString("email_id"), rs.getString("email_id_alt"),rs.getInt("tr_customer_address_id"));
		}
		}
		catch(SQLException e)
		{
			LOGGER.error("Cannot convert the result set to the CustomerDBModel bean" , e);
			System.err.print(e.getMessage());
		}
		
		return customerDBModel;
	}
	

}
