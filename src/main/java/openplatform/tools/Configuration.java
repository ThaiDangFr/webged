package openplatform.tools;

import java.util.ResourceBundle;

public class Configuration
{
	// Global
	private static String VERSION;
	
	// DATABASE
	private static String DB_driverclass;
	private static String DB_jdbcurl;
	private static String DB_username;
	private static String DB_password;
	
	
	// ROOT directory
	private static String ROOT;
	
	static 
	{
		ResourceBundle rb = ResourceBundle.getBundle("configuration");
		
		VERSION = rb.getString("version");
		
		DB_driverclass = rb.getString("DB_driverclass");
		DB_jdbcurl = rb.getString("DB_jdbcurl");
		DB_username = rb.getString("DB_username");
		DB_password = rb.getString("DB_password");
		
		
		if(OsName.isLinux()) ROOT = rb.getString("root.linux");
		else if(OsName.isWindows()) ROOT = rb.getString("root.windows");
		else ROOT = rb.getString("root.default");
	}

	public static String getVERSION() {
		return VERSION;
	}

	public static String getROOT() {
		return ROOT;
	}

	public static String getDB_driverclass() {
		return DB_driverclass;
	}

	public static String getDB_jdbcurl() {
		return DB_jdbcurl;
	}

	public static String getDB_password() {
		return DB_password;
	}

	public static String getDB_username() {
		return DB_username;
	}
	
	
}
