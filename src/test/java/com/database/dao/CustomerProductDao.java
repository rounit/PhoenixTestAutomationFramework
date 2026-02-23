package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDao 
{
	private static final String PRODUCT_QUERY=
			"""
			SELECT * from tr_customer_product where id   = ?;
			
			""";
	
	private CustomerProductDao()
	{}
	
	public static CustomerProductDBModel getProductInfo(int tr_customer_product_id)
	{
		Connection conn;
		PreparedStatement preapareStatement;
		ResultSet rs ;
		CustomerProductDBModel customerProductDBModel=null;
		try {
			 conn = DatabaseManager.getConnection();
			 preapareStatement=conn.prepareStatement(PRODUCT_QUERY);
			 preapareStatement.setInt(1, tr_customer_product_id);
			 rs=preapareStatement.executeQuery();
			 
			 while(rs.next())
			 {
				 customerProductDBModel = new CustomerProductDBModel(rs.getInt("id"),
						 rs.getInt("tr_customer_id"), 
						 rs.getInt("mst_model_id"), 
						 rs.getString("dop"), 
						 rs.getString("popurl"), 
						 rs.getString("imei2"), 
						 rs.getString("imei1"), 
						 rs.getString("serial_number"));
			 }
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerProductDBModel;
	}
	
	

}
