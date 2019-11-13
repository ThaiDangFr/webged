package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpReport
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpReport
{
    protected boolean inError = false;
    protected String reportId;
    protected String displayName;
    protected String frequency;
    protected String hostId;
    protected String timeRange;

    public static String REPORTID = "reportId";
    public static String DISPLAYNAME = "displayName";
    public static String FREQUENCY = "frequency";
    public static String HOSTID = "hostId";
    public static String TIMERANGE = "timeRange";
    public static String TABLE = "SnmpReport";

    private static Database db = Database.getInstance();


    public void clear()
    {
        reportId = null;
        displayName = null;
        frequency = null;
        hostId = null;
        timeRange = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBSnmpReport a = (DBSnmpReport)obj;
        return (reportId==null?a.getReportId()==null:reportId.equals(a.getReportId())) && (displayName==null?a.getDisplayName()==null:displayName.equals(a.getDisplayName())) && (frequency==null?a.getFrequency()==null:frequency.equals(a.getFrequency())) && (hostId==null?a.getHostId()==null:hostId.equals(a.getHostId())) && (timeRange==null?a.getTimeRange()==null:timeRange.equals(a.getTimeRange()));    }

    public int hashCode()
    {
        return (reportId!=null?reportId.hashCode():0) + (displayName!=null?displayName.hashCode():0) + (frequency!=null?frequency.hashCode():0) + (hostId!=null?hostId.hashCode():0) + (timeRange!=null?timeRange.hashCode():0);
    }

    public String toString()
    {
        return "reportId="+reportId+"|"+"displayName="+displayName+"|"+"frequency="+frequency+"|"+"hostId="+hostId+"|"+"timeRange="+timeRange;
    }

    public void refresh()
    {
        if(reportId != null)
        {
            DBSnmpReport mask = new DBSnmpReport();
            mask.setReportId(reportId);
            DBSnmpReport var = DBSnmpReport.loadByKey(mask);
            if(var != null)
            {
                reportId = var.getReportId();
                displayName = var.getDisplayName();
                frequency = var.getFrequency();
                hostId = var.getHostId();
                timeRange = var.getTimeRange();
            }
        }
    }

    public void initBean(DBSnmpReport db)
    {
        this.reportId = db.getReportId();
        this.displayName = db.getDisplayName();
        this.frequency = db.getFrequency();
        this.hostId = db.getHostId();
        this.timeRange = db.getTimeRange();
    }

    public String getReportId()
    {
        return reportId;
    }

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getFrequency()
    {
        return frequency;
    }

    public void setFrequency(String frequency)
    {
        this.frequency = frequency;
    }

    public String getHostId()
    {
        return hostId;
    }

    public void setHostId(String hostId)
    {
        this.hostId = hostId;
    }

    public String getTimeRange()
    {
        return timeRange;
    }

    public void setTimeRange(String timeRange)
    {
        this.timeRange = timeRange;
    }

    /**
     * @return a DBSnmpReport table or null if nothing found or if an error occured
     **/
    public static DBSnmpReport[] load(DBSnmpReport mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpReport table or null if nothing found or if an error occured
     **/
    public static DBSnmpReport[] load(SQLConstraint sqlconstraint, DBSnmpReport mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getFrequency() != null) filter.set(FREQUENCY, mask.getFrequency());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getTimeRange() != null) filter.set(TIMERANGE, mask.getTimeRange());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{REPORTID, DISPLAYNAME, FREQUENCY, HOSTID, TIMERANGE}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpReport[] result = new DBSnmpReport[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpReport();
                    result[i].setReportId(pvp[i].get(REPORTID));
                    result[i].setDisplayName(pvp[i].get(DISPLAYNAME));
                    result[i].setFrequency(pvp[i].get(FREQUENCY));
                    result[i].setHostId(pvp[i].get(HOSTID));
                    result[i].setTimeRange(pvp[i].get(TIMERANGE));
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
     * @return a DBSnmpReport object or null if nothing found or if an error occured
     **/
    public static DBSnmpReport loadByKey(DBSnmpReport mask)
    {
        DBSnmpReport[] res = DBSnmpReport.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpReport object or null if nothing found or if an error occured
     **/
    public static DBSnmpReport loadByKey(DBSnmpReport mask, SQLConstraint sqlconstraint)
    {
        DBSnmpReport[] res = DBSnmpReport.load(sqlconstraint, mask);
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
        toinsert.set(REPORTID,reportId);
        toinsert.set(DISPLAYNAME,displayName);
        toinsert.set(FREQUENCY,frequency);
        toinsert.set(HOSTID,hostId);
        toinsert.set(TIMERANGE,timeRange);

        // Store a new entry
        if(reportId == null)
        {
            try
            {
                reportId = db.insert(TABLE, toinsert);
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
            filter.set(REPORTID, reportId);

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
        if(reportId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(REPORTID, reportId);

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
    public static void delete(DBSnmpReport mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getFrequency() != null) filter.set(FREQUENCY, mask.getFrequency());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getTimeRange() != null) filter.set(TIMERANGE, mask.getTimeRange());
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
    public static int count(DBSnmpReport mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getFrequency() != null) filter.set(FREQUENCY, mask.getFrequency());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getTimeRange() != null) filter.set(TIMERANGE, mask.getTimeRange());
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
    public static double avg(String field, DBSnmpReport mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getFrequency() != null) filter.set(FREQUENCY, mask.getFrequency());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getTimeRange() != null) filter.set(TIMERANGE, mask.getTimeRange());
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
    public static double min(String field, DBSnmpReport mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getFrequency() != null) filter.set(FREQUENCY, mask.getFrequency());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getTimeRange() != null) filter.set(TIMERANGE, mask.getTimeRange());
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
    public static double max(String field, DBSnmpReport mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getFrequency() != null) filter.set(FREQUENCY, mask.getFrequency());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getTimeRange() != null) filter.set(TIMERANGE, mask.getTimeRange());
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
    public static double std(String field, DBSnmpReport mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getFrequency() != null) filter.set(FREQUENCY, mask.getFrequency());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getTimeRange() != null) filter.set(TIMERANGE, mask.getTimeRange());
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
    public static String[] distinct(String field, DBSnmpReport mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getReportId() != null) filter.set(REPORTID, mask.getReportId());
            if(mask.getDisplayName() != null) filter.set(DISPLAYNAME, mask.getDisplayName());
            if(mask.getFrequency() != null) filter.set(FREQUENCY, mask.getFrequency());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getTimeRange() != null) filter.set(TIMERANGE, mask.getTimeRange());
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
