package openplatform.snmp;

import java.util.Hashtable;
import openplatform.database.dbean.*;
import openplatform.tools.*;
import java.util.*;


/**
 * Class SnmpFactory
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class SnmpFactory extends SnmpAbstract
{
    private static Hashtable snmpHT = new Hashtable();
    
    private DBSnmpHost snmpHost;



    public DBSnmpHost getSnmpHost()
    {
        return snmpHost;
    }

    public void setSnmpHost(DBSnmpHost asnmpHost)
    {
        snmpHost=asnmpHost;
    }

    private SnmpFactory(DBSnmpHost h)
        throws Exception
    {
        super(Integer.parseInt(h.getSnmpVersion()), h.getIpAddress(), h.getCommunity(), h.getUserName(), h.getAuthentPassword(), h.getPrivacyPassword());
        this.snmpHost = h;
    }



    /**
     * Get a snmp Object
     */
    public static SnmpAbstract getSnmp(String hostid)
    {
        if(hostid == null) return null;

        Object obj = snmpHT.get(hostid);
        if(obj != null) return (SnmpAbstract)obj;
        else
        {
            DBSnmpHost hmask = new DBSnmpHost();
            hmask.setSnmpHostId(hostid);

            DBSnmpHost h = DBSnmpHost.loadByKey(hmask);

            if(h == null) return null;

            try
            {
                SnmpFactory f = new SnmpFactory(h);
                snmpHT.put(hostid, f);
                return f;
            }
            catch(Exception e)
            {
                Debug.println(null, Debug.ERROR, e);
                return null;
            }
        }
    }


    /**
     * If the DBSnmpHost changed.
     */
    public static void reloadSnmp(String hostid)
    {
        if(hostid == null) return;
        Object obj = snmpHT.get(hostid);
        
        if(obj == null) return;

        DBSnmpHost hmask = new DBSnmpHost();
        hmask.setSnmpHostId(hostid);
        DBSnmpHost h = DBSnmpHost.loadByKey(hmask);
        
        if(h == null) return;

        try
        {
            SnmpFactory s = (SnmpFactory)obj;
            s.close();
            s.setSnmpHost(h);
            s.initComInterface(Integer.parseInt(h.getSnmpVersion()), h.getIpAddress(), h.getCommunity(), h.getUserName(), h.getAuthentPassword(), h.getPrivacyPassword());
            Debug.println(null, Debug.DEBUG, "Snmp reloaded for host "+h.getDisplayName());
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }


    /**
     * Instanciate at start
     */
    public static void instanciate()
    {
        DBSnmpHost[] h = DBSnmpHost.load(null);
        if(h == null) return;
        int len = h.length;

        for(int i=0; i<len; i++)
        {
            getSnmp(h[i].getSnmpHostId());
        }
    }



    public static void stopProcess()
    {
        Enumeration enum0 = snmpHT.keys();
        while(enum0.hasMoreElements())
        {
            SnmpFactory f = (SnmpFactory)snmpHT.remove(enum0.nextElement());
            f.close();
            f = null;
        }
    }
}
