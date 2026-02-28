package com.api.utils;

import java.util.Map;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDemo 
{
	public static void main(String[] args) throws VaultException 
	{
		VaultConfig vaultConfig = new VaultConfig()
				.address("http://100.52.252.8:8200/")
				.token("root")
				.build();
		
		Vault vault = new Vault(vaultConfig);
		LogicalResponse response =vault.logical().read("secret/phoenix/qa/database");
		Map<String,String> datamap = response.getData();
		System.out.println(datamap.get("DB_URL"));
		System.out.println(datamap.get("DB_USER_NAME"));
		System.out.println(datamap.get("DB_PASSWORD"));
		
		String data =System.getenv("VAULT_Token");		
		System.out.println(data);
	}

}
