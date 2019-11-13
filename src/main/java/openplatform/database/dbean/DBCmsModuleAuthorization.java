package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBCmsModuleAuthorization
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBCmsModuleAuthorization
{
    protected boolean inError = false;
    protected String id;
    protected String moduleId;
    protected String groupId;

    public static String ID = "id";
    public static String MODULEID = "moduleId";
    public static String GROUPID = "groupId";
    public static String TABLE = "CmsModuleAuthorization";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        moduleId = null;
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
        DBCmsModuleAuthorization a = (DBCmsModuleAuthorization)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (moduleId==null?a.getModuleId()==null:moduleId.equals(a.getModuleId())) && (groupId==null?a.getGroupId()==null:groupId.equals(a.getGroupId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (moduleId!=null?moduleId.hashCode():0) + (groupId!=null?groupId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"moduleId="+moduleId+"|"+"groupId="+groupId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBCmsModuleAuthorization mask = new DBCmsModuleAuthorization();
            mask.setId(id);
            DBCmsModuleAuthorization var = DBCmsModuleAuthorization.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                moduleId = var.getModuleId();
                groupId = var.getGroupId();
            }
        }
    }

    public void initBean(DBCmsModuleAuthorization db)
    {
        this.id = db.getId();
        this.moduleId = db.getModuleId();
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

    public String getModuleId()
    {
        return moduleId;
    }

    public void setModuleId(String moduleId)
    {
        this.moduleId = moduleId;
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
     * @return a DBCmsModuleAuthorization table or null if nothing found or if an error occured
     **/
    public static DBCmsModuleAuthorization[] load(DBCmsModuleAuthorization mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBCmsModuleAuthorization table or null if nothing found or if an error occured
     **/
    public static DBCmsModuleAuthorization[] load(SQLConstraint sqlconstraint, DBCmsModuleAuthorization mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, MODULEID, GROUPID}, filter);
            if(pvp == null) return null;
            else
            {
                DBCmsModuleAuthorization[] result = new DBCmsModuleAuthorization[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBCmsModuleAuthorization();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setModuleId(pvp[i].get(MODULEID));
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
     * @return a DBCmsModuleAuthorization object or null if nothing found or if an error occured
     **/
    public static DBCmsModuleAuthorization loadByKey(DBCmsModuleAuthorization mask)
    {
        DBCmsModuleAuthorization[] res = DBCmsModuleAuthorization.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBCmsModuleAuthorization object or null if nothing found or if an error occured
     **/
    public static DBCmsModuleAuthorization loadByKey(DBCmsModuleAuthorization mask, SQLConstraint sqlconstraint)
    {
        DBCmsModuleAuthorization[] res = DBCmsModuleAuthorization.load(sqlconstraint, mask);
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
        toinsert.set(MODULEID,moduleId);
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
    public static void delete(DBCmsModuleAuthorization mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static int count(DBCmsModuleAuthorization mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static double avg(String field, DBCmsModuleAuthorization mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static double min(String field, DBCmsModuleAuthorization mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static double max(String field, DBCmsModuleAuthorization mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static double std(String field, DBCmsModuleAuthorization mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static String[] distinct(String field, DBCmsModuleAuthorization mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
