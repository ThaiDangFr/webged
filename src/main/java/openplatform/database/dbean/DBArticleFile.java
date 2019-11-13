package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBArticleFile
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBArticleFile
{
    protected boolean inError = false;
    protected String id;
    protected String articleId;
    protected String fileBaseId;

    public static String ID = "id";
    public static String ARTICLEID = "articleId";
    public static String FILEBASEID = "fileBaseId";
    public static String TABLE = "ArticleFile";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        articleId = null;
        fileBaseId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBArticleFile a = (DBArticleFile)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (articleId==null?a.getArticleId()==null:articleId.equals(a.getArticleId())) && (fileBaseId==null?a.getFileBaseId()==null:fileBaseId.equals(a.getFileBaseId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (articleId!=null?articleId.hashCode():0) + (fileBaseId!=null?fileBaseId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"articleId="+articleId+"|"+"fileBaseId="+fileBaseId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBArticleFile mask = new DBArticleFile();
            mask.setId(id);
            DBArticleFile var = DBArticleFile.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                articleId = var.getArticleId();
                fileBaseId = var.getFileBaseId();
            }
        }
    }

    public void initBean(DBArticleFile db)
    {
        this.id = db.getId();
        this.articleId = db.getArticleId();
        this.fileBaseId = db.getFileBaseId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getArticleId()
    {
        return articleId;
    }

    public void setArticleId(String articleId)
    {
        this.articleId = articleId;
    }

    public String getFileBaseId()
    {
        return fileBaseId;
    }

    public void setFileBaseId(String fileBaseId)
    {
        this.fileBaseId = fileBaseId;
    }

    /**
     * @return a DBArticleFile table or null if nothing found or if an error occured
     **/
    public static DBArticleFile[] load(DBArticleFile mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBArticleFile table or null if nothing found or if an error occured
     **/
    public static DBArticleFile[] load(SQLConstraint sqlconstraint, DBArticleFile mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, ARTICLEID, FILEBASEID}, filter);
            if(pvp == null) return null;
            else
            {
                DBArticleFile[] result = new DBArticleFile[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBArticleFile();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setArticleId(pvp[i].get(ARTICLEID));
                    result[i].setFileBaseId(pvp[i].get(FILEBASEID));
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
     * @return a DBArticleFile object or null if nothing found or if an error occured
     **/
    public static DBArticleFile loadByKey(DBArticleFile mask)
    {
        DBArticleFile[] res = DBArticleFile.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBArticleFile object or null if nothing found or if an error occured
     **/
    public static DBArticleFile loadByKey(DBArticleFile mask, SQLConstraint sqlconstraint)
    {
        DBArticleFile[] res = DBArticleFile.load(sqlconstraint, mask);
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
        toinsert.set(ARTICLEID,articleId);
        toinsert.set(FILEBASEID,fileBaseId);

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
    public static void delete(DBArticleFile mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
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
    public static int count(DBArticleFile mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
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
    public static double avg(String field, DBArticleFile mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
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
    public static double min(String field, DBArticleFile mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
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
    public static double max(String field, DBArticleFile mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
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
    public static double std(String field, DBArticleFile mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
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
    public static String[] distinct(String field, DBArticleFile mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
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
