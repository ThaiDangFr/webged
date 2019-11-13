package dang.websnmp;

import openplatform.database.dbean.*;
import java.util.*;
import openplatform.tools.*;
import openplatform.snmp.*;
import openplatform.database.*;

/**
 * SnmpAction.java
 *
 *
 * Created: Wed Jun 15 14:30:55 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class SnmpAction extends DBSnmpAction
{
    private DBSnmpHost hmask = new DBSnmpHost();
    private DBSnmpOID oidmask = new DBSnmpOID();

    protected String mibName;
    protected String oidString;


    public SnmpAction()
    {
        super();
    }
    
    public SnmpAction(DBSnmpAction action)
    {
        initBean(action);

        if(this.oid != null)
        {
            oidmask.clear();
            oidmask.setId(this.oid);
            DBSnmpOID oid = DBSnmpOID.loadByKey(oidmask);
            mibName = oid.getMibName(); 

            oidmask.clear();
            oidmask.setId(this.oid);
            oid = DBSnmpOID.loadByKey(oidmask);
            oidString = oid.getCaptorName();
        }
    }

    public String getHostName()
    {
        if(this.hostId == null) return null;

        hmask.clear();
        hmask.setSnmpHostId(this.hostId);
        DBSnmpHost h = DBSnmpHost.loadByKey(hmask);
        return h.getDisplayName();
    }

    
    public HashMap getHostOptions()
    {
        HashMap map = new OrdHashMap();
        DBSnmpHost[] h = DBSnmpHost.load(null);
        if(h == null) return map;

        int len = h.length;
        for(int i=0; i<len; i++)
            map.put(h[i].getSnmpHostId(), h[i].getDisplayName());

        return map;
    }


    public HashMap getTypeOptions()
    {
        return (HashMap)DBSnmpAction._TYPE_MAP;
    }

   
    public HashMap getMibNameOptions()
    {
        HashMap map = new OrdHashMap();
        String[] allmibs = SnmpJSPBeanBase.listMib();
        if(allmibs == null) return map;

        int len = allmibs.length;
        
        for(int i=0; i<len; i++)
            map.put(allmibs[i], allmibs[i]);

        return map;
    }
    
    /**
     * oid id / oidstring
     */
    public HashMap getOidStringOptions()
    {
        HashMap map = new OrdHashMap();
        
        if(this.mibName == null) return map;

        oidmask.clear();
        oidmask.setMibName(this.mibName);
        DBSnmpOID[] oids = DBSnmpOID.load(oidmask);

        if(oids == null) return map;

        int len = oids.length;

        for(int i=0; i<len; i++)
        {
            map.put(oids[i].getId(), oids[i].getCaptorName());
        }

        return map;
    }
   

    public boolean isGetType()
    {
        if(DBSnmpAction.TYPE_GET.equals(this.type)) return true;
        else return false;
    }

    
    public boolean isSetType()
    {
        if(DBSnmpAction.TYPE_SET.equals(this.type)) return true;
        else return false;
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
     * Gets the value of oidmask
     *
     * @return the value of oidmask
     */
    public final DBSnmpOID getOidmask()
    {
        return this.oidmask;
    }

    /**
     * Sets the value of oidmask
     *
     * @param argOidmask Value to assign to this.oidmask
     */
    public final void setOidmask(final DBSnmpOID argOidmask)
    {
        this.oidmask = argOidmask;
    }

    /**
     * Gets the value of mibName
     *
     * @return the value of mibName
     */
    public final String getMibName()
    {
        return this.mibName;
    }

    /**
     * Sets the value of mibName
     *
     * @param argMibName Value to assign to this.mibName
     */
    public final void setMibName(final String argMibName)
    {
        this.mibName = argMibName;
    }

    /**
     * Gets the value of oidString
     *
     * @return the value of oidString
     */
    public final String getOidString()
    {
        return this.oidString;
    }

    /**
     * Sets the value of oidString
     *
     * @param argOidString Value to assign to this.oidString
     */
    public final void setOidString(final String argOidString)
    {
        this.oidString = argOidString;
    }

}
