package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBArticleComment
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBArticleComment
{
    protected boolean inError = false;
    protected String id;
    protected String articleId;
    protected String authorId;
    protected String date;
    protected String title;
    protected String comment;

    public static String ID = "id";
    public static String ARTICLEID = "articleId";
    public static String AUTHORID = "authorId";
    public static String DATE = "date";
    public static String TITLE = "title";
    public static String COMMENT = "comment";
    public static String TABLE = "ArticleComment";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        articleId = null;
        authorId = null;
        date = null;
        title = null;
        comment = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBArticleComment a = (DBArticleComment)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (articleId==null?a.getArticleId()==null:articleId.equals(a.getArticleId())) && (authorId==null?a.getAuthorId()==null:authorId.equals(a.getAuthorId())) && (date==null?a.getDate()==null:date.equals(a.getDate())) && (title==null?a.getTitle()==null:title.equals(a.getTitle())) && (comment==null?a.getComment()==null:comment.equals(a.getComment()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (articleId!=null?articleId.hashCode():0) + (authorId!=null?authorId.hashCode():0) + (date!=null?date.hashCode():0) + (title!=null?title.hashCode():0) + (comment!=null?comment.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"articleId="+articleId+"|"+"authorId="+authorId+"|"+"date="+date+"|"+"title="+title+"|"+"comment="+comment;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBArticleComment mask = new DBArticleComment();
            mask.setId(id);
            DBArticleComment var = DBArticleComment.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                articleId = var.getArticleId();
                authorId = var.getAuthorId();
                date = var.getDate();
                title = var.getTitle();
                comment = var.getComment();
            }
        }
    }

    public void initBean(DBArticleComment db)
    {
        this.id = db.getId();
        this.articleId = db.getArticleId();
        this.authorId = db.getAuthorId();
        this.date = db.getDate();
        this.title = db.getTitle();
        this.comment = db.getComment();
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

    public String getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(String authorId)
    {
        this.authorId = authorId;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    /**
     * @return a DBArticleComment table or null if nothing found or if an error occured
     **/
    public static DBArticleComment[] load(DBArticleComment mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBArticleComment table or null if nothing found or if an error occured
     **/
    public static DBArticleComment[] load(SQLConstraint sqlconstraint, DBArticleComment mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, ARTICLEID, AUTHORID, DATE, TITLE, COMMENT}, filter);
            if(pvp == null) return null;
            else
            {
                DBArticleComment[] result = new DBArticleComment[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBArticleComment();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setArticleId(pvp[i].get(ARTICLEID));
                    result[i].setAuthorId(pvp[i].get(AUTHORID));
                    result[i].setDate(pvp[i].get(DATE));
                    result[i].setTitle(pvp[i].get(TITLE));
                    result[i].setComment(pvp[i].get(COMMENT));
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
     * @return a DBArticleComment object or null if nothing found or if an error occured
     **/
    public static DBArticleComment loadByKey(DBArticleComment mask)
    {
        DBArticleComment[] res = DBArticleComment.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBArticleComment object or null if nothing found or if an error occured
     **/
    public static DBArticleComment loadByKey(DBArticleComment mask, SQLConstraint sqlconstraint)
    {
        DBArticleComment[] res = DBArticleComment.load(sqlconstraint, mask);
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
        toinsert.set(AUTHORID,authorId);
        toinsert.set(DATE,date);
        toinsert.set(TITLE,title);
        toinsert.set(COMMENT,comment);

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
    public static void delete(DBArticleComment mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static int count(DBArticleComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static double avg(String field, DBArticleComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static double min(String field, DBArticleComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static double max(String field, DBArticleComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static double std(String field, DBArticleComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static String[] distinct(String field, DBArticleComment mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getArticleId() != null) filter.set(ARTICLEID, mask.getArticleId());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getDate() != null) filter.set(DATE, mask.getDate());
            if(mask.getTitle() != null) filter.set(TITLE, mask.getTitle());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
