package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpAlert
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpAlert
{
    protected boolean inError = false;
    protected String id;
    protected String type;
    protected String source;
    protected String description;
    protected String level;
    protected String date;
    protected String reportId;

    public static String ID = "id";
    public static String TYPE = "type";
    /** Polling **/
    public static final String TYPE_POLLING = "Polling";
    /** Trap **/
    public static final String TYPE_TRAP = "Trap";
    /** Contains the SQL enumeration for TYPE **/
    public static final ArrayList _TYPE_LIST = new ArrayList();
    static {_TYPE_LIST.add(TYPE_POLLING);_TYPE_LIST.add(TYPE_TRAP);}
    /** Contains the SQL HASHMAP for TYPE **/
    public static final OrdHashMap _TYPE_MAP = new OrdHashMap();
    static {_TYPE_MAP.put(TYPE_POLLING,TYPE_POLLING);_TYPE_MAP.put(TYPE_TRAP,TYPE_TRAP);}
    public static String SOURCE = "source";
    public static String DESCRIPTION = "description";
    public static String LEVEL = "level";
    /** Debug **/
    public static final String LEVEL_DEBUG = "Debug";
    /** Informational **/
    public static final String LEVEL_INFORMATIONAL = "Informational";
    /** Notice **/
    public static final String LEVEL_NOTICE = "Notice";
    /** Warning **/
    public static final String LEVEL_WARNING = "Warning";
    /** Error **/
    public static final String LEVEL_ERROR = "Error";
    /** Critical **/
    public static final String LEVEL_CRITICAL = "Critical";
    /** Alert **/
    public static final String LEVEL_ALERT = "Alert";
    /** Emergency **/
    public static final String LEVEL_EMERGENCY = "Emergency";
    /** Contains the SQL enumeration for LEVEL **/
    public static final ArrayList _LEVEL_LIST = new ArrayList();
    static {_LEVEL_LIST.add(LEVEL_DEBUG);_LEVEL_LIST.add(LEVEL_INFORMATIONAL);_LEVEL_LIST.add(LEVEL_NOTICE);_LEVEL_LIST.add(LEVEL_WARNING);_LEVEL_LIST.add(LEVEL_ERROR);_LEVEL_LIST.add(LEVEL_CRITICAL);_LEVEL_LIST.add(LEVEL_ALERT);_LEVEL_LIST.add(LEVEL_EMERGENCY);}
    /** Contains the SQL HASHMAP for LEVEL **/
    public static final OrdHashMap _LEVEL_MAP = new OrdHashMap();
    static {_LEVEL_MAP.put(LEVEL_DEBUG,LEVEL_DEBUG);_LEVEL_MAP.put(LEVEL_INFORMATIONAL,LEVEL_INFORMATIONAL);_LEVEL_MAP.put(LEVEL_NOTICE,LEVEL_NOTICE);_LEVEL_MAP.put(LEVEL_WARNING,LEVEL_WARNING);_LEVEL_MAP.put(LEVEL_ERROR,LEVEL_ERROR);_LEVEL_MAP.put(LEVEL_CRITICAL,LEVEL_CRITICAL);_LEVEL_MAP.put(LEVEL_ALERT,LEVEL_ALERT);_LEVEL_MAP.put(LEVEL_EMERGENCY,LEVEL_EMERGENCY);}
    public static String DATE = "date";
    public static String REPORTID = "reportId";
    public static String TABLE = "SnmpAlert";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        type = null;
        source = null;
        description = null;
        level = null;
        date = null;
        reportId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBSnmpAlert a = (DBSnmpAlert)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (type==null?a.getType()==null:type.equals(a.getType())) && (source==null?a.getSource()==null:source.equals(a.getSource())) && (description==null?a.getDescription()==null:description.equals(a.getDescription())) && (level==null?a.getLevel()==null:level.equals(a.getLevel())) && (date==null?a.getDate()==null:date.equals(a.getDate())) && (reportId==null?a.getReportId()==null:reportId.equals(a.getReportId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (type!=null?type.hashCode():0) + (source!=null?source.hashCode():0) + (description!=null?description.hashCode():0) + (level!=null?level.hashCode():0) + (date!=null?date.hashCode():0) + (reportId!=null?reportId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"type="+type+"|"+"source="+source+"|"+"description="+description+"|"+"level="+level+"|"+"date="+date+"|"+"reportId="+reportId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBSnmpAlert mask = new DBSnmpAlert();
            mask.setId(id);
            DBSnmpAlert var = DBSnmpAlert.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                type = var.getType();
                source = var.getSource();
                description = var.getDescription();
                level = var.getLevel();
                date = var.getDate();
                reportId = var.getReportId();
            }
        }
    }

    public void initBean(DBSnmpAlert db)
    {
        this.id = db.getId();
        this.type = db.getType();
        this.source = db.getSource();
        this.description = db.getDescription();
        this.level = db.getLevel();
        this.date = db.getDate();
        this.reportId = db.getReportId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getLevel()
    {
        return level;
    }

    public void setLevel(String level)
    {
        this.level = level;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getReportId()
    {
        return reportId;
    }

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    /**
     * @return a DBSnmpAlert table or null if nothing found or if an error occured
     **/
    public static DBSnmpAlert[] load(DBSnmpAlert mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpAlert table or null if nothing found or if an error occured
     **/
    public static DBSnmpAlert[] load(SQLConstraint sqlconstraint, DBSnmpAlert mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getSource() != null) filter.set(SOURCE, mask.getSource());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getLevel() != null) filter.set(LEVEL, mask.getLevel());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, TYPE, SOURCE, DESCRIPTION, LEVEL, DATE, REPORTID}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpAlert[] result = new DBSnmpAlert[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpAlert();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setType(pvp[i].get(TYPE));
                    result[i].setSource(pvp[i].get(SOURCE));
                    result[i].setDescription(pvp[i].get(DESCRIPTION));
                    result[i].setLevel(pvp[i].get(LEVEL));
                    result[i].setDate(pvp[i].get(DATE));
                    result[i].setReportId(pvp[i].get(REPORTID));
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
     * @return a DBSnmpAlert object or null if nothing found or if an error occured
     **/
    public static DBSnmpAlert loadByKey(DBSnmpAlert mask)
    {
        DBSnmpAlert[] res = DBSnmpAlert.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpAlert object or null if nothing found or if an error occured
     **/
    public static DBSnmpAlert loadByKey(DBSnmpAlert mask, SQLConstraint sqlconstraint)
    {
        DBSnmpAlert[] res = DBSnmpAlert.load(sqlconstraint, mask);
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
        toinsert.set(TYPE,type);
        toinsert.set(SOURCE,source);
        toinsert.set(DESCRIPTION,description);
        toinsert.set(LEVEL,level);
        toinsert.set(DATE,date);
        toinsert.set(REPORTID,reportId);

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
    public static void delete(DBSnmpAlert mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getSource() != null) filter.set(SOURCE, mask.getSource());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getLevel() != null) filter.set(LEVEL, mask.getLevel());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
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
    public static int count(DBSnmpAlert mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getSource() != null) filter.set(SOURCE, mask.getSource());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getLevel() != null) filter.set(LEVEL, mask.getLevel());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
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
    public static double avg(String field, DBSnmpAlert mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getSource() != null) filter.set(SOURCE, mask.getSource());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getLevel() != null) filter.set(LEVEL, mask.getLevel());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
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
    public static double min(String field, DBSnmpAlert mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getSource() != null) filter.set(SOURCE, mask.getSource());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getLevel() != null) filter.set(LEVEL, mask.getLevel());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
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
    public static double max(String field, DBSnmpAlert mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getSource() != null) filter.set(SOURCE, mask.getSource());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getLevel() != null) filter.set(LEVEL, mask.getLevel());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
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
    public static double std(String field, DBSnmpAlert mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getSource() != null) filter.set(SOURCE, mask.getSource());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getLevel() != null) filter.set(LEVEL, mask.getLevel());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
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
    public static String[] distinct(String field, DBSnmpAlert mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getSource() != null) filter.set(SOURCE, mask.getSource());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getLevel() != null) filter.set(LEVEL, mask.getLevel());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
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
