package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBMailArchive
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBMailArchive
{
    protected boolean inError = false;
    protected String id;
    protected String mailAccountId;
    protected String headerMessageId;
    protected String date;
    protected String fromMail;
    protected String toMail;
    protected String subject;
    protected String messageId;
    protected String rawId;

    public static String ID = "id";
    public static String MAILACCOUNTID = "mailAccountId";
    public static String HEADERMESSAGEID = "headerMessageId";
    public static String DATE = "date";
    public static String FROMMAIL = "fromMail";
    public static String TOMAIL = "toMail";
    public static String SUBJECT = "subject";
    public static String MESSAGEID = "messageId";
    public static String RAWID = "rawId";
    public static String TABLE = "MailArchive";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        mailAccountId = null;
        headerMessageId = null;
        date = null;
        fromMail = null;
        toMail = null;
        subject = null;
        messageId = null;
        rawId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBMailArchive a = (DBMailArchive)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (mailAccountId==null?a.getMailAccountId()==null:mailAccountId.equals(a.getMailAccountId())) && (headerMessageId==null?a.getHeaderMessageId()==null:headerMessageId.equals(a.getHeaderMessageId())) && (date==null?a.getDate()==null:date.equals(a.getDate())) && (fromMail==null?a.getFromMail()==null:fromMail.equals(a.getFromMail())) && (toMail==null?a.getToMail()==null:toMail.equals(a.getToMail())) && (subject==null?a.getSubject()==null:subject.equals(a.getSubject())) && (messageId==null?a.getMessageId()==null:messageId.equals(a.getMessageId())) && (rawId==null?a.getRawId()==null:rawId.equals(a.getRawId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (mailAccountId!=null?mailAccountId.hashCode():0) + (headerMessageId!=null?headerMessageId.hashCode():0) + (date!=null?date.hashCode():0) + (fromMail!=null?fromMail.hashCode():0) + (toMail!=null?toMail.hashCode():0) + (subject!=null?subject.hashCode():0) + (messageId!=null?messageId.hashCode():0) + (rawId!=null?rawId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"mailAccountId="+mailAccountId+"|"+"headerMessageId="+headerMessageId+"|"+"date="+date+"|"+"fromMail="+fromMail+"|"+"toMail="+toMail+"|"+"subject="+subject+"|"+"messageId="+messageId+"|"+"rawId="+rawId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBMailArchive mask = new DBMailArchive();
            mask.setId(id);
            DBMailArchive var = DBMailArchive.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                mailAccountId = var.getMailAccountId();
                headerMessageId = var.getHeaderMessageId();
                date = var.getDate();
                fromMail = var.getFromMail();
                toMail = var.getToMail();
                subject = var.getSubject();
                messageId = var.getMessageId();
                rawId = var.getRawId();
            }
        }
    }

    public void initBean(DBMailArchive db)
    {
        this.id = db.getId();
        this.mailAccountId = db.getMailAccountId();
        this.headerMessageId = db.getHeaderMessageId();
        this.date = db.getDate();
        this.fromMail = db.getFromMail();
        this.toMail = db.getToMail();
        this.subject = db.getSubject();
        this.messageId = db.getMessageId();
        this.rawId = db.getRawId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMailAccountId()
    {
        return mailAccountId;
    }

    public void setMailAccountId(String mailAccountId)
    {
        this.mailAccountId = mailAccountId;
    }

    public String getHeaderMessageId()
    {
        return headerMessageId;
    }

    public void setHeaderMessageId(String headerMessageId)
    {
        this.headerMessageId = headerMessageId;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getFromMail()
    {
        return fromMail;
    }

    public void setFromMail(String fromMail)
    {
        this.fromMail = fromMail;
    }

    public String getToMail()
    {
        return toMail;
    }

    public void setToMail(String toMail)
    {
        this.toMail = toMail;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getMessageId()
    {
        return messageId;
    }

    public void setMessageId(String messageId)
    {
        this.messageId = messageId;
    }

    public String getRawId()
    {
        return rawId;
    }

    public void setRawId(String rawId)
    {
        this.rawId = rawId;
    }

    /**
     * @return a DBMailArchive table or null if nothing found or if an error occured
     **/
    public static DBMailArchive[] load(DBMailArchive mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBMailArchive table or null if nothing found or if an error occured
     **/
    public static DBMailArchive[] load(SQLConstraint sqlconstraint, DBMailArchive mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailAccountId() != null) filter.set(MAILACCOUNTID, mask.getMailAccountId());
            if(mask.getHeaderMessageId() != null) filter.set(HEADERMESSAGEID, mask.getHeaderMessageId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromMail() != null) filter.set(FROMMAIL, mask.getFromMail());
            if(mask.getToMail() != null) filter.set(TOMAIL, mask.getToMail());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getMessageId() != null) filter.set(MESSAGEID, mask.getMessageId());
            if(mask.getRawId() != null) filter.set(RAWID, mask.getRawId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, MAILACCOUNTID, HEADERMESSAGEID, DATE, FROMMAIL, TOMAIL, SUBJECT, MESSAGEID, RAWID}, filter);
            if(pvp == null) return null;
            else
            {
                DBMailArchive[] result = new DBMailArchive[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBMailArchive();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setMailAccountId(pvp[i].get(MAILACCOUNTID));
                    result[i].setHeaderMessageId(pvp[i].get(HEADERMESSAGEID));
                    result[i].setDate(pvp[i].get(DATE));
                    result[i].setFromMail(pvp[i].get(FROMMAIL));
                    result[i].setToMail(pvp[i].get(TOMAIL));
                    result[i].setSubject(pvp[i].get(SUBJECT));
                    result[i].setMessageId(pvp[i].get(MESSAGEID));
                    result[i].setRawId(pvp[i].get(RAWID));
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
     * @return a DBMailArchive object or null if nothing found or if an error occured
     **/
    public static DBMailArchive loadByKey(DBMailArchive mask)
    {
        DBMailArchive[] res = DBMailArchive.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBMailArchive object or null if nothing found or if an error occured
     **/
    public static DBMailArchive loadByKey(DBMailArchive mask, SQLConstraint sqlconstraint)
    {
        DBMailArchive[] res = DBMailArchive.load(sqlconstraint, mask);
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
        toinsert.set(MAILACCOUNTID,mailAccountId);
        toinsert.set(HEADERMESSAGEID,headerMessageId);
        toinsert.set(DATE,date);
        toinsert.set(FROMMAIL,fromMail);
        toinsert.set(TOMAIL,toMail);
        toinsert.set(SUBJECT,subject);
        toinsert.set(MESSAGEID,messageId);
        toinsert.set(RAWID,rawId);

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
    public static void delete(DBMailArchive mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailAccountId() != null) filter.set(MAILACCOUNTID, mask.getMailAccountId());
            if(mask.getHeaderMessageId() != null) filter.set(HEADERMESSAGEID, mask.getHeaderMessageId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromMail() != null) filter.set(FROMMAIL, mask.getFromMail());
            if(mask.getToMail() != null) filter.set(TOMAIL, mask.getToMail());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getMessageId() != null) filter.set(MESSAGEID, mask.getMessageId());
            if(mask.getRawId() != null) filter.set(RAWID, mask.getRawId());
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
    public static int count(DBMailArchive mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailAccountId() != null) filter.set(MAILACCOUNTID, mask.getMailAccountId());
            if(mask.getHeaderMessageId() != null) filter.set(HEADERMESSAGEID, mask.getHeaderMessageId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromMail() != null) filter.set(FROMMAIL, mask.getFromMail());
            if(mask.getToMail() != null) filter.set(TOMAIL, mask.getToMail());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getMessageId() != null) filter.set(MESSAGEID, mask.getMessageId());
            if(mask.getRawId() != null) filter.set(RAWID, mask.getRawId());
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
    public static double avg(String field, DBMailArchive mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailAccountId() != null) filter.set(MAILACCOUNTID, mask.getMailAccountId());
            if(mask.getHeaderMessageId() != null) filter.set(HEADERMESSAGEID, mask.getHeaderMessageId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromMail() != null) filter.set(FROMMAIL, mask.getFromMail());
            if(mask.getToMail() != null) filter.set(TOMAIL, mask.getToMail());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getMessageId() != null) filter.set(MESSAGEID, mask.getMessageId());
            if(mask.getRawId() != null) filter.set(RAWID, mask.getRawId());
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
    public static double min(String field, DBMailArchive mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailAccountId() != null) filter.set(MAILACCOUNTID, mask.getMailAccountId());
            if(mask.getHeaderMessageId() != null) filter.set(HEADERMESSAGEID, mask.getHeaderMessageId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromMail() != null) filter.set(FROMMAIL, mask.getFromMail());
            if(mask.getToMail() != null) filter.set(TOMAIL, mask.getToMail());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getMessageId() != null) filter.set(MESSAGEID, mask.getMessageId());
            if(mask.getRawId() != null) filter.set(RAWID, mask.getRawId());
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
    public static double max(String field, DBMailArchive mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailAccountId() != null) filter.set(MAILACCOUNTID, mask.getMailAccountId());
            if(mask.getHeaderMessageId() != null) filter.set(HEADERMESSAGEID, mask.getHeaderMessageId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromMail() != null) filter.set(FROMMAIL, mask.getFromMail());
            if(mask.getToMail() != null) filter.set(TOMAIL, mask.getToMail());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getMessageId() != null) filter.set(MESSAGEID, mask.getMessageId());
            if(mask.getRawId() != null) filter.set(RAWID, mask.getRawId());
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
    public static double std(String field, DBMailArchive mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailAccountId() != null) filter.set(MAILACCOUNTID, mask.getMailAccountId());
            if(mask.getHeaderMessageId() != null) filter.set(HEADERMESSAGEID, mask.getHeaderMessageId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromMail() != null) filter.set(FROMMAIL, mask.getFromMail());
            if(mask.getToMail() != null) filter.set(TOMAIL, mask.getToMail());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getMessageId() != null) filter.set(MESSAGEID, mask.getMessageId());
            if(mask.getRawId() != null) filter.set(RAWID, mask.getRawId());
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
    public static String[] distinct(String field, DBMailArchive mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getMailAccountId() != null) filter.set(MAILACCOUNTID, mask.getMailAccountId());
            if(mask.getHeaderMessageId() != null) filter.set(HEADERMESSAGEID, mask.getHeaderMessageId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getFromMail() != null) filter.set(FROMMAIL, mask.getFromMail());
            if(mask.getToMail() != null) filter.set(TOMAIL, mask.getToMail());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getMessageId() != null) filter.set(MESSAGEID, mask.getMessageId());
            if(mask.getRawId() != null) filter.set(RAWID, mask.getRawId());
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
