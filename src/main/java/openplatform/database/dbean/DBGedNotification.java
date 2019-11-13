package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedNotification
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedNotification
{
    protected boolean inError = false;
    protected String id;
    protected String directoryId;
    protected String groupId;

    public static String ID = "id";
    public static String DIRECTORYID = "directoryId";
    public static String GROUPID = "groupId";
    public static String TABLE = "GedNotification";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        directoryId = null;
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
        DBGedNotification a = (DBGedNotification)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (directoryId==null?a.getDirectoryId()==null:directoryId.equals(a.getDirectoryId())) && (groupId==null?a.getGroupId()==null:groupId.equals(a.getGroupId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (directoryId!=null?directoryId.hashCode():0) + (groupId!=null?groupId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"directoryId="+directoryId+"|"+"groupId="+groupId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedNotification mask = new DBGedNotification();
            mask.setId(id);
            DBGedNotification var = DBGedNotification.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                directoryId = var.getDirectoryId();
                groupId = var.getGroupId();
            }
        }
    }

    public void initBean(DBGedNotification db)
    {
        this.id = db.getId();
        this.directoryId = db.getDirectoryId();
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

    public String getDirectoryId()
    {
        return directoryId;
    }

    public void setDirectoryId(String directoryId)
    {
        this.directoryId = directoryId;
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
     * @return a DBGedNotification table or null if nothing found or if an error occured
     **/
    public static DBGedNotification[] load(DBGedNotification mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedNotification table or null if nothing found or if an error occured
     **/
    public static DBGedNotification[] load(SQLConstraint sqlconstraint, DBGedNotification mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, DIRECTORYID, GROUPID}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedNotification[] result = new DBGedNotification[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedNotification();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setDirectoryId(pvp[i].get(DIRECTORYID));
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
     * @return a DBGedNotification object or null if nothing found or if an error occured
     **/
    public static DBGedNotification loadByKey(DBGedNotification mask)
    {
        DBGedNotification[] res = DBGedNotification.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedNotification object or null if nothing found or if an error occured
     **/
    public static DBGedNotification loadByKey(DBGedNotification mask, SQLConstraint sqlconstraint)
    {
        DBGedNotification[] res = DBGedNotification.load(sqlconstraint, mask);
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
        toinsert.set(DIRECTORYID,directoryId);
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
    public static void delete(DBGedNotification mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
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
    public static int count(DBGedNotification mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
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
    public static double avg(String field, DBGedNotification mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
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
    public static double min(String field, DBGedNotification mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
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
    public static double max(String field, DBGedNotification mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
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
    public static double std(String field, DBGedNotification mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
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
    public static String[] distinct(String field, DBGedNotification mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
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
