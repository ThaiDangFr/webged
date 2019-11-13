package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpHost
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpHost
{
    protected boolean inError = false;
    protected String snmpHostId;
    protected String displayName;
    protected String ipAddress;
    protected String community;
    protected String userName;
    protected String authentPassword;
    protected String privacyPassword;
    protected String snmpVersion;

    public static String SNMPHOSTID = "snmpHostId";
    public static String DISPLAYNAME = "displayName";
    public static String IPADDRESS = "ipAddress";
    public static String COMMUNITY = "community";
    public static String USERNAME = "userName";
    public static String AUTHENTPASSWORD = "authentPassword";
    public static String PRIVACYPASSWORD = "privacyPassword";
    public static String SNMPVERSION = "snmpVersion";
    /** 0 **/
    public static final String SNMPVERSION_0 = "0";
    /** 1 **/
    public static final String SNMPVERSION_1 = "1";
    /** 2 **/
    public static final String SNMPVERSION_2 = "2";
    /** Contains the SQL enumeration for SNMPVERSION **/
    public static final ArrayList _SNMPVERSION_LIST = new ArrayList();
    static {_SNMPVERSION_LIST.add(SNMPVERSION_0);_SNMPVERSION_LIST.add(SNMPVERSION_1);_SNMPVERSION_LIST.add(SNMPVERSION_2);}
    /** Contains the SQL HASHMAP for SNMPVERSION **/
    public static final OrdHashMap _SNMPVERSION_MAP = new OrdHashMap();
    static {_SNMPVERSION_MAP.put(SNMPVERSION_0,SNMPVERSION_0);_SNMPVERSION_MAP.put(SNMPVERSION_1,SNMPVERSION_1);_SNMPVERSION_MAP.put(SNMPVERSION_2,SNMPVERSION_2);}
    public static String TABLE = "SnmpHost";

    private static Database db = Database.getInstance();


    public void clear()
    {
        snmpHostId = null;
        displayName = null;
        ipAddress = null;
        community = null;
        userName = null;
        authentPassword = null;
        privacyPassword = null;
        snmpVersion = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBSnmpHost a = (DBSnmpHost)obj;
        return (snmpHostId==null?a.getSnmpHostId()==null:snmpHostId.equals(a.getSnmpHostId())) && (displayName==null?a.getDisplayName()==null:displayName.equals(a.getDisplayName())) && (ipAddress==null?a.getIpAddress()==null:ipAddress.equals(a.getIpAddress())) && (community==null?a.getCommunity()==null:community.equals(a.getCommunity())) && (userName==null?a.getUserName()==null:userName.equals(a.getUserName())) && (authentPassword==null?a.getAuthentPassword()==null:authentPassword.equals(a.getAuthentPassword())) && (privacyPassword==null?a.getPrivacyPassword()==null:privacyPassword.equals(a.getPrivacyPassword())) && (snmpVersion==null?a.getSnmpVersion()==null:snmpVersion.equals(a.getSnmpVersion()));    }

    public int hashCode()
    {
        return (snmpHostId!=null?snmpHostId.hashCode():0) + (displayName!=null?displayName.hashCode():0) + (ipAddress!=null?ipAddress.hashCode():0) + (community!=null?community.hashCode():0) + (userName!=null?userName.hashCode():0) + (authentPassword!=null?authentPassword.hashCode():0) + (privacyPassword!=null?privacyPassword.hashCode():0) + (snmpVersion!=null?snmpVersion.hashCode():0);
    }

    public String toString()
    {
        return "snmpHostId="+snmpHostId+"|"+"displayName="+displayName+"|"+"ipAddress="+ipAddress+"|"+"community="+community+"|"+"userName="+userName+"|"+"authentPassword="+authentPassword+"|"+"privacyPassword="+privacyPassword+"|"+"snmpVersion="+snmpVersion;
    }

    public void refresh()
    {
        if(snmpHostId != null)
        {
            DBSnmpHost mask = new DBSnmpHost();
            mask.setSnmpHostId(snmpHostId);
            DBSnmpHost var = DBSnmpHost.loadByKey(mask);
            if(var != null)
            {
                snmpHostId = var.getSnmpHostId();
                displayName = var.getDisplayName();
                ipAddress = var.getIpAddress();
                community = var.getCommunity();
                userName = var.getUserName();
                authentPassword = var.getAuthentPassword();
                privacyPassword = var.getPrivacyPassword();
                snmpVersion = var.getSnmpVersion();
            }
        }
    }

    public void initBean(DBSnmpHost db)
    {
        this.snmpHostId = db.getSnmpHostId();
        this.displayName = db.getDisplayName();
        this.ipAddress = db.getIpAddress();
        this.community = db.getCommunity();
        this.userName = db.getUserName();
        this.authentPassword = db.getAuthentPassword();
        this.privacyPassword = db.getPrivacyPassword();
        this.snmpVersion = db.getSnmpVersion();
    }

    public String getSnmpHostId()
    {
        return snmpHostId;
    }

    public void setSnmpHostId(String snmpHostId)
    {
        this.snmpHostId = snmpHostId;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getCommunity()
    {
        return community;
    }

    public void setCommunity(String community)
    {
        this.community = community;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getAuthentPassword()
    {
        return authentPassword;
    }

    public void setAuthentPassword(String authentPassword)
    {
        this.authentPassword = authentPassword;
    }

    public String getPrivacyPassword()
    {
        return privacyPassword;
    }

    public void setPrivacyPassword(String privacyPassword)
    {
        this.privacyPassword = privacyPassword;
    }

    public String getSnmpVersion()
    {
        return snmpVersion;
    }

    public void setSnmpVersion(String snmpVersion)
    {
        this.snmpVersion = snmpVersion;
    }

    /**
     * @return a DBSnmpHost table or null if nothing found or if an error occured
     **/
    public static DBSnmpHost[] load(DBSnmpHost mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpHost table or null if nothing found or if an error occured
     **/
    public static DBSnmpHost[] load(SQLConstraint sqlconstraint, DBSnmpHost mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getCommunity() != null) filter.set(COMMUNITY, mask.getCommunity());
            if(mask.getUserName() != null) filter.set(USERNAME, mask.getUserName());
            if(mask.getAuthentPassword() != null) filter.set(AUTHENTPASSWORD, mask.getAuthentPassword());
            if(mask.getPrivacyPassword() != null) filter.set(PRIVACYPASSWORD, mask.getPrivacyPassword());
            if(mask.getSnmpVersion() != null) filter.set(SNMPVERSION, mask.getSnmpVersion());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{SNMPHOSTID, DISPLAYNAME, IPADDRESS, COMMUNITY, USERNAME, AUTHENTPASSWORD, PRIVACYPASSWORD, SNMPVERSION}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpHost[] result = new DBSnmpHost[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpHost();
                    result[i].setSnmpHostId(pvp[i].get(SNMPHOSTID));
                    result[i].setDisplayName(pvp[i].get(DISPLAYNAME));
                    result[i].setIpAddress(pvp[i].get(IPADDRESS));
                    result[i].setCommunity(pvp[i].get(COMMUNITY));
                    result[i].setUserName(pvp[i].get(USERNAME));
                    result[i].setAuthentPassword(pvp[i].get(AUTHENTPASSWORD));
                    result[i].setPrivacyPassword(pvp[i].get(PRIVACYPASSWORD));
                    result[i].setSnmpVersion(pvp[i].get(SNMPVERSION));
                }
                return result;
            }
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return null;
        }
    }

    /**
     * @return a DBSnmpHost object or null if nothing found or if an error occured
     **/
    public static DBSnmpHost loadByKey(DBSnmpHost mask)
    {
        DBSnmpHost[] res = DBSnmpHost.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpHost object or null if nothing found or if an error occured
     **/
    public static DBSnmpHost loadByKey(DBSnmpHost mask, SQLConstraint sqlconstraint)
    {
        DBSnmpHost[] res = DBSnmpHost.load(sqlconstraint, mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * Store the object in database
     **/
    public void store()
    {
        inError = false;
        ParamValuePair toinsert = new ParamValuePair();
        toinsert.set(SNMPHOSTID,snmpHostId);
        toinsert.set(DISPLAYNAME,displayName);
        toinsert.set(IPADDRESS,ipAddress);
        toinsert.set(COMMUNITY,community);
        toinsert.set(USERNAME,userName);
        toinsert.set(AUTHENTPASSWORD,authentPassword);
        toinsert.set(PRIVACYPASSWORD,privacyPassword);
        toinsert.set(SNMPVERSION,snmpVersion);

        // Store a new entry
        if(snmpHostId == null)
        {
            try
            {
                snmpHostId = db.insert(TABLE, toinsert);
            }
            catch(Exception e)
            {
                inError=true;
                Debug.println(this, Debug.ERROR, e);
            }
        }
        // update entry
        else
        {
            ParamValuePair filter = new ParamValuePair();
            filter.set(SNMPHOSTID, snmpHostId);

            try
            {
                db.update(TABLE, filter, toinsert);
            }
            catch(Exception e)
            {
                inError=true;
                Debug.println(this, Debug.ERROR, e);
            }
        }
    }

    /**
     * Delete the object in database (if primary key is not null)
     **/
    public void delete()
    {
        if(snmpHostId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(SNMPHOSTID, snmpHostId);

            try
            {
                db.delete(TABLE, filter);
            }
            catch(Exception e)
            {
                inError=true;
                Debug.println(this, Debug.ERROR, e);
            }
        }
    }

    /**
     * Delete many files
     **/
    public static void delete(DBSnmpHost mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getCommunity() != null) filter.set(COMMUNITY, mask.getCommunity());
            if(mask.getUserName() != null) filter.set(USERNAME, mask.getUserName());
            if(mask.getAuthentPassword() != null) filter.set(AUTHENTPASSWORD, mask.getAuthentPassword());
            if(mask.getPrivacyPassword() != null) filter.set(PRIVACYPASSWORD, mask.getPrivacyPassword());
            if(mask.getSnmpVersion() != null) filter.set(SNMPVERSION, mask.getSnmpVersion());
        }

        try
        {
            db.delete(TABLE, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }

    /**
     * Delete many files
     **/
    public static int count(DBSnmpHost mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getCommunity() != null) filter.set(COMMUNITY, mask.getCommunity());
            if(mask.getUserName() != null) filter.set(USERNAME, mask.getUserName());
            if(mask.getAuthentPassword() != null) filter.set(AUTHENTPASSWORD, mask.getAuthentPassword());
            if(mask.getPrivacyPassword() != null) filter.set(PRIVACYPASSWORD, mask.getPrivacyPassword());
            if(mask.getSnmpVersion() != null) filter.set(SNMPVERSION, mask.getSnmpVersion());
        }

        try
        {
            return db.count(TABLE, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return an average
     **/
    public static double avg(String field, DBSnmpHost mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getCommunity() != null) filter.set(COMMUNITY, mask.getCommunity());
            if(mask.getUserName() != null) filter.set(USERNAME, mask.getUserName());
            if(mask.getAuthentPassword() != null) filter.set(AUTHENTPASSWORD, mask.getAuthentPassword());
            if(mask.getPrivacyPassword() != null) filter.set(PRIVACYPASSWORD, mask.getPrivacyPassword());
            if(mask.getSnmpVersion() != null) filter.set(SNMPVERSION, mask.getSnmpVersion());
        }

        try
        {
            return db.average(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return a minimum
     **/
    public static double min(String field, DBSnmpHost mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getCommunity() != null) filter.set(COMMUNITY, mask.getCommunity());
            if(mask.getUserName() != null) filter.set(USERNAME, mask.getUserName());
            if(mask.getAuthentPassword() != null) filter.set(AUTHENTPASSWORD, mask.getAuthentPassword());
            if(mask.getPrivacyPassword() != null) filter.set(PRIVACYPASSWORD, mask.getPrivacyPassword());
            if(mask.getSnmpVersion() != null) filter.set(SNMPVERSION, mask.getSnmpVersion());
        }

        try
        {
            return db.min(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return a max
     **/
    public static double max(String field, DBSnmpHost mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getCommunity() != null) filter.set(COMMUNITY, mask.getCommunity());
            if(mask.getUserName() != null) filter.set(USERNAME, mask.getUserName());
            if(mask.getAuthentPassword() != null) filter.set(AUTHENTPASSWORD, mask.getAuthentPassword());
            if(mask.getPrivacyPassword() != null) filter.set(PRIVACYPASSWORD, mask.getPrivacyPassword());
            if(mask.getSnmpVersion() != null) filter.set(SNMPVERSION, mask.getSnmpVersion());
        }

        try
        {
            return db.max(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return a standard deviation (french ecart type)
     **/
    public static double std(String field, DBSnmpHost mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getCommunity() != null) filter.set(COMMUNITY, mask.getCommunity());
            if(mask.getUserName() != null) filter.set(USERNAME, mask.getUserName());
            if(mask.getAuthentPassword() != null) filter.set(AUTHENTPASSWORD, mask.getAuthentPassword());
            if(mask.getPrivacyPassword() != null) filter.set(PRIVACYPASSWORD, mask.getPrivacyPassword());
            if(mask.getSnmpVersion() != null) filter.set(SNMPVERSION, mask.getSnmpVersion());
        }

        try
        {
            return db.std(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return distinct entries)
     **/
    public static String[] distinct(String field, DBSnmpHost mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getCommunity() != null) filter.set(COMMUNITY, mask.getCommunity());
            if(mask.getUserName() != null) filter.set(USERNAME, mask.getUserName());
            if(mask.getAuthentPassword() != null) filter.set(AUTHENTPASSWORD, mask.getAuthentPassword());
            if(mask.getPrivacyPassword() != null) filter.set(PRIVACYPASSWORD, mask.getPrivacyPassword());
            if(mask.getSnmpVersion() != null) filter.set(SNMPVERSION, mask.getSnmpVersion());
        }

        try
        {
            return db.selectDistinct(TABLE, sqlconst, field, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return null;
    }
}
