package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpAlertUser
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpAlertUser
{
    protected boolean inError = false;
    protected String id;
    protected String groupId;

    public static String ID = "id";
    public static String GROUPID = "groupId";
    public static String TABLE = "SnmpAlertUser";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        groupId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBSnmpAlertUser a = (DBSnmpAlertUser)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (groupId==null?a.getGroupId()==null:groupId.equals(a.getGroupId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (groupId!=null?groupId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"groupId="+groupId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBSnmpAlertUser mask = new DBSnmpAlertUser();
            mask.setId(id);
            DBSnmpAlertUser var = DBSnmpAlertUser.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                groupId = var.getGroupId();
            }
        }
    }

    public void initBean(DBSnmpAlertUser db)
    {
        this.id = db.getId();
        this.groupId = db.getGroupId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    /**
     * @return a DBSnmpAlertUser table or null if nothing found or if an error occured
     **/
    public static DBSnmpAlertUser[] load(DBSnmpAlertUser mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpAlertUser table or null if nothing found or if an error occured
     **/
    public static DBSnmpAlertUser[] load(SQLConstraint sqlconstraint, DBSnmpAlertUser mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, GROUPID}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpAlertUser[] result = new DBSnmpAlertUser[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpAlertUser();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setGroupId(pvp[i].get(GROUPID));
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
     * @return a DBSnmpAlertUser object or null if nothing found or if an error occured
     **/
    public static DBSnmpAlertUser loadByKey(DBSnmpAlertUser mask)
    {
        DBSnmpAlertUser[] res = DBSnmpAlertUser.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpAlertUser object or null if nothing found or if an error occured
     **/
    public static DBSnmpAlertUser loadByKey(DBSnmpAlertUser mask, SQLConstraint sqlconstraint)
    {
        DBSnmpAlertUser[] res = DBSnmpAlertUser.load(sqlconstraint, mask);
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
        toinsert.set(GROUPID,groupId);

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
    public static void delete(DBSnmpAlertUser mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static int count(DBSnmpAlertUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static double avg(String field, DBSnmpAlertUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static double min(String field, DBSnmpAlertUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static double max(String field, DBSnmpAlertUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static double std(String field, DBSnmpAlertUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static String[] distinct(String field, DBSnmpAlertUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
