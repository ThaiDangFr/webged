package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBCmsPreferences
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBCmsPreferences
{
    protected boolean inError = false;
    protected String id;
    protected String css;
    protected String smtpHost;
    protected String ipAddress;
    protected String firstPageId;
    protected String header;
    protected String footer;
    protected String logoId;
    protected String siteTitle;
    protected String debugLog;
    protected String cmsLang;
    protected String ldapAuthent;
    protected String ldapDN;
    protected String ldapServer;

    public static String ID = "id";
    public static String CSS = "css";
    public static String SMTPHOST = "smtpHost";
    public static String IPADDRESS = "ipAddress";
    public static String FIRSTPAGEID = "firstPageId";
    public static String HEADER = "header";
    public static String FOOTER = "footer";
    public static String LOGOID = "logoId";
    public static String SITETITLE = "siteTitle";
    public static String DEBUGLOG = "debugLog";
    public static String CMSLANG = "cmsLang";
    /** en **/
    public static final String CMSLANG_EN = "en";
    /** fr **/
    public static final String CMSLANG_FR = "fr";
    /** Contains the SQL enumeration for CMSLANG **/
    public static final ArrayList _CMSLANG_LIST = new ArrayList();
    static {_CMSLANG_LIST.add(CMSLANG_EN);_CMSLANG_LIST.add(CMSLANG_FR);}
    /** Contains the SQL HASHMAP for CMSLANG **/
    public static final OrdHashMap _CMSLANG_MAP = new OrdHashMap();
    static {_CMSLANG_MAP.put(CMSLANG_EN,CMSLANG_EN);_CMSLANG_MAP.put(CMSLANG_FR,CMSLANG_FR);}
    public static String LDAPAUTHENT = "ldapAuthent";
    public static String LDAPDN = "ldapDN";
    public static String LDAPSERVER = "ldapServer";
    public static String TABLE = "CmsPreferences";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        css = null;
        smtpHost = null;
        ipAddress = null;
        firstPageId = null;
        header = null;
        footer = null;
        logoId = null;
        siteTitle = null;
        debugLog = null;
        cmsLang = null;
        ldapAuthent = null;
        ldapDN = null;
        ldapServer = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBCmsPreferences a = (DBCmsPreferences)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (css==null?a.getCss()==null:css.equals(a.getCss())) && (smtpHost==null?a.getSmtpHost()==null:smtpHost.equals(a.getSmtpHost())) && (ipAddress==null?a.getIpAddress()==null:ipAddress.equals(a.getIpAddress())) && (firstPageId==null?a.getFirstPageId()==null:firstPageId.equals(a.getFirstPageId())) && (header==null?a.getHeader()==null:header.equals(a.getHeader())) && (footer==null?a.getFooter()==null:footer.equals(a.getFooter())) && (logoId==null?a.getLogoId()==null:logoId.equals(a.getLogoId())) && (siteTitle==null?a.getSiteTitle()==null:siteTitle.equals(a.getSiteTitle())) && (debugLog==null?a.getDebugLog()==null:debugLog.equals(a.getDebugLog())) && (cmsLang==null?a.getCmsLang()==null:cmsLang.equals(a.getCmsLang())) && (ldapAuthent==null?a.getLdapAuthent()==null:ldapAuthent.equals(a.getLdapAuthent())) && (ldapDN==null?a.getLdapDN()==null:ldapDN.equals(a.getLdapDN())) && (ldapServer==null?a.getLdapServer()==null:ldapServer.equals(a.getLdapServer()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (css!=null?css.hashCode():0) + (smtpHost!=null?smtpHost.hashCode():0) + (ipAddress!=null?ipAddress.hashCode():0) + (firstPageId!=null?firstPageId.hashCode():0) + (header!=null?header.hashCode():0) + (footer!=null?footer.hashCode():0) + (logoId!=null?logoId.hashCode():0) + (siteTitle!=null?siteTitle.hashCode():0) + (debugLog!=null?debugLog.hashCode():0) + (cmsLang!=null?cmsLang.hashCode():0) + (ldapAuthent!=null?ldapAuthent.hashCode():0) + (ldapDN!=null?ldapDN.hashCode():0) + (ldapServer!=null?ldapServer.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"css="+css+"|"+"smtpHost="+smtpHost+"|"+"ipAddress="+ipAddress+"|"+"firstPageId="+firstPageId+"|"+"header="+header+"|"+"footer="+footer+"|"+"logoId="+logoId+"|"+"siteTitle="+siteTitle+"|"+"debugLog="+debugLog+"|"+"cmsLang="+cmsLang+"|"+"ldapAuthent="+ldapAuthent+"|"+"ldapDN="+ldapDN+"|"+"ldapServer="+ldapServer;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBCmsPreferences mask = new DBCmsPreferences();
            mask.setId(id);
            DBCmsPreferences var = DBCmsPreferences.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                css = var.getCss();
                smtpHost = var.getSmtpHost();
                ipAddress = var.getIpAddress();
                firstPageId = var.getFirstPageId();
                header = var.getHeader();
                footer = var.getFooter();
                logoId = var.getLogoId();
                siteTitle = var.getSiteTitle();
                debugLog = var.getDebugLog();
                cmsLang = var.getCmsLang();
                ldapAuthent = var.getLdapAuthent();
                ldapDN = var.getLdapDN();
                ldapServer = var.getLdapServer();
            }
        }
    }

    public void initBean(DBCmsPreferences db)
    {
        this.id = db.getId();
        this.css = db.getCss();
        this.smtpHost = db.getSmtpHost();
        this.ipAddress = db.getIpAddress();
        this.firstPageId = db.getFirstPageId();
        this.header = db.getHeader();
        this.footer = db.getFooter();
        this.logoId = db.getLogoId();
        this.siteTitle = db.getSiteTitle();
        this.debugLog = db.getDebugLog();
        this.cmsLang = db.getCmsLang();
        this.ldapAuthent = db.getLdapAuthent();
        this.ldapDN = db.getLdapDN();
        this.ldapServer = db.getLdapServer();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCss()
    {
        return css;
    }

    public void setCss(String css)
    {
        this.css = css;
    }

    public String getSmtpHost()
    {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost)
    {
        this.smtpHost = smtpHost;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getFirstPageId()
    {
        return firstPageId;
    }

    public void setFirstPageId(String firstPageId)
    {
        this.firstPageId = firstPageId;
    }

    public String getHeader()
    {
        return header;
    }

    public void setHeader(String header)
    {
        this.header = header;
    }

    public String getFooter()
    {
        return footer;
    }

    public void setFooter(String footer)
    {
        this.footer = footer;
    }

    public String getLogoId()
    {
        return logoId;
    }

    public void setLogoId(String logoId)
    {
        this.logoId = logoId;
    }

    public String getSiteTitle()
    {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle)
    {
        this.siteTitle = siteTitle;
    }

    public String getDebugLog()
    {
        return debugLog;
    }

    public void setDebugLog(String debugLog)
    {
        this.debugLog = debugLog;
    }

    public String getCmsLang()
    {
        return cmsLang;
    }

    public void setCmsLang(String cmsLang)
    {
        this.cmsLang = cmsLang;
    }

    public String getLdapAuthent()
    {
        return ldapAuthent;
    }

    public void setLdapAuthent(String ldapAuthent)
    {
        this.ldapAuthent = ldapAuthent;
    }

    public String getLdapDN()
    {
        return ldapDN;
    }

    public void setLdapDN(String ldapDN)
    {
        this.ldapDN = ldapDN;
    }

    public String getLdapServer()
    {
        return ldapServer;
    }

    public void setLdapServer(String ldapServer)
    {
        this.ldapServer = ldapServer;
    }

    /**
     * @return a DBCmsPreferences table or null if nothing found or if an error occured
     **/
    public static DBCmsPreferences[] load(DBCmsPreferences mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBCmsPreferences table or null if nothing found or if an error occured
     **/
    public static DBCmsPreferences[] load(SQLConstraint sqlconstraint, DBCmsPreferences mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getSmtpHost() != null) filter.set(SMTPHOST, mask.getSmtpHost());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getFirstPageId() != null) filter.set(FIRSTPAGEID, mask.getFirstPageId());
            if(mask.getHeader() != null) filter.set(HEADER, mask.getHeader());
            if(mask.getFooter() != null) filter.set(FOOTER, mask.getFooter());
            if(mask.getLogoId() != null) filter.set(LOGOID, mask.getLogoId());
            if(mask.getSiteTitle() != null) filter.set(SITETITLE, mask.getSiteTitle());
            if(mask.getDebugLog() != null) filter.set(DEBUGLOG, mask.getDebugLog());
            if(mask.getCmsLang() != null) filter.set(CMSLANG, mask.getCmsLang());
            if(mask.getLdapAuthent() != null) filter.set(LDAPAUTHENT, mask.getLdapAuthent());
            if(mask.getLdapDN() != null) filter.set(LDAPDN, mask.getLdapDN());
            if(mask.getLdapServer() != null) filter.set(LDAPSERVER, mask.getLdapServer());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, CSS, SMTPHOST, IPADDRESS, FIRSTPAGEID, HEADER, FOOTER, LOGOID, SITETITLE, DEBUGLOG, CMSLANG, LDAPAUTHENT, LDAPDN, LDAPSERVER}, filter);
            if(pvp == null) return null;
            else
            {
                DBCmsPreferences[] result = new DBCmsPreferences[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBCmsPreferences();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setCss(pvp[i].get(CSS));
                    result[i].setSmtpHost(pvp[i].get(SMTPHOST));
                    result[i].setIpAddress(pvp[i].get(IPADDRESS));
                    result[i].setFirstPageId(pvp[i].get(FIRSTPAGEID));
                    result[i].setHeader(pvp[i].get(HEADER));
                    result[i].setFooter(pvp[i].get(FOOTER));
                    result[i].setLogoId(pvp[i].get(LOGOID));
                    result[i].setSiteTitle(pvp[i].get(SITETITLE));
                    result[i].setDebugLog(pvp[i].get(DEBUGLOG));
                    result[i].setCmsLang(pvp[i].get(CMSLANG));
                    result[i].setLdapAuthent(pvp[i].get(LDAPAUTHENT));
                    result[i].setLdapDN(pvp[i].get(LDAPDN));
                    result[i].setLdapServer(pvp[i].get(LDAPSERVER));
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
     * @return a DBCmsPreferences object or null if nothing found or if an error occured
     **/
    public static DBCmsPreferences loadByKey(DBCmsPreferences mask)
    {
        DBCmsPreferences[] res = DBCmsPreferences.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBCmsPreferences object or null if nothing found or if an error occured
     **/
    public static DBCmsPreferences loadByKey(DBCmsPreferences mask, SQLConstraint sqlconstraint)
    {
        DBCmsPreferences[] res = DBCmsPreferences.load(sqlconstraint, mask);
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
        toinsert.set(ID,id);
        toinsert.set(CSS,css);
        toinsert.set(SMTPHOST,smtpHost);
        toinsert.set(IPADDRESS,ipAddress);
        toinsert.set(FIRSTPAGEID,firstPageId);
        toinsert.set(HEADER,header);
        toinsert.set(FOOTER,footer);
        toinsert.set(LOGOID,logoId);
        toinsert.set(SITETITLE,siteTitle);
        toinsert.set(DEBUGLOG,debugLog);
        toinsert.set(CMSLANG,cmsLang);
        toinsert.set(LDAPAUTHENT,ldapAuthent);
        toinsert.set(LDAPDN,ldapDN);
        toinsert.set(LDAPSERVER,ldapServer);

        // Store a new entry
        if(id == null)
        {
            try
            {
                id = db.insert(TABLE, toinsert);
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
            filter.set(ID, id);

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
        if(id != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(ID, id);

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
    public static void delete(DBCmsPreferences mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getSmtpHost() != null) filter.set(SMTPHOST, mask.getSmtpHost());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getFirstPageId() != null) filter.set(FIRSTPAGEID, mask.getFirstPageId());
            if(mask.getHeader() != null) filter.set(HEADER, mask.getHeader());
            if(mask.getFooter() != null) filter.set(FOOTER, mask.getFooter());
            if(mask.getLogoId() != null) filter.set(LOGOID, mask.getLogoId());
            if(mask.getSiteTitle() != null) filter.set(SITETITLE, mask.getSiteTitle());
            if(mask.getDebugLog() != null) filter.set(DEBUGLOG, mask.getDebugLog());
            if(mask.getCmsLang() != null) filter.set(CMSLANG, mask.getCmsLang());
            if(mask.getLdapAuthent() != null) filter.set(LDAPAUTHENT, mask.getLdapAuthent());
            if(mask.getLdapDN() != null) filter.set(LDAPDN, mask.getLdapDN());
            if(mask.getLdapServer() != null) filter.set(LDAPSERVER, mask.getLdapServer());
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
    public static int count(DBCmsPreferences mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getSmtpHost() != null) filter.set(SMTPHOST, mask.getSmtpHost());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getFirstPageId() != null) filter.set(FIRSTPAGEID, mask.getFirstPageId());
            if(mask.getHeader() != null) filter.set(HEADER, mask.getHeader());
            if(mask.getFooter() != null) filter.set(FOOTER, mask.getFooter());
            if(mask.getLogoId() != null) filter.set(LOGOID, mask.getLogoId());
            if(mask.getSiteTitle() != null) filter.set(SITETITLE, mask.getSiteTitle());
            if(mask.getDebugLog() != null) filter.set(DEBUGLOG, mask.getDebugLog());
            if(mask.getCmsLang() != null) filter.set(CMSLANG, mask.getCmsLang());
            if(mask.getLdapAuthent() != null) filter.set(LDAPAUTHENT, mask.getLdapAuthent());
            if(mask.getLdapDN() != null) filter.set(LDAPDN, mask.getLdapDN());
            if(mask.getLdapServer() != null) filter.set(LDAPSERVER, mask.getLdapServer());
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
    public static double avg(String field, DBCmsPreferences mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getSmtpHost() != null) filter.set(SMTPHOST, mask.getSmtpHost());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getFirstPageId() != null) filter.set(FIRSTPAGEID, mask.getFirstPageId());
            if(mask.getHeader() != null) filter.set(HEADER, mask.getHeader());
            if(mask.getFooter() != null) filter.set(FOOTER, mask.getFooter());
            if(mask.getLogoId() != null) filter.set(LOGOID, mask.getLogoId());
            if(mask.getSiteTitle() != null) filter.set(SITETITLE, mask.getSiteTitle());
            if(mask.getDebugLog() != null) filter.set(DEBUGLOG, mask.getDebugLog());
            if(mask.getCmsLang() != null) filter.set(CMSLANG, mask.getCmsLang());
            if(mask.getLdapAuthent() != null) filter.set(LDAPAUTHENT, mask.getLdapAuthent());
            if(mask.getLdapDN() != null) filter.set(LDAPDN, mask.getLdapDN());
            if(mask.getLdapServer() != null) filter.set(LDAPSERVER, mask.getLdapServer());
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
    public static double min(String field, DBCmsPreferences mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getSmtpHost() != null) filter.set(SMTPHOST, mask.getSmtpHost());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getFirstPageId() != null) filter.set(FIRSTPAGEID, mask.getFirstPageId());
            if(mask.getHeader() != null) filter.set(HEADER, mask.getHeader());
            if(mask.getFooter() != null) filter.set(FOOTER, mask.getFooter());
            if(mask.getLogoId() != null) filter.set(LOGOID, mask.getLogoId());
            if(mask.getSiteTitle() != null) filter.set(SITETITLE, mask.getSiteTitle());
            if(mask.getDebugLog() != null) filter.set(DEBUGLOG, mask.getDebugLog());
            if(mask.getCmsLang() != null) filter.set(CMSLANG, mask.getCmsLang());
            if(mask.getLdapAuthent() != null) filter.set(LDAPAUTHENT, mask.getLdapAuthent());
            if(mask.getLdapDN() != null) filter.set(LDAPDN, mask.getLdapDN());
            if(mask.getLdapServer() != null) filter.set(LDAPSERVER, mask.getLdapServer());
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
    public static double max(String field, DBCmsPreferences mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getSmtpHost() != null) filter.set(SMTPHOST, mask.getSmtpHost());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getFirstPageId() != null) filter.set(FIRSTPAGEID, mask.getFirstPageId());
            if(mask.getHeader() != null) filter.set(HEADER, mask.getHeader());
            if(mask.getFooter() != null) filter.set(FOOTER, mask.getFooter());
            if(mask.getLogoId() != null) filter.set(LOGOID, mask.getLogoId());
            if(mask.getSiteTitle() != null) filter.set(SITETITLE, mask.getSiteTitle());
            if(mask.getDebugLog() != null) filter.set(DEBUGLOG, mask.getDebugLog());
            if(mask.getCmsLang() != null) filter.set(CMSLANG, mask.getCmsLang());
            if(mask.getLdapAuthent() != null) filter.set(LDAPAUTHENT, mask.getLdapAuthent());
            if(mask.getLdapDN() != null) filter.set(LDAPDN, mask.getLdapDN());
            if(mask.getLdapServer() != null) filter.set(LDAPSERVER, mask.getLdapServer());
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
    public static double std(String field, DBCmsPreferences mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getSmtpHost() != null) filter.set(SMTPHOST, mask.getSmtpHost());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getFirstPageId() != null) filter.set(FIRSTPAGEID, mask.getFirstPageId());
            if(mask.getHeader() != null) filter.set(HEADER, mask.getHeader());
            if(mask.getFooter() != null) filter.set(FOOTER, mask.getFooter());
            if(mask.getLogoId() != null) filter.set(LOGOID, mask.getLogoId());
            if(mask.getSiteTitle() != null) filter.set(SITETITLE, mask.getSiteTitle());
            if(mask.getDebugLog() != null) filter.set(DEBUGLOG, mask.getDebugLog());
            if(mask.getCmsLang() != null) filter.set(CMSLANG, mask.getCmsLang());
            if(mask.getLdapAuthent() != null) filter.set(LDAPAUTHENT, mask.getLdapAuthent());
            if(mask.getLdapDN() != null) filter.set(LDAPDN, mask.getLdapDN());
            if(mask.getLdapServer() != null) filter.set(LDAPSERVER, mask.getLdapServer());
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
    public static String[] distinct(String field, DBCmsPreferences mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getSmtpHost() != null) filter.set(SMTPHOST, mask.getSmtpHost());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
            if(mask.getFirstPageId() != null) filter.set(FIRSTPAGEID, mask.getFirstPageId());
            if(mask.getHeader() != null) filter.set(HEADER, mask.getHeader());
            if(mask.getFooter() != null) filter.set(FOOTER, mask.getFooter());
            if(mask.getLogoId() != null) filter.set(LOGOID, mask.getLogoId());
            if(mask.getSiteTitle() != null) filter.set(SITETITLE, mask.getSiteTitle());
            if(mask.getDebugLog() != null) filter.set(DEBUGLOG, mask.getDebugLog());
            if(mask.getCmsLang() != null) filter.set(CMSLANG, mask.getCmsLang());
            if(mask.getLdapAuthent() != null) filter.set(LDAPAUTHENT, mask.getLdapAuthent());
            if(mask.getLdapDN() != null) filter.set(LDAPDN, mask.getLdapDN());
            if(mask.getLdapServer() != null) filter.set(LDAPSERVER, mask.getLdapServer());
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
