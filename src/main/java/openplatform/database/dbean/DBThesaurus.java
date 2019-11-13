package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBThesaurus
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBThesaurus
{
    protected boolean inError = false;
    protected String id;
    protected String name;

    public static String ID = "id";
    public static String NAME = "name";
    public static String TABLE = "Thesaurus";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        name = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBThesaurus a = (DBThesaurus)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (name==null?a.getName()==null:name.equals(a.getName()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (name!=null?name.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"name="+name;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBThesaurus mask = new DBThesaurus();
            mask.setId(id);
            DBThesaurus var = DBThesaurus.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                name = var.getName();
            }
        }
    }

    public void initBean(DBThesaurus db)
    {
        this.id = db.getId();
        this.name = db.getName();
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

    /**
     * @return a DBThesaurus table or null if nothing found or if an error occured
     **/
    public static DBThesaurus[] load(DBThesaurus mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBThesaurus table or null if nothing found or if an error occured
     **/
    public static DBThesaurus[] load(SQLConstraint sqlconstraint, DBThesaurus mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, NAME}, filter);
            if(pvp == null) return null;
            else
            {
                DBThesaurus[] result = new DBThesaurus[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBThesaurus();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setName(pvp[i].get(NAME));
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
     * @return a DBThesaurus object or null if nothing found or if an error occured
     **/
    public static DBThesaurus loadByKey(DBThesaurus mask)
    {
        DBThesaurus[] res = DBThesaurus.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBThesaurus object or null if nothing found or if an error occured
     **/
    public static DBThesaurus loadByKey(DBThesaurus mask, SQLConstraint sqlconstraint)
    {
        DBThesaurus[] res = DBThesaurus.load(sqlconstraint, mask);
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
    public static void delete(DBThesaurus mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
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
    public static int count(DBThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
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
    public static double avg(String field, DBThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
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
    public static double min(String field, DBThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
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
    public static double max(String field, DBThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
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
    public static double std(String field, DBThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
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
    public static String[] distinct(String field, DBThesaurus mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
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
