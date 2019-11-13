package openplatform.session;
import java.util.Enumeration;

import openplatform.database.dbean.DBUserGroups;
import openplatform.tools.Debug;

/**
 * Class AdminSession
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminSession extends UserSession
{
    private DBUserGroups ugpmask = new DBUserGroups();
    public final static String ADMIN_GROUP_ID = "1";
    


    public AdminSession()
    {
        super();
    }

    public boolean getLogoutUser()
    {
        try
        {
            Debug.println(this, Debug.DEBUG, "Session logout");
            connected = false;

            Enumeration enum0 = session.getAttributeNames();
            while(enum0.hasMoreElements())
            {
                String key = (String)enum0.nextElement();
                if(!USERSESSION.equals(key))
                {
                    session.removeAttribute(key);
                    Debug.println(this, Debug.DEBUG, "Session object removed:"+key);
                }
            }

            return true;
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return false;
        }
    }


    public boolean getLoginUser()
    {
    	super.getLoginUser();
    	
    	if(!isConnected()) return false;
    	
    	// Group authent
        ugpmask.clear();
        ugpmask.setUserId(getUserId());
        ugpmask.setGroupId(ADMIN_GROUP_ID);
        
        if(DBUserGroups.count(ugpmask, null) == 1)
        {
            connected = true;
            return true;
        }
        else
        	return false;
    }
    
    
    /*
    public boolean getLoginUser()
    {
        connected = false;

        if(!isLoginOpened())
        {
            Debug.println(this, Debug.WARNING, "License expired !");
            return false; // license expired !
        }

        if(getLogin() == null || getPassword() == null)
        {
            Debug.println(this, Debug.ERROR, "Miss login or password");
            return false;
        }

        umask.clear();
        umask.setLogin(getLogin());
        umask.setPassword(getPassword());

        if(DBUser.count(umask, null) == 1)
        {
            DBUser user = DBUser.loadByKey(umask);
            String date = user.getExpiryTime();
            if(date != null)
            {
                Timestamp ts = Timestamp.valueOf(date);
                long l = ts.getTime() - System.currentTimeMillis();
                if(l<=0)
                {
                    Debug.println(this, Debug.ERROR, "Login:"+getLogin()+" expired.");
                    return false;
                }
  
            }


            setUserId(user.getUserId());
            refresh();
            
            ugpmask.clear();
            ugpmask.setUserId(getUserId());
            ugpmask.setGroupId(ADMIN_GROUP_ID);
            
            if(DBUserGroups.count(ugpmask, null) == 1)
            {
                connected = true;
                return true;
            }
            else return false;
        }
        else return false;
    }
*/
}
