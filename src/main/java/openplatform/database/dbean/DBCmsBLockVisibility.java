package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBCmsBLockVisibility
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBCmsBLockVisibility
{
    protected boolean inError = false;
    protected String id;
    protected String blockId;
    protected String moduleId;

    public static String ID = "id";
    public static String BLOCKID = "blockId";
    public static String MODULEID = "moduleId";
    public static String TABLE = "CmsBLockVisibility";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        blockId = null;
        moduleId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBCmsBLockVisibility a = (DBCmsBLockVisibility)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (blockId==null?a.getBlockId()==null:blockId.equals(a.getBlockId())) && (moduleId==null?a.getModuleId()==null:moduleId.equals(a.getModuleId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (blockId!=null?blockId.hashCode():0) + (moduleId!=null?moduleId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"blockId="+blockId+"|"+"moduleId="+moduleId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBCmsBLockVisibility mask = new DBCmsBLockVisibility();
            mask.setId(id);
            DBCmsBLockVisibility var = DBCmsBLockVisibility.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                blockId = var.getBlockId();
                moduleId = var.getModuleId();
            }
        }
    }

    public void initBean(DBCmsBLockVisibility db)
    {
        this.id = db.getId();
        this.blockId = db.getBlockId();
        this.moduleId = db.getModuleId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getBlockId()
    {
        return blockId;
    }

    public void setBlockId(String blockId)
    {
        this.blockId = blockId;
    }

    public String getModuleId()
    {
        return moduleId;
    }

    public void setModuleId(String moduleId)
    {
        this.moduleId = moduleId;
    }

    /**
     * @return a DBCmsBLockVisibility table or null if nothing found or if an error occured
     **/
    public static DBCmsBLockVisibility[] load(DBCmsBLockVisibility mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBCmsBLockVisibility table or null if nothing found or if an error occured
     **/
    public static DBCmsBLockVisibility[] load(SQLConstraint sqlconstraint, DBCmsBLockVisibility mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getBlockId() != null) filter.set(BLOCKID, mask.getBlockId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, BLOCKID, MODULEID}, filter);
            if(pvp == null) return null;
            else
            {
                DBCmsBLockVisibility[] result = new DBCmsBLockVisibility[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBCmsBLockVisibility();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setBlockId(pvp[i].get(BLOCKID));
                    result[i].setModuleId(pvp[i].get(MODULEID));
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
     * @return a DBCmsBLockVisibility object or null if nothing found or if an error occured
     **/
    public static DBCmsBLockVisibility loadByKey(DBCmsBLockVisibility mask)
    {
        DBCmsBLockVisibility[] res = DBCmsBLockVisibility.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBCmsBLockVisibility object or null if nothing found or if an error occured
     **/
    public static DBCmsBLockVisibility loadByKey(DBCmsBLockVisibility mask, SQLConstraint sqlconstraint)
    {
        DBCmsBLockVisibility[] res = DBCmsBLockVisibility.load(sqlconstraint, mask);
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
        toinsert.set(BLOCKID,blockId);
        toinsert.set(MODULEID,moduleId);

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
    public static void delete(DBCmsBLockVisibility mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getBlockId() != null) filter.set(BLOCKID, mask.getBlockId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static int count(DBCmsBLockVisibility mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getBlockId() != null) filter.set(BLOCKID, mask.getBlockId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static double avg(String field, DBCmsBLockVisibility mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getBlockId() != null) filter.set(BLOCKID, mask.getBlockId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static double min(String field, DBCmsBLockVisibility mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getBlockId() != null) filter.set(BLOCKID, mask.getBlockId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static double max(String field, DBCmsBLockVisibility mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getBlockId() != null) filter.set(BLOCKID, mask.getBlockId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static double std(String field, DBCmsBLockVisibility mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getBlockId() != null) filter.set(BLOCKID, mask.getBlockId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
    public static String[] distinct(String field, DBCmsBLockVisibility mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getBlockId() != null) filter.set(BLOCKID, mask.getBlockId());
            if(mask.getModuleId() != null) filter.set(MODULEID, mask.getModuleId());
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
