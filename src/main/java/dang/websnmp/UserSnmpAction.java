package dang.websnmp;

import openplatform.database.dbean.*;
import openplatform.snmp.*;
import openplatform.tools.*;
import java.util.*;

import dang.cms.CmsLanguage;

/**
 * UserSnmpAction.java
 *
 *
 * Created: Fri Jun 17 15:43:19 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class UserSnmpAction 
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private String id;
    private String value;
    private String hostId;
    private StringArrayList result = new StringArrayList();


    public String doAction()
        throws Exception
    {
        if(this.id == null)
        	throw new Exception(cmsLang.translate("no.item.selected"));

        result.clear();

        DBSnmpAction amask = new DBSnmpAction();
        amask.setId(this.id);
        DBSnmpAction a = DBSnmpAction.loadByKey(amask);

        String hostId = a.getHostId();
        SnmpFactory.reloadSnmp(hostId);
        SnmpAbstract snmp = SnmpFactory.getSnmp(hostId);

        DBSnmpOID oidmask = new DBSnmpOID();
        oidmask.setId(a.getOid());
        DBSnmpOID oid = DBSnmpOID.loadByKey(oidmask);
        

        if(DBSnmpAction.TYPE_SET.equals(a.getType()))
        {
            try
            {
                SnmpNodeList nl = snmp.get(oid.getOid());
                int len = nl.size();

                for(int i=0; i<len; i++)
                {
                    SnmpNode n = nl.getSnmpNode(i);
                    if(SnmpNode.TYPE_STRING == n.getType())
                        snmp.set(n.getOid(), value);
                    else if(SnmpNode.TYPE_INTEGER == n.getType())
                        snmp.set(n.getOid(), Integer.parseInt(value));
                    else
                        snmp.set(n.getOid(), Long.parseLong(value));
                        
                }
            }
            catch(Exception e)
            {
                throw new Exception(cmsLang.translate("impossible"));
            }
        }

        SnmpNodeList nl = snmp.get(oid.getOid());
            
        int len = nl.size();
        result.clear();
        for(int i=0; i<len; i++)
        {
            SnmpNode n = nl.getSnmpNode(i);
            result.addString(n.getValue());
        }


        return null;
    }


    /**
     * @return arraylist of DBSnmpHost
     */
    public ArrayList getHostList()
    {
        ArrayList array = new ArrayList();

        DBSnmpHost[] h = DBSnmpHost.load(null);
        if(h == null) return array;

        DBSnmpAction amask = new DBSnmpAction();
        int len = h.length;
        for(int i=0; i<len; i++)
        {
            amask.setHostId(h[i].getSnmpHostId());
            if(DBSnmpAction.count(amask, null) != 0)
                array.add(h[i]);
        }

        return array;
    }


    public ArrayList getSnmpActionList()
    {
        ArrayList array = new ArrayList();

        if(this.hostId == null) return array;

        DBSnmpAction amask = new DBSnmpAction();
        amask.setHostId(this.hostId);

        DBSnmpAction[] a = DBSnmpAction.load(amask);
        
        if(a == null) return array;

        int len = a.length;
        for(int i=0; i<len; i++)
        {
            array.add(new SnmpAction(a[i]));
        }

        return array;
    }

    // GET / SET

    /**
     * Gets the value of id
     *
     * @return the value of id
     */
    public final String getId()
    {
        return this.id;
    }

    /**
     * Sets the value of id
     *
     * @param argId Value to assign to this.id
     */
    public final void setId(final String argId)
    {
        this.id = argId;
    }

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public final String getValue()
    {
        return this.value;
    }

    /**
     * Sets the value of value
     *
     * @param argValue Value to assign to this.value
     */
    public final void setValue(final String argValue)
    {
        this.value = argValue;
    }

    /**
     * Gets the value of hostId
     *
     * @return the value of hostId
     */
    public final String getHostId()
    {
        return this.hostId;
    }

    /**
     * Sets the value of hostId
     *
     * @param argHostId Value to assign to this.hostId
     */
    public final void setHostId(final String argHostId)
    {
        this.hostId = argHostId;
    }

    /**
     * Gets the value of result
     *
     * @return the value of result
     */
    public final StringArrayList getResult()
    {
        return this.result;
    }

    /**
     * Sets the value of result
     *
     * @param argResult Value to assign to this.result
     */
    public final void setResult(final StringArrayList argResult)
    {
        this.result = argResult;
    }

}
