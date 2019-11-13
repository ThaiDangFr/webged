package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBArticleLink
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBArticleLink
{
    protected boolean inError = false;
    protected String id;
    protected String articleId;
    protected String link;

    public static String ID = "id";
    public static String ARTICLEID = "articleId";
    public static String LINK = "link";
    public static String TABLE = "ArticleLink";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        articleId = null;
        link = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBArticleLink a = (DBArticleLink)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (articleId==null?a.getArticleId()==null:articleId.equals(a.getArticleId())) && (link==null?a.getLink()==null:link.equals(a.getLink()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (articleId!=null?articleId.hashCode():0) + (link!=null?link.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"articleId="+articleId+"|"+"link="+link;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBArticleLink mask = new DBArticleLink();
            mask.setId(id);
            DBArticleLink var = DBArticleLink.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                articleId = var.getArticleId();
                link = var.getLink();
            }
        }
    }

    public void initBean(DBArticleLink db)
    {
        this.id = db.getId();
        this.articleId = db.getArticleId();
        this.link = db.getLink();
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

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    /**
     * @return a DBArticleLink table or null if nothing found or if an error occured
     **/
    public static DBArticleLink[] load(DBArticleLink mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBArticleLink table or null if nothing found or if an error occured
     **/
    public static DBArticleLink[] load(SQLConstraint sqlconstraint, DBArticleLink mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getLink() != null) filter.set(LINK, mask.getLink());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, ARTICLEID, LINK}, filter);
            if(pvp == null) return null;
            else
            {
                DBArticleLink[] result = new DBArticleLink[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBArticleLink();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setArticleId(pvp[i].get(ARTICLEID));
                    result[i].setLink(pvp[i].get(LINK));
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
     * @return a DBArticleLink object or null if nothing found or if an error occured
     **/
    public static DBArticleLink loadByKey(DBArticleLink mask)
    {
        DBArticleLink[] res = DBArticleLink.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBArticleLink object or null if nothing found or if an error occured
     **/
    public static DBArticleLink loadByKey(DBArticleLink mask, SQLConstraint sqlconstraint)
    {
        DBArticleLink[] res = DBArticleLink.load(sqlconstraint, mask);
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
        toinsert.set(LINK,link);

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
    public static void delete(DBArticleLink mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getLink() != null) filter.set(LINK, mask.getLink());
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
    public static int count(DBArticleLink mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getLink() != null) filter.set(LINK, mask.getLink());
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
    public static double avg(String field, DBArticleLink mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getLink() != null) filter.set(LINK, mask.getLink());
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
    public static double min(String field, DBArticleLink mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getLink() != null) filter.set(LINK, mask.getLink());
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
    public static double max(String field, DBArticleLink mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getLink() != null) filter.set(LINK, mask.getLink());
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
    public static double std(String field, DBArticleLink mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getLink() != null) filter.set(LINK, mask.getLink());
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
    public static String[] distinct(String field, DBArticleLink mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getLink() != null) filter.set(LINK, mask.getLink());
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
