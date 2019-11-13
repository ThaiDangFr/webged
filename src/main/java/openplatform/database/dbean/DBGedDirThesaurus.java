package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedDirThesaurus
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedDirThesaurus
{
    protected boolean inError = false;
    protected String id;
    protected String directoryId;
    protected String thesaurusId;

    public static String ID = "id";
    public static String DIRECTORYID = "directoryId";
    public static String THESAURUSID = "thesaurusId";
    public static String TABLE = "GedDirThesaurus";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        directoryId = null;
        thesaurusId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedDirThesaurus a = (DBGedDirThesaurus)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (directoryId==null?a.getDirectoryId()==null:directoryId.equals(a.getDirectoryId())) && (thesaurusId==null?a.getThesaurusId()==null:thesaurusId.equals(a.getThesaurusId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (directoryId!=null?directoryId.hashCode():0) + (thesaurusId!=null?thesaurusId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"directoryId="+directoryId+"|"+"thesaurusId="+thesaurusId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedDirThesaurus mask = new DBGedDirThesaurus();
            mask.setId(id);
            DBGedDirThesaurus var = DBGedDirThesaurus.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                directoryId = var.getDirectoryId();
                thesaurusId = var.getThesaurusId();
            }
        }
    }

    public void initBean(DBGedDirThesaurus db)
    {
        this.id = db.getId();
        this.directoryId = db.getDirectoryId();
        this.thesaurusId = db.getThesaurusId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDirectoryId()
    {
        return directoryId;
    }

    public void setDirectoryId(String directoryId)
    {
        this.directoryId = directoryId;
    }

    public String getThesaurusId()
    {
        return thesaurusId;
    }

    public void setThesaurusId(String thesaurusId)
    {
        this.thesaurusId = thesaurusId;
    }

    /**
     * @return a DBGedDirThesaurus table or null if nothing found or if an error occured
     **/
    public static DBGedDirThesaurus[] load(DBGedDirThesaurus mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedDirThesaurus table or null if nothing found or if an error occured
     **/
    public static DBGedDirThesaurus[] load(SQLConstraint sqlconstraint, DBGedDirThesaurus mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, DIRECTORYID, THESAURUSID}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedDirThesaurus[] result = new DBGedDirThesaurus[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedDirThesaurus();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setDirectoryId(pvp[i].get(DIRECTORYID));
                    result[i].setThesaurusId(pvp[i].get(THESAURUSID));
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
     * @return a DBGedDirThesaurus object or null if nothing found or if an error occured
     **/
    public static DBGedDirThesaurus loadByKey(DBGedDirThesaurus mask)
    {
        DBGedDirThesaurus[] res = DBGedDirThesaurus.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedDirThesaurus object or null if nothing found or if an error occured
     **/
    public static DBGedDirThesaurus loadByKey(DBGedDirThesaurus mask, SQLConstraint sqlconstraint)
    {
        DBGedDirThesaurus[] res = DBGedDirThesaurus.load(sqlconstraint, mask);
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
        toinsert.set(DIRECTORYID,directoryId);
        toinsert.set(THESAURUSID,thesaurusId);

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
    public static void delete(DBGedDirThesaurus mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
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
    public static int count(DBGedDirThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
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
    public static double avg(String field, DBGedDirThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
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
    public static double min(String field, DBGedDirThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
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
    public static double max(String field, DBGedDirThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
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
    public static double std(String field, DBGedDirThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
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
    public static String[] distinct(String field, DBGedDirThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getThesaurusId() != null) filter.set(THESAURUSID, mask.getThesaurusId());
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
