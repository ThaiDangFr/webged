package openplatform.license;

import java.util.*;
import openplatform.database.*;
import openplatform.database.dbean.*;
import java.text.*;
import java.io.*;
import openplatform.tools.*;
import openplatform.scheduler.*;


/**
 * LicenseState.java
 *
 *
 * Created: Fri Jun 24 14:18:46 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class LicenseState implements Constants
{
    private static long expiryDate;
    private static String licenseId;
    private static String companyName;
    private static String smsApiId;
    private static String smsUser;
    private static String smsPass;
    private static int maxHost;
    private static int maxUser;
    
    protected final static String EXPIRYDATE = "EXPIRYDATE";
    protected final static String LICENSEID = "LICENSEID";
    protected final static String COMPANYNAME = "COMPANYNAME";
    protected final static String SMS_API_ID = "SMS_API_ID";
    protected final static String SMS_USER = "SMS_USER";
    protected final static String SMS_PASS = "SMS_PASS";
    protected final static String MODULES = "MODULES";
    protected final static String MAXHOST = "MAXHOST";
    protected final static String MAXUSER = "MAXUSER";

     
    private static void clear()
    {
        expiryDate = -1;
        licenseId = null;
        companyName = null;
        maxHost = -1;
        maxUser = -1;
    }

    public static void parse()
    {
        DesEncrypter desEncrypter = new DesEncrypter(PHRASE);

        SQLConstraint constr = new SQLConstraint();
        constr.setLimit(1);
        DBLicense lic = DBLicense.loadByKey(null, constr);

        if(lic == null)
        {
            clear();
            return;
        }

        try
        {
            String enc = lic.getLicenseKey();

            if(enc == null)
            {
                clear();
                return;
            }

            String dec = desEncrypter.decrypt(enc);
            ByteArrayInputStream bais
                = new ByteArrayInputStream(dec.getBytes("UTF-8"));
            Properties prop = new Properties();
            prop.load(bais);

            String sExpDate = prop.getProperty(EXPIRYDATE);

            if(sExpDate != null && !sExpDate.trim().equals(""))
            {
                SimpleDateFormat dateFormat
                    = new SimpleDateFormat("dd'/'MM'/'yyyy HH':'mm");
                expiryDate = (dateFormat.parse(sExpDate, new ParsePosition(0)))
                    .getTime();
            }
            else
                expiryDate = -1;

            licenseId = prop.getProperty(LICENSEID);
            companyName = prop.getProperty(COMPANYNAME);
            smsApiId = prop.getProperty(SMS_API_ID);
            smsUser = prop.getProperty(SMS_USER);
            smsPass = prop.getProperty(SMS_PASS);

            String smaxHost = prop.getProperty(MAXHOST);
            if(smaxHost != null && !smaxHost.trim().equals(""))
                maxHost = Integer.parseInt(smaxHost);
            else
                maxHost = -1;

            String smaxUser = prop.getProperty(MAXUSER);
            if(smaxUser != null && !smaxUser.trim().equals(""))
                maxUser = Integer.parseInt(smaxUser);
            else
                maxUser = -1;

            if(licenseId != null && licenseId.trim().equals("")) licenseId = null;
            if(companyName != null && companyName.trim().equals("")) companyName = null;
            if(smsApiId != null && smsApiId.trim().equals("")) smsApiId = null;
            if(smsUser != null && smsUser.trim().equals("")) smsUser = null;
            if(smsPass != null && smsPass.trim().equals("")) smsPass = null;
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            clear();
        }

    }
    
    
    protected static void updateModules()
    {
        DesEncrypter desEncrypter = new DesEncrypter(PHRASE);

        SQLConstraint constr = new SQLConstraint();
        constr.setLimit(1);
        DBLicense lic = DBLicense.loadByKey(null, constr);

        if(lic == null)
            return;

        try
        {
            String enc = lic.getLicenseKey();

            if(enc == null)
                return;

            String dec = desEncrypter.decrypt(enc);
            ByteArrayInputStream bais
                = new ByteArrayInputStream(dec.getBytes("UTF-8"));
            Properties prop = new Properties();
            prop.load(bais);

            String modules = prop.getProperty(MODULES);
            if(modules == null) return;

            if("*".equals(modules.trim())) return;

            ArrayList okModules = new ArrayList();
            StringTokenizer st = new StringTokenizer(modules, ",");
            while(st.hasMoreTokens())
            {
                String tok = st.nextToken().trim();
                okModules.add(tok);
            }
            

            DBCmsModule[] mod = DBCmsModule.load(null);
            if(mod == null) return;

            int len = mod.length;
            
            for(int i=0; i<len; i++)
            {
                String id = mod[i].getId();
                if(!okModules.contains(id))
                    mod[i].delete();
            }
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }


    public static boolean isSmsServiceOpened()
    {
        if(smsApiId == null || smsUser == null || smsPass == null) return false;
        else return true;
    }


    // GET / SET

    public static final int getMaxHost()
    {
        return LicenseState.maxHost;
    }

    public static final int getMaxUser()
    {
        return LicenseState.maxUser;
    }


    /**
     * Gets the value of expiryDate
     *
     * @return the value of expiryDate
     */
    public static final long getExpiryDate()
    {
        return LicenseState.expiryDate;
    }


    /**
     * Gets the value of macAddress
     *
     * @return the value of macAddress
     */
    public static final String getLicenseId()
    {
        return LicenseState.licenseId;
    }


    /**
     * Gets the value of companyName
     *
     * @return the value of companyName
     */
    public static final String getCompanyName()
    {
        return LicenseState.companyName;
    }


    public static final String getSmsApiId()
    {
        return LicenseState.smsApiId;
    }


    public static final String getSmsUser()
    {
        return LicenseState.smsUser;
    }

    
    public static final String getSmsPass()
    {
        return LicenseState.smsPass;
    }


}
