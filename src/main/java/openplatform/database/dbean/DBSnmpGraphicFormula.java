package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpGraphicFormula
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpGraphicFormula
{
    protected boolean inError = false;
    protected String gFormulaId;
    protected String reportId;
    protected String formulaId;
    protected String lastUpdateTime;
    protected String condition;
    protected String value;

    public static String GFORMULAID = "gFormulaId";
    public static String REPORTID = "reportId";
    public static String FORMULAID = "formulaId";
    public static String LASTUPDATETIME = "lastUpdateTime";
    public static String CONDITION = "condition";
    /** lt **/
    public static final String CONDITION_LT = "lt";
    /** gt **/
    public static final String CONDITION_GT = "gt";
    /** eq **/
    public static final String CONDITION_EQ = "eq";
    /** Contains the SQL enumeration for CONDITION **/
    public static final ArrayList _CONDITION_LIST = new ArrayList();
    static {_CONDITION_LIST.add(CONDITION_LT);_CONDITION_LIST.add(CONDITION_GT);_CONDITION_LIST.add(CONDITION_EQ);}
    /** Contains the SQL HASHMAP for CONDITION **/
    public static final OrdHashMap _CONDITION_MAP = new OrdHashMap();
    static {_CONDITION_MAP.put(CONDITION_LT,CONDITION_LT);_CONDITION_MAP.put(CONDITION_GT,CONDITION_GT);_CONDITION_MAP.put(CONDITION_EQ,CONDITION_EQ);}
    public static String VALUE = "value";
    public static String TABLE = "SnmpGraphicFormula";

    private static Database db = Database.getInstance();


    public void clear()
    {
        gFormulaId = null;
        reportId = null;
        formulaId = null;
        lastUpdateTime = null;
        condition = null;
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
        DBSnmpGraphicFormula a = (DBSnmpGraphicFormula)obj;
        return (gFormulaId==null?a.getGFormulaId()==null:gFormulaId.equals(a.getGFormulaId())) && (reportId==null?a.getReportId()==null:reportId.equals(a.getReportId())) && (formulaId==null?a.getFormulaId()==null:formulaId.equals(a.getFormulaId())) && (lastUpdateTime==null?a.getLastUpdateTime()==null:lastUpdateTime.equals(a.getLastUpdateTime())) && (condition==null?a.getCondition()==null:condition.equals(a.getCondition())) && (value==null?a.getValue()==null:value.equals(a.getValue()));    }

    public int hashCode()
    {
        return (gFormulaId!=null?gFormulaId.hashCode():0) + (reportId!=null?reportId.hashCode():0) + (formulaId!=null?formulaId.hashCode():0) + (lastUpdateTime!=null?lastUpdateTime.hashCode():0) + (condition!=null?condition.hashCode():0) + (value!=null?value.hashCode():0);
    }

    public String toString()
    {
        return "gFormulaId="+gFormulaId+"|"+"reportId="+reportId+"|"+"formulaId="+formulaId+"|"+"lastUpdateTime="+lastUpdateTime+"|"+"condition="+condition+"|"+"value="+value;
    }

    public void refresh()
    {
        if(gFormulaId != null)
        {
            DBSnmpGraphicFormula mask = new DBSnmpGraphicFormula();
            mask.setGFormulaId(gFormulaId);
            DBSnmpGraphicFormula var = DBSnmpGraphicFormula.loadByKey(mask);
            if(var != null)
            {
                gFormulaId = var.getGFormulaId();
                reportId = var.getReportId();
                formulaId = var.getFormulaId();
                lastUpdateTime = var.getLastUpdateTime();
                condition = var.getCondition();
                value = var.getValue();
            }
        }
    }

    public void initBean(DBSnmpGraphicFormula db)
    {
        this.gFormulaId = db.getGFormulaId();
        this.reportId = db.getReportId();
        this.formulaId = db.getFormulaId();
        this.lastUpdateTime = db.getLastUpdateTime();
        this.condition = db.getCondition();
        this.value = db.getValue();
    }

    public String getGFormulaId()
    {
        return gFormulaId;
    }

    public void setGFormulaId(String gFormulaId)
    {
        this.gFormulaId = gFormulaId;
    }

    public String getReportId()
    {
        return reportId;
    }

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    public String getFormulaId()
    {
        return formulaId;
    }

    public void setFormulaId(String formulaId)
    {
        this.formulaId = formulaId;
    }

    public String getLastUpdateTime()
    {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getCondition()
    {
        return condition;
    }

    public void setCondition(String condition)
    {
        this.condition = condition;
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
     * @return a DBSnmpGraphicFormula table or null if nothing found or if an error occured
     **/
    public static DBSnmpGraphicFormula[] load(DBSnmpGraphicFormula mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpGraphicFormula table or null if nothing found or if an error occured
     **/
    public static DBSnmpGraphicFormula[] load(SQLConstraint sqlconstraint, DBSnmpGraphicFormula mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getLastUpdateTime() != null) filter.set(LASTUPDATETIME, mask.getLastUpdateTime());
            if(mask.getCondition() != null) filter.set(CONDITION, mask.getCondition());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{GFORMULAID, REPORTID, FORMULAID, LASTUPDATETIME, CONDITION, VALUE}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpGraphicFormula[] result = new DBSnmpGraphicFormula[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpGraphicFormula();
                    result[i].setGFormulaId(pvp[i].get(GFORMULAID));
                    result[i].setReportId(pvp[i].get(REPORTID));
                    result[i].setFormulaId(pvp[i].get(FORMULAID));
                    result[i].setLastUpdateTime(pvp[i].get(LASTUPDATETIME));
                    result[i].setCondition(pvp[i].get(CONDITION));
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
     * @return a DBSnmpGraphicFormula object or null if nothing found or if an error occured
     **/
    public static DBSnmpGraphicFormula loadByKey(DBSnmpGraphicFormula mask)
    {
        DBSnmpGraphicFormula[] res = DBSnmpGraphicFormula.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpGraphicFormula object or null if nothing found or if an error occured
     **/
    public static DBSnmpGraphicFormula loadByKey(DBSnmpGraphicFormula mask, SQLConstraint sqlconstraint)
    {
        DBSnmpGraphicFormula[] res = DBSnmpGraphicFormula.load(sqlconstraint, mask);
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
        toinsert.set(GFORMULAID,gFormulaId);
        toinsert.set(REPORTID,reportId);
        toinsert.set(FORMULAID,formulaId);
        toinsert.set(LASTUPDATETIME,lastUpdateTime);
        toinsert.set(CONDITION,condition);
        toinsert.set(VALUE,value);

        // Store a new entry
        if(gFormulaId == null)
        {
            try
            {
                gFormulaId = db.insert(TABLE, toinsert);
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
            filter.set(GFORMULAID, gFormulaId);

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
        if(gFormulaId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(GFORMULAID, gFormulaId);

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
    public static void delete(DBSnmpGraphicFormula mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getLastUpdateTime() != null) filter.set(LASTUPDATETIME, mask.getLastUpdateTime());
            if(mask.getCondition() != null) filter.set(CONDITION, mask.getCondition());
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
    public static int count(DBSnmpGraphicFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getLastUpdateTime() != null) filter.set(LASTUPDATETIME, mask.getLastUpdateTime());
            if(mask.getCondition() != null) filter.set(CONDITION, mask.getCondition());
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
    public static double avg(String field, DBSnmpGraphicFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getLastUpdateTime() != null) filter.set(LASTUPDATETIME, mask.getLastUpdateTime());
            if(mask.getCondition() != null) filter.set(CONDITION, mask.getCondition());
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
    public static double min(String field, DBSnmpGraphicFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getLastUpdateTime() != null) filter.set(LASTUPDATETIME, mask.getLastUpdateTime());
            if(mask.getCondition() != null) filter.set(CONDITION, mask.getCondition());
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
    public static double max(String field, DBSnmpGraphicFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getLastUpdateTime() != null) filter.set(LASTUPDATETIME, mask.getLastUpdateTime());
            if(mask.getCondition() != null) filter.set(CONDITION, mask.getCondition());
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
    public static double std(String field, DBSnmpGraphicFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getLastUpdateTime() != null) filter.set(LASTUPDATETIME, mask.getLastUpdateTime());
            if(mask.getCondition() != null) filter.set(CONDITION, mask.getCondition());
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
    public static String[] distinct(String field, DBSnmpGraphicFormula mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGFormulaId() != null) filter.set(GFORMULAID, mask.getGFormulaId());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getFormulaId() != null) filter.set(FORMULAID, mask.getFormulaId());
            if(mask.getLastUpdateTime() != null) filter.set(LASTUPDATETIME, mask.getLastUpdateTime());
            if(mask.getCondition() != null) filter.set(CONDITION, mask.getCondition());
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
