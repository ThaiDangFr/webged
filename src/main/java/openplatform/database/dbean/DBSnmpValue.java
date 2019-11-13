package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpValue
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpValue
{
    protected boolean inError = false;
    protected String snmpValueId;
    protected String snmpParamId;
    protected String dateTime;

    public static String SNMPVALUEID = "snmpValueId";
    public static String SNMPPARAMID = "snmpParamId";
    public static String DATETIME = "dateTime";
    public static String TABLE = "SnmpValue";

    private static Database db = Database.getInstance();


    public void clear()
    {
        snmpValueId = null;
        snmpParamId = null;
        dateTime = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBSnmpValue a = (DBSnmpValue)obj;
        return (snmpValueId==null?a.getSnmpValueId()==null:snmpValueId.equals(a.getSnmpValueId())) && (snmpParamId==null?a.getSnmpParamId()==null:snmpParamId.equals(a.getSnmpParamId())) && (dateTime==null?a.getDateTime()==null:dateTime.equals(a.getDateTime()));    }

    public int hashCode()
    {
        return (snmpValueId!=null?snmpValueId.hashCode():0) + (snmpParamId!=null?snmpParamId.hashCode():0) + (dateTime!=null?dateTime.hashCode():0);
    }

    public String toString()
    {
        return "snmpValueId="+snmpValueId+"|"+"snmpParamId="+snmpParamId+"|"+"dateTime="+dateTime;
    }

    public void refresh()
    {
        if(snmpValueId != null)
        {
            DBSnmpValue mask = new DBSnmpValue();
            mask.setSnmpValueId(snmpValueId);
            DBSnmpValue var = DBSnmpValue.loadByKey(mask);
            if(var != null)
            {
                snmpValueId = var.getSnmpValueId();
                snmpParamId = var.getSnmpParamId();
                dateTime = var.getDateTime();
            }
        }
    }

    public void initBean(DBSnmpValue db)
    {
        this.snmpValueId = db.getSnmpValueId();
        this.snmpParamId = db.getSnmpParamId();
        this.dateTime = db.getDateTime();
    }

    public String getSnmpValueId()
    {
        return snmpValueId;
    }

    public void setSnmpValueId(String snmpValueId)
    {
        this.snmpValueId = snmpValueId;
    }

    public String getSnmpParamId()
    {
        return snmpParamId;
    }

    public void setSnmpParamId(String snmpParamId)
    {
        this.snmpParamId = snmpParamId;
    }

    public String getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(String dateTime)
    {
        this.dateTime = dateTime;
    }

    /**
     * @return a DBSnmpValue table or null if nothing found or if an error occured
     **/
    public static DBSnmpValue[] load(DBSnmpValue mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpValue table or null if nothing found or if an error occured
     **/
    public static DBSnmpValue[] load(SQLConstraint sqlconstraint, DBSnmpValue mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getDateTime() != null) filter.set(DATETIME, mask.getDateTime());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{SNMPVALUEID, SNMPPARAMID, DATETIME}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpValue[] result = new DBSnmpValue[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpValue();
                    result[i].setSnmpValueId(pvp[i].get(SNMPVALUEID));
                    result[i].setSnmpParamId(pvp[i].get(SNMPPARAMID));
                    result[i].setDateTime(pvp[i].get(DATETIME));
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
     * @return a DBSnmpValue object or null if nothing found or if an error occured
     **/
    public static DBSnmpValue loadByKey(DBSnmpValue mask)
    {
        DBSnmpValue[] res = DBSnmpValue.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpValue object or null if nothing found or if an error occured
     **/
    public static DBSnmpValue loadByKey(DBSnmpValue mask, SQLConstraint sqlconstraint)
    {
        DBSnmpValue[] res = DBSnmpValue.load(sqlconstraint, mask);
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
        toinsert.set(SNMPVALUEID,snmpValueId);
        toinsert.set(SNMPPARAMID,snmpParamId);
        toinsert.set(DATETIME,dateTime);

        // Store a new entry
        if(snmpValueId == null)
        {
            try
            {
                snmpValueId = db.insert(TABLE, toinsert);
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
            filter.set(SNMPVALUEID, snmpValueId);

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
        if(snmpValueId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(SNMPVALUEID, snmpValueId);

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
    public static void delete(DBSnmpValue mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getDateTime() != null) filter.set(DATETIME, mask.getDateTime());
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
    public static int count(DBSnmpValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getDateTime() != null) filter.set(DATETIME, mask.getDateTime());
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
    public static double avg(String field, DBSnmpValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getDateTime() != null) filter.set(DATETIME, mask.getDateTime());
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
    public static double min(String field, DBSnmpValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getDateTime() != null) filter.set(DATETIME, mask.getDateTime());
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
    public static double max(String field, DBSnmpValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getDateTime() != null) filter.set(DATETIME, mask.getDateTime());
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
    public static double std(String field, DBSnmpValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getDateTime() != null) filter.set(DATETIME, mask.getDateTime());
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
    public static String[] distinct(String field, DBSnmpValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getDateTime() != null) filter.set(DATETIME, mask.getDateTime());
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
