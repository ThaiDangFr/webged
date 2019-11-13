package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedWorkflowProc
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedWorkflowProc
{
    protected boolean inError = false;
    protected String id;
    protected String fileId;
    protected String workflowStepId;
    protected String beginDate;
    protected String status;

    public static String ID = "id";
    public static String FILEID = "fileId";
    public static String WORKFLOWSTEPID = "workflowStepId";
    public static String BEGINDATE = "beginDate";
    public static String STATUS = "status";
    /** Approved **/
    public static final String STATUS_APPROVED = "Approved";
    /** Disapproved **/
    public static final String STATUS_DISAPPROVED = "Disapproved";
    /** Sent **/
    public static final String STATUS_SENT = "Sent";
    /** SentToBackup **/
    public static final String STATUS_SENTTOBACKUP = "SentToBackup";
    /** New **/
    public static final String STATUS_NEW = "New";
    /** Expired **/
    public static final String STATUS_EXPIRED = "Expired";
    /** Contains the SQL enumeration for STATUS **/
    public static final ArrayList _STATUS_LIST = new ArrayList();
    static {_STATUS_LIST.add(STATUS_APPROVED);_STATUS_LIST.add(STATUS_DISAPPROVED);_STATUS_LIST.add(STATUS_SENT);_STATUS_LIST.add(STATUS_SENTTOBACKUP);_STATUS_LIST.add(STATUS_NEW);_STATUS_LIST.add(STATUS_EXPIRED);}
    /** Contains the SQL HASHMAP for STATUS **/
    public static final OrdHashMap _STATUS_MAP = new OrdHashMap();
    static {_STATUS_MAP.put(STATUS_APPROVED,STATUS_APPROVED);_STATUS_MAP.put(STATUS_DISAPPROVED,STATUS_DISAPPROVED);_STATUS_MAP.put(STATUS_SENT,STATUS_SENT);_STATUS_MAP.put(STATUS_SENTTOBACKUP,STATUS_SENTTOBACKUP);_STATUS_MAP.put(STATUS_NEW,STATUS_NEW);_STATUS_MAP.put(STATUS_EXPIRED,STATUS_EXPIRED);}
    public static String TABLE = "GedWorkflowProc";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        fileId = null;
        workflowStepId = null;
        beginDate = null;
        status = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedWorkflowProc a = (DBGedWorkflowProc)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (fileId==null?a.getFileId()==null:fileId.equals(a.getFileId())) && (workflowStepId==null?a.getWorkflowStepId()==null:workflowStepId.equals(a.getWorkflowStepId())) && (beginDate==null?a.getBeginDate()==null:beginDate.equals(a.getBeginDate())) && (status==null?a.getStatus()==null:status.equals(a.getStatus()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (fileId!=null?fileId.hashCode():0) + (workflowStepId!=null?workflowStepId.hashCode():0) + (beginDate!=null?beginDate.hashCode():0) + (status!=null?status.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"fileId="+fileId+"|"+"workflowStepId="+workflowStepId+"|"+"beginDate="+beginDate+"|"+"status="+status;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedWorkflowProc mask = new DBGedWorkflowProc();
            mask.setId(id);
            DBGedWorkflowProc var = DBGedWorkflowProc.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                fileId = var.getFileId();
                workflowStepId = var.getWorkflowStepId();
                beginDate = var.getBeginDate();
                status = var.getStatus();
            }
        }
    }

    public void initBean(DBGedWorkflowProc db)
    {
        this.id = db.getId();
        this.fileId = db.getFileId();
        this.workflowStepId = db.getWorkflowStepId();
        this.beginDate = db.getBeginDate();
        this.status = db.getStatus();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFileId()
    {
        return fileId;
    }

    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public String getWorkflowStepId()
    {
        return workflowStepId;
    }

    public void setWorkflowStepId(String workflowStepId)
    {
        this.workflowStepId = workflowStepId;
    }

    public String getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(String beginDate)
    {
        this.beginDate = beginDate;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * @return a DBGedWorkflowProc table or null if nothing found or if an error occured
     **/
    public static DBGedWorkflowProc[] load(DBGedWorkflowProc mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedWorkflowProc table or null if nothing found or if an error occured
     **/
    public static DBGedWorkflowProc[] load(SQLConstraint sqlconstraint, DBGedWorkflowProc mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getWorkflowStepId() != null) filter.set(WORKFLOWSTEPID, mask.getWorkflowStepId());
            if(mask.getBeginDate() != null) filter.set(BEGINDATE, mask.getBeginDate());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, FILEID, WORKFLOWSTEPID, BEGINDATE, STATUS}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedWorkflowProc[] result = new DBGedWorkflowProc[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedWorkflowProc();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setFileId(pvp[i].get(FILEID));
                    result[i].setWorkflowStepId(pvp[i].get(WORKFLOWSTEPID));
                    result[i].setBeginDate(pvp[i].get(BEGINDATE));
                    result[i].setStatus(pvp[i].get(STATUS));
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
     * @return a DBGedWorkflowProc object or null if nothing found or if an error occured
     **/
    public static DBGedWorkflowProc loadByKey(DBGedWorkflowProc mask)
    {
        DBGedWorkflowProc[] res = DBGedWorkflowProc.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedWorkflowProc object or null if nothing found or if an error occured
     **/
    public static DBGedWorkflowProc loadByKey(DBGedWorkflowProc mask, SQLConstraint sqlconstraint)
    {
        DBGedWorkflowProc[] res = DBGedWorkflowProc.load(sqlconstraint, mask);
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
        toinsert.set(FILEID,fileId);
        toinsert.set(WORKFLOWSTEPID,workflowStepId);
        toinsert.set(BEGINDATE,beginDate);
        toinsert.set(STATUS,status);

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
    public static void delete(DBGedWorkflowProc mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getWorkflowStepId() != null) filter.set(WORKFLOWSTEPID, mask.getWorkflowStepId());
            if(mask.getBeginDate() != null) filter.set(BEGINDATE, mask.getBeginDate());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
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
    public static int count(DBGedWorkflowProc mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getWorkflowStepId() != null) filter.set(WORKFLOWSTEPID, mask.getWorkflowStepId());
            if(mask.getBeginDate() != null) filter.set(BEGINDATE, mask.getBeginDate());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
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
    public static double avg(String field, DBGedWorkflowProc mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getWorkflowStepId() != null) filter.set(WORKFLOWSTEPID, mask.getWorkflowStepId());
            if(mask.getBeginDate() != null) filter.set(BEGINDATE, mask.getBeginDate());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
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
    public static double min(String field, DBGedWorkflowProc mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getWorkflowStepId() != null) filter.set(WORKFLOWSTEPID, mask.getWorkflowStepId());
            if(mask.getBeginDate() != null) filter.set(BEGINDATE, mask.getBeginDate());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
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
    public static double max(String field, DBGedWorkflowProc mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getWorkflowStepId() != null) filter.set(WORKFLOWSTEPID, mask.getWorkflowStepId());
            if(mask.getBeginDate() != null) filter.set(BEGINDATE, mask.getBeginDate());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
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
    public static double std(String field, DBGedWorkflowProc mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getWorkflowStepId() != null) filter.set(WORKFLOWSTEPID, mask.getWorkflowStepId());
            if(mask.getBeginDate() != null) filter.set(BEGINDATE, mask.getBeginDate());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
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
    public static String[] distinct(String field, DBGedWorkflowProc mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getWorkflowStepId() != null) filter.set(WORKFLOWSTEPID, mask.getWorkflowStepId());
            if(mask.getBeginDate() != null) filter.set(BEGINDATE, mask.getBeginDate());
            if(mask.getStatus() != null) filter.set(STATUS, mask.getStatus());
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
