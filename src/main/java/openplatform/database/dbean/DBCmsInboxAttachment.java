package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBCmsInboxAttachment
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBCmsInboxAttachment
{
    protected boolean inError = false;
    protected String id;
    protected String fileBaseId;
    protected String inboxId;

    public static String ID = "id";
    public static String FILEBASEID = "fileBaseId";
    public static String INBOXID = "inboxId";
    public static String TABLE = "CmsInboxAttachment";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        fileBaseId = null;
        inboxId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBCmsInboxAttachment a = (DBCmsInboxAttachment)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (fileBaseId==null?a.getFileBaseId()==null:fileBaseId.equals(a.getFileBaseId())) && (inboxId==null?a.getInboxId()==null:inboxId.equals(a.getInboxId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (fileBaseId!=null?fileBaseId.hashCode():0) + (inboxId!=null?inboxId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"fileBaseId="+fileBaseId+"|"+"inboxId="+inboxId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBCmsInboxAttachment mask = new DBCmsInboxAttachment();
            mask.setId(id);
            DBCmsInboxAttachment var = DBCmsInboxAttachment.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                fileBaseId = var.getFileBaseId();
                inboxId = var.getInboxId();
            }
        }
    }

    public void initBean(DBCmsInboxAttachment db)
    {
        this.id = db.getId();
        this.fileBaseId = db.getFileBaseId();
        this.inboxId = db.getInboxId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFileBaseId()
    {
        return fileBaseId;
    }

    public void setFileBaseId(String fileBaseId)
    {
        this.fileBaseId = fileBaseId;
    }

    public String getInboxId()
    {
        return inboxId;
    }

    public void setInboxId(String inboxId)
    {
        this.inboxId = inboxId;
    }

    /**
     * @return a DBCmsInboxAttachment table or null if nothing found or if an error occured
     **/
    public static DBCmsInboxAttachment[] load(DBCmsInboxAttachment mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBCmsInboxAttachment table or null if nothing found or if an error occured
     **/
    public static DBCmsInboxAttachment[] load(SQLConstraint sqlconstraint, DBCmsInboxAttachment mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getInboxId() != null) filter.set(INBOXID, mask.getInboxId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, FILEBASEID, INBOXID}, filter);
            if(pvp == null) return null;
            else
            {
                DBCmsInboxAttachment[] result = new DBCmsInboxAttachment[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBCmsInboxAttachment();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setFileBaseId(pvp[i].get(FILEBASEID));
                    result[i].setInboxId(pvp[i].get(INBOXID));
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
     * @return a DBCmsInboxAttachment object or null if nothing found or if an error occured
     **/
    public static DBCmsInboxAttachment loadByKey(DBCmsInboxAttachment mask)
    {
        DBCmsInboxAttachment[] res = DBCmsInboxAttachment.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBCmsInboxAttachment object or null if nothing found or if an error occured
     **/
    public static DBCmsInboxAttachment loadByKey(DBCmsInboxAttachment mask, SQLConstraint sqlconstraint)
    {
        DBCmsInboxAttachment[] res = DBCmsInboxAttachment.load(sqlconstraint, mask);
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
        toinsert.set(FILEBASEID,fileBaseId);
        toinsert.set(INBOXID,inboxId);

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
    public static void delete(DBCmsInboxAttachment mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getInboxId() != null) filter.set(INBOXID, mask.getInboxId());
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
    public static int count(DBCmsInboxAttachment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getInboxId() != null) filter.set(INBOXID, mask.getInboxId());
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
    public static double avg(String field, DBCmsInboxAttachment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getInboxId() != null) filter.set(INBOXID, mask.getInboxId());
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
    public static double min(String field, DBCmsInboxAttachment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getInboxId() != null) filter.set(INBOXID, mask.getInboxId());
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
    public static double max(String field, DBCmsInboxAttachment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getInboxId() != null) filter.set(INBOXID, mask.getInboxId());
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
    public static double std(String field, DBCmsInboxAttachment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getInboxId() != null) filter.set(INBOXID, mask.getInboxId());
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
    public static String[] distinct(String field, DBCmsInboxAttachment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getInboxId() != null) filter.set(INBOXID, mask.getInboxId());
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
