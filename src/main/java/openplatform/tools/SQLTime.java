package openplatform.tools;

import java.sql.*;

/**
 * SQLTime.java
 *
 *
 * Created: Thu Apr 21 17:20:27 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class SQLTime 
{
    public static String getSQLTime(long time)
    {
        Timestamp ts = new Timestamp(time);
        return ts.toString();
    }

    public static long getJavaTime(String sqltime)
    {
        Timestamp ts = Timestamp.valueOf(sqltime);
        return ts.getTime();
    }
}
