package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpValueData
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpValueData
{
    protected boolean inError = false;
    protected String id;
    protected String snmpValueId;
    protected String value;

    public static String ID = "id";
    public static String SNMPVALUEID = "snmpValueId";
    public static String VALUE = "value";
    public static String TABLE = "SnmpValueData";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        snmpValueId = null;
        value = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBSnmpValueData a = (DBSnmpValueData)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (snmpValueId==null?a.getSnmpValueId()==null:snmpValueId.equals(a.getSnmpValueId())) && (value==null?a.getValue()==null:value.equals(a.getValue()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (snmpValueId!=null?snmpValueId.hashCode():0) + (value!=null?value.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"snmpValueId="+snmpValueId+"|"+"value="+value;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBSnmpValueData mask = new DBSnmpValueData();
            mask.setId(id);
            DBSnmpValueData var = DBSnmpValueData.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                snmpValueId = var.getSnmpValueId();
                value = var.getValue();
            }
        }
    }

    public void initBean(DBSnmpValueData db)
    {
        this.id = db.getId();
        this.snmpValueId = db.getSnmpValueId();
        this.value = db.getValue();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getSnmpValueId()
    {
        return snmpValueId;
    }

    public void setSnmpValueId(String snmpValueId)
    {
        this.snmpValueId = snmpValueId;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    /**
     * @return a DBSnmpValueData table or null if nothing found or if an error occured
     **/
    public static DBSnmpValueData[] load(DBSnmpValueData mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpValueData table or null if nothing found or if an error occured
     **/
    public static DBSnmpValueData[] load(SQLConstraint sqlconstraint, DBSnmpValueData mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, SNMPVALUEID, VALUE}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpValueData[] result = new DBSnmpValueData[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpValueData();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setSnmpValueId(pvp[i].get(SNMPVALUEID));
                    result[i].setValue(pvp[i].get(VALUE));
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
     * @return a DBSnmpValueData object or null if nothing found or if an error occured
     **/
    public static DBSnmpValueData loadByKey(DBSnmpValueData mask)
    {
        DBSnmpValueData[] res = DBSnmpValueData.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpValueData object or null if nothing found or if an error occured
     **/
    public static DBSnmpValueData loadByKey(DBSnmpValueData mask, SQLConstraint sqlconstraint)
    {
        DBSnmpValueData[] res = DBSnmpValueData.load(sqlconstraint, mask);
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
        toinsert.set(SNMPVALUEID,snmpValueId);
        toinsert.set(VALUE,value);

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
    public static void delete(DBSnmpValueData mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
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
    public static int count(DBSnmpValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
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
    public static double avg(String field, DBSnmpValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
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
    public static double min(String field, DBSnmpValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
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
    public static double max(String field, DBSnmpValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
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
    public static double std(String field, DBSnmpValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
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
    public static String[] distinct(String field, DBSnmpValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getSnmpValueId() != null) filter.set(SNMPVALUEID, mask.getSnmpValueId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
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
