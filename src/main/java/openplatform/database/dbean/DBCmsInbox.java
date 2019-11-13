package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBCmsInbox
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBCmsInbox
{
    protected boolean inError = false;
    protected String id;
    protected String isRead;
    protected String date;
    protected String fromUserId;
    protected String toUserId;
    protected String subject;
    protected String priority;
    protected String text;

    public static String ID = "id";
    public static String ISREAD = "isRead";
    public static String DATE = "date";
    public static String FROMUSERID = "fromUserId";
    public static String TOUSERID = "toUserId";
    public static String SUBJECT = "subject";
    public static String PRIORITY = "priority";
    /** normal **/
    public static final String PRIORITY_NORMAL = "normal";
    /** high **/
    public static final String PRIORITY_HIGH = "high";
    /** Contains the SQL enumeration for PRIORITY **/
    public static final ArrayList _PRIORITY_LIST = new ArrayList();
    static {_PRIORITY_LIST.add(PRIORITY_NORMAL);_PRIORITY_LIST.add(PRIORITY_HIGH);}
    /** Contains the SQL HASHMAP for PRIORITY **/
    public static final OrdHashMap _PRIORITY_MAP = new OrdHashMap();
    static {_PRIORITY_MAP.put(PRIORITY_NORMAL,PRIORITY_NORMAL);_PRIORITY_MAP.put(PRIORITY_HIGH,PRIORITY_HIGH);}
    public static String TEXT = "text";
    public static String TABLE = "CmsInbox";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        isRead = null;
        date = null;
        fromUserId = null;
        toUserId = null;
        subject = null;
        priority = null;
        text = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBCmsInbox a = (DBCmsInbox)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (isRead==null?a.getIsRead()==null:isRead.equals(a.getIsRead())) && (date==null?a.getDate()==null:date.equals(a.getDate())) && (fromUserId==null?a.getFromUserId()==null:fromUserId.equals(a.getFromUserId())) && (toUserId==null?a.getToUserId()==null:toUserId.equals(a.getToUserId())) && (subject==null?a.getSubject()==null:subject.equals(a.getSubject())) && (priority==null?a.getPriority()==null:priority.equals(a.getPriority())) && (text==null?a.getText()==null:text.equals(a.getText()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (isRead!=null?isRead.hashCode():0) + (date!=null?date.hashCode():0) + (fromUserId!=null?fromUserId.hashCode():0) + (toUserId!=null?toUserId.hashCode():0) + (subject!=null?subject.hashCode():0) + (priority!=null?priority.hashCode():0) + (text!=null?text.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"isRead="+isRead+"|"+"date="+date+"|"+"fromUserId="+fromUserId+"|"+"toUserId="+toUserId+"|"+"subject="+subject+"|"+"priority="+priority+"|"+"text="+text;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBCmsInbox mask = new DBCmsInbox();
            mask.setId(id);
            DBCmsInbox var = DBCmsInbox.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                isRead = var.getIsRead();
                date = var.getDate();
                fromUserId = var.getFromUserId();
                toUserId = var.getToUserId();
                subject = var.getSubject();
                priority = var.getPriority();
                text = var.getText();
            }
        }
    }

    public void initBean(DBCmsInbox db)
    {
        this.id = db.getId();
        this.isRead = db.getIsRead();
        this.date = db.getDate();
        this.fromUserId = db.getFromUserId();
        this.toUserId = db.getToUserId();
        this.subject = db.getSubject();
        this.priority = db.getPriority();
        this.text = db.getText();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getIsRead()
    {
        return isRead;
    }

    public void setIsRead(String isRead)
    {
        this.isRead = isRead;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getFromUserId()
    {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId)
    {
        this.fromUserId = fromUserId;
    }

    public String getToUserId()
    {
        return toUserId;
    }

    public void setToUserId(String toUserId)
    {
        this.toUserId = toUserId;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getPriority()
    {
        return priority;
    }

    public void setPriority(String priority)
    {
        this.priority = priority;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * @return a DBCmsInbox table or null if nothing found or if an error occured
     **/
    public static DBCmsInbox[] load(DBCmsInbox mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBCmsInbox table or null if nothing found or if an error occured
     **/
    public static DBCmsInbox[] load(SQLConstraint sqlconstraint, DBCmsInbox mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getIsRead() != null) filter.set(ISREAD, mask.getIsRead());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromUserId() != null) filter.set(FROMUSERID, mask.getFromUserId());
            if(mask.getToUserId() != null) filter.set(TOUSERID, mask.getToUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getPriority() != null) filter.set(PRIORITY, mask.getPriority());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, ISREAD, DATE, FROMUSERID, TOUSERID, SUBJECT, PRIORITY, TEXT}, filter);
            if(pvp == null) return null;
            else
            {
                DBCmsInbox[] result = new DBCmsInbox[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBCmsInbox();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setIsRead(pvp[i].get(ISREAD));
                    result[i].setDate(pvp[i].get(DATE));
                    result[i].setFromUserId(pvp[i].get(FROMUSERID));
                    result[i].setToUserId(pvp[i].get(TOUSERID));
                    result[i].setSubject(pvp[i].get(SUBJECT));
                    result[i].setPriority(pvp[i].get(PRIORITY));
                    result[i].setText(pvp[i].get(TEXT));
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
     * @return a DBCmsInbox object or null if nothing found or if an error occured
     **/
    public static DBCmsInbox loadByKey(DBCmsInbox mask)
    {
        DBCmsInbox[] res = DBCmsInbox.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBCmsInbox object or null if nothing found or if an error occured
     **/
    public static DBCmsInbox loadByKey(DBCmsInbox mask, SQLConstraint sqlconstraint)
    {
        DBCmsInbox[] res = DBCmsInbox.load(sqlconstraint, mask);
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
        toinsert.set(ISREAD,isRead);
        toinsert.set(DATE,date);
        toinsert.set(FROMUSERID,fromUserId);
        toinsert.set(TOUSERID,toUserId);
        toinsert.set(SUBJECT,subject);
        toinsert.set(PRIORITY,priority);
        toinsert.set(TEXT,text);

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
    public static void delete(DBCmsInbox mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getIsRead() != null) filter.set(ISREAD, mask.getIsRead());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromUserId() != null) filter.set(FROMUSERID, mask.getFromUserId());
            if(mask.getToUserId() != null) filter.set(TOUSERID, mask.getToUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getPriority() != null) filter.set(PRIORITY, mask.getPriority());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
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
    public static int count(DBCmsInbox mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getIsRead() != null) filter.set(ISREAD, mask.getIsRead());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromUserId() != null) filter.set(FROMUSERID, mask.getFromUserId());
            if(mask.getToUserId() != null) filter.set(TOUSERID, mask.getToUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getPriority() != null) filter.set(PRIORITY, mask.getPriority());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
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
    public static double avg(String field, DBCmsInbox mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getIsRead() != null) filter.set(ISREAD, mask.getIsRead());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromUserId() != null) filter.set(FROMUSERID, mask.getFromUserId());
            if(mask.getToUserId() != null) filter.set(TOUSERID, mask.getToUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getPriority() != null) filter.set(PRIORITY, mask.getPriority());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
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
    public static double min(String field, DBCmsInbox mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getIsRead() != null) filter.set(ISREAD, mask.getIsRead());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromUserId() != null) filter.set(FROMUSERID, mask.getFromUserId());
            if(mask.getToUserId() != null) filter.set(TOUSERID, mask.getToUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getPriority() != null) filter.set(PRIORITY, mask.getPriority());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
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
    public static double max(String field, DBCmsInbox mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getIsRead() != null) filter.set(ISREAD, mask.getIsRead());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromUserId() != null) filter.set(FROMUSERID, mask.getFromUserId());
            if(mask.getToUserId() != null) filter.set(TOUSERID, mask.getToUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getPriority() != null) filter.set(PRIORITY, mask.getPriority());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
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
    public static double std(String field, DBCmsInbox mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getIsRead() != null) filter.set(ISREAD, mask.getIsRead());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromUserId() != null) filter.set(FROMUSERID, mask.getFromUserId());
            if(mask.getToUserId() != null) filter.set(TOUSERID, mask.getToUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getPriority() != null) filter.set(PRIORITY, mask.getPriority());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
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
    public static String[] distinct(String field, DBCmsInbox mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getIsRead() != null) filter.set(ISREAD, mask.getIsRead());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromUserId() != null) filter.set(FROMUSERID, mask.getFromUserId());
            if(mask.getToUserId() != null) filter.set(TOUSERID, mask.getToUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getPriority() != null) filter.set(PRIORITY, mask.getPriority());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
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
