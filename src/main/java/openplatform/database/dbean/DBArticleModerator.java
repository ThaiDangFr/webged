package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBArticleModerator
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBArticleModerator
{
    protected boolean inError = false;
    protected String id;
    protected String categoryId;
    protected String userId;

    public static String ID = "id";
    public static String CATEGORYID = "categoryId";
    public static String USERID = "userId";
    public static String TABLE = "ArticleModerator";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        categoryId = null;
        userId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBArticleModerator a = (DBArticleModerator)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (categoryId==null?a.getCategoryId()==null:categoryId.equals(a.getCategoryId())) && (userId==null?a.getUserId()==null:userId.equals(a.getUserId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (categoryId!=null?categoryId.hashCode():0) + (userId!=null?userId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"categoryId="+categoryId+"|"+"userId="+userId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBArticleModerator mask = new DBArticleModerator();
            mask.setId(id);
            DBArticleModerator var = DBArticleModerator.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                categoryId = var.getCategoryId();
                userId = var.getUserId();
            }
        }
    }

    public void initBean(DBArticleModerator db)
    {
        this.id = db.getId();
        this.categoryId = db.getCategoryId();
        this.userId = db.getUserId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * @return a DBArticleModerator table or null if nothing found or if an error occured
     **/
    public static DBArticleModerator[] load(DBArticleModerator mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBArticleModerator table or null if nothing found or if an error occured
     **/
    public static DBArticleModerator[] load(SQLConstraint sqlconstraint, DBArticleModerator mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, CATEGORYID, USERID}, filter);
            if(pvp == null) return null;
            else
            {
                DBArticleModerator[] result = new DBArticleModerator[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBArticleModerator();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setCategoryId(pvp[i].get(CATEGORYID));
                    result[i].setUserId(pvp[i].get(USERID));
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
     * @return a DBArticleModerator object or null if nothing found or if an error occured
     **/
    public static DBArticleModerator loadByKey(DBArticleModerator mask)
    {
        DBArticleModerator[] res = DBArticleModerator.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBArticleModerator object or null if nothing found or if an error occured
     **/
    public static DBArticleModerator loadByKey(DBArticleModerator mask, SQLConstraint sqlconstraint)
    {
        DBArticleModerator[] res = DBArticleModerator.load(sqlconstraint, mask);
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
        toinsert.set(CATEGORYID,categoryId);
        toinsert.set(USERID,userId);

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
    public static void delete(DBArticleModerator mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
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
    public static int count(DBArticleModerator mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
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
    public static double avg(String field, DBArticleModerator mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
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
    public static double min(String field, DBArticleModerator mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
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
    public static double max(String field, DBArticleModerator mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
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
    public static double std(String field, DBArticleModerator mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
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
    public static String[] distinct(String field, DBArticleModerator mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getCategoryId() != null) filter.set(CATEGORYID, mask.getCategoryId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
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
