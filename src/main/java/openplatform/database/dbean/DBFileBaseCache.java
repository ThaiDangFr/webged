package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBFileBaseCache
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBFileBaseCache
{
    protected boolean inError = false;
    protected String cacheId;
    protected String imageId;
    protected String fileBase;
    protected String cacheFile;

    public static String CACHEID = "cacheId";
    public static String IMAGEID = "imageId";
    public static String FILEBASE = "fileBase";
    public static String CACHEFILE = "cacheFile";
    public static String TABLE = "FileBaseCache";

    private static Database db = Database.getInstance();


    public void clear()
    {
        cacheId = null;
        imageId = null;
        fileBase = null;
        cacheFile = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBFileBaseCache a = (DBFileBaseCache)obj;
        return (cacheId==null?a.getCacheId()==null:cacheId.equals(a.getCacheId())) && (imageId==null?a.getImageId()==null:imageId.equals(a.getImageId())) && (fileBase==null?a.getFileBase()==null:fileBase.equals(a.getFileBase())) && (cacheFile==null?a.getCacheFile()==null:cacheFile.equals(a.getCacheFile()));    }

    public int hashCode()
    {
        return (cacheId!=null?cacheId.hashCode():0) + (imageId!=null?imageId.hashCode():0) + (fileBase!=null?fileBase.hashCode():0) + (cacheFile!=null?cacheFile.hashCode():0);
    }

    public String toString()
    {
        return "cacheId="+cacheId+"|"+"imageId="+imageId+"|"+"fileBase="+fileBase+"|"+"cacheFile="+cacheFile;
    }

    public void refresh()
    {
        if(cacheId != null)
        {
            DBFileBaseCache mask = new DBFileBaseCache();
            mask.setCacheId(cacheId);
            DBFileBaseCache var = DBFileBaseCache.loadByKey(mask);
            if(var != null)
            {
                cacheId = var.getCacheId();
                imageId = var.getImageId();
                fileBase = var.getFileBase();
                cacheFile = var.getCacheFile();
            }
        }
    }

    public void initBean(DBFileBaseCache db)
    {
        this.cacheId = db.getCacheId();
        this.imageId = db.getImageId();
        this.fileBase = db.getFileBase();
        this.cacheFile = db.getCacheFile();
    }

    public String getCacheId()
    {
        return cacheId;
    }

    public void setCacheId(String cacheId)
    {
        this.cacheId = cacheId;
    }

    public String getImageId()
    {
        return imageId;
    }

    public void setImageId(String imageId)
    {
        this.imageId = imageId;
    }

    public String getFileBase()
    {
        return fileBase;
    }

    public void setFileBase(String fileBase)
    {
        this.fileBase = fileBase;
    }

    public String getCacheFile()
    {
        return cacheFile;
    }

    public void setCacheFile(String cacheFile)
    {
        this.cacheFile = cacheFile;
    }

    /**
     * @return a DBFileBaseCache table or null if nothing found or if an error occured
     **/
    public static DBFileBaseCache[] load(DBFileBaseCache mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBFileBaseCache table or null if nothing found or if an error occured
     **/
    public static DBFileBaseCache[] load(SQLConstraint sqlconstraint, DBFileBaseCache mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCacheId() != null) filter.set(CACHEID, mask.getCacheId());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getFileBase() != null) filter.set(FILEBASE, mask.getFileBase());
            if(mask.getCacheFile() != null) filter.set(CACHEFILE, mask.getCacheFile());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{CACHEID, IMAGEID, FILEBASE, CACHEFILE}, filter);
            if(pvp == null) return null;
            else
            {
                DBFileBaseCache[] result = new DBFileBaseCache[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBFileBaseCache();
                    result[i].setCacheId(pvp[i].get(CACHEID));
                    result[i].setImageId(pvp[i].get(IMAGEID));
                    result[i].setFileBase(pvp[i].get(FILEBASE));
                    result[i].setCacheFile(pvp[i].get(CACHEFILE));
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
     * @return a DBFileBaseCache object or null if nothing found or if an error occured
     **/
    public static DBFileBaseCache loadByKey(DBFileBaseCache mask)
    {
        DBFileBaseCache[] res = DBFileBaseCache.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBFileBaseCache object or null if nothing found or if an error occured
     **/
    public static DBFileBaseCache loadByKey(DBFileBaseCache mask, SQLConstraint sqlconstraint)
    {
        DBFileBaseCache[] res = DBFileBaseCache.load(sqlconstraint, mask);
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
        toinsert.set(CACHEID,cacheId);
        toinsert.set(IMAGEID,imageId);
        toinsert.set(FILEBASE,fileBase);
        toinsert.set(CACHEFILE,cacheFile);

        // Store a new entry
        if(cacheId == null)
        {
            try
            {
                cacheId = db.insert(TABLE, toinsert);
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
            filter.set(CACHEID, cacheId);

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
        if(cacheId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(CACHEID, cacheId);

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
    public static void delete(DBFileBaseCache mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCacheId() != null) filter.set(CACHEID, mask.getCacheId());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getFileBase() != null) filter.set(FILEBASE, mask.getFileBase());
            if(mask.getCacheFile() != null) filter.set(CACHEFILE, mask.getCacheFile());
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
    public static int count(DBFileBaseCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCacheId() != null) filter.set(CACHEID, mask.getCacheId());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getFileBase() != null) filter.set(FILEBASE, mask.getFileBase());
            if(mask.getCacheFile() != null) filter.set(CACHEFILE, mask.getCacheFile());
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
    public static double avg(String field, DBFileBaseCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCacheId() != null) filter.set(CACHEID, mask.getCacheId());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getFileBase() != null) filter.set(FILEBASE, mask.getFileBase());
            if(mask.getCacheFile() != null) filter.set(CACHEFILE, mask.getCacheFile());
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
    public static double min(String field, DBFileBaseCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCacheId() != null) filter.set(CACHEID, mask.getCacheId());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getFileBase() != null) filter.set(FILEBASE, mask.getFileBase());
            if(mask.getCacheFile() != null) filter.set(CACHEFILE, mask.getCacheFile());
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
    public static double max(String field, DBFileBaseCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCacheId() != null) filter.set(CACHEID, mask.getCacheId());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getFileBase() != null) filter.set(FILEBASE, mask.getFileBase());
            if(mask.getCacheFile() != null) filter.set(CACHEFILE, mask.getCacheFile());
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
    public static double std(String field, DBFileBaseCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCacheId() != null) filter.set(CACHEID, mask.getCacheId());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getFileBase() != null) filter.set(FILEBASE, mask.getFileBase());
            if(mask.getCacheFile() != null) filter.set(CACHEFILE, mask.getCacheFile());
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
    public static String[] distinct(String field, DBFileBaseCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCacheId() != null) filter.set(CACHEID, mask.getCacheId());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
            if(mask.getFileBase() != null) filter.set(FILEBASE, mask.getFileBase());
            if(mask.getCacheFile() != null) filter.set(CACHEFILE, mask.getCacheFile());
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
