package openplatform.database;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.sql.DataSource;

import openplatform.tools.Configuration;
import openplatform.tools.Debug;

//import org.hibernate.Session;

import com.mchange.v2.c3p0.DataSources;



/**
 * Class Database
 *
 * This class use a connection pool which makes the lib running faster !
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Database
{
	private static Logger log = Logger.getLogger(Database.class.getName());
    //private static final String NULL = "NULL";
    private static Database db = new Database();

    
    private DataSource pooled;
    
    
    private ResourceBundle specificSQL = ResourceBundle.getBundle("specific_sql");
    
    

    /**
     * Singleton
     */
    private Database()
    {
    	try
    	{
    		/*
    		ResourceBundle rb = ResourceBundle.getBundle("configuration");
    		String driverclass = rb.getString("driverclass");
    		String jdbcurl = rb.getString("jdbcurl");
    		String username = rb.getString("username");
    		String password = rb.getString("password");
    		
    		Class.forName(driverclass);
    		
    		DataSource unpooled = DataSources.unpooledDataSource(jdbcurl, username, password);
    		pooled = DataSources.pooledDataSource(unpooled);
    		*/
    		
    		Class.forName(Configuration.getDB_driverclass());
    		DataSource unpooled = DataSources.unpooledDataSource(Configuration.getDB_jdbcurl()
    				, Configuration.getDB_username(), Configuration.getDB_password());
    		pooled = DataSources.pooledDataSource(unpooled);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }

    
    public String getSpecificSQL(String key)
    {
    	return specificSQL.getString(key);
    }
    
    
    public void stopDatabase()
    {
    	try
    	{
    		DataSources.destroy(pooled);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    
    private Connection getConnection()
    	throws Exception
    {
    	return pooled.getConnection();
    }
    
    
    
    public static Database getInstance()
    {
        return db;
    }




    /*=======*
     * BEGIN *
     *=======*/


    /**
     * clean string to have a proper entry in the database
     */
    public static String cleanChars(String value)
    {
        if(value==null) return null;

        try
        {
            String res = value.replaceAll("\\\\","\\\\\\\\");
            res = res.replaceAll("'","\\\\'");
            return res;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            return value;
        }
    }


    /**
     * clean string to have a proper entry in the database
     */
    public static String likeCleanChars(String value)
    {
        if(value==null) return null;

        try
        {
            String res= value.replaceAll("\\\\","\\\\\\\\\\\\\\\\");
            res = res.replaceAll("'","\\\\'");
            return res;
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            return value;
        }
    } 
    
    
    

    /**
     * for example :
     * INSERT INTO user (password,login) VALUES ('matrix','neo');
     * @return keyid
     **/
    
    public String insert(String table_name, ParamValuePair valuetoinsert) 
        throws Exception
    {
        Connection conn = null;
        String sgk = null;
        PreparedStatement stmt = null;

        if(valuetoinsert == null || valuetoinsert.size() == 0)
        	throw new Exception("Nothing to INSERT.");
        
        try
        {
        	conn = getConnection();

            String[] keys = valuetoinsert.keys();
            StringBuffer query = new StringBuffer("INSERT INTO ");
            StringBuffer qm = new StringBuffer();
            
            query.append(table_name).append(" (");

            
            int len = keys.length;
            for(int i=0; i<len; i++)
            {
            	query.append(keys[i]);
            	qm.append("?");
            	
            	if(i<len-1)
            	{
            		query.append(",");
            		qm.append(",");
            	}
            }
            
            query.append(") VALUES (").append(qm).append(")");
            
            if(Debug.DEBUGON) log.info("query="+query);

            stmt = conn.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            
            for(int i=1; i<=len; i++)
            {
            	String key = keys[i-1];
            	Object value = valuetoinsert.getObject(key);
            	stmt.setObject(i, value);
            }
            
            
            stmt.executeUpdate();
            

            ResultSet gk = stmt.getGeneratedKeys();
            if(gk!=null)
            {
                gk.first();
                sgk = gk.getString(1);
            }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            throw e;
        }
        finally
        {
            if(stmt!=null) stmt.close();
            if(conn != null) conn.close();
        }

        if(Debug.DEBUGON) log.info("Database key="+sgk);
        return sgk;
    }


    
    
    
    /**
     * filter can be null
     **/
    public void update(String table_name, ParamValuePair filter, ParamValuePair valuetoset)
        throws Exception
    {
        Connection conn = null;
        PreparedStatement stmt = null;

        if(valuetoset == null || valuetoset.size() == 0) throw new Exception("Nothing to UPDATE.");

        try
        {
        	conn = getConnection();
        
            StringBuffer query = new StringBuffer("UPDATE ");
            query.append(table_name);

            String[] param=valuetoset.keys();
            int len = param.length;
            
            String[] param2 = null;
            int len2 = 0;
            
            StringBuffer set = new StringBuffer("SET ");

            
            for(int i=0;i<len;i++)
            {
                set.append(param[i]).append("=");
                set.append("?");
            
                if(i!=len-1)
                    set.append(",");
            }


            if(filter!=null && filter.size()!=0)
            {
            	param2=filter.keys();
            	len2 = param2.length;
            	
                StringBuffer where = new StringBuffer("WHERE ");
   
                for(int i=0;i<len2;i++)
                {
                    where.append(param2[i]).append("=");
                    where.append("?");

                    if(i!=len2-1)
                        where.append(SQLConstraint.AND);
                }

                query.append(" ").append(set).append(" ").append(where);
            }
            else
                query.append(" ").append(set);


            if(Debug.DEBUGON) log.info("query="+query);
            
            stmt = conn.prepareStatement(query.toString());
            
            for(int i=1; i<=len; i++)
            {
            	String key = param[i-1];
            	Object value = valuetoset.getObject(key);
            	stmt.setObject(i, value);
            }
            
            for(int i=(len+1); i<=(len+len2); i++)
            {
            	String key = param2[i-len-1];
            	Object value = filter.getObject(key);
            	stmt.setObject(i, value);
            }
            
            stmt.executeUpdate();           
        }
        catch(Exception e)
        {
            
        	e.printStackTrace();
            throw e;
        }
        finally
        {
        	if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }


    /**
     * filter can be null
     **/
    public void delete(String table_name, ParamValuePair filter)
        throws Exception
    {
        Connection conn = null;
        PreparedStatement stmt = null;


        try
        {
        	conn = getConnection();
            StringBuffer query = new StringBuffer("DELETE FROM ");
            query.append(table_name);

            String[] param = null;
            int len = 0;
        
            if(filter!=null && filter.size()!=0)
            {
                StringBuffer where = new StringBuffer("WHERE ");
                StringBuffer qm = new StringBuffer();
                
                param = filter.keys();
                len = param.length;

                for(int i=0; i<len; i++)
                {
                	where.append(param[i]).append("=?");
                	if(i<len-1)
                		where.append(SQLConstraint.AND);
                }

                query.append(" ").append(where);
                
            }
            
            if(Debug.DEBUGON) log.info("query="+query);
            
            stmt = conn.prepareStatement(query.toString());
            
            for(int i=1; i<=len; i++)
            {
            	String key = param[i-1];
            	Object value = filter.getObject(key);
            	stmt.setObject(i, value);
            }

            stmt.executeUpdate();
        }
        catch(Exception e)
        {
            
        	e.printStackTrace();
            throw e;
        }
        finally
        {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }





    /**
     * filter can be null
     * return null if nothing found
     * special character like % _ are taken in account
     **/
    public ParamValuePair[] select(String table_name, SQLConstraint constraint, String[] requestedvalue, ParamValuePair filter)
        throws Exception
    {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
    	

    	
        ParamValuePair[] pvp = null;

        String separator = constraint==null?SQLConstraint.AND:constraint.getSeparator();

        try
        {            
        	conn = getConnection();

            StringBuffer query = new StringBuffer();

            StringBuffer requestedval = new StringBuffer();
            
            int rvlen = requestedvalue.length;
            
            for(int i=0;i<rvlen;i++)
            {
                requestedval.append(requestedvalue[i]);
                if(i!=rvlen-1)
                    requestedval.append(",");
            }

            
            String[] param = null;
            int len = 0;

            if(filter!=null && filter.size()!=0)
            {
                StringBuffer where = new StringBuffer("WHERE ");
                param = filter.keys();
                len = param.length;

                for(int i=0; i<len; i++)
                {
                	where.append(param[i]);
                	if(constraint != null && constraint.isCaseSensitive()) where.append(" LIKE BINARY ?");
                    else where.append(" LIKE ?");
                	
                	if(i<len-1) where.append(separator);
                }

                query.append("SELECT ").append(requestedval).append(" FROM ").append(table_name).append(" ").append(where);
            }
            else
            {
                query.append("SELECT ").append(requestedval).append(" FROM ").append(table_name);
            }

            if(constraint!=null)
            {
                if(filter==null || filter.size() == 0 && constraint.getCustomWhere()!=null) query.append(" WHERE");
                if(filter!=null && filter.size()!=0 && constraint.getCustomWhere()!=null) query.append(" AND");
                query.append(" ").append(constraint.compile());
            }



            if(Debug.DEBUGON) log.info("query="+query);

            stmt = conn.prepareStatement(query.toString());
            
            for(int i=1; i<=len; i++)
            {
            	String key = param[i-1];
            	Object value = filter.getObject(key);
            	stmt.setObject(i, value);
            }
            
            rs = stmt.executeQuery();

            rs.last();
            int size = rs.getRow();
            rs.first();

            if(size==0)
            {
            	if(Debug.DEBUGON) log.info("no results found");
                return null;
            }

            pvp = new ParamValuePair[size];
            
            
            int i=0;
        
            ResultSetMetaData rsmd = rs.getMetaData();
            
            while(!rs.isAfterLast())
            {
                pvp[i] = new ParamValuePair();
            
                for(int j=0;j<requestedvalue.length;j++)
                {
                    String key = requestedvalue[j];
                    
                    int col = rs.findColumn(key);
                    int type = rsmd.getColumnType(col);
                    
                    if(type == Types.BINARY || type == Types.BLOB
                    		|| type == Types.CLOB || type == Types.LONGVARBINARY
                    		|| type == Types.VARBINARY)
                    {
                    	//pvp[i].set(key, rs.getBinaryStream(key));
                    	pvp[i].set(key, rs.getBlob(key).getBinaryStream());
                    }
                    else
                    {
                    	pvp[i].set(key,rs.getString(key));
                    }

                } 

                rs.next();
                i++;
            }
            

            if(Debug.DEBUGON) log.info(pvp.length+ " results found");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            throw e;
        }
        finally
        {
            if(stmt != null) stmt.close();
            if(rs != null) rs.close();
            if(conn != null) conn.close();
        }

        return pvp;
    }


    /**
     * Execute a list of queries
     */
    public void executeQueries(ArrayList query)
        throws Exception
    {
        Connection conn = null;
        Statement stmt = null;
        
        try
        {
        	conn = getConnection();
            stmt = conn.createStatement();

            for(int i=0;i<query.size();i++)
            {
                String oneQ = (String)query.get(i);

                if(Debug.DEBUGON) log.info("executing ==>"+oneQ);
                
                try
                {
                    stmt.execute(oneQ);
                }
                catch(Exception e)
                {
                	e.printStackTrace();
                }
            }     
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            throw e;
        }
        finally
        {
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }
    }



    /**
     * Count the number of arrows
     */
    public int count(String tableName, SQLConstraint sqlconst, ParamValuePair filter)
        throws Exception
    {
        int deft = 0;

        ParamValuePair[] pvp = select(tableName, sqlconst, new String[]{"COUNT(*)"}, filter);
        
        if(pvp!=null && pvp.length==1)
        {
            String res = pvp[0].get("COUNT(*)");

            if(res==null) return deft;
            else return Integer.parseInt(res);
        }
        else
            return deft;
    }


    /**
     * @return the minimum
     */
    public double min(String tableName, String field, SQLConstraint sqlconst, ParamValuePair filter)
        throws Exception
    {
        double deft = 0D;

        StringBuffer sb = new StringBuffer("MIN(");
        sb.append(field).append(")");
        String req = sb.toString();

        ParamValuePair[] pvp = select(tableName, sqlconst, new String[]{req}, filter);
        
        if(pvp!=null && pvp.length==1)
        {
            String res = pvp[0].get(req);

            if(res == null) return deft;
            else return Double.parseDouble(res);
        }
        else
            return deft;
    }



    /**
     * @return the maximum
     */
    public double max(String tableName, String field, SQLConstraint sqlconst, ParamValuePair filter)
        throws Exception
    {
        double deft = 0D;

        StringBuffer sb = new StringBuffer("MAX(");
        sb.append(field).append(")");
        String req = sb.toString();

        ParamValuePair[] pvp = select(tableName, sqlconst, new String[]{req}, filter);
        
        if(pvp!=null && pvp.length==1)
        {
            String res = pvp[0].get(req);
            if(res == null) return deft;
            else return Double.parseDouble(res);
        }
        else
            return deft;
    }


    /**
     * @return an average
     */
    public double average(String tableName, String field, SQLConstraint sqlconst, ParamValuePair filter)
        throws Exception
    {
        double deft = 0D;

        StringBuffer sb = new StringBuffer("AVG(");
        sb.append(field).append(")");
        String req = sb.toString();

        ParamValuePair[] pvp = select(tableName, sqlconst, new String[]{req}, filter);
        
        if(pvp!=null && pvp.length==1)
        {
            String res = pvp[0].get(req);
            
            if(res == null) return deft;
            else return Double.parseDouble(res);
        }
        else
            return deft;
    }


    /**
     * @return the standard deviation (the french "ecart type")
     */
    public double std(String tableName, String field, SQLConstraint sqlconst, ParamValuePair filter)
        throws Exception
    {
        double deft = 0D;

        StringBuffer sb = new StringBuffer("STD(");
        sb.append(field).append(")");
        String req = sb.toString();

        ParamValuePair[] pvp = select(tableName, sqlconst, new String[]{req}, filter);
        
        if(pvp!=null && pvp.length==1)
        {
            String res = pvp[0].get(req);
            
            if(res == null) return deft;
            else return Double.parseDouble(res);
        }
        else
            return deft;
    }


    
    /**
     * filter can be null
     * return null if nothing found
     * special character like % _ are taken in account
     **/
    public String[] selectDistinct(String table_name, SQLConstraint constraint, String field, ParamValuePair filter)
        throws Exception
    {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String[] res = null;
        String separator = constraint==null?SQLConstraint.AND:constraint.getSeparator();

        try
        {
        	conn = getConnection();
            StringBuffer query = new StringBuffer();

            
            String[] param = null;
            int len = 0;
            
            if(filter!=null && filter.size()!=0)
            {
                StringBuffer where = new StringBuffer("WHERE ");
                param = filter.keys();
                len = param.length;

                for(int i=0; i<len; i++)
                {
                	where.append(param[i]);
                	if(constraint != null && constraint.isCaseSensitive()) where.append(" LIKE BINARY ?");
                    else where.append(" LIKE ?");
                	
                	if(i<len-1) where.append(separator);
                }
                
                query.append("SELECT DISTINCT ").append(field).append(" FROM ").append(table_name).append(" ").append(where);
            }
            else
            {
                query.append("SELECT DISTINCT ").append(field).append(" FROM ").append(table_name);
            }


            if(constraint!=null)
            {
                if(filter==null || filter.size() == 0 && constraint.getCustomWhere()!=null) query.append(" WHERE");
                if(filter!=null && filter.size()!=0 && constraint.getCustomWhere()!=null ) query.append(" AND");
                query.append(" ").append(constraint.compile());
            }


            if(Debug.DEBUGON) log.info("query="+query);

            stmt = conn.prepareStatement(query.toString());
            
            for(int i=1; i<=len; i++)
            {
            	String key = param[i-1];
            	Object value = filter.getObject(key);
            	stmt.setObject(i, value);
            }
            
            rs = stmt.executeQuery();
            
            rs.last();
            int size = rs.getRow();
            rs.first();


            if(size==0)
            {
            	if(Debug.DEBUGON) log.info("no results found");
                return null;
            }

            res = new String[size];
            int i=0;
        

            while(!rs.isAfterLast())
            {
                res[i] = rs.getString(field);

                rs.next();
                i++;
            }

            if(Debug.DEBUGON) log.info(res.length+ " results found");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            throw e;
        }
        finally
        {
            if(stmt != null) stmt.close();
            if(rs != null) rs.close();
            if(conn != null) conn.close();
        }

        return res;
    }




    public String[] listTables()
        throws Exception
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        String[] res = null;

        try
        {
        	conn = getConnection();
            stmt = conn.createStatement();
            StringBuffer query = new StringBuffer();

            query.append("SHOW TABLES");

            if(Debug.DEBUGON) log.info("query="+query);

            rs = stmt.executeQuery(query.toString());

            rs.last();
            int size = rs.getRow();
            rs.first();


            if(size==0)
            {
            	if(Debug.DEBUGON) log.info("no results found");
                return null;
            }

            res = new String[size];
            int i=0;
        

            while(!rs.isAfterLast())
            {
                res[i] = rs.getString(1);

                rs.next();
                i++;
            }

            if(Debug.DEBUGON) log.info(res.length+ " results found");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            throw e;
        }
        finally
        {
            if(stmt != null) stmt.close();
            if(rs != null) rs.close();
            if(conn != null) conn.close();
        }

        return res;
    }




    public String[] listField(String tableName)
        throws Exception
    {
        ArrayList arr = descTable(tableName);
        int size = arr.size();

        if(size == 0) return null;

        String[] res = new String[size];
        for(int i=0; i<size; i++)
        {
            res[i] = ((DatabaseField)arr.get(i)).getField();
        }
        return res;
    }


    /**
     * @return arraylist of DatabaseField object
     */
    public ArrayList descTable(String tableName)
        throws Exception
    {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList array = new ArrayList();

        try
        {
        	conn = getConnection();
            stmt = conn.createStatement();
            StringBuffer query = new StringBuffer();

            query.append("DESC ").append(tableName);

            if(Debug.DEBUGON) log.info("query="+query);

            rs = stmt.executeQuery(query.toString());

            rs.last();
            int size = rs.getRow();
            rs.first();


            if(size==0)
            {
            	if(Debug.DEBUGON) log.info("no results found");
                return null;
            }

            int i=0;
        
            while(!rs.isAfterLast())
            {
                DatabaseField field = new DatabaseField();
                field.setField(rs.getString("Field"));
                field.setType(rs.getString("Type"));
                field.setCanBeNull("YES".equals(rs.getString("Null"))?true:false);
                field.setIsPrimaryKey("PRI".equals(rs.getString("Key"))?true:false);
                field.setDefaultValue(rs.getString("Default"));
                field.setExtra(rs.getString("Extra"));

                array.add(field);
                rs.next();
                i++;
            }

            if(Debug.DEBUGON) log.info(array.size()+ " results found");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
            throw e;
        }
        finally
        {
            if(stmt != null) stmt.close();
            if(rs != null) rs.close();
            if(conn != null) conn.close();
        }

        return array;
    }
    
    
    public ArrayList loadSQL(String query)
    throws Exception
    {
    	return loadSQL(query, null);
    }
    
    
    public ArrayList loadSQL(String query, ArrayList param)
    throws Exception
    {
		ArrayList array = new ArrayList();
		
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		
        try
        {
        	conn = getConnection();
        	stmt = conn.prepareStatement(query);
        	
        	// ENTER PARAM
        	if(param != null)
        	{
        		int len = param.size();
        		for(int i=0; i<len; i++)
        		{
        			stmt.setObject(i+1, param.get(i));
        		}
        	}
        	
        	
        	
        	stmt.execute();
        	rs = stmt.getResultSet();
        	
            rs.last();
            int size = rs.getRow();
            rs.first();
            
            if(size==0)
            {
            	if(Debug.DEBUGON) log.info("no results found");
                return null;
            }
            

            while(!rs.isAfterLast())
            {
            	ParamValuePair pvp = new ParamValuePair();
            
                ResultSetMetaData rsmd = rs.getMetaData();
                int cols = rsmd.getColumnCount();
                for(int j=1;j<=cols;j++)
                {
                    String key = rsmd.getColumnName(j);
                    String value = rs.getString(j);

                    pvp.set(key,value);
                } 

                rs.next();
                array.add(pvp);
            }
            
            if(Debug.DEBUGON) log.info("query="+query);
            if(Debug.DEBUGON) log.info(array.size()+ " results found");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	throw e;
        }
        finally
        {
        	if(stmt != null) stmt.close();
        	if(rs != null) rs.close();
        	if(conn != null) conn.close();
        }
        
		return array;
    }
    

	/**
	 * 
	 * @param query
	 * @param param
	 * @return
	 * @throws Exception
	 */
    public ArrayList loadSQL(StringBuffer query, ArrayList param)
    throws Exception
    {
    	return loadSQL(query.toString(), param);
    }
    
    
    
    /**
     * Array of ParamValuePair
     * @param query
     * @return
     * @throws Exception
     */
    public ArrayList loadSQL(StringBuffer query)
    	throws Exception
    	{
    		return loadSQL(query, null);
    		
    	/*
    		ArrayList array = new ArrayList();
    		
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
    		
            try
            {
            	conn = getConnection();
            	stmt = conn.createStatement();
            	rs = stmt.executeQuery(query.toString());
                rs.last();
                int size = rs.getRow();
                rs.first();
                
                if(size==0)
                {
                	if(Debug.DEBUGON) log.info("no results found");
                    return null;
                }
                
   
                while(!rs.isAfterLast())
                {
                	ParamValuePair pvp = new ParamValuePair();
                
                    ResultSetMetaData rsmd = rs.getMetaData();
                    int cols = rsmd.getColumnCount();
                    for(int j=1;j<=cols;j++)
                    {
                        String key = rsmd.getColumnName(j);
                        String value = rs.getString(j);

                        pvp.set(key,value);
                    } 

                    rs.next();
                    array.add(pvp);
                }
                
                if(Debug.DEBUGON) log.info(array.size()+ " results found");
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            	throw e;
            }
            finally
            {
            	if(stmt != null) stmt.close();
            	if(rs != null) rs.close();
            	if(conn != null) conn.close();
            }
            
    		return array;
    		*/
    	}
    
    
    
    public static void main(String[] args)
    	throws Exception
    {
    	Database db = Database.getInstance();
    	
    	/*
    	ParamValuePair valuetoinsert = new ParamValuePair();
    	valuetoinsert.set("login", "testeur'");
    	valuetoinsert.set("password", "testeur");
    	valuetoinsert.set("fwdMsgToMail", new Integer(0));
    	valuetoinsert.set("cpMsgToSMS", new Integer(0));
    	valuetoinsert.set("expiryTime", new java.util.Date());
    	db.insert("User", valuetoinsert);
    	*/
    	
    	/*
    	ParamValuePair filter = new ParamValuePair();
    	filter.set("login", "testeur");
    	
    	ParamValuePair valuetoset = new ParamValuePair();
    	valuetoset.set("firstName", "truc");
    	
    	db.update("User", null, valuetoset);
    	*/
    	
    	/*
    	db.delete("GedFiles", null);
    	*/
    	
    	/*
    	String[] rv = new String[] {"userId","login","password"};
    	ParamValuePair filter = new ParamValuePair();
    	filter.set("login", "t%");
    	
    	ParamValuePair[] pvp = db.select("User", null, rv, filter);
    	if(pvp !=null)
    		for(int i=0; i<pvp.length; i++)
    		{
    			String[] keys = pvp[i].keys();
    			for(int j=0; j<keys.length; j++)
    				System.out.println(keys[j]+","+ pvp[i].get(keys[j]));
    		}
    	*/
    	
    	/*
    	ParamValuePair filter = new ParamValuePair();
    	filter.set("login", "t%");
    	String[] d = db.selectDistinct("User", null, "login", filter);
    	
    	if(d!=null)
    	for(int i=0; i<d.length; i++)
    		System.out.println(d[i]);
    	*/
    	
    }

}
