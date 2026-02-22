package com.database.dao;

import java.sql.SQLException;

import com.database.model.MapJobProblemModel;

public class DemoRunnerDao {

	public static void main(String[] args) throws SQLException 
	{
		MapJobProblemModel maoJobProblemModel =MapJobProblemDao.getProblemDetails(193923);
		System.out.println(maoJobProblemModel);
		

	}

}
