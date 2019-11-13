package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedFiles
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedFiles
{
    protected boolean inError = false;
    protected String id;
    protected String directoryId;
    protected String lockBySyst;
    protected String lockByUser;

    public static String ID = "id";
    public static String DIRECTORYID = "directoryId";
    public static String LOCKBYSYST = "lockBySyst";
    public static String LOCKBYUSER = "lockByUser";
    public static String TABLE = "GedFiles";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        directoryId = null;
        lockBySyst = null;
        lockByUser = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedFiles a = (DBGedFiles)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (directoryId==null?a.getDirectoryId()==null:directoryId.equals(a.getDirectoryId())) && (lockBySyst==null?a.getLockBySyst()==null:lockBySyst.equals(a.getLockBySyst())) && (lockByUser==null?a.getLockByUser()==null:lockByUser.equals(a.getLockByUser()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (directoryId!=null?directoryId.hashCode():0) + (lockBySyst!=null?lockBySyst.hashCode():0) + (lockByUser!=null?lockByUser.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"directoryId="+directoryId+"|"+"lockBySyst="+lockBySyst+"|"+"lockByUser="+lockByUser;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedFiles mask = new DBGedFiles();
            mask.setId(id);
            DBGedFiles var = DBGedFiles.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                directoryId = var.getDirectoryId();
                lockBySyst = var.getLockBySyst();
                lockByUser = var.getLockByUser();
            }
        }
    }

    public void initBean(DBGedFiles db)
    {
        this.id = db.getId();
        this.directoryId = db.getDirectoryId();
        this.lockBySyst = db.getLockBySyst();
        this.lockByUser = db.getLockByUser();
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

    public String getLockBySyst()
    {
        return lockBySyst;
    }

    public void setLockBySyst(String lockBySyst)
    {
        this.lockBySyst = lockBySyst;
    }

    public String getLockByUser()
    {
        return lockByUser;
    }

    public void setLockByUser(String lockByUser)
    {
        this.lockByUser = lockByUser;
    }

    /**
     * @return a DBGedFiles table or null if nothing found or if an error occured
     **/
    public static DBGedFiles[] load(DBGedFiles mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedFiles table or null if nothing found or if an error occured
     **/
    public static DBGedFiles[] load(SQLConstraint sqlconstraint, DBGedFiles mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getLockBySyst() != null) filter.set(LOCKBYSYST, mask.getLockBySyst());
            if(mask.getLockByUser() != null) filter.set(LOCKBYUSER, mask.getLockByUser());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, DIRECTORYID, LOCKBYSYST, LOCKBYUSER}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedFiles[] result = new DBGedFiles[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedFiles();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setDirectoryId(pvp[i].get(DIRECTORYID));
                    result[i].setLockBySyst(pvp[i].get(LOCKBYSYST));
                    result[i].setLockByUser(pvp[i].get(LOCKBYUSER));
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
     * @return a DBGedFiles object or null if nothing found or if an error occured
     **/
    public static DBGedFiles loadByKey(DBGedFiles mask)
    {
        DBGedFiles[] res = DBGedFiles.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedFiles object or null if nothing found or if an error occured
     **/
    public static DBGedFiles loadByKey(DBGedFiles mask, SQLConstraint sqlconstraint)
    {
        DBGedFiles[] res = DBGedFiles.load(sqlconstraint, mask);
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
        toinsert.set(LOCKBYSYST,lockBySyst);
        toinsert.set(LOCKBYUSER,lockByUser);

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
    public static void delete(DBGedFiles mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getLockBySyst() != null) filter.set(LOCKBYSYST, mask.getLockBySyst());
            if(mask.getLockByUser() != null) filter.set(LOCKBYUSER, mask.getLockByUser());
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
    public static int count(DBGedFiles mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getLockBySyst() != null) filter.set(LOCKBYSYST, mask.getLockBySyst());
            if(mask.getLockByUser() != null) filter.set(LOCKBYUSER, mask.getLockByUser());
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
    public static double avg(String field, DBGedFiles mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getLockBySyst() != null) filter.set(LOCKBYSYST, mask.getLockBySyst());
            if(mask.getLockByUser() != null) filter.set(LOCKBYUSER, mask.getLockByUser());
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
    public static double min(String field, DBGedFiles mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getLockBySyst() != null) filter.set(LOCKBYSYST, mask.getLockBySyst());
            if(mask.getLockByUser() != null) filter.set(LOCKBYUSER, mask.getLockByUser());
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
    public static double max(String field, DBGedFiles mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getLockBySyst() != null) filter.set(LOCKBYSYST, mask.getLockBySyst());
            if(mask.getLockByUser() != null) filter.set(LOCKBYUSER, mask.getLockByUser());
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
    public static double std(String field, DBGedFiles mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getLockBySyst() != null) filter.set(LOCKBYSYST, mask.getLockBySyst());
            if(mask.getLockByUser() != null) filter.set(LOCKBYUSER, mask.getLockByUser());
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
    public static String[] distinct(String field, DBGedFiles mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getLockBySyst() != null) filter.set(LOCKBYSYST, mask.getLockBySyst());
            if(mask.getLockByUser() != null) filter.set(LOCKBYUSER, mask.getLockByUser());
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
