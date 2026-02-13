package com.database;

import java.io.IOException;
import java.sql.SQLException;

public class DemoRunner 
{
	public static void main(String[] args) throws SQLException, IOException 
	{
		DatabaseManager.createConnection();
		
	}

}
