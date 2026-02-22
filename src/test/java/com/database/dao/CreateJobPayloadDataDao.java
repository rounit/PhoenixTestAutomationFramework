package com.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.database.DatabaseManager;
import com.dataproviders.api.bean.CreateJobBean;

public class CreateJobPayloadDataDao {
	private static final String SQL_QUERY = """
						select
			mst_service_location_id,
			mst_platform_id,
			mst_warrenty_status_id,
			mst_oem_id,
			first_name,
			last_name,
			mobile_number,
			mobile_number_alt,
			email_id,
			email_id_alt,
			flat_number,
			apartment_name,
			street_name,
			landmark,
			area,
			pincode,
			country,
			state,
			serial_number,
			imei1,
			imei2,
			popurl,
			dop,
			mst_model_id,
			mst_problem_id,
			remark

			FROM tr_customer
			inner join tr_customer_address
			on tr_customer.tr_customer_address_id = tr_customer_address_id

			INNER JOIN tr_customer_product
			on tr_customer_product.tr_customer_id=tr_customer.id

			INNER JOIN tr_job_head
			on tr_job_head.tr_customer_id=tr_customer.id

			INNER JOIN map_job_problem
			on tr_job_head.id=map_job_problem.tr_job_head_id

			LIMIT 5;
						""";
	
	private CreateJobPayloadDataDao()
	{}
	
	
	public static List<CreateJobBean> getCreateJobPayloadData()
	{
		Connection conn = null;
		Statement stmt ;
		ResultSet rs;
		List<CreateJobBean> beanList = new ArrayList<CreateJobBean>();
		try {
			conn = DatabaseManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL_QUERY);
			
			while(rs.next())
			{
				CreateJobBean bean = new CreateJobBean();
				bean.setMst_service_location_id(rs.getString("mst_service_location_id"));
				bean.setMst_platform_id(rs.getString("mst_platform_id"));
				bean.setMst_warrenty_status_id(rs.getString("mst_warrenty_status_id"));
				bean.setMst_oem_id("1");
				bean.setCustomer__first_name(rs.getString("first_name"));
				bean.setCustomer__last_name(rs.getString("last_name"));
				bean.setCustomer__mobile_number(rs.getString("mobile_number"));
				bean.setCustomer__mobile_number_alt(rs.getString("mobile_number_alt"));
				bean.setCustomer__email_id(rs.getString("email_id"));
				bean.setCustomer__email_id_alt(rs.getString("email_id_alt"));
				bean.setCustomer_address__flat_number(rs.getString("flat_number"));
				bean.setCustomer_address__apartment_name(rs.getString("apartment_name"));
				bean.setCustomer_address__street_name(rs.getString("street_name"));
				bean.setCustomer_address__landmark(rs.getString("landmark"));
				bean.setCustomer_address__area(rs.getString("area"));
				bean.setCustomer_address__pincode(rs.getString("pincode"));
				bean.setCustomer_address__country(rs.getString("country"));
				bean.setCustomer_address__state(rs.getString("state"));
				bean.setCustomer_product__serial_number(rs.getString("serial_number"));
				bean.setCustomer_product__imei1(rs.getString("imei1"));
				bean.setCustomer_product__imei2(rs.getString("imei2"));
				bean.setCustomer_product__popurl(rs.getString("popurl"));
				bean.setCustomer_product__dop(rs.getString("dop"));
				bean.setCustomer_product__mst_model_id("1");
				bean.setProblems__id(rs.getString("mst_problem_id"));
				bean.setProblems__remark(rs.getString("remark"));
				bean.setCustomer_product__product_id("1");
				beanList.add(bean);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(CreateJobBean b : beanList)
		{
			System.out.println(b);
		}
		
		return beanList;
		
	}

}
