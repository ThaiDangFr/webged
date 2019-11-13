package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpGFValue
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpGFValue
{
    protected boolean inError = false;
    protected String gfValueId;
    protected String gFormulaId;
    protected String dateTime;

    public static String GFVALUEID = "gfValueId";
    public static String GFORMULAID = "gFormulaId";
    public static String DATETIME = "dateTime";
    public static String TABLE = "SnmpGFValue";

    private static Database db = Database.getInstance();


    public void clear()
    {
        gfValueId = null;
        gFormulaId = null;
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
        DBSnmpGFValue a = (DBSnmpGFValue)obj;
        return (gfValueId==null?a.getGfValueId()==null:gfValueId.equals(a.getGfValueId())) && (gFormulaId==null?a.getGFormulaId()==null:gFormulaId.equals(a.getGFormulaId())) && (dateTime==null?a.getDateTime()==null:dateTime.equals(a.getDateTime()));    }

    public int hashCode()
    {
        return (gfValueId!=null?gfValueId.hashCode():0) + (gFormulaId!=null?gFormulaId.hashCode():0) + (dateTime!=null?dateTime.hashCode():0);
    }

    public String toString()
    {
        return "gfValueId="+gfValueId+"|"+"gFormulaId="+gFormulaId+"|"+"dateTime="+dateTime;
    }

    public void refresh()
    {
        if(gfValueId != null)
        {
            DBSnmpGFValue mask = new DBSnmpGFValue();
            mask.setGfValueId(gfValueId);
            DBSnmpGFValue var = DBSnmpGFValue.loadByKey(mask);
            if(var != null)
            {
                gfValueId = var.getGfValueId();
                gFormulaId = var.getGFormulaId();
                dateTime = var.getDateTime();
            }
        }
    }

    public void initBean(DBSnmpGFValue db)
    {
        this.gfValueId = db.getGfValueId();
        this.gFormulaId = db.getGFormulaId();
        this.dateTime = db.getDateTime();
    }

    public String getGfValueId()
    {
        return gfValueId;
    }

    public void setGfValueId(String gfValueId)
    {
        this.gfValueId = gfValueId;
    }

    public String getGFormulaId()
    {
        return gFormulaId;
    }

    public void setGFormulaId(String gFormulaId)
    {
        this.gFormulaId = gFormulaId;
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
     * @return a DBSnmpGFValue table or null if nothing found or if an error occured
     **/
    public static DBSnmpGFValue[] load(DBSnmpGFValue mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpGFValue table or null if nothing found or if an error occured
     **/
    public static DBSnmpGFValue[] load(SQLConstraint sqlconstraint, DBSnmpGFValue mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
            if(mask.getDateTime() != null) filter.set(DATETIME, mask.getDateTime());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{GFVALUEID, GFORMULAID, DATETIME}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpGFValue[] result = new DBSnmpGFValue[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpGFValue();
                    result[i].setGfValueId(pvp[i].get(GFVALUEID));
                    result[i].setGFormulaId(pvp[i].get(GFORMULAID));
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
     * @return a DBSnmpGFValue object or null if nothing found or if an error occured
     **/
    public static DBSnmpGFValue loadByKey(DBSnmpGFValue mask)
    {
        DBSnmpGFValue[] res = DBSnmpGFValue.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpGFValue object or null if nothing found or if an error occured
     **/
    public static DBSnmpGFValue loadByKey(DBSnmpGFValue mask, SQLConstraint sqlconstraint)
    {
        DBSnmpGFValue[] res = DBSnmpGFValue.load(sqlconstraint, mask);
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
        toinsert.set(GFVALUEID,gfValueId);
        toinsert.set(GFORMULAID,gFormulaId);
        toinsert.set(DATETIME,dateTime);

        // Store a new entry
        if(gfValueId == null)
        {
            try
            {
                gfValueId = db.insert(TABLE, toinsert);
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
            filter.set(GFVALUEID, gfValueId);

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
        if(gfValueId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(GFVALUEID, gfValueId);

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
    public static void delete(DBSnmpGFValue mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
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
    public static int count(DBSnmpGFValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
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
    public static double avg(String field, DBSnmpGFValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
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
    public static double min(String field, DBSnmpGFValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
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
    public static double max(String field, DBSnmpGFValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
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
    public static double std(String field, DBSnmpGFValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
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
    public static String[] distinct(String field, DBSnmpGFValue mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGfValueId() != null) filter.set(GFVALUEID, mask.getGfValueId());
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
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
