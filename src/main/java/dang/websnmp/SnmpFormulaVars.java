package dang.websnmp;

import openplatform.tools.*;
import openplatform.database.dbean.*;
import openplatform.database.dbeanext.*;
import openplatform.database.SQLConstraint;
import java.util.*;
import openplatform.snmp.process.SnmpScheduler;

/**
 * Class SnmpFormulaVars
 *
 * Represents a variable in the formula
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class SnmpFormulaVars
{
    private String mibname;
    private String sensorname;
    private String oid;
    private boolean t1;

    private DoubleArray[] values;
    private int length=-1;
    private int index=0;
    private DBSnmpParam maskparam = new DBSnmpParam();
    private SQLConstraint sqlconst = new SQLConstraint();
    private DBSnmpValue maskvalue = new DBSnmpValue();
    private long beginDate;


    public boolean isT1()
    {
        return t1;
    }

    public void setT1(boolean at1)
    {
        t1=at1;
    }

    public String getOid()
    {
        return oid;
    }

    public void setOid(String aoid)
    {
        oid=aoid;
    }

    public String getSensorname()
    {
        return sensorname;
    }

    public void setSensorname(String asensorname)
    {
        sensorname=asensorname;
    }

    public String getMibname()
    {
        return mibname;
    }

    public void setMibname(String amibname)
    {
        mibname=amibname;
    }


    //============== CALCULATION METHODS ==============


    /**
     * @return the number of DoubleArray loaded
     */
    public final int length()
    {
        return length;
    }


    public final int index()
    {
        return index;
    }


    /**
     * @return the next DoubleArray (doublearray because, we can have more than one value for an OID (up to 9)
     */
    public DoubleArray next()
    {
        DoubleArray da = values[index];
        index++;
        return da;
    }


    public boolean hasMoreValues()
    {
        if(index>= length)
            return false;
        else
            return true;
    }



    public long getBeginDate()
    {
        return beginDate;
    }


    /**
     * load the DBSnmpValues given the hostid and a beginning date
     */
    public void loadSnmpValues(String hostid, long begindate)
    {
        // 1. load
        maskparam.clear();
        maskparam.setSnmpHostId(hostid);
        maskparam.setOid(oid);

        DBSnmpParam param = maskparam.loadByKey(maskparam);

        if(param==null)
        {
            Debug.println(this, Debug.WARNING, "No DBSnmpParam found for "+toString());
            return;
        }

        maskvalue.clear();
        maskvalue.setSnmpParamId(param.getSnmpParamId());
        sqlconst.setCustomWhere(DBSnmpValue.DATETIME+SQLConstraint.GREATER_EQ_THAN+"'"+SQLTime.getSQLTime(begindate)+"'");
        
        DBSnmpValue[] snmpval = DBSnmpValue.load(sqlconst, maskvalue);
        if(snmpval==null || snmpval.length==0)
        {
            Debug.println(this, Debug.WARNING, "No DBSnmpValue found for "+toString());
            return;
        }


        // Calculate the *REAL* begindate
        try
        {
            beginDate = SQLTime.getJavaTime(snmpval[0].getDateTime());
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }


        // each DoubleArray represents the values of DBSnmpValue
        int len = snmpval.length;
        ArrayList al = new ArrayList();
        long prevdate = 0;
        long date = 0;

        for(int i=0; i<len; i++) // loop on DBSnmpValue
        {
            DBSnmpValueData vdmask = new DBSnmpValueData();
            vdmask.clear();
            vdmask.setSnmpValueId(snmpval[i].getSnmpValueId());

            DBSnmpValueData[] valueData = DBSnmpValueData.load(vdmask);
            int looplen = 0;
            if(valueData != null) looplen = valueData.length;

            if(i==0)
            {
                prevdate = SQLTime.getJavaTime(snmpval[0].getDateTime());
            }
            // add blank value if some snmpvalues are missing
            else
            {
                date = SQLTime.getJavaTime(snmpval[i].getDateTime());
                float fratio = (float)(date - prevdate)/(float)(SnmpScheduler.FREQUENCE*1000);
                int iratio = Math.round(fratio);
                Debug.println(this, Debug.DEBUG, "fratio="+fratio+" iratio="+iratio);

                
                if(iratio > 1)
                {
                    Debug.println(this, Debug.WARNING, "Adding "+(iratio-1)+" NaN double because some are missing !");
                    
                    for(int blk=1; blk<iratio; blk++)
                    {
                        DoubleArray daBlk = new DoubleArray();

                        for(int j=0; j<looplen; j++)
                            daBlk.add(Double.NaN);

                        al.add(daBlk);
                    }
                }
                else if(iratio == 0)
                {
                    Debug.println(this, Debug.WARNING, "Skipping SnmpValue because it is too close");
                    continue;
                }

                prevdate = date;
            }

            
            
            DoubleArray da = new DoubleArray();

            try
            {
                for(int j=0; j<looplen; j++)
                {
                    double d = Double.parseDouble(valueData[j].getValue());
                    da.add(d);
                }

//                 int nbOfValues = Integer.parseInt(snmpval[i].getNbOfValues());
//                 for(int j=0; j<nbOfValues; j++)
//                 {
//                     double d = (DBSnmpValueExt.getValue(snmpval[i], j));
//                     da.add(d);
//                 }

                if(da.size() !=0)
                    al.add(da);
            }
            catch(Exception e)
            {
                Debug.println(this, Debug.ERROR, e);
            }

            
        }


        Object[] obj = al.toArray();
        len = obj.length;
        values = new DoubleArray[len];
        
        for(int i=0; i<len; i++)
            values[i] = (DoubleArray)obj[i];




        // 2. init length
        length = len;




        // 3. init if T1
        if(t1)
        {
            index=1;
            length--;
        }
        else
        {
            index=0;
        }


        Debug.println(this, Debug.DEBUG, length + " snmpValues loaded for " + toString());
    }


    /**
     * Free some memories
     */
    public void unloadSnmpValues()
    {
        values = null;
        length=-1;
        index=0;

        Debug.println(this, Debug.DEBUG, "snmpValues unloaded for "+toString());
    }



    /**
     * Return the name as in the formula
     */
    public String toString()
    {
        return mibname+"_"+sensorname+(t1?"_T1":"_T0");
    }
}
