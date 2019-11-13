package dang.websnmp;

import openplatform.database.dbean.*;
import openplatform.pushlet.*;
import openplatform.database.*;
import openplatform.tools.*;
import openplatform.snmp.*;
import java.util.*;


/**
 * Class AdminMibTestBean
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminMibTestBean
{
    private String mibName;
    private String oidName;
    private String hostId;

    private DBSnmpOID oidmask = new DBSnmpOID();
    private SQLConstraint constr = new SQLConstraint();

    private StringArrayList response = new StringArrayList();


    public AdminMibTestBean()
    {
    }


    public String getHostId()
    {
        return hostId;
    }

    public void setHostId(String ahostId)
    {
        hostId=ahostId;
    }

    public String getOidName()
    {
        return oidName;
    }

    public void setOidName(String aoidName)
    {
        oidName=aoidName;
    }

    public String getMibName()
    {
        return mibName;
    }

    public void setMibName(String amibName)
    {
        if(amibName == null)
        {
            constr.clear();
            constr.setLimit(1);
            constr.setOrderBy(DBSnmpOID.MIBNAME);
            String[] m = DBSnmpOID.distinct(DBSnmpOID.MIBNAME, null, constr);
            if(m!=null && m.length>0) mibName=m[0];
            Debug.println(this, Debug.DEBUG, "mibName="+mibName); 
        }
        else
            mibName=amibName;
    }    


    public HashMap getOidOptions()
    {
        OrdHashMap map = new OrdHashMap();
        
        if(mibName == null) return map;

        String[] st = SnmpJSPBeanBase.listSensorName(mibName);

        if(st == null) return map;

        int len = st.length;

        for(int i=0; i<len; i++)
        {
            map.put(st[i], st[i]);
        }

        return map;        
    }


    public HashMap getMibOptions()
    {
        OrdHashMap map = new OrdHashMap();
        String[] st = SnmpJSPBeanBase.listMib();
        if(st == null) return map;

        int len = st.length;

        for(int i=0; i<len; i++)
        {
            map.put(st[i], st[i]);
        }

        return map;
    }


    public HashMap getHostOptions()
    {
        OrdHashMap map = new OrdHashMap();
        DBSnmpHost[] h = DBSnmpHost.load(null);
        
        if(h!=null)
        {
            int len = h.length;

            for(int i=0; i<len; i++)
            {
                String name = h[i].getDisplayName();
                String value = h[i].getSnmpHostId();

                if(name != null) map.put(name, value);
            }
        }

        return map;
    }


//     public String getRun()
    public StringArrayList getRun()
    {
        if(hostId == null || mibName == null || oidName == null)
            return null;

        SnmpFactory.reloadSnmp(hostId);
        SnmpAbstract snmp = SnmpFactory.getSnmp(hostId);
        
        oidmask.clear();
        oidmask.setMibName(mibName);
        oidmask.setCaptorName(oidName);

        DBSnmpOID dboid = DBSnmpOID.loadByKey(oidmask);
        
        SnmpNodeList nl = snmp.get(dboid.getOid());
        
        if(nl == null || nl.size() == 0) return null;

        response.clear();
        int len = nl.size();
        
        for(int i=0; i<len; i++)
        {
            SnmpNode n = nl.getSnmpNode(i);
            
            response.addString(n.getOid() + " : " + n.getValue());
        }

        Debug.println(this, Debug.DEBUG, dboid.getOid()+" => "+response);

        return response;
    }
}
