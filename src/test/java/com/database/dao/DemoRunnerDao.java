package com.database.dao;

import java.sql.SQLException;

import com.database.model.JobHeadModel;

public class DemoRunnerDao {

	public static void main(String[] args) throws SQLException 
	{
		JobHeadModel jobHeadModel =JobHeadDao.getDataFromJobHead(193941);
		System.out.println(jobHeadModel);
		

	}

}
