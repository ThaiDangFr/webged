package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBConversionCache
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBConversionCache
{
    protected boolean inError = false;
    protected String id;
    protected String extension;
    protected String param;
    protected String orifile;
    protected String cachefile;

    public static String ID = "id";
    public static String EXTENSION = "extension";
    public static String PARAM = "param";
    public static String ORIFILE = "orifile";
    public static String CACHEFILE = "cachefile";
    public static String TABLE = "ConversionCache";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        extension = null;
        param = null;
        orifile = null;
        cachefile = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBConversionCache a = (DBConversionCache)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (extension==null?a.getExtension()==null:extension.equals(a.getExtension())) && (param==null?a.getParam()==null:param.equals(a.getParam())) && (orifile==null?a.getOrifile()==null:orifile.equals(a.getOrifile())) && (cachefile==null?a.getCachefile()==null:cachefile.equals(a.getCachefile()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (extension!=null?extension.hashCode():0) + (param!=null?param.hashCode():0) + (orifile!=null?orifile.hashCode():0) + (cachefile!=null?cachefile.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"extension="+extension+"|"+"param="+param+"|"+"orifile="+orifile+"|"+"cachefile="+cachefile;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBConversionCache mask = new DBConversionCache();
            mask.setId(id);
            DBConversionCache var = DBConversionCache.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                extension = var.getExtension();
                param = var.getParam();
                orifile = var.getOrifile();
                cachefile = var.getCachefile();
            }
        }
    }

    public void initBean(DBConversionCache db)
    {
        this.id = db.getId();
        this.extension = db.getExtension();
        this.param = db.getParam();
        this.orifile = db.getOrifile();
        this.cachefile = db.getCachefile();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getExtension()
    {
        return extension;
    }

    public void setExtension(String extension)
    {
        this.extension = extension;
    }

    public String getParam()
    {
        return param;
    }

    public void setParam(String param)
    {
        this.param = param;
    }

    public String getOrifile()
    {
        return orifile;
    }

    public void setOrifile(String orifile)
    {
        this.orifile = orifile;
    }

    public String getCachefile()
    {
        return cachefile;
    }

    public void setCachefile(String cachefile)
    {
        this.cachefile = cachefile;
    }

    /**
     * @return a DBConversionCache table or null if nothing found or if an error occured
     **/
    public static DBConversionCache[] load(DBConversionCache mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBConversionCache table or null if nothing found or if an error occured
     **/
    public static DBConversionCache[] load(SQLConstraint sqlconstraint, DBConversionCache mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getParam() != null) filter.set(PARAM, mask.getParam());
            if(mask.getOrifile() != null) filter.set(ORIFILE, mask.getOrifile());
            if(mask.getCachefile() != null) filter.set(CACHEFILE, mask.getCachefile());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, EXTENSION, PARAM, ORIFILE, CACHEFILE}, filter);
            if(pvp == null) return null;
            else
            {
                DBConversionCache[] result = new DBConversionCache[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBConversionCache();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setExtension(pvp[i].get(EXTENSION));
                    result[i].setParam(pvp[i].get(PARAM));
                    result[i].setOrifile(pvp[i].get(ORIFILE));
                    result[i].setCachefile(pvp[i].get(CACHEFILE));
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
     * @return a DBConversionCache object or null if nothing found or if an error occured
     **/
    public static DBConversionCache loadByKey(DBConversionCache mask)
    {
        DBConversionCache[] res = DBConversionCache.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBConversionCache object or null if nothing found or if an error occured
     **/
    public static DBConversionCache loadByKey(DBConversionCache mask, SQLConstraint sqlconstraint)
    {
        DBConversionCache[] res = DBConversionCache.load(sqlconstraint, mask);
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
        toinsert.set(EXTENSION,extension);
        toinsert.set(PARAM,param);
        toinsert.set(ORIFILE,orifile);
        toinsert.set(CACHEFILE,cachefile);

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
    public static void delete(DBConversionCache mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getParam() != null) filter.set(PARAM, mask.getParam());
            if(mask.getOrifile() != null) filter.set(ORIFILE, mask.getOrifile());
            if(mask.getCachefile() != null) filter.set(CACHEFILE, mask.getCachefile());
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
    public static int count(DBConversionCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getParam() != null) filter.set(PARAM, mask.getParam());
            if(mask.getOrifile() != null) filter.set(ORIFILE, mask.getOrifile());
            if(mask.getCachefile() != null) filter.set(CACHEFILE, mask.getCachefile());
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
    public static double avg(String field, DBConversionCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getParam() != null) filter.set(PARAM, mask.getParam());
            if(mask.getOrifile() != null) filter.set(ORIFILE, mask.getOrifile());
            if(mask.getCachefile() != null) filter.set(CACHEFILE, mask.getCachefile());
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
    public static double min(String field, DBConversionCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getParam() != null) filter.set(PARAM, mask.getParam());
            if(mask.getOrifile() != null) filter.set(ORIFILE, mask.getOrifile());
            if(mask.getCachefile() != null) filter.set(CACHEFILE, mask.getCachefile());
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
    public static double max(String field, DBConversionCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getParam() != null) filter.set(PARAM, mask.getParam());
            if(mask.getOrifile() != null) filter.set(ORIFILE, mask.getOrifile());
            if(mask.getCachefile() != null) filter.set(CACHEFILE, mask.getCachefile());
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
    public static double std(String field, DBConversionCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getParam() != null) filter.set(PARAM, mask.getParam());
            if(mask.getOrifile() != null) filter.set(ORIFILE, mask.getOrifile());
            if(mask.getCachefile() != null) filter.set(CACHEFILE, mask.getCachefile());
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
    public static String[] distinct(String field, DBConversionCache mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getParam() != null) filter.set(PARAM, mask.getParam());
            if(mask.getOrifile() != null) filter.set(ORIFILE, mask.getOrifile());
            if(mask.getCachefile() != null) filter.set(CACHEFILE, mask.getCachefile());
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
