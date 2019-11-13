package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBStyleSheet
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBStyleSheet
{
    protected boolean inError = false;
    protected String cssId;
    protected String name;
    protected String css;

    public static String CSSID = "cssId";
    public static String NAME = "name";
    public static String CSS = "css";
    public static String TABLE = "StyleSheet";

    private static Database db = Database.getInstance();


    public void clear()
    {
        cssId = null;
        name = null;
        css = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBStyleSheet a = (DBStyleSheet)obj;
        return (cssId==null?a.getCssId()==null:cssId.equals(a.getCssId())) && (name==null?a.getName()==null:name.equals(a.getName())) && (css==null?a.getCss()==null:css.equals(a.getCss()));    }

    public int hashCode()
    {
        return (cssId!=null?cssId.hashCode():0) + (name!=null?name.hashCode():0) + (css!=null?css.hashCode():0);
    }

    public String toString()
    {
        return "cssId="+cssId+"|"+"name="+name+"|"+"css="+css;
    }

    public void refresh()
    {
        if(cssId != null)
        {
            DBStyleSheet mask = new DBStyleSheet();
            mask.setCssId(cssId);
            DBStyleSheet var = DBStyleSheet.loadByKey(mask);
            if(var != null)
            {
                cssId = var.getCssId();
                name = var.getName();
                css = var.getCss();
            }
        }
    }

    public void initBean(DBStyleSheet db)
    {
        this.cssId = db.getCssId();
        this.name = db.getName();
        this.css = db.getCss();
    }

    public String getCssId()
    {
        return cssId;
    }

    public void setCssId(String cssId)
    {
        this.cssId = cssId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCss()
    {
        return css;
    }

    public void setCss(String css)
    {
        this.css = css;
    }

    /**
     * @return a DBStyleSheet table or null if nothing found or if an error occured
     **/
    public static DBStyleSheet[] load(DBStyleSheet mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBStyleSheet table or null if nothing found or if an error occured
     **/
    public static DBStyleSheet[] load(SQLConstraint sqlconstraint, DBStyleSheet mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCssId() != null) filter.set(CSSID, mask.getCssId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{CSSID, NAME, CSS}, filter);
            if(pvp == null) return null;
            else
            {
                DBStyleSheet[] result = new DBStyleSheet[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBStyleSheet();
                    result[i].setCssId(pvp[i].get(CSSID));
                    result[i].setName(pvp[i].get(NAME));
                    result[i].setCss(pvp[i].get(CSS));
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
     * @return a DBStyleSheet object or null if nothing found or if an error occured
     **/
    public static DBStyleSheet loadByKey(DBStyleSheet mask)
    {
        DBStyleSheet[] res = DBStyleSheet.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBStyleSheet object or null if nothing found or if an error occured
     **/
    public static DBStyleSheet loadByKey(DBStyleSheet mask, SQLConstraint sqlconstraint)
    {
        DBStyleSheet[] res = DBStyleSheet.load(sqlconstraint, mask);
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
        toinsert.set(CSSID,cssId);
        toinsert.set(NAME,name);
        toinsert.set(CSS,css);

        // Store a new entry
        if(cssId == null)
        {
            try
            {
                cssId = db.insert(TABLE, toinsert);
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
            filter.set(CSSID, cssId);

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
        if(cssId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(CSSID, cssId);

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
    public static void delete(DBStyleSheet mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCssId() != null) filter.set(CSSID, mask.getCssId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
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
    public static int count(DBStyleSheet mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCssId() != null) filter.set(CSSID, mask.getCssId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
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
    public static double avg(String field, DBStyleSheet mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCssId() != null) filter.set(CSSID, mask.getCssId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
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
    public static double min(String field, DBStyleSheet mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCssId() != null) filter.set(CSSID, mask.getCssId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
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
    public static double max(String field, DBStyleSheet mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCssId() != null) filter.set(CSSID, mask.getCssId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
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
    public static double std(String field, DBStyleSheet mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCssId() != null) filter.set(CSSID, mask.getCssId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
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
    public static String[] distinct(String field, DBStyleSheet mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getCssId() != null) filter.set(CSSID, mask.getCssId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
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
