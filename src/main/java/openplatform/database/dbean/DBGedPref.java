package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedpref
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedPref
{
    protected boolean inError = false;
    protected String id;
    protected String trashExpiry;
    protected String stateReaderGrp;

    public static String ID = "id";
    public static String TRASHEXPIRY = "trashExpiry";
    public static String STATEREADERGRP = "stateReaderGrp";
    public static String TABLE = "GedPref";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        trashExpiry = null;
        stateReaderGrp = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedPref a = (DBGedPref)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (trashExpiry==null?a.getTrashExpiry()==null:trashExpiry.equals(a.getTrashExpiry())) && (stateReaderGrp==null?a.getStateReaderGrp()==null:stateReaderGrp.equals(a.getStateReaderGrp()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (trashExpiry!=null?trashExpiry.hashCode():0) + (stateReaderGrp!=null?stateReaderGrp.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"trashExpiry="+trashExpiry+"|"+"stateReaderGrp="+stateReaderGrp;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedPref mask = new DBGedPref();
            mask.setId(id);
            DBGedPref var = DBGedPref.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                trashExpiry = var.getTrashExpiry();
                stateReaderGrp = var.getStateReaderGrp();
            }
        }
    }

    public void initBean(DBGedPref db)
    {
        this.id = db.getId();
        this.trashExpiry = db.getTrashExpiry();
        this.stateReaderGrp = db.getStateReaderGrp();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTrashExpiry()
    {
        return trashExpiry;
    }

    public void setTrashExpiry(String trashExpiry)
    {
        this.trashExpiry = trashExpiry;
    }

    public String getStateReaderGrp()
    {
        return stateReaderGrp;
    }

    public void setStateReaderGrp(String stateReaderGrp)
    {
        this.stateReaderGrp = stateReaderGrp;
    }

    /**
     * @return a DBGedPref table or null if nothing found or if an error occured
     **/
    public static DBGedPref[] load(DBGedPref mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedPref table or null if nothing found or if an error occured
     **/
    public static DBGedPref[] load(SQLConstraint sqlconstraint, DBGedPref mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTrashExpiry() != null) filter.set(TRASHEXPIRY, mask.getTrashExpiry());
            if(mask.getStateReaderGrp() != null) filter.set(STATEREADERGRP, mask.getStateReaderGrp());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, TRASHEXPIRY, STATEREADERGRP}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedPref[] result = new DBGedPref[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedPref();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setTrashExpiry(pvp[i].get(TRASHEXPIRY));
                    result[i].setStateReaderGrp(pvp[i].get(STATEREADERGRP));
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
     * @return a DBGedPref object or null if nothing found or if an error occured
     **/
    public static DBGedPref loadByKey(DBGedPref mask)
    {
        DBGedPref[] res = DBGedPref.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedPref object or null if nothing found or if an error occured
     **/
    public static DBGedPref loadByKey(DBGedPref mask, SQLConstraint sqlconstraint)
    {
        DBGedPref[] res = DBGedPref.load(sqlconstraint, mask);
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
        toinsert.set(TRASHEXPIRY,trashExpiry);
        toinsert.set(STATEREADERGRP,stateReaderGrp);

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
    public static void delete(DBGedPref mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTrashExpiry() != null) filter.set(TRASHEXPIRY, mask.getTrashExpiry());
            if(mask.getStateReaderGrp() != null) filter.set(STATEREADERGRP, mask.getStateReaderGrp());
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
    public static int count(DBGedPref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTrashExpiry() != null) filter.set(TRASHEXPIRY, mask.getTrashExpiry());
            if(mask.getStateReaderGrp() != null) filter.set(STATEREADERGRP, mask.getStateReaderGrp());
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
    public static double avg(String field, DBGedPref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTrashExpiry() != null) filter.set(TRASHEXPIRY, mask.getTrashExpiry());
            if(mask.getStateReaderGrp() != null) filter.set(STATEREADERGRP, mask.getStateReaderGrp());
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
    public static double min(String field, DBGedPref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTrashExpiry() != null) filter.set(TRASHEXPIRY, mask.getTrashExpiry());
            if(mask.getStateReaderGrp() != null) filter.set(STATEREADERGRP, mask.getStateReaderGrp());
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
    public static double max(String field, DBGedPref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTrashExpiry() != null) filter.set(TRASHEXPIRY, mask.getTrashExpiry());
            if(mask.getStateReaderGrp() != null) filter.set(STATEREADERGRP, mask.getStateReaderGrp());
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
    public static double std(String field, DBGedPref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTrashExpiry() != null) filter.set(TRASHEXPIRY, mask.getTrashExpiry());
            if(mask.getStateReaderGrp() != null) filter.set(STATEREADERGRP, mask.getStateReaderGrp());
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
    public static String[] distinct(String field, DBGedPref mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTrashExpiry() != null) filter.set(TRASHEXPIRY, mask.getTrashExpiry());
            if(mask.getStateReaderGrp() != null) filter.set(STATEREADERGRP, mask.getStateReaderGrp());
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
