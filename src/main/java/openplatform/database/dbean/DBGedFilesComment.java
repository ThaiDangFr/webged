package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedFilesComment
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedFilesComment
{
    protected boolean inError = false;
    protected String id;
    protected String fileId;
    protected String userId;
    protected String subject;
    protected String text;

    public static String ID = "id";
    public static String FILEID = "fileId";
    public static String USERID = "userId";
    public static String SUBJECT = "subject";
    public static String TEXT = "text";
    public static String TABLE = "GedFilesComment";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        fileId = null;
        userId = null;
        subject = null;
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
        DBGedFilesComment a = (DBGedFilesComment)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (fileId==null?a.getFileId()==null:fileId.equals(a.getFileId())) && (userId==null?a.getUserId()==null:userId.equals(a.getUserId())) && (subject==null?a.getSubject()==null:subject.equals(a.getSubject())) && (text==null?a.getText()==null:text.equals(a.getText()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (fileId!=null?fileId.hashCode():0) + (userId!=null?userId.hashCode():0) + (subject!=null?subject.hashCode():0) + (text!=null?text.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"fileId="+fileId+"|"+"userId="+userId+"|"+"subject="+subject+"|"+"text="+text;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedFilesComment mask = new DBGedFilesComment();
            mask.setId(id);
            DBGedFilesComment var = DBGedFilesComment.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                fileId = var.getFileId();
                userId = var.getUserId();
                subject = var.getSubject();
                text = var.getText();
            }
        }
    }

    public void initBean(DBGedFilesComment db)
    {
        this.id = db.getId();
        this.fileId = db.getFileId();
        this.userId = db.getUserId();
        this.subject = db.getSubject();
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

    public String getFileId()
    {
        return fileId;
    }

    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
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
     * @return a DBGedFilesComment table or null if nothing found or if an error occured
     **/
    public static DBGedFilesComment[] load(DBGedFilesComment mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedFilesComment table or null if nothing found or if an error occured
     **/
    public static DBGedFilesComment[] load(SQLConstraint sqlconstraint, DBGedFilesComment mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, FILEID, USERID, SUBJECT, TEXT}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedFilesComment[] result = new DBGedFilesComment[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedFilesComment();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setFileId(pvp[i].get(FILEID));
                    result[i].setUserId(pvp[i].get(USERID));
                    result[i].setSubject(pvp[i].get(SUBJECT));
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
     * @return a DBGedFilesComment object or null if nothing found or if an error occured
     **/
    public static DBGedFilesComment loadByKey(DBGedFilesComment mask)
    {
        DBGedFilesComment[] res = DBGedFilesComment.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedFilesComment object or null if nothing found or if an error occured
     **/
    public static DBGedFilesComment loadByKey(DBGedFilesComment mask, SQLConstraint sqlconstraint)
    {
        DBGedFilesComment[] res = DBGedFilesComment.load(sqlconstraint, mask);
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
        toinsert.set(USERID,userId);
        toinsert.set(SUBJECT,subject);
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
    public static void delete(DBGedFilesComment mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
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
    public static int count(DBGedFilesComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
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
    public static double avg(String field, DBGedFilesComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
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
    public static double min(String field, DBGedFilesComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
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
    public static double max(String field, DBGedFilesComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
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
    public static double std(String field, DBGedFilesComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
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
    public static String[] distinct(String field, DBGedFilesComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getSubject() != null) filter.set(SUBJECT, mask.getSubject());
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
