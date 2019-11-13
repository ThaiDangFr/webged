package openplatform.snmp;

import openplatform.database.*;
import openplatform.database.dbean.*;

public class SnmpJSPBeanBase
{
    private static SQLConstraint sqlconst = new SQLConstraint();

    static 
    {
        sqlconst.setCaseSensitive(true);
    }

    
    public static String getOid(String mibname, String captorname)
    {
        DBSnmpOID mask = new DBSnmpOID();
        mask.setMibName(mibname);
        mask.setCaptorName(captorname);

        DBSnmpOID soid = DBSnmpOID.loadByKey(mask, sqlconst);

        if(soid==null) return null;
        
        return soid.getOid();
    }


    public static String getSensorName(String mibname, String oid)
    {
        DBSnmpOID mask = new DBSnmpOID();
        mask.setMibName(mibname);
        mask.setOid(oid);
        
        DBSnmpOID soid = DBSnmpOID.loadByKey(mask, sqlconst);

        if(soid==null) return null;
        
        return soid.getCaptorName();
    }


    public static String[] listMib()
    {
        SQLConstraint constr = new SQLConstraint();
        constr.setOrderBy(DBSnmpOID.MIBNAME);

        return DBSnmpOID.distinct(DBSnmpOID.MIBNAME, null, constr);
    }


    public static String[] listSensorName(String mibname)
    {
        DBSnmpOID mask = new DBSnmpOID();
        mask.setMibName(mibname);
        return DBSnmpOID.distinct(DBSnmpOID.CAPTORNAME, mask, null);
    }



}
