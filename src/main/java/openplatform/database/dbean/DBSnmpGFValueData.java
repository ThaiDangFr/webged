package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpGFValueData
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpGFValueData
{
    protected boolean inError = false;
    protected String id;
    protected String gfValueId;
    protected String value;

    public static String ID = "id";
    public static String GFVALUEID = "gfValueId";
    public static String VALUE = "value";
    public static String TABLE = "SnmpGFValueData";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        gfValueId = null;
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
        DBSnmpGFValueData a = (DBSnmpGFValueData)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (gfValueId==null?a.getGfValueId()==null:gfValueId.equals(a.getGfValueId())) && (value==null?a.getValue()==null:value.equals(a.getValue()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (gfValueId!=null?gfValueId.hashCode():0) + (value!=null?value.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"gfValueId="+gfValueId+"|"+"value="+value;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBSnmpGFValueData mask = new DBSnmpGFValueData();
            mask.setId(id);
            DBSnmpGFValueData var = DBSnmpGFValueData.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                gfValueId = var.getGfValueId();
                value = var.getValue();
            }
        }
    }

    public void initBean(DBSnmpGFValueData db)
    {
        this.id = db.getId();
        this.gfValueId = db.getGfValueId();
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

    public String getGfValueId()
    {
        return gfValueId;
    }

    public void setGfValueId(String gfValueId)
    {
        this.gfValueId = gfValueId;
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
     * @return a DBSnmpGFValueData table or null if nothing found or if an error occured
     **/
    public static DBSnmpGFValueData[] load(DBSnmpGFValueData mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpGFValueData table or null if nothing found or if an error occured
     **/
    public static DBSnmpGFValueData[] load(SQLConstraint sqlconstraint, DBSnmpGFValueData mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, GFVALUEID, VALUE}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpGFValueData[] result = new DBSnmpGFValueData[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpGFValueData();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setGfValueId(pvp[i].get(GFVALUEID));
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
     * @return a DBSnmpGFValueData object or null if nothing found or if an error occured
     **/
    public static DBSnmpGFValueData loadByKey(DBSnmpGFValueData mask)
    {
        DBSnmpGFValueData[] res = DBSnmpGFValueData.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpGFValueData object or null if nothing found or if an error occured
     **/
    public static DBSnmpGFValueData loadByKey(DBSnmpGFValueData mask, SQLConstraint sqlconstraint)
    {
        DBSnmpGFValueData[] res = DBSnmpGFValueData.load(sqlconstraint, mask);
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
        toinsert.set(GFVALUEID,gfValueId);
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
    public static void delete(DBSnmpGFValueData mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
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
    public static int count(DBSnmpGFValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
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
    public static double avg(String field, DBSnmpGFValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
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
    public static double min(String field, DBSnmpGFValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
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
    public static double max(String field, DBSnmpGFValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
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
    public static double std(String field, DBSnmpGFValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
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
    public static String[] distinct(String field, DBSnmpGFValueData mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
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
