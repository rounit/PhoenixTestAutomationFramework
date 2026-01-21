package com.demo.csv;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class ReadCSVFile 
{
	public static void main(String[] args) throws IOException, CsvException {
		
	
	
//	File csvFile = new File("D:\\eclipse_workspace\\SDET_With_Jatin\\PhoenixTestAutomationFramework\\src\\main\\resources\\testData\\LoginCred.csv");
//	FileReader fr = new FileReader(csvFile);
		
	InputStream is =  Thread.currentThread().getContextClassLoader().getResourceAsStream("testData/LoginCred.csv");
	InputStreamReader isr = new InputStreamReader(is);
	CSVReader csvReader = new CSVReader(isr);
	
	
	
	List<String[]> dataList = csvReader.readAll();
	
	for(String[] dataArray : dataList)
	{
		for(String data:dataArray)
		{
			System.out.print(data+" ");
		}
		
		System.out.println(" ");
	}
	
	}

}
