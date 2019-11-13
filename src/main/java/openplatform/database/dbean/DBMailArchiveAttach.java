package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBMailArchiveAttach
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBMailArchiveAttach
{
    protected boolean inError = false;
    protected String id;
    protected String mailArchiveId;
    protected String attachmentId;

    public static String ID = "id";
    public static String MAILARCHIVEID = "mailArchiveId";
    public static String ATTACHMENTID = "attachmentId";
    public static String TABLE = "MailArchiveAttach";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        mailArchiveId = null;
        attachmentId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBMailArchiveAttach a = (DBMailArchiveAttach)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (mailArchiveId==null?a.getMailArchiveId()==null:mailArchiveId.equals(a.getMailArchiveId())) && (attachmentId==null?a.getAttachmentId()==null:attachmentId.equals(a.getAttachmentId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (mailArchiveId!=null?mailArchiveId.hashCode():0) + (attachmentId!=null?attachmentId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"mailArchiveId="+mailArchiveId+"|"+"attachmentId="+attachmentId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBMailArchiveAttach mask = new DBMailArchiveAttach();
            mask.setId(id);
            DBMailArchiveAttach var = DBMailArchiveAttach.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                mailArchiveId = var.getMailArchiveId();
                attachmentId = var.getAttachmentId();
            }
        }
    }

    public void initBean(DBMailArchiveAttach db)
    {
        this.id = db.getId();
        this.mailArchiveId = db.getMailArchiveId();
        this.attachmentId = db.getAttachmentId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMailArchiveId()
    {
        return mailArchiveId;
    }

    public void setMailArchiveId(String mailArchiveId)
    {
        this.mailArchiveId = mailArchiveId;
    }

    public String getAttachmentId()
    {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId)
    {
        this.attachmentId = attachmentId;
    }

    /**
     * @return a DBMailArchiveAttach table or null if nothing found or if an error occured
     **/
    public static DBMailArchiveAttach[] load(DBMailArchiveAttach mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBMailArchiveAttach table or null if nothing found or if an error occured
     **/
    public static DBMailArchiveAttach[] load(SQLConstraint sqlconstraint, DBMailArchiveAttach mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailArchiveId() != null) filter.set(MAILARCHIVEID, mask.getMailArchiveId());
            if(mask.getAttachmentId() != null) filter.set(ATTACHMENTID, mask.getAttachmentId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, MAILARCHIVEID, ATTACHMENTID}, filter);
            if(pvp == null) return null;
            else
            {
                DBMailArchiveAttach[] result = new DBMailArchiveAttach[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBMailArchiveAttach();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setMailArchiveId(pvp[i].get(MAILARCHIVEID));
                    result[i].setAttachmentId(pvp[i].get(ATTACHMENTID));
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
     * @return a DBMailArchiveAttach object or null if nothing found or if an error occured
     **/
    public static DBMailArchiveAttach loadByKey(DBMailArchiveAttach mask)
    {
        DBMailArchiveAttach[] res = DBMailArchiveAttach.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBMailArchiveAttach object or null if nothing found or if an error occured
     **/
    public static DBMailArchiveAttach loadByKey(DBMailArchiveAttach mask, SQLConstraint sqlconstraint)
    {
        DBMailArchiveAttach[] res = DBMailArchiveAttach.load(sqlconstraint, mask);
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
        toinsert.set(MAILARCHIVEID,mailArchiveId);
        toinsert.set(ATTACHMENTID,attachmentId);

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
    public static void delete(DBMailArchiveAttach mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailArchiveId() != null) filter.set(MAILARCHIVEID, mask.getMailArchiveId());
            if(mask.getAttachmentId() != null) filter.set(ATTACHMENTID, mask.getAttachmentId());
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
    public static int count(DBMailArchiveAttach mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailArchiveId() != null) filter.set(MAILARCHIVEID, mask.getMailArchiveId());
            if(mask.getAttachmentId() != null) filter.set(ATTACHMENTID, mask.getAttachmentId());
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
    public static double avg(String field, DBMailArchiveAttach mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailArchiveId() != null) filter.set(MAILARCHIVEID, mask.getMailArchiveId());
            if(mask.getAttachmentId() != null) filter.set(ATTACHMENTID, mask.getAttachmentId());
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
    public static double min(String field, DBMailArchiveAttach mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailArchiveId() != null) filter.set(MAILARCHIVEID, mask.getMailArchiveId());
            if(mask.getAttachmentId() != null) filter.set(ATTACHMENTID, mask.getAttachmentId());
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
    public static double max(String field, DBMailArchiveAttach mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailArchiveId() != null) filter.set(MAILARCHIVEID, mask.getMailArchiveId());
            if(mask.getAttachmentId() != null) filter.set(ATTACHMENTID, mask.getAttachmentId());
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
    public static double std(String field, DBMailArchiveAttach mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailArchiveId() != null) filter.set(MAILARCHIVEID, mask.getMailArchiveId());
            if(mask.getAttachmentId() != null) filter.set(ATTACHMENTID, mask.getAttachmentId());
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
    public static String[] distinct(String field, DBMailArchiveAttach mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailArchiveId() != null) filter.set(MAILARCHIVEID, mask.getMailArchiveId());
            if(mask.getAttachmentId() != null) filter.set(ATTACHMENTID, mask.getAttachmentId());
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
