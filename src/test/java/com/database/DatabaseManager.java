package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.qameta.allure.Step;

public class DatabaseManager

{
	private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class);
	private static Connection conn;
	private static HikariConfig hikariConfig;
	private volatile static HikariDataSource hikariDataSource;
	private static final int MAXIMUM_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE"));
	private static final int MINIMUM_IDLE = Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE")) * 1000;
	private static final int CONNECTION_TIMEOUT_IN_SEC = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SEC")) * 1000;
	private static final int IDLE_TIMEOUT_IN_SEC = Integer.parseInt(ConfigManager.getProperty("IDLE_TIMEOUT_IN_SEC"));
	private static final int MAX_LIFETIMEIN_SEC = Integer.parseInt(ConfigManager.getProperty("MAX_LIFETIMEIN_SEC"));
	private static final String POOL_NAME = ConfigManager.getProperty("POOL_NAME");
	
	
	private static boolean isVaultUp = true;
	private static final String DB_URL = loadSecrets("DB_URL");
	private static final String DB_USER_NAME = loadSecrets("DB_USER_NAME");
	private static final String DB_PASSWORD = loadSecrets("DB_PASSWORD");
	
	
	@Step("Loading Database Secrets")
	public static String loadSecrets(String key)
	{
		String value = null;
		
		if(isVaultUp)
		{
		value = VaultDBConfig.getSecret(key);
	
		if(value==null)
		{
			
			LOGGER.error("Vault is Down!! or some issue with Vault");
			isVaultUp = false;
		}
		else
		{
			
			LOGGER.info("Reading Value for key {} from Vault .....", key);
			return value;
		}
		
		}
		
		
		LOGGER.info("Reading Value from ENV .....");
		value = EnvUtil.getValue(key);
		return value;
		
	}
		
	
	
	
	
	

	private DatabaseManager() {

	}

	
	@Step("Intializing the Database Connection Pool")
	private static void intializePool() {
		if (hikariDataSource == null) {
			LOGGER.warn("Database Connection is not available...Creating HikariDataSource");
			synchronized (DatabaseManager.class) {
				if (hikariDataSource == null) {
					HikariConfig hikariConfig = new HikariConfig();
					hikariConfig.setJdbcUrl(DB_URL);
					hikariConfig.setUsername(DB_USER_NAME);
					hikariConfig.setPassword(DB_PASSWORD);
					hikariConfig.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
					hikariConfig.setMinimumIdle(MINIMUM_IDLE);
					hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SEC);
					hikariConfig.setIdleTimeout(IDLE_TIMEOUT_IN_SEC * 1000);
					hikariConfig.setMaxLifetime(MAX_LIFETIMEIN_SEC * 60 * 1000);
					hikariConfig.setPoolName(POOL_NAME);

					hikariDataSource = new HikariDataSource(hikariConfig);
					LOGGER.info("Hikari DataSource Created");
				}

			}

		}
	}

	
	@Step("Getting the Database Connection")
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		if (hikariDataSource == null) {
			LOGGER.info("Intializing the Database Connection using HikariCP");
			intializePool();
		} else if (hikariDataSource.isClosed()) {
			LOGGER.error("HIKARI DATA SOURCE IS CLOSED");
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
			
		}

		conn = hikariDataSource.getConnection();

		return conn;

	}
}
