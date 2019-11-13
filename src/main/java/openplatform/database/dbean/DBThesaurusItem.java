package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBThesaurusItem
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBThesaurusItem
{
    protected boolean inError = false;
    protected String id;
    protected String text;
    protected String thesaurusId;
    protected String parentItemId;

    public static String ID = "id";
    public static String TEXT = "text";
    public static String THESAURUSID = "thesaurusId";
    public static String PARENTITEMID = "parentItemId";
    public static String TABLE = "ThesaurusItem";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        text = null;
        thesaurusId = null;
        parentItemId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBThesaurusItem a = (DBThesaurusItem)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (text==null?a.getText()==null:text.equals(a.getText())) && (thesaurusId==null?a.getThesaurusId()==null:thesaurusId.equals(a.getThesaurusId())) && (parentItemId==null?a.getParentItemId()==null:parentItemId.equals(a.getParentItemId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (text!=null?text.hashCode():0) + (thesaurusId!=null?thesaurusId.hashCode():0) + (parentItemId!=null?parentItemId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"text="+text+"|"+"thesaurusId="+thesaurusId+"|"+"parentItemId="+parentItemId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBThesaurusItem mask = new DBThesaurusItem();
            mask.setId(id);
            DBThesaurusItem var = DBThesaurusItem.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                text = var.getText();
                thesaurusId = var.getThesaurusId();
                parentItemId = var.getParentItemId();
            }
        }
    }

    public void initBean(DBThesaurusItem db)
    {
        this.id = db.getId();
        this.text = db.getText();
        this.thesaurusId = db.getThesaurusId();
        this.parentItemId = db.getParentItemId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getThesaurusId()
    {
        return thesaurusId;
    }

    public void setThesaurusId(String thesaurusId)
    {
        this.thesaurusId = thesaurusId;
    }

    public String getParentItemId()
    {
        return parentItemId;
    }

    public void setParentItemId(String parentItemId)
    {
        this.parentItemId = parentItemId;
    }

    /**
     * @return a DBThesaurusItem table or null if nothing found or if an error occured
     **/
    public static DBThesaurusItem[] load(DBThesaurusItem mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBThesaurusItem table or null if nothing found or if an error occured
     **/
    public static DBThesaurusItem[] load(SQLConstraint sqlconstraint, DBThesaurusItem mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
            if(mask.getParentItemId() != null) filter.set(PARENTITEMID, mask.getParentItemId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, TEXT, THESAURUSID, PARENTITEMID}, filter);
            if(pvp == null) return null;
            else
            {
                DBThesaurusItem[] result = new DBThesaurusItem[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBThesaurusItem();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setText(pvp[i].get(TEXT));
                    result[i].setThesaurusId(pvp[i].get(THESAURUSID));
                    result[i].setParentItemId(pvp[i].get(PARENTITEMID));
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
     * @return a DBThesaurusItem object or null if nothing found or if an error occured
     **/
    public static DBThesaurusItem loadByKey(DBThesaurusItem mask)
    {
        DBThesaurusItem[] res = DBThesaurusItem.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBThesaurusItem object or null if nothing found or if an error occured
     **/
    public static DBThesaurusItem loadByKey(DBThesaurusItem mask, SQLConstraint sqlconstraint)
    {
        DBThesaurusItem[] res = DBThesaurusItem.load(sqlconstraint, mask);
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
        toinsert.set(TEXT,text);
        toinsert.set(THESAURUSID,thesaurusId);
        toinsert.set(PARENTITEMID,parentItemId);

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
    public static void delete(DBThesaurusItem mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
            if(mask.getParentItemId() != null) filter.set(PARENTITEMID, mask.getParentItemId());
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
    public static int count(DBThesaurusItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
            if(mask.getParentItemId() != null) filter.set(PARENTITEMID, mask.getParentItemId());
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
    public static double avg(String field, DBThesaurusItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
            if(mask.getParentItemId() != null) filter.set(PARENTITEMID, mask.getParentItemId());
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
    public static double min(String field, DBThesaurusItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
            if(mask.getParentItemId() != null) filter.set(PARENTITEMID, mask.getParentItemId());
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
    public static double max(String field, DBThesaurusItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
            if(mask.getParentItemId() != null) filter.set(PARENTITEMID, mask.getParentItemId());
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
    public static double std(String field, DBThesaurusItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
            if(mask.getParentItemId() != null) filter.set(PARENTITEMID, mask.getParentItemId());
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
    public static String[] distinct(String field, DBThesaurusItem mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getText() != null) filter.set(TEXT, mask.getText());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
            if(mask.getParentItemId() != null) filter.set(PARENTITEMID, mask.getParentItemId());
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
