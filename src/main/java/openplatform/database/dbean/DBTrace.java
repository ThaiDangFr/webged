package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBTrace
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBTrace
{
    protected boolean inError = false;
    protected String id;
    protected String date;
    protected String moduleId;
    protected String object;
    protected String action;
    protected String statusMsg;
    protected String statusUserId;
    protected String statusGroupId;
    protected String ipAddress;

    public static String ID = "id";
    public static String DATE = "date";
    public static String MODULEID = "moduleId";
    public static String OBJECT = "object";
    public static String ACTION = "action";
    /** index **/
    public static final String ACTION_INDEX = "index";
    /** save **/
    public static final String ACTION_SAVE = "save";
    /** delete **/
    public static final String ACTION_DELETE = "delete";
    /** submit **/
    public static final String ACTION_SUBMIT = "submit";
    /** recover **/
    public static final String ACTION_RECOVER = "recover";
    /** expire **/
    public static final String ACTION_EXPIRE = "expire";
    /** move **/
    public static final String ACTION_MOVE = "move";
    /** notify **/
    public static final String ACTION_NOTIFY = "notify";
    /** approve **/
    public static final String ACTION_APPROVE = "approve";
    /** disapprove **/
    public static final String ACTION_DISAPPROVE = "disapprove";
    /** revise **/
    public static final String ACTION_REVISE = "revise";
    /** Contains the SQL enumeration for ACTION **/
    public static final ArrayList _ACTION_LIST = new ArrayList();
    static {_ACTION_LIST.add(ACTION_INDEX);_ACTION_LIST.add(ACTION_SAVE);_ACTION_LIST.add(ACTION_DELETE);_ACTION_LIST.add(ACTION_SUBMIT);_ACTION_LIST.add(ACTION_RECOVER);_ACTION_LIST.add(ACTION_EXPIRE);_ACTION_LIST.add(ACTION_MOVE);_ACTION_LIST.add(ACTION_NOTIFY);_ACTION_LIST.add(ACTION_APPROVE);_ACTION_LIST.add(ACTION_DISAPPROVE);_ACTION_LIST.add(ACTION_REVISE);}
    /** Contains the SQL HASHMAP for ACTION **/
    public static final OrdHashMap _ACTION_MAP = new OrdHashMap();
    static {_ACTION_MAP.put(ACTION_INDEX,ACTION_INDEX);_ACTION_MAP.put(ACTION_SAVE,ACTION_SAVE);_ACTION_MAP.put(ACTION_DELETE,ACTION_DELETE);_ACTION_MAP.put(ACTION_SUBMIT,ACTION_SUBMIT);_ACTION_MAP.put(ACTION_RECOVER,ACTION_RECOVER);_ACTION_MAP.put(ACTION_EXPIRE,ACTION_EXPIRE);_ACTION_MAP.put(ACTION_MOVE,ACTION_MOVE);_ACTION_MAP.put(ACTION_NOTIFY,ACTION_NOTIFY);_ACTION_MAP.put(ACTION_APPROVE,ACTION_APPROVE);_ACTION_MAP.put(ACTION_DISAPPROVE,ACTION_DISAPPROVE);_ACTION_MAP.put(ACTION_REVISE,ACTION_REVISE);}
    public static String STATUSMSG = "statusMsg";
    /** fail **/
    public static final String STATUSMSG_FAIL = "fail";
    /** ok **/
    public static final String STATUSMSG_OK = "ok";
    /** Contains the SQL enumeration for STATUSMSG **/
    public static final ArrayList _STATUSMSG_LIST = new ArrayList();
    static {_STATUSMSG_LIST.add(STATUSMSG_FAIL);_STATUSMSG_LIST.add(STATUSMSG_OK);}
    /** Contains the SQL HASHMAP for STATUSMSG **/
    public static final OrdHashMap _STATUSMSG_MAP = new OrdHashMap();
    static {_STATUSMSG_MAP.put(STATUSMSG_FAIL,STATUSMSG_FAIL);_STATUSMSG_MAP.put(STATUSMSG_OK,STATUSMSG_OK);}
    public static String STATUSUSERID = "statusUserId";
    public static String STATUSGROUPID = "statusGroupId";
    public static String IPADDRESS = "ipAddress";
    public static String TABLE = "Trace";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        date = null;
        moduleId = null;
        object = null;
        action = null;
        statusMsg = null;
        statusUserId = null;
        statusGroupId = null;
        ipAddress = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBTrace a = (DBTrace)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (date==null?a.getDate()==null:date.equals(a.getDate())) && (moduleId==null?a.getModuleId()==null:moduleId.equals(a.getModuleId())) && (object==null?a.getObject()==null:object.equals(a.getObject())) && (action==null?a.getAction()==null:action.equals(a.getAction())) && (statusMsg==null?a.getStatusMsg()==null:statusMsg.equals(a.getStatusMsg())) && (statusUserId==null?a.getStatusUserId()==null:statusUserId.equals(a.getStatusUserId())) && (statusGroupId==null?a.getStatusGroupId()==null:statusGroupId.equals(a.getStatusGroupId())) && (ipAddress==null?a.getIpAddress()==null:ipAddress.equals(a.getIpAddress()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (date!=null?date.hashCode():0) + (moduleId!=null?moduleId.hashCode():0) + (object!=null?object.hashCode():0) + (action!=null?action.hashCode():0) + (statusMsg!=null?statusMsg.hashCode():0) + (statusUserId!=null?statusUserId.hashCode():0) + (statusGroupId!=null?statusGroupId.hashCode():0) + (ipAddress!=null?ipAddress.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"date="+date+"|"+"moduleId="+moduleId+"|"+"object="+object+"|"+"action="+action+"|"+"statusMsg="+statusMsg+"|"+"statusUserId="+statusUserId+"|"+"statusGroupId="+statusGroupId+"|"+"ipAddress="+ipAddress;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBTrace mask = new DBTrace();
            mask.setId(id);
            DBTrace var = DBTrace.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                date = var.getDate();
                moduleId = var.getModuleId();
                object = var.getObject();
                action = var.getAction();
                statusMsg = var.getStatusMsg();
                statusUserId = var.getStatusUserId();
                statusGroupId = var.getStatusGroupId();
                ipAddress = var.getIpAddress();
            }
        }
    }

    public void initBean(DBTrace db)
    {
        this.id = db.getId();
        this.date = db.getDate();
        this.moduleId = db.getModuleId();
        this.object = db.getObject();
        this.action = db.getAction();
        this.statusMsg = db.getStatusMsg();
        this.statusUserId = db.getStatusUserId();
        this.statusGroupId = db.getStatusGroupId();
        this.ipAddress = db.getIpAddress();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getModuleId()
    {
        return moduleId;
    }

    public void setModuleId(String moduleId)
    {
        this.moduleId = moduleId;
    }

    public String getObject()
    {
        return object;
    }

    public void setObject(String object)
    {
        this.object = object;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getStatusMsg()
    {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg)
    {
        this.statusMsg = statusMsg;
    }

    public String getStatusUserId()
    {
        return statusUserId;
    }

    public void setStatusUserId(String statusUserId)
    {
        this.statusUserId = statusUserId;
    }

    public String getStatusGroupId()
    {
        return statusGroupId;
    }

    public void setStatusGroupId(String statusGroupId)
    {
        this.statusGroupId = statusGroupId;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    /**
     * @return a DBTrace table or null if nothing found or if an error occured
     **/
    public static DBTrace[] load(DBTrace mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBTrace table or null if nothing found or if an error occured
     **/
    public static DBTrace[] load(SQLConstraint sqlconstraint, DBTrace mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getObject() != null) filter.set(OBJECT, mask.getObject());
            if(mask.getAction() != null) filter.set(ACTION, mask.getAction());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
            if(mask.getStatusUserId() != null) filter.set(STATUSUSERID, mask.getStatusUserId());
            if(mask.getStatusGroupId() != null) filter.set(STATUSGROUPID, mask.getStatusGroupId());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, DATE, MODULEID, OBJECT, ACTION, STATUSMSG, STATUSUSERID, STATUSGROUPID, IPADDRESS}, filter);
            if(pvp == null) return null;
            else
            {
                DBTrace[] result = new DBTrace[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBTrace();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setDate(pvp[i].get(DATE));
                    result[i].setModuleId(pvp[i].get(MODULEID));
                    result[i].setObject(pvp[i].get(OBJECT));
                    result[i].setAction(pvp[i].get(ACTION));
                    result[i].setStatusMsg(pvp[i].get(STATUSMSG));
                    result[i].setStatusUserId(pvp[i].get(STATUSUSERID));
                    result[i].setStatusGroupId(pvp[i].get(STATUSGROUPID));
                    result[i].setIpAddress(pvp[i].get(IPADDRESS));
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
     * @return a DBTrace object or null if nothing found or if an error occured
     **/
    public static DBTrace loadByKey(DBTrace mask)
    {
        DBTrace[] res = DBTrace.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBTrace object or null if nothing found or if an error occured
     **/
    public static DBTrace loadByKey(DBTrace mask, SQLConstraint sqlconstraint)
    {
        DBTrace[] res = DBTrace.load(sqlconstraint, mask);
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
        toinsert.set(DATE,date);
        toinsert.set(MODULEID,moduleId);
        toinsert.set(OBJECT,object);
        toinsert.set(ACTION,action);
        toinsert.set(STATUSMSG,statusMsg);
        toinsert.set(STATUSUSERID,statusUserId);
        toinsert.set(STATUSGROUPID,statusGroupId);
        toinsert.set(IPADDRESS,ipAddress);

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
    public static void delete(DBTrace mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getObject() != null) filter.set(OBJECT, mask.getObject());
            if(mask.getAction() != null) filter.set(ACTION, mask.getAction());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
            if(mask.getStatusUserId() != null) filter.set(STATUSUSERID, mask.getStatusUserId());
            if(mask.getStatusGroupId() != null) filter.set(STATUSGROUPID, mask.getStatusGroupId());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
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
    public static int count(DBTrace mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getObject() != null) filter.set(OBJECT, mask.getObject());
            if(mask.getAction() != null) filter.set(ACTION, mask.getAction());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
            if(mask.getStatusUserId() != null) filter.set(STATUSUSERID, mask.getStatusUserId());
            if(mask.getStatusGroupId() != null) filter.set(STATUSGROUPID, mask.getStatusGroupId());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
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
    public static double avg(String field, DBTrace mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getObject() != null) filter.set(OBJECT, mask.getObject());
            if(mask.getAction() != null) filter.set(ACTION, mask.getAction());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
            if(mask.getStatusUserId() != null) filter.set(STATUSUSERID, mask.getStatusUserId());
            if(mask.getStatusGroupId() != null) filter.set(STATUSGROUPID, mask.getStatusGroupId());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
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
    public static double min(String field, DBTrace mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getObject() != null) filter.set(OBJECT, mask.getObject());
            if(mask.getAction() != null) filter.set(ACTION, mask.getAction());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
            if(mask.getStatusUserId() != null) filter.set(STATUSUSERID, mask.getStatusUserId());
            if(mask.getStatusGroupId() != null) filter.set(STATUSGROUPID, mask.getStatusGroupId());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
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
    public static double max(String field, DBTrace mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getObject() != null) filter.set(OBJECT, mask.getObject());
            if(mask.getAction() != null) filter.set(ACTION, mask.getAction());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
            if(mask.getStatusUserId() != null) filter.set(STATUSUSERID, mask.getStatusUserId());
            if(mask.getStatusGroupId() != null) filter.set(STATUSGROUPID, mask.getStatusGroupId());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
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
    public static double std(String field, DBTrace mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getObject() != null) filter.set(OBJECT, mask.getObject());
            if(mask.getAction() != null) filter.set(ACTION, mask.getAction());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
            if(mask.getStatusUserId() != null) filter.set(STATUSUSERID, mask.getStatusUserId());
            if(mask.getStatusGroupId() != null) filter.set(STATUSGROUPID, mask.getStatusGroupId());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
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
    public static String[] distinct(String field, DBTrace mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getObject() != null) filter.set(OBJECT, mask.getObject());
            if(mask.getAction() != null) filter.set(ACTION, mask.getAction());
            if(mask.getStatusMsg() != null) filter.set(STATUSMSG, mask.getStatusMsg());
            if(mask.getStatusUserId() != null) filter.set(STATUSUSERID, mask.getStatusUserId());
            if(mask.getStatusGroupId() != null) filter.set(STATUSGROUPID, mask.getStatusGroupId());
            if(mask.getIpAddress() != null) filter.set(IPADDRESS, mask.getIpAddress());
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
