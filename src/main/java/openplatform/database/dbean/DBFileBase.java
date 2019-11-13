package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBFileBase
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBFileBase
{
    protected boolean inError = false;
    protected String fileBaseId;
    protected String filename;
    protected String application;
    protected String date;
    protected String expired;
    protected String size;

    public static String FILEBASEID = "fileBaseId";
    public static String FILENAME = "filename";
    public static String APPLICATION = "application";
    public static String DATE = "date";
    public static String EXPIRED = "expired";
    public static String SIZE = "size";
    public static String TABLE = "FileBase";

    private static Database db = Database.getInstance();


    public void clear()
    {
        fileBaseId = null;
        filename = null;
        application = null;
        date = null;
        expired = null;
        size = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBFileBase a = (DBFileBase)obj;
        return (fileBaseId==null?a.getFileBaseId()==null:fileBaseId.equals(a.getFileBaseId())) && (filename==null?a.getFilename()==null:filename.equals(a.getFilename())) && (application==null?a.getApplication()==null:application.equals(a.getApplication())) && (date==null?a.getDate()==null:date.equals(a.getDate())) && (expired==null?a.getExpired()==null:expired.equals(a.getExpired())) && (size==null?a.getSize()==null:size.equals(a.getSize()));    }

    public int hashCode()
    {
        return (fileBaseId!=null?fileBaseId.hashCode():0) + (filename!=null?filename.hashCode():0) + (application!=null?application.hashCode():0) + (date!=null?date.hashCode():0) + (expired!=null?expired.hashCode():0) + (size!=null?size.hashCode():0);
    }

    public String toString()
    {
        return "fileBaseId="+fileBaseId+"|"+"filename="+filename+"|"+"application="+application+"|"+"date="+date+"|"+"expired="+expired+"|"+"size="+size;
    }

    public void refresh()
    {
        if(fileBaseId != null)
        {
            DBFileBase mask = new DBFileBase();
            mask.setFileBaseId(fileBaseId);
            DBFileBase var = DBFileBase.loadByKey(mask);
            if(var != null)
            {
                fileBaseId = var.getFileBaseId();
                filename = var.getFilename();
                application = var.getApplication();
                date = var.getDate();
                expired = var.getExpired();
                size = var.getSize();
            }
        }
    }

    public void initBean(DBFileBase db)
    {
        this.fileBaseId = db.getFileBaseId();
        this.filename = db.getFilename();
        this.application = db.getApplication();
        this.date = db.getDate();
        this.expired = db.getExpired();
        this.size = db.getSize();
    }

    public String getFileBaseId()
    {
        return fileBaseId;
    }

    public void setFileBaseId(String fileBaseId)
    {
        this.fileBaseId = fileBaseId;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getApplication()
    {
        return application;
    }

    public void setApplication(String application)
    {
        this.application = application;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getExpired()
    {
        return expired;
    }

    public void setExpired(String expired)
    {
        this.expired = expired;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    /**
     * @return a DBFileBase table or null if nothing found or if an error occured
     **/
    public static DBFileBase[] load(DBFileBase mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBFileBase table or null if nothing found or if an error occured
     **/
    public static DBFileBase[] load(SQLConstraint sqlconstraint, DBFileBase mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getFilename() != null) filter.set(FILENAME, mask.getFilename());
            if(mask.getApplication() != null) filter.set(APPLICATION, mask.getApplication());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getExpired() != null) filter.set(EXPIRED, mask.getExpired());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{FILEBASEID, FILENAME, APPLICATION, DATE, EXPIRED, SIZE}, filter);
            if(pvp == null) return null;
            else
            {
                DBFileBase[] result = new DBFileBase[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBFileBase();
                    result[i].setFileBaseId(pvp[i].get(FILEBASEID));
                    result[i].setFilename(pvp[i].get(FILENAME));
                    result[i].setApplication(pvp[i].get(APPLICATION));
                    result[i].setDate(pvp[i].get(DATE));
                    result[i].setExpired(pvp[i].get(EXPIRED));
                    result[i].setSize(pvp[i].get(SIZE));
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
     * @return a DBFileBase object or null if nothing found or if an error occured
     **/
    public static DBFileBase loadByKey(DBFileBase mask)
    {
        DBFileBase[] res = DBFileBase.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBFileBase object or null if nothing found or if an error occured
     **/
    public static DBFileBase loadByKey(DBFileBase mask, SQLConstraint sqlconstraint)
    {
        DBFileBase[] res = DBFileBase.load(sqlconstraint, mask);
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
        toinsert.set(FILEBASEID,fileBaseId);
        toinsert.set(FILENAME,filename);
        toinsert.set(APPLICATION,application);
        toinsert.set(DATE,date);
        toinsert.set(EXPIRED,expired);
        toinsert.set(SIZE,size);

        // Store a new entry
        if(fileBaseId == null)
        {
            try
            {
                fileBaseId = db.insert(TABLE, toinsert);
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
            filter.set(FILEBASEID, fileBaseId);

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
        if(fileBaseId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(FILEBASEID, fileBaseId);

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
    public static void delete(DBFileBase mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getFilename() != null) filter.set(FILENAME, mask.getFilename());
            if(mask.getApplication() != null) filter.set(APPLICATION, mask.getApplication());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getExpired() != null) filter.set(EXPIRED, mask.getExpired());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
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
    public static int count(DBFileBase mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getFilename() != null) filter.set(FILENAME, mask.getFilename());
            if(mask.getApplication() != null) filter.set(APPLICATION, mask.getApplication());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getExpired() != null) filter.set(EXPIRED, mask.getExpired());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
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
    public static double avg(String field, DBFileBase mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getFilename() != null) filter.set(FILENAME, mask.getFilename());
            if(mask.getApplication() != null) filter.set(APPLICATION, mask.getApplication());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getExpired() != null) filter.set(EXPIRED, mask.getExpired());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
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
    public static double min(String field, DBFileBase mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getFilename() != null) filter.set(FILENAME, mask.getFilename());
            if(mask.getApplication() != null) filter.set(APPLICATION, mask.getApplication());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getExpired() != null) filter.set(EXPIRED, mask.getExpired());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
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
    public static double max(String field, DBFileBase mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getFilename() != null) filter.set(FILENAME, mask.getFilename());
            if(mask.getApplication() != null) filter.set(APPLICATION, mask.getApplication());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getExpired() != null) filter.set(EXPIRED, mask.getExpired());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
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
    public static double std(String field, DBFileBase mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getFilename() != null) filter.set(FILENAME, mask.getFilename());
            if(mask.getApplication() != null) filter.set(APPLICATION, mask.getApplication());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getExpired() != null) filter.set(EXPIRED, mask.getExpired());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
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
    public static String[] distinct(String field, DBFileBase mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getFilename() != null) filter.set(FILENAME, mask.getFilename());
            if(mask.getApplication() != null) filter.set(APPLICATION, mask.getApplication());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getExpired() != null) filter.set(EXPIRED, mask.getExpired());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
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
