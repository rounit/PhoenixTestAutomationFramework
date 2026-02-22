package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.MapJobProblemModel;

public class MapJobProblemDao 
{
	
	private static final String PROBLEM_QUERY=
			"""
			select * from map_job_problem where tr_job_head_id = ?; 
			
			""";
	
	public static MapJobProblemModel getProblemDetails(int tr_job_head_id)
	{
		Connection conn;
		PreparedStatement preapareStatement;
		ResultSet rs ;
		MapJobProblemModel mapJobProblemModel=null;
		try {
			 conn = DatabaseManager.getConnection();
			 preapareStatement=conn.prepareStatement(PROBLEM_QUERY);
			 preapareStatement.setInt(1, tr_job_head_id);
			 rs=preapareStatement.executeQuery();
			 
			 while(rs.next())
			 {
				 mapJobProblemModel = new MapJobProblemModel(rs.getInt("id"), 
						 rs.getInt("tr_job_head_id"), 
						 rs.getInt("mst_problem_id"), 
						 rs.getString("remark"));
			 }
			 }
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapJobProblemModel;
		
	}

}
