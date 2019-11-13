package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpAction
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpAction
{
    protected boolean inError = false;
    protected String id;
    protected String name;
    protected String hostId;
    protected String type;
    protected String oid;

    public static String ID = "id";
    public static String NAME = "name";
    public static String HOSTID = "hostId";
    public static String TYPE = "type";
    /** Get **/
    public static final String TYPE_GET = "Get";
    /** Set **/
    public static final String TYPE_SET = "Set";
    /** Contains the SQL enumeration for TYPE **/
    public static final ArrayList _TYPE_LIST = new ArrayList();
    static {_TYPE_LIST.add(TYPE_GET);_TYPE_LIST.add(TYPE_SET);}
    /** Contains the SQL HASHMAP for TYPE **/
    public static final OrdHashMap _TYPE_MAP = new OrdHashMap();
    static {_TYPE_MAP.put(TYPE_GET,TYPE_GET);_TYPE_MAP.put(TYPE_SET,TYPE_SET);}
    public static String OID = "oid";
    public static String TABLE = "SnmpAction";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        name = null;
        hostId = null;
        type = null;
        oid = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBSnmpAction a = (DBSnmpAction)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (name==null?a.getName()==null:name.equals(a.getName())) && (hostId==null?a.getHostId()==null:hostId.equals(a.getHostId())) && (type==null?a.getType()==null:type.equals(a.getType())) && (oid==null?a.getOid()==null:oid.equals(a.getOid()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (name!=null?name.hashCode():0) + (hostId!=null?hostId.hashCode():0) + (type!=null?type.hashCode():0) + (oid!=null?oid.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"name="+name+"|"+"hostId="+hostId+"|"+"type="+type+"|"+"oid="+oid;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBSnmpAction mask = new DBSnmpAction();
            mask.setId(id);
            DBSnmpAction var = DBSnmpAction.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                name = var.getName();
                hostId = var.getHostId();
                type = var.getType();
                oid = var.getOid();
            }
        }
    }

    public void initBean(DBSnmpAction db)
    {
        this.id = db.getId();
        this.name = db.getName();
        this.hostId = db.getHostId();
        this.type = db.getType();
        this.oid = db.getOid();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getHostId()
    {
        return hostId;
    }

    public void setHostId(String hostId)
    {
        this.hostId = hostId;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getOid()
    {
        return oid;
    }

    public void setOid(String oid)
    {
        this.oid = oid;
    }

    /**
     * @return a DBSnmpAction table or null if nothing found or if an error occured
     **/
    public static DBSnmpAction[] load(DBSnmpAction mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpAction table or null if nothing found or if an error occured
     **/
    public static DBSnmpAction[] load(SQLConstraint sqlconstraint, DBSnmpAction mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, NAME, HOSTID, TYPE, OID}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpAction[] result = new DBSnmpAction[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpAction();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setName(pvp[i].get(NAME));
                    result[i].setHostId(pvp[i].get(HOSTID));
                    result[i].setType(pvp[i].get(TYPE));
                    result[i].setOid(pvp[i].get(OID));
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
     * @return a DBSnmpAction object or null if nothing found or if an error occured
     **/
    public static DBSnmpAction loadByKey(DBSnmpAction mask)
    {
        DBSnmpAction[] res = DBSnmpAction.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpAction object or null if nothing found or if an error occured
     **/
    public static DBSnmpAction loadByKey(DBSnmpAction mask, SQLConstraint sqlconstraint)
    {
        DBSnmpAction[] res = DBSnmpAction.load(sqlconstraint, mask);
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
        toinsert.set(NAME,name);
        toinsert.set(HOSTID,hostId);
        toinsert.set(TYPE,type);
        toinsert.set(OID,oid);

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
    public static void delete(DBSnmpAction mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static int count(DBSnmpAction mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static double avg(String field, DBSnmpAction mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static double min(String field, DBSnmpAction mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static double max(String field, DBSnmpAction mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static double std(String field, DBSnmpAction mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static String[] distinct(String field, DBSnmpAction mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getHostId() != null) filter.set(HOSTID, mask.getHostId());
            if(mask.getType() != null) filter.set(TYPE, mask.getType());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
