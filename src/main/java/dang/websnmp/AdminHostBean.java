/*
 * AdminHostBean.java
 *
 * Created on 6 juillet 2004, 18:33
 */

package dang.websnmp;
import openplatform.database.dbean.DBSnmpHost;
import openplatform.snmp.Snmp;
import openplatform.tools.DNS;
import openplatform.tools.Debug;
import openplatform.tools.OrdHashMap;
import dang.cms.CmsLanguage;


/**
 * Class AdminHostBean
 *
 * @author Minh DANG<BR/>
 *
 * 30/7/04 : Modified by Thai
 * 4/8/04 : JSTL adaptation
 * 
 * Source code is copyright
 **/
public class AdminHostBean
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private DBSnmpHost hmask = new DBSnmpHost();

    private String displayName;
    private String ipAddress;
    private String community;
    private String snmpVersion;
    private String snmpHostId;

    private String userName;
    private String authentPw;
    private String privacyPw;

    private String[] hostToDelete;



    private static OrdHashMap snmpOptions = new OrdHashMap();

    static
    {
        // Display string - value
        snmpOptions.put(Integer.toString(Snmp.SNMP_V1),"SNMP V1");
        snmpOptions.put(Integer.toString(Snmp.SNMP_V2),"SNMP V2");
        snmpOptions.put(Integer.toString(Snmp.SNMP_V3),"SNMP V3");
    }

    // DO BEG


    public String doDeleteHost()
        throws Exception
    {
        if(hostToDelete == null)
        {
            throw new Exception(cmsLang.translate("no.item.selected"));
        }
        

        int len = hostToDelete.length;
        for(int i=0; i<len; i++)
        {
            hmask.clear();
            hmask.setSnmpHostId(hostToDelete[i]);
        
            DBSnmpHost d = DBSnmpHost.loadByKey(hmask);
            if(d != null) d.delete();
        }

        return null;
    }

    public String doAddHost()
        throws Exception
    {
        if(displayName==null || ipAddress==null  || snmpVersion==null
           || displayName.trim().equals(""))
        {
            throw new Exception(cmsLang.translate("miss.parameter"));
        }

        String ip = ipConvert(ipAddress);

        if(ip == null)
        {
            throw new Exception(cmsLang.translate("bad.value")+" IP");
        }

        prepareSave();

        DBSnmpHost dbs = new DBSnmpHost();
        dbs.setDisplayName(displayName);
        dbs.setIpAddress(ip);
        dbs.setCommunity(community);
        dbs.setSnmpVersion(snmpVersion);
        dbs.setUserName(userName);
        dbs.setAuthentPassword(authentPw);
        dbs.setPrivacyPassword(privacyPw);
        dbs.store();

        if(dbs.isInError())
        {
            throw new Exception(cmsLang.translate("fail.save"));
        }
        else
        {
            return(cmsLang.translate("item.added"));
        }
    }

    public String doUpdateHost()
        throws Exception
    {
        Debug.println(this, Debug.DEBUG, "update host snmpHostId="+snmpHostId);


        if(displayName==null || ipAddress==null  || snmpVersion==null
           || displayName.trim().equals("") || snmpHostId==null)
        {
            throw new Exception(cmsLang.translate("miss.parameter"));
        }

        hmask.clear();
        hmask.setSnmpHostId(snmpHostId);
        DBSnmpHost dbs = DBSnmpHost.loadByKey(hmask);

        String oriDisplayName = dbs.getDisplayName();

        if(dbs!=null)
        {
            String ip = ipConvert(ipAddress);

            if(ip == null)
            {
                throw new Exception(cmsLang.translate("bad.value")+" IP");
            }

            prepareSave();

            dbs.setDisplayName(displayName);
            dbs.setIpAddress(ip);
            dbs.setCommunity(community);
            dbs.setSnmpVersion(snmpVersion);
            dbs.setUserName(userName);
            dbs.setAuthentPassword(authentPw);
            dbs.setPrivacyPassword(privacyPw);
            dbs.store();     

            if(dbs.isInError())
            {
                throw new Exception(cmsLang.translate("fail.save"));
            }
            else
            {
                return(cmsLang.translate("item.updated"));
            }
        }
        else
        {
            throw new Exception(cmsLang.translate("fail.save"));
        }
    }

    public String doClear()
    {
        displayName = null;
        ipAddress = null;
        community = null;
        snmpVersion = null;
        snmpHostId = null;
        userName = null;
        authentPw = null;
        privacyPw = null;

        hostToDelete = null;

        return null;
    }


    // DO END




    public void setSnmpHostId(String asnmpHostId)
    {
        snmpHostId=asnmpHostId;

        if(snmpHostId!=null)
        {
            hmask.clear();
            hmask.setSnmpHostId(snmpHostId);
            DBSnmpHost h = DBSnmpHost.loadByKey(hmask);

            displayName = h.getDisplayName();
            ipAddress = h.getIpAddress();
            community = h.getCommunity();
            snmpVersion = h.getSnmpVersion();
            snmpHostId = h.getSnmpHostId();
            userName = h.getUserName();
            authentPw = h.getAuthentPassword();
            privacyPw = h.getPrivacyPassword();
        }
    }

    public boolean getSnmpVOneTwo()
    {
        if(snmpVersion == null) return true;

        int ver = Integer.parseInt(snmpVersion);
        
        if((ver == Snmp.SNMP_V1) || (ver == Snmp.SNMP_V2)) return true;
        else return false;
    }


    public boolean getSnmpVThree()
    {
        if(snmpVersion == null) return false;

        int ver = Integer.parseInt(snmpVersion);

        if(ver == Snmp.SNMP_V3) return true;
        else return false;
    }

    
    public DBSnmpHost[] getAllHost()
    {
        return DBSnmpHost.load(null);
    }


    private void prepareSave()
    {
        if(snmpVersion == null) return;

        int ver = Integer.parseInt(snmpVersion);
        if((ver == Snmp.SNMP_V1) || (ver == Snmp.SNMP_V2))
        {
            userName = null;
            authentPw = null;
            privacyPw = null;
        }
        else if(ver == Snmp.SNMP_V3)
        {
            community = null;
        }
    }



    /**
     * Accept IPAddress
     */
    private String ipConvert(String hostname)
    {
        if(hostname==null) return null;

        return DNS.getIPAddress(hostname);
    }



    // GET / SET


    /**
     * Gets the value of hmask
     *
     * @return the value of hmask
     */
    public final DBSnmpHost getHmask()
    {
        return this.hmask;
    }

    /**
     * Sets the value of hmask
     *
     * @param argHmask Value to assign to this.hmask
     */
    public final void setHmask(final DBSnmpHost argHmask)
    {
        this.hmask = argHmask;
    }

    /**
     * Gets the value of displayName
     *
     * @return the value of displayName
     */
    public final String getDisplayName()
    {
        return this.displayName;
    }

    /**
     * Sets the value of displayName
     *
     * @param argDisplayName Value to assign to this.displayName
     */
    public final void setDisplayName(final String argDisplayName)
    {
        this.displayName = argDisplayName;
    }

    /**
     * Gets the value of ipAddress
     *
     * @return the value of ipAddress
     */
    public final String getIpAddress()
    {
        return this.ipAddress;
    }

    /**
     * Sets the value of ipAddress
     *
     * @param argIpAddress Value to assign to this.ipAddress
     */
    public final void setIpAddress(final String argIpAddress)
    {
        this.ipAddress = argIpAddress;
    }

    /**
     * Gets the value of community
     *
     * @return the value of community
     */
    public final String getCommunity()
    {
        return this.community;
    }

    /**
     * Sets the value of community
     *
     * @param argCommunity Value to assign to this.community
     */
    public final void setCommunity(final String argCommunity)
    {
        this.community = argCommunity;
    }

    /**
     * Gets the value of snmpVersion
     *
     * @return the value of snmpVersion
     */
    public final String getSnmpVersion()
    {
        return this.snmpVersion;
    }

    /**
     * Sets the value of snmpVersion
     *
     * @param argSnmpVersion Value to assign to this.snmpVersion
     */
    public final void setSnmpVersion(final String argSnmpVersion)
    {
        this.snmpVersion = argSnmpVersion;
    }

    /**
     * Gets the value of snmpHostId
     *
     * @return the value of snmpHostId
     */
    public final String getSnmpHostId()
    {
        return this.snmpHostId;
    }

    /**
     * Gets the value of userName
     *
     * @return the value of userName
     */
    public final String getUserName()
    {
        return this.userName;
    }

    /**
     * Sets the value of userName
     *
     * @param argUserName Value to assign to this.userName
     */
    public final void setUserName(final String argUserName)
    {
        this.userName = argUserName;
    }

    /**
     * Gets the value of authentPw
     *
     * @return the value of authentPw
     */
    public final String getAuthentPw()
    {
        return this.authentPw;
    }

    /**
     * Sets the value of authentPw
     *
     * @param argAuthentPw Value to assign to this.authentPw
     */
    public final void setAuthentPw(final String argAuthentPw)
    {
        this.authentPw = argAuthentPw;
    }

    /**
     * Gets the value of privacyPw
     *
     * @return the value of privacyPw
     */
    public final String getPrivacyPw()
    {
        return this.privacyPw;
    }

    /**
     * Sets the value of privacyPw
     *
     * @param argPrivacyPw Value to assign to this.privacyPw
     */
    public final void setPrivacyPw(final String argPrivacyPw)
    {
        this.privacyPw = argPrivacyPw;
    }

    /**
     * Gets the value of hostToDelete
     *
     * @return the value of hostToDelete
     */
    public final String[] getHostToDelete()
    {
        return this.hostToDelete;
    }

    /**
     * Sets the value of hostToDelete
     *
     * @param argHostToDelete Value to assign to this.hostToDelete
     */
    public final void setHostToDelete(final String[] argHostToDelete)
    {
        this.hostToDelete = argHostToDelete;
    }

    /**
     * Gets the value of snmpOptions
     *
     * @return the value of snmpOptions
     */
    public final OrdHashMap getSnmpOptions()
    {
        return AdminHostBean.snmpOptions;
    }

    /**
     * Sets the value of snmpOptions
     *
     * @param argSnmpOptions Value to assign to this.snmpOptions
     */
    public final void setSnmpOptions(final OrdHashMap argSnmpOptions)
    {
        AdminHostBean.snmpOptions = argSnmpOptions;
    }

 

}

