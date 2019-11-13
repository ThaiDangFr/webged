package openplatform.snmp;

import openplatform.database.dbean.*;

/**
 * SnmpUtils.java
 *
 *
 * Created: Wed Jun 22 16:04:53 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class SnmpUtils 
{
    /*
     * Parse Rfc1213Mib_IFOUTOCTETS
     */
    public static DBSnmpOID parseSimpleFormula(String s)
    {
        int idx = s.indexOf("_");
        if(idx == -1) return null;

        String mibName = s.substring(0,idx);
        String oidString = s.substring(idx+1);

        DBSnmpOID omask = new DBSnmpOID();
        omask.setMibName(mibName);
        omask.setCaptorName(oidString);
        DBSnmpOID o = DBSnmpOID.loadByKey(omask);

        return o;
    }
}
