package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerAddressDBModel;

public class CustomerAddressDAO {
	private static final String CUSTOMER_ADDRESS_QUERY = """
          SELECT
            id,
			flat_number,
			apartment_name,
			street_name,
			landmark,
			area,
			pincode,
			country,
			state
         from tr_customer_address 
         where id = ?
						""";
	
	private CustomerAddressDAO()
	{
		
	}

	public static CustomerAddressDBModel getCustomerAddressData(int customerAddressId)
	{
		CustomerAddressDBModel customerAddressDBModel = null;
		Connection conn;
		PreparedStatement ps;
		ResultSet rs ;
		try {
			conn = DatabaseManager.getConnection();
			 ps = conn.prepareStatement(CUSTOMER_ADDRESS_QUERY);
			 ps.setInt(1, customerAddressId);
			 rs = ps.executeQuery();
			 while(rs.next())
			 {
				 customerAddressDBModel = new CustomerAddressDBModel(rs.getInt("id")
						 ,rs.getString("flat_number")
						 ,rs.getString("apartment_name")
						 ,rs.getString("street_name")
						 ,rs.getString("landmark")
						 ,rs.getString("area")
						 ,rs.getString("pincode")
						 ,rs.getString("country")
						 ,rs.getString("state"));
			 }
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return customerAddressDBModel;
	}

}
