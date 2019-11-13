package openplatform.snmp.process;

import openplatform.tools.*;
import openplatform.database.dbean.*;
import openplatform.database.dbeanext.*;
import openplatform.snmp.*;
import openplatform.database.SQLConstraint;


/**
 * Class Logger
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Logger
{
    public final static int INFINITE_TIME = -1;    
    private SnmpAbstract snmp;
    private String hostId;



    /**
     * @param hostId See Factory class to obtain a DBSnmpHost object
     **/
    public Logger(String hostId)
    {
        this.hostId = hostId;

        try
        {
            snmp = SnmpFactory.getSnmp(hostId);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    public String getHostId()
    {
        return hostId;
    }


    /**
     * Send a snmp query with the DBSnmpParam to the host and store the result in database.
     * @param snmpParam obtain with Factory.snmpParam(DBSnmpHost host, String mibName, String sensorName)
     * @return the value logged or null if nothing found
     **/
    public DBSnmpValue log(DBSnmpParam snmpParam)
    {
        try
        {
            SnmpNodeList snl = snmp.get(snmpParam.getOid());
        
            int size=0;
            if(snl==null || (size=snl.size())==0) return null;


            long date = currentTime();
            

            DBSnmpValue snmpValue = new DBSnmpValue();
            snmpValue.setSnmpParamId(snmpParam.getSnmpParamId());
            snmpValue.setDateTime(SQLTime.getSQLTime(date));
            snmpValue.store();

            DBSnmpValueData valData = new DBSnmpValueData();
            for(int i=0; i<size; i++)
            {
                SnmpNode node = snl.getSnmpNode(i);
                String value = node.getValue();
                valData.setSnmpValueId(snmpValue.getSnmpValueId());
                valData.setValue(value);
                valData.store();
                valData.clear();
            }

            return snmpValue;

//             DBSnmpValueExt val = new DBSnmpValueExt();
//             val.setNbOfValues(Integer.toString(size));
//             val.setDate(Long.toString(date));
//             val.setSnmpParamId(snmpParam.getSnmpParamId());
        
//             for(int i=0; i<size; i++)
//             {
//                 SnmpNode node = snl.getSnmpNode(i);
//                 String value = node.getValue();
//                 val.setValue(i, value);
//             }
        
//             val.store();
//             if(val.isInError())
//                 return null;
//             else
//                 return val;
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return null;
        }
    }



    /**
     * Load some values depending on begin and end time
     **/
    public static DBSnmpValue[] load(long beginTime, long endTime, DBSnmpParam filter)
    {
        DBSnmpValue mask = new DBSnmpValue();
        mask.setSnmpParamId(filter.getSnmpParamId());

        SQLConstraint sqlc = new SQLConstraint();
        
        String where = DBSnmpValue.DATETIME + SQLConstraint.GREATER_EQ_THAN + SQLTime.getSQLTime(beginTime);
        if(endTime!=-1)
            where += SQLConstraint.AND + SQLConstraint.LOWER_EQ_THAN + SQLTime.getSQLTime(endTime);

        sqlc.setCustomWhere(where);

        return DBSnmpValue.load(sqlc, mask);
    }


    /**
     * @return the current time
     **/
    public static long currentTime()
    {
        return System.currentTimeMillis();
    }
}
