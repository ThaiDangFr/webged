package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBLicense
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBLicense
{
    protected boolean inError = false;
    protected String licenseId;
    protected String licenseKey;

    public static String LICENSEID = "licenseId";
    public static String LICENSEKEY = "licenseKey";
    public static String TABLE = "License";

    private static Database db = Database.getInstance();


    public void clear()
    {
        licenseId = null;
        licenseKey = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBLicense a = (DBLicense)obj;
        return (licenseId==null?a.getLicenseId()==null:licenseId.equals(a.getLicenseId())) && (licenseKey==null?a.getLicenseKey()==null:licenseKey.equals(a.getLicenseKey()));    }

    public int hashCode()
    {
        return (licenseId!=null?licenseId.hashCode():0) + (licenseKey!=null?licenseKey.hashCode():0);
    }

    public String toString()
    {
        return "licenseId="+licenseId+"|"+"licenseKey="+licenseKey;
    }

    public void refresh()
    {
        if(licenseId != null)
        {
            DBLicense mask = new DBLicense();
            mask.setLicenseId(licenseId);
            DBLicense var = DBLicense.loadByKey(mask);
            if(var != null)
            {
                licenseId = var.getLicenseId();
                licenseKey = var.getLicenseKey();
            }
        }
    }

    public void initBean(DBLicense db)
    {
        this.licenseId = db.getLicenseId();
        this.licenseKey = db.getLicenseKey();
    }

    public String getLicenseId()
    {
        return licenseId;
    }

    public void setLicenseId(String licenseId)
    {
        this.licenseId = licenseId;
    }

    public String getLicenseKey()
    {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey)
    {
        this.licenseKey = licenseKey;
    }

    /**
     * @return a DBLicense table or null if nothing found or if an error occured
     **/
    public static DBLicense[] load(DBLicense mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBLicense table or null if nothing found or if an error occured
     **/
    public static DBLicense[] load(SQLConstraint sqlconstraint, DBLicense mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getLicenseId() != null) filter.set(LICENSEID, mask.getLicenseId());
            if(mask.getLicenseKey() != null) filter.set(LICENSEKEY, mask.getLicenseKey());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{LICENSEID, LICENSEKEY}, filter);
            if(pvp == null) return null;
            else
            {
                DBLicense[] result = new DBLicense[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBLicense();
                    result[i].setLicenseId(pvp[i].get(LICENSEID));
                    result[i].setLicenseKey(pvp[i].get(LICENSEKEY));
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
     * @return a DBLicense object or null if nothing found or if an error occured
     **/
    public static DBLicense loadByKey(DBLicense mask)
    {
        DBLicense[] res = DBLicense.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBLicense object or null if nothing found or if an error occured
     **/
    public static DBLicense loadByKey(DBLicense mask, SQLConstraint sqlconstraint)
    {
        DBLicense[] res = DBLicense.load(sqlconstraint, mask);
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
        toinsert.set(LICENSEID,licenseId);
        toinsert.set(LICENSEKEY,licenseKey);

        // Store a new entry
        if(licenseId == null)
        {
            try
            {
                licenseId = db.insert(TABLE, toinsert);
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
            filter.set(LICENSEID, licenseId);

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
        if(licenseId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(LICENSEID, licenseId);

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
    public static void delete(DBLicense mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getLicenseId() != null) filter.set(LICENSEID, mask.getLicenseId());
            if(mask.getLicenseKey() != null) filter.set(LICENSEKEY, mask.getLicenseKey());
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
    public static int count(DBLicense mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getLicenseId() != null) filter.set(LICENSEID, mask.getLicenseId());
            if(mask.getLicenseKey() != null) filter.set(LICENSEKEY, mask.getLicenseKey());
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
    public static double avg(String field, DBLicense mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getLicenseId() != null) filter.set(LICENSEID, mask.getLicenseId());
            if(mask.getLicenseKey() != null) filter.set(LICENSEKEY, mask.getLicenseKey());
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
    public static double min(String field, DBLicense mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getLicenseId() != null) filter.set(LICENSEID, mask.getLicenseId());
            if(mask.getLicenseKey() != null) filter.set(LICENSEKEY, mask.getLicenseKey());
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
    public static double max(String field, DBLicense mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getLicenseId() != null) filter.set(LICENSEID, mask.getLicenseId());
            if(mask.getLicenseKey() != null) filter.set(LICENSEKEY, mask.getLicenseKey());
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
    public static double std(String field, DBLicense mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getLicenseId() != null) filter.set(LICENSEID, mask.getLicenseId());
            if(mask.getLicenseKey() != null) filter.set(LICENSEKEY, mask.getLicenseKey());
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
    public static String[] distinct(String field, DBLicense mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getLicenseId() != null) filter.set(LICENSEID, mask.getLicenseId());
            if(mask.getLicenseKey() != null) filter.set(LICENSEKEY, mask.getLicenseKey());
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
