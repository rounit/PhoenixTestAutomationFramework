package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerProductDBModel;

public class DemoRunnerDao {

	public static void main(String[] args) throws SQLException 
	{
		CustomerProductDBModel customerProductDBModel =CustomerProductDao.getProductInfo(193931);
		System.out.println(customerProductDBModel);
		

	}

}
