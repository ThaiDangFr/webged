package openplatform.ldap;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

import openplatform.tools.Debug;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPJSSESecureSocketFactory;

/**
 * 
 * JDBC DRIVER : http://developer.novell.com/documentation/samplecode/ldapjdbc_sample/index.htm
 * JLDAP : http://developer.novell.com/documentation/samplecode/jldap_sample/index.htm
 * 
 * @author Thai DANG
 *
 */
public class Ldap {
	
	
	public boolean jdbcAuthent(String server, String user, String password)
	{
		try
		{
			Class.forName("com.novell.sql.LDAPDriver");
			
			Driver driver = DriverManager.getDriver("jdbc:ldap");
			
			Connection conn = 
				driver.connect("jdbc:ldap://" + server + ";user=" + user +
						";password=" + password + ";useCleartext=true", null); 
			conn.close();
			
			return true;
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.WARNING, e);
			return false;
		}
		
		/*
		 DatabaseMetaData  metaData = conn.getMetaData();
		 
		 ResultSet tables = metaData.getTables(null, null, "?", null);
		 
		 while (tables.next())
		 {
		 tableName =  tables.getString("NAME");//column 3 is tableName
		 
		 System.out.println("Table name: " + tableName);
		 
		 //get columns for this table            
		  
		  ResultSet columns = 
		  metaData.getColumns(null, null, tableName, "?");
		  
		  while (columns.next())
		  {
		  System.out.println("     column: " + columns.getString(4));
		  System.out.println("               Type: " + 
		  columns.getString(6));
		  }
		  System.out.println("");
		  }
		  */  
	}
	
	
	public boolean anonymousAuthent(String server, int port )
	{
		LDAPConnection conn = null;
		
		try 
		{
			conn = new LDAPConnection();
			conn.connect(server, port);	
			return conn.isBound();
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.WARNING, e);
			return false;
		}
		finally
		{
			if(conn != null) try { conn.disconnect(); } catch(Exception e) {}
		}
		
	}
	

	
	public boolean authent(int version, String server, int port, String user, String passwd)
	{
		LDAPConnection conn = null;
		
		try
		{
			conn = new LDAPConnection();
			conn.connect( server, port );
			conn.bind(version, user, passwd.getBytes("UTF8"));
			return conn.isBound();
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.WARNING, e);
			return false;
		}
		finally
		{
			if(conn != null) try { conn.disconnect(); } catch(Exception e) {}
		}
	}
	
	
	public boolean sslAuthent(int version, String server, int SSLPort, String user, String passwd)
	{
		LDAPConnection  conn = null;
		
		try
		{
			LDAPJSSESecureSocketFactory ssf = new LDAPJSSESecureSocketFactory();
			conn = new LDAPConnection(ssf);
			conn.connect(server, SSLPort);
			conn.bind( version, user, passwd.getBytes("UTF8") );
			return conn.isBound();
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.WARNING, e);
			return false;
		}
		finally
		{
			if(conn != null) try { conn.disconnect(); } catch(Exception e) {}
		}
	}
	
	
	public boolean defaultAuthent(String server, String user, String password)
	{
		int ldapVersion   = LDAPConnection.LDAP_V3;
		int ldapPort      = LDAPConnection.DEFAULT_PORT;
		
		return authent(ldapVersion, server, ldapPort, user, password);
	}
	
	public boolean defaultSSLAuthent(String server, String user, String password)
	{
		int ldapVersion   = LDAPConnection.LDAP_V3;
		int ldapSSLPort   = LDAPConnection.DEFAULT_SSL_PORT;
		
		return sslAuthent(ldapVersion, server, ldapSSLPort, user, password);
	}
	
	
}
