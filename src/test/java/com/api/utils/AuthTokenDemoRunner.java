package com.api.utils;

import java.io.IOException;
import java.time.Duration;

import com.api.constant.Roles;

public class AuthTokenDemoRunner {

	public static void main(String[] args) throws IOException, InterruptedException 
	{
		for(int i=0;i<=100;i++)
		{
		String token = AuthTokenProvider.getToken(Roles.FD);
		
		System.out.println(token);
		}
				

	}

}
