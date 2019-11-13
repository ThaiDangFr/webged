package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBCmsModule
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBCmsModule
{
    protected boolean inError = false;
    protected String id;
    protected String name;
    protected String visible;
    protected String css;
    protected String auth;
    protected String advConf;
    protected String advUrl;
    protected String jsp;

    public static String ID = "id";
    public static String NAME = "name";
    public static String VISIBLE = "visible";
    public static String CSS = "css";
    public static String AUTH = "auth";
    /** specific **/
    public static final String AUTH_SPECIFIC = "specific";
    /** allgroups **/
    public static final String AUTH_ALLGROUPS = "allgroups";
    /** anonymous **/
    public static final String AUTH_ANONYMOUS = "anonymous";
    /** Contains the SQL enumeration for AUTH **/
    public static final ArrayList _AUTH_LIST = new ArrayList();
    static {_AUTH_LIST.add(AUTH_SPECIFIC);_AUTH_LIST.add(AUTH_ALLGROUPS);_AUTH_LIST.add(AUTH_ANONYMOUS);}
    /** Contains the SQL HASHMAP for AUTH **/
    public static final OrdHashMap _AUTH_MAP = new OrdHashMap();
    static {_AUTH_MAP.put(AUTH_SPECIFIC,AUTH_SPECIFIC);_AUTH_MAP.put(AUTH_ALLGROUPS,AUTH_ALLGROUPS);_AUTH_MAP.put(AUTH_ANONYMOUS,AUTH_ANONYMOUS);}
    public static String ADVCONF = "advConf";
    public static String ADVURL = "advUrl";
    public static String JSP = "jsp";
    public static String TABLE = "CmsModule";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        name = null;
        visible = null;
        css = null;
        auth = null;
        advConf = null;
        advUrl = null;
        jsp = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBCmsModule a = (DBCmsModule)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (name==null?a.getName()==null:name.equals(a.getName())) && (visible==null?a.getVisible()==null:visible.equals(a.getVisible())) && (css==null?a.getCss()==null:css.equals(a.getCss())) && (auth==null?a.getAuth()==null:auth.equals(a.getAuth())) && (advConf==null?a.getAdvConf()==null:advConf.equals(a.getAdvConf())) && (advUrl==null?a.getAdvUrl()==null:advUrl.equals(a.getAdvUrl())) && (jsp==null?a.getJsp()==null:jsp.equals(a.getJsp()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (name!=null?name.hashCode():0) + (visible!=null?visible.hashCode():0) + (css!=null?css.hashCode():0) + (auth!=null?auth.hashCode():0) + (advConf!=null?advConf.hashCode():0) + (advUrl!=null?advUrl.hashCode():0) + (jsp!=null?jsp.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"name="+name+"|"+"visible="+visible+"|"+"css="+css+"|"+"auth="+auth+"|"+"advConf="+advConf+"|"+"advUrl="+advUrl+"|"+"jsp="+jsp;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBCmsModule mask = new DBCmsModule();
            mask.setId(id);
            DBCmsModule var = DBCmsModule.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                name = var.getName();
                visible = var.getVisible();
                css = var.getCss();
                auth = var.getAuth();
                advConf = var.getAdvConf();
                advUrl = var.getAdvUrl();
                jsp = var.getJsp();
            }
        }
    }

    public void initBean(DBCmsModule db)
    {
        this.id = db.getId();
        this.name = db.getName();
        this.visible = db.getVisible();
        this.css = db.getCss();
        this.auth = db.getAuth();
        this.advConf = db.getAdvConf();
        this.advUrl = db.getAdvUrl();
        this.jsp = db.getJsp();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVisible()
    {
        return visible;
    }

    public void setVisible(String visible)
    {
        this.visible = visible;
    }

    public String getCss()
    {
        return css;
    }

    public void setCss(String css)
    {
        this.css = css;
    }

    public String getAuth()
    {
        return auth;
    }

    public void setAuth(String auth)
    {
        this.auth = auth;
    }

    public String getAdvConf()
    {
        return advConf;
    }

    public void setAdvConf(String advConf)
    {
        this.advConf = advConf;
    }

    public String getAdvUrl()
    {
        return advUrl;
    }

    public void setAdvUrl(String advUrl)
    {
        this.advUrl = advUrl;
    }

    public String getJsp()
    {
        return jsp;
    }

    public void setJsp(String jsp)
    {
        this.jsp = jsp;
    }

    /**
     * @return a DBCmsModule table or null if nothing found or if an error occured
     **/
    public static DBCmsModule[] load(DBCmsModule mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBCmsModule table or null if nothing found or if an error occured
     **/
    public static DBCmsModule[] load(SQLConstraint sqlconstraint, DBCmsModule mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getAuth() != null) filter.set(AUTH, mask.getAuth());
            if(mask.getAdvConf() != null) filter.set(ADVCONF, mask.getAdvConf());
            if(mask.getAdvUrl() != null) filter.set(ADVURL, mask.getAdvUrl());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, NAME, VISIBLE, CSS, AUTH, ADVCONF, ADVURL, JSP}, filter);
            if(pvp == null) return null;
            else
            {
                DBCmsModule[] result = new DBCmsModule[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBCmsModule();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setName(pvp[i].get(NAME));
                    result[i].setVisible(pvp[i].get(VISIBLE));
                    result[i].setCss(pvp[i].get(CSS));
                    result[i].setAuth(pvp[i].get(AUTH));
                    result[i].setAdvConf(pvp[i].get(ADVCONF));
                    result[i].setAdvUrl(pvp[i].get(ADVURL));
                    result[i].setJsp(pvp[i].get(JSP));
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
     * @return a DBCmsModule object or null if nothing found or if an error occured
     **/
    public static DBCmsModule loadByKey(DBCmsModule mask)
    {
        DBCmsModule[] res = DBCmsModule.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBCmsModule object or null if nothing found or if an error occured
     **/
    public static DBCmsModule loadByKey(DBCmsModule mask, SQLConstraint sqlconstraint)
    {
        DBCmsModule[] res = DBCmsModule.load(sqlconstraint, mask);
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
        toinsert.set(NAME,name);
        toinsert.set(VISIBLE,visible);
        toinsert.set(CSS,css);
        toinsert.set(AUTH,auth);
        toinsert.set(ADVCONF,advConf);
        toinsert.set(ADVURL,advUrl);
        toinsert.set(JSP,jsp);

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
    public static void delete(DBCmsModule mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getAuth() != null) filter.set(AUTH, mask.getAuth());
            if(mask.getAdvConf() != null) filter.set(ADVCONF, mask.getAdvConf());
            if(mask.getAdvUrl() != null) filter.set(ADVURL, mask.getAdvUrl());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static int count(DBCmsModule mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getAuth() != null) filter.set(AUTH, mask.getAuth());
            if(mask.getAdvConf() != null) filter.set(ADVCONF, mask.getAdvConf());
            if(mask.getAdvUrl() != null) filter.set(ADVURL, mask.getAdvUrl());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static double avg(String field, DBCmsModule mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getAuth() != null) filter.set(AUTH, mask.getAuth());
            if(mask.getAdvConf() != null) filter.set(ADVCONF, mask.getAdvConf());
            if(mask.getAdvUrl() != null) filter.set(ADVURL, mask.getAdvUrl());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static double min(String field, DBCmsModule mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getAuth() != null) filter.set(AUTH, mask.getAuth());
            if(mask.getAdvConf() != null) filter.set(ADVCONF, mask.getAdvConf());
            if(mask.getAdvUrl() != null) filter.set(ADVURL, mask.getAdvUrl());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static double max(String field, DBCmsModule mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getAuth() != null) filter.set(AUTH, mask.getAuth());
            if(mask.getAdvConf() != null) filter.set(ADVCONF, mask.getAdvConf());
            if(mask.getAdvUrl() != null) filter.set(ADVURL, mask.getAdvUrl());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static double std(String field, DBCmsModule mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getAuth() != null) filter.set(AUTH, mask.getAuth());
            if(mask.getAdvConf() != null) filter.set(ADVCONF, mask.getAdvConf());
            if(mask.getAdvUrl() != null) filter.set(ADVURL, mask.getAdvUrl());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
    public static String[] distinct(String field, DBCmsModule mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getCss() != null) filter.set(CSS, mask.getCss());
            if(mask.getAuth() != null) filter.set(AUTH, mask.getAuth());
            if(mask.getAdvConf() != null) filter.set(ADVCONF, mask.getAdvConf());
            if(mask.getAdvUrl() != null) filter.set(ADVURL, mask.getAdvUrl());
            if(mask.getJsp() != null) filter.set(JSP, mask.getJsp());
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
