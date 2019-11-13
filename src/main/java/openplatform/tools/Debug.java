package openplatform.tools;

import java.io.PrintStream;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import openplatform.database.dbean.DBCmsPreferences;

 
/**
 * Class Debug
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Debug
{
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd'/'MM'/'yy'@'H':'mm");

    public static int DEBUG = 0;
    public static int WARNING = 1;
    public static int ERROR = 2;
   

    public static boolean DEBUGON = true;
    
    static
    {
    	refresh();
    }
    
    public static void refresh()
    {
    	DBCmsPreferences pref = DBCmsPreferences.loadByKey(null);
    	if(pref != null)
    	{
    		if("0".equals(pref.getDebugLog())) DEBUGON = false;
    		else DEBUGON = true;
    	}
    }
    
    
    /*
    public static void instanciate()
    {
        try
        {
            ResourceBundle props = ResourceBundle.getBundle("configuration");
            String sdbg = props.getString("debug");
            Boolean bdbg = new Boolean(sdbg);
            DEBUGON = bdbg.booleanValue();
            Debug.println(null, Debug.DEBUG, "debug on="+DEBUGON);
        }
        catch(MissingResourceException e)
        {
            Debug.println(null,Debug.ERROR,e);
        }        
    }
     */
    

    public static void println(Object obj, int level, Object msg)
    {
        if(!DEBUGON)
            return;

        Class aclass = null;

        if(obj!=null)
            aclass = obj.getClass();
        
        StringBuffer log = new StringBuffer();
        Calendar now = Calendar.getInstance();
 
        StringBuffer date = sdf.format(now.getTime(), new StringBuffer(), new FieldPosition(0));


        
        String slevel="DEBUG";
        if(level==1)
            slevel="WARNING";
        else if(level==2)
            slevel="ERROR";

        if(aclass!=null)
        {
            if(level==2)
                System.err.println(log.append(slevel).append(" ").append(date).append(" ").append(aclass.getName()).append(" > ").append(msg));
            else
                System.out.println(log.append(slevel).append(" ").append(date).append(" ").append(aclass.getName()).append(" > ").append(msg));
        }
        else
        {
            if(level==2)
                System.err.println(log.append(slevel).append(" ").append(date).append(" > ").append(msg));
            else
                System.out.println(log.append(slevel).append(" ").append(date).append(" > ").append(msg));
        }


        if(msg instanceof Exception)
        {
            Exception e = (Exception)msg;
            e.printStackTrace();
        }
    }


    public static PrintStream debugStream()
    {
        return System.out;
    }

    public static PrintStream errorStream()
    {
        return System.err;
    }
}
