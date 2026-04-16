package com.api.utils;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

import io.qameta.allure.Step;

public class VaultDBConfig 
{
	private static VaultConfig vaultConfig;
	private static Vault vault;
	
	private static final Logger LOGGER = LogManager.getLogger(VaultDBConfig.class);
	
	static
	{
		try {
			vaultConfig = new VaultConfig()
					.address(System.getenv("VAULT_Server"))
					.token(System.getenv("VAULT_Token"))
					.build();
		} catch (VaultException e) {
			LOGGER.error("Something went wrong with the Vault",e);
			e.printStackTrace();
			
		}
		
		vault = new Vault(vaultConfig);
	}
	
	private VaultDBConfig()
	{
		
	}
	
	@Step("Retriving the secret from the Vault")
	public static String getSecret(String Key)
	{
		LogicalResponse response = null;
		 try {
			response =vault.logical().read("secret/phoenix/qa/database");
		} catch (VaultException e) {
			LOGGER.error("Something went wrong reading of vault response ",e);
			e.printStackTrace();
			return null;
		}
		 
		 Map<String,String> dataMap = response.getData();
		 
		 String secretValue = dataMap.get(Key);
		 LOGGER.info("Secret found in the Vault");
		 return secretValue;
	}

}
