package openplatform.session;

import openplatform.database.dbean.*;
import openplatform.tools.*;
import java.util.*;

import openplatform.regexp.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

import dang.applimgt.AdminUser;

import java.sql.*;

import openplatform.ldap.Ldap;
import openplatform.license.*;

/**
 * Class UserSession
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class UserSession extends DBUser
{
    protected DBUser umask = new DBUser();
    private DBImage imask = new DBImage();

    // Remove actions of the dbean object
    public void delete() {}
    public void store() {}
    public boolean isInError() { return false; }
    // 


    private DBTerminal terminal = null;
    private DBImage image = null;
    protected boolean connected = false;
    private String defaultGroupId = "2"; // default group

    protected String userAgent;
    protected HttpSession session;
    //protected Locale locale;

    private Hashtable uaRegexp = new Hashtable();
    private DBCmsPreferences cmsPref;
    protected boolean ldapAuthent;
    
    public static final String SESSION_TERMINAL_TYPE = "terminalType";
    public static final String SESSION_IMAGE_ID = "imageId";
    public final static String USERSESSION = "usersession";
    public final static String ADMINSESSION = "adminsession";


    public UserSession()
    {   	
    	DBTerminal[] t = DBTerminal.load(null);
    	if(t != null)
    	{
    		int len = t.length;
    		for(int i=0; i<len; i++)
    		{
    			Regexp reg = new Regexp(t[i].getUserAgent());
    			uaRegexp.put(reg, t[i]);
    		}
    	}
    	
    	cmsPref = DBCmsPreferences.loadByKey(null);
    	ldapAuthent = false;
        if("1".equals(cmsPref.getLdapAuthent())) ldapAuthent = true;
    }

    
    
    
    public String getThumbnailImageId()
    {
        return "1";
    }


    public DBTerminal getTerminalProfile()
    {
        return terminal;
    }

    public DBImage getImageProfile()
    {
        return image;
    }


    protected boolean isNormalAuthent()
    {
        umask.clear();
        umask.setLogin(getLogin());
        umask.setPassword(getPassword());

        if(DBUser.count(umask, null) == 1)
        	return true;
        else
        	return false;
    }
    
    protected boolean isLdapAuthent()
    {
    	String dn = null;
    	String server = cmsPref.getLdapServer();
    	
    	String dnMask = cmsPref.getLdapDN();
    	if(dnMask != null)
    	{
    		dn = dnMask.replaceAll("\\[LOGIN\\]",getLogin());
    	}
    	
    	if(dn == null) return false;
    	
    	Debug.println(this, Debug.DEBUG, "Checking LDAP authent for DN:"+dn); 
    	
    	Ldap ldap = new Ldap();
    	return ldap.defaultAuthent(server, dn, getPassword());
    }
    
    protected boolean isExpired(DBUser user)
    {
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
        else return false;
        
        return true;
    }
    
  
    
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

        // Check if we need to authent via ldap
        // if user is admin, dont try to authent via ldap
        if(ldapAuthent && !"admin".equals(getLogin()))
        {
        	if(!isLdapAuthent()) return false;
        	
        	try
        	{
        		umask.clear();
                umask.setLogin(getLogin());
                DBUser u = DBUser.loadByKey(umask);
        		
        		// Create/update user with admin bean
        		AdminUser au = new AdminUser();
        		if(u != null)
        			au.setUserId(u.getUserId());
        		au.setLogin(getLogin());
        		au.setPassword(getPassword());
        		au.doStore();
        	}
        	catch(Exception e)
        	{
        		Debug.println(this, Debug.ERROR, e);
        		return false;
        	}
        }
        else
        {
        	if(!isNormalAuthent()) return false;
        }
        
        connected = true;
        
        umask.clear();
        umask.setLogin(getLogin());
        umask.setPassword(getPassword());
        DBUser user = DBUser.loadByKey(umask);
        if(user != null) initBean(user);
        
        if(terminal == null || image == null) return getDetectTerminal();
        else return true;
        
        /*
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

            connected = true;
            setUserId(user.getUserId());
            refresh(); // refresh all the data for this user object (possible because we know the userId !)
            
            if(terminal == null || image == null) return getDetectTerminal();
            else return true;
        }
        else return false;
        */
    }


    public boolean getTerminalDetected()
    {
        if(session == null)
        {
            Debug.println(this, Debug.WARNING, "terminalDetected : Session is not set !");
            return false;
        }
        
        if(session.getAttribute(SESSION_TERMINAL_TYPE) != null
           && session.getAttribute(SESSION_IMAGE_ID) != null)
            return true;
        else return false;
    }    


    public boolean getDetectTerminal()
    {
        if(userAgent == null)
        {
            Debug.println(this, Debug.WARNING, "detectTerminal:Miss user-agent");
            return false;
        }

        if(session == null)
        {
            Debug.println(this, Debug.WARNING, "detectTerminal:Session is not set !");
            return false;
        } 
 
        Debug.println(this, Debug.DEBUG, "user-agent:"+userAgent);

        Enumeration enums = uaRegexp.keys();
        
        boolean terminalFound = false;
        
        while(enums.hasMoreElements())
        {
        	Regexp reg = (Regexp)enums.nextElement();
        	if(reg.matches(userAgent))
        	{
        		terminalFound = true;
        		
        		DBTerminal term = (DBTerminal)uaRegexp.get(reg);
        		term.refresh();
        		term.setCounter(Integer.toString(1+Integer.parseInt(term.getCounter())));
        		term.store();
        		
        		terminal = term;
        		
                imask.clear();
                imask.setImageId(term.getImageId());
                image = DBImage.loadByKey(imask); // initialize image for that session

                // put objects in session
                session.setAttribute(SESSION_TERMINAL_TYPE, terminal.getNavType());
                session.setAttribute(SESSION_IMAGE_ID, image.getImageId());

                break;
        	}
        }
        
        if(!terminalFound)
        {
            terminal = new DBTerminal();
            terminal.setUserAgent(Regexp.stringToMatchregexp(userAgent));
            terminal.setImageId("2");
            terminal.store();
        }


        Debug.println(this, Debug.DEBUG, (getLogin()!=null?getLogin():"") +
                      " connected with terminal regexp:"+terminal.getUserAgent());
        return true;
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
                if(!ADMINSESSION.equals(key))
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

    /*
    public boolean getRegister()
    {
        if(defaultGroupId==null)
        {
            Debug.println(this, Debug.WARNING, "Can't register because there is no defaultGroupId");
            return false;
        }

        if(getLogin() == null || getLogin().trim().equals("")
           || getPassword() == null || getPassword().trim().equals(""))
        {
            Debug.println(this, Debug.WARNING, "Login or password can not be empty !");
            return false;
        }


        umask.clear();
        umask.setLogin(getLogin());
        
        int nb = DBUser.count(umask, null);
        if(nb != 0)
        {
            Debug.println(this, Debug.WARNING, "Login "+getLogin()+" already exists !");
            return false;
        }

        super.store();

        // User groups mapping
        DBUserGroups ugmask = new DBUserGroups();
        ugmask.setUserId(getUserId());
        ugmask.setGroupId(defaultGroupId);
        ugmask.store();

        Debug.println(this, Debug.DEBUG, "User:"+getLogin()+" registered");

        return true;
    }
*/
    
    
    public boolean getDelete()
    {
        delete();
        return !isInError();
    }


    public boolean isMobile()
    {
        if(terminal == null) return false;

        if(DBTerminal.NAVTYPE_PC.equals(terminal.getNavType())) return false;
        else return true;
    }

    /**
     * License verification
     */
    public boolean isLoginOpened()
    {
        AdminLicense aLic = new AdminLicense();
        return aLic.isLicensed();
    }

    public boolean isConnected()
    {
        return connected;
    }

    public String getUserAgent()
    {
        return userAgent;
    }

    public void setPageContext(PageContext pc)
    {
        if(pc == null) return;
        
        try
        {
            session = pc.getSession();
            HttpServletRequest request = ((HttpServletRequest)pc.getRequest());
            userAgent = request.getHeader("user-agent");
            //locale = pc.getRequest().getLocale();

            Enumeration enum0 = request.getHeaderNames();
            StringBuffer sb = new StringBuffer();
            while(enum0.hasMoreElements())
            {
                String header = (String)enum0.nextElement();
                String value = request.getHeader(header);
                sb.append(header).append("=[").append(value).append("]\n");
            }
            Debug.println(this, Debug.DEBUG, sb.toString());
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }
}
