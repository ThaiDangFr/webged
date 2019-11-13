package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedTrash
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedTrash
{
    protected boolean inError = false;
    protected String id;
    protected String fileId;
    protected String expiryTime;
    protected String ownerId;

    public static String ID = "id";
    public static String FILEID = "fileId";
    public static String EXPIRYTIME = "expiryTime";
    public static String OWNERID = "ownerId";
    public static String TABLE = "GedTrash";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        fileId = null;
        expiryTime = null;
        ownerId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedTrash a = (DBGedTrash)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (fileId==null?a.getFileId()==null:fileId.equals(a.getFileId())) && (expiryTime==null?a.getExpiryTime()==null:expiryTime.equals(a.getExpiryTime())) && (ownerId==null?a.getOwnerId()==null:ownerId.equals(a.getOwnerId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (fileId!=null?fileId.hashCode():0) + (expiryTime!=null?expiryTime.hashCode():0) + (ownerId!=null?ownerId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"fileId="+fileId+"|"+"expiryTime="+expiryTime+"|"+"ownerId="+ownerId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedTrash mask = new DBGedTrash();
            mask.setId(id);
            DBGedTrash var = DBGedTrash.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                fileId = var.getFileId();
                expiryTime = var.getExpiryTime();
                ownerId = var.getOwnerId();
            }
        }
    }

    public void initBean(DBGedTrash db)
    {
        this.id = db.getId();
        this.fileId = db.getFileId();
        this.expiryTime = db.getExpiryTime();
        this.ownerId = db.getOwnerId();
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

    public String getExpiryTime()
    {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime)
    {
        this.expiryTime = expiryTime;
    }

    public String getOwnerId()
    {
        return ownerId;
    }

    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }

    /**
     * @return a DBGedTrash table or null if nothing found or if an error occured
     **/
    public static DBGedTrash[] load(DBGedTrash mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedTrash table or null if nothing found or if an error occured
     **/
    public static DBGedTrash[] load(SQLConstraint sqlconstraint, DBGedTrash mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getOwnerId() != null) filter.set(OWNERID, mask.getOwnerId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, FILEID, EXPIRYTIME, OWNERID}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedTrash[] result = new DBGedTrash[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedTrash();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setFileId(pvp[i].get(FILEID));
                    result[i].setExpiryTime(pvp[i].get(EXPIRYTIME));
                    result[i].setOwnerId(pvp[i].get(OWNERID));
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
     * @return a DBGedTrash object or null if nothing found or if an error occured
     **/
    public static DBGedTrash loadByKey(DBGedTrash mask)
    {
        DBGedTrash[] res = DBGedTrash.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedTrash object or null if nothing found or if an error occured
     **/
    public static DBGedTrash loadByKey(DBGedTrash mask, SQLConstraint sqlconstraint)
    {
        DBGedTrash[] res = DBGedTrash.load(sqlconstraint, mask);
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
        toinsert.set(EXPIRYTIME,expiryTime);
        toinsert.set(OWNERID,ownerId);

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
    public static void delete(DBGedTrash mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getOwnerId() != null) filter.set(OWNERID, mask.getOwnerId());
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
    public static int count(DBGedTrash mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getOwnerId() != null) filter.set(OWNERID, mask.getOwnerId());
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
    public static double avg(String field, DBGedTrash mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getOwnerId() != null) filter.set(OWNERID, mask.getOwnerId());
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
    public static double min(String field, DBGedTrash mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getOwnerId() != null) filter.set(OWNERID, mask.getOwnerId());
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
    public static double max(String field, DBGedTrash mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getOwnerId() != null) filter.set(OWNERID, mask.getOwnerId());
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
    public static double std(String field, DBGedTrash mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getOwnerId() != null) filter.set(OWNERID, mask.getOwnerId());
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
    public static String[] distinct(String field, DBGedTrash mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getOwnerId() != null) filter.set(OWNERID, mask.getOwnerId());
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
