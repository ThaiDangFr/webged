package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBArticlePref
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBArticlePref
{
    protected boolean inError = false;
    protected String id;
    protected String articlePerPage;
    protected String comments;

    public static String ID = "id";
    public static String ARTICLEPERPAGE = "articlePerPage";
    public static String COMMENTS = "comments";
    public static String TABLE = "ArticlePref";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        articlePerPage = null;
        comments = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBArticlePref a = (DBArticlePref)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (articlePerPage==null?a.getArticlePerPage()==null:articlePerPage.equals(a.getArticlePerPage())) && (comments==null?a.getComments()==null:comments.equals(a.getComments()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (articlePerPage!=null?articlePerPage.hashCode():0) + (comments!=null?comments.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"articlePerPage="+articlePerPage+"|"+"comments="+comments;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBArticlePref mask = new DBArticlePref();
            mask.setId(id);
            DBArticlePref var = DBArticlePref.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                articlePerPage = var.getArticlePerPage();
                comments = var.getComments();
            }
        }
    }

    public void initBean(DBArticlePref db)
    {
        this.id = db.getId();
        this.articlePerPage = db.getArticlePerPage();
        this.comments = db.getComments();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getArticlePerPage()
    {
        return articlePerPage;
    }

    public void setArticlePerPage(String articlePerPage)
    {
        this.articlePerPage = articlePerPage;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    /**
     * @return a DBArticlePref table or null if nothing found or if an error occured
     **/
    public static DBArticlePref[] load(DBArticlePref mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBArticlePref table or null if nothing found or if an error occured
     **/
    public static DBArticlePref[] load(SQLConstraint sqlconstraint, DBArticlePref mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticlePerPage() != null) filter.set(ARTICLEPERPAGE, mask.getArticlePerPage());
            if(mask.getComments() != null) filter.set(COMMENTS, mask.getComments());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, ARTICLEPERPAGE, COMMENTS}, filter);
            if(pvp == null) return null;
            else
            {
                DBArticlePref[] result = new DBArticlePref[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBArticlePref();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setArticlePerPage(pvp[i].get(ARTICLEPERPAGE));
                    result[i].setComments(pvp[i].get(COMMENTS));
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
     * @return a DBArticlePref object or null if nothing found or if an error occured
     **/
    public static DBArticlePref loadByKey(DBArticlePref mask)
    {
        DBArticlePref[] res = DBArticlePref.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBArticlePref object or null if nothing found or if an error occured
     **/
    public static DBArticlePref loadByKey(DBArticlePref mask, SQLConstraint sqlconstraint)
    {
        DBArticlePref[] res = DBArticlePref.load(sqlconstraint, mask);
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
        toinsert.set(ARTICLEPERPAGE,articlePerPage);
        toinsert.set(COMMENTS,comments);

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
    public static void delete(DBArticlePref mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticlePerPage() != null) filter.set(ARTICLEPERPAGE, mask.getArticlePerPage());
            if(mask.getComments() != null) filter.set(COMMENTS, mask.getComments());
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
    public static int count(DBArticlePref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticlePerPage() != null) filter.set(ARTICLEPERPAGE, mask.getArticlePerPage());
            if(mask.getComments() != null) filter.set(COMMENTS, mask.getComments());
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
    public static double avg(String field, DBArticlePref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticlePerPage() != null) filter.set(ARTICLEPERPAGE, mask.getArticlePerPage());
            if(mask.getComments() != null) filter.set(COMMENTS, mask.getComments());
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
    public static double min(String field, DBArticlePref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticlePerPage() != null) filter.set(ARTICLEPERPAGE, mask.getArticlePerPage());
            if(mask.getComments() != null) filter.set(COMMENTS, mask.getComments());
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
    public static double max(String field, DBArticlePref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticlePerPage() != null) filter.set(ARTICLEPERPAGE, mask.getArticlePerPage());
            if(mask.getComments() != null) filter.set(COMMENTS, mask.getComments());
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
    public static double std(String field, DBArticlePref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticlePerPage() != null) filter.set(ARTICLEPERPAGE, mask.getArticlePerPage());
            if(mask.getComments() != null) filter.set(COMMENTS, mask.getComments());
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
    public static String[] distinct(String field, DBArticlePref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticlePerPage() != null) filter.set(ARTICLEPERPAGE, mask.getArticlePerPage());
            if(mask.getComments() != null) filter.set(COMMENTS, mask.getComments());
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
