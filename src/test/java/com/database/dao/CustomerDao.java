package com.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

public class CustomerDao 
{
	private static final String CUSTOMER_DETAIL_QUERY=
			"""
			SELECT * FROM tr_customer where id =191186
			
			""" ;
	
	public static CustomerDBModel getCustomerInfo() throws SQLException
	{
		Connection conn = DatabaseManager.getConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(CUSTOMER_DETAIL_QUERY);
		CustomerDBModel customerDBModel = null;
		
		while(rs.next())
		{
			System.out.println(rs.getString("first_name"));
			
			customerDBModel = new CustomerDBModel(rs.getString("first_name"), rs.getString("last_name"), rs.getString("mobile_number"), rs.getString("mobile_number_alt"), rs.getString("email_id"), rs.getString("email_id_alt"));
		}
		
		return customerDBModel;
	}

}
