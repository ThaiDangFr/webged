package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBMimeTypes
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBMimeTypes
{
    protected boolean inError = false;
    protected String mimetypesId;
    protected String extension;
    protected String primaryType;
    protected String secondaryType;

    public static String MIMETYPESID = "mimetypesId";
    public static String EXTENSION = "extension";
    public static String PRIMARYTYPE = "primaryType";
    public static String SECONDARYTYPE = "secondaryType";
    public static String TABLE = "MimeTypes";

    private static Database db = Database.getInstance();


    public void clear()
    {
        mimetypesId = null;
        extension = null;
        primaryType = null;
        secondaryType = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBMimeTypes a = (DBMimeTypes)obj;
        return (mimetypesId==null?a.getMimetypesId()==null:mimetypesId.equals(a.getMimetypesId())) && (extension==null?a.getExtension()==null:extension.equals(a.getExtension())) && (primaryType==null?a.getPrimaryType()==null:primaryType.equals(a.getPrimaryType())) && (secondaryType==null?a.getSecondaryType()==null:secondaryType.equals(a.getSecondaryType()));    }

    public int hashCode()
    {
        return (mimetypesId!=null?mimetypesId.hashCode():0) + (extension!=null?extension.hashCode():0) + (primaryType!=null?primaryType.hashCode():0) + (secondaryType!=null?secondaryType.hashCode():0);
    }

    public String toString()
    {
        return "mimetypesId="+mimetypesId+"|"+"extension="+extension+"|"+"primaryType="+primaryType+"|"+"secondaryType="+secondaryType;
    }

    public void refresh()
    {
        if(mimetypesId != null)
        {
            DBMimeTypes mask = new DBMimeTypes();
            mask.setMimetypesId(mimetypesId);
            DBMimeTypes var = DBMimeTypes.loadByKey(mask);
            if(var != null)
            {
                mimetypesId = var.getMimetypesId();
                extension = var.getExtension();
                primaryType = var.getPrimaryType();
                secondaryType = var.getSecondaryType();
            }
        }
    }

    public void initBean(DBMimeTypes db)
    {
        this.mimetypesId = db.getMimetypesId();
        this.extension = db.getExtension();
        this.primaryType = db.getPrimaryType();
        this.secondaryType = db.getSecondaryType();
    }

    public String getMimetypesId()
    {
        return mimetypesId;
    }

    public void setMimetypesId(String mimetypesId)
    {
        this.mimetypesId = mimetypesId;
    }

    public String getExtension()
    {
        return extension;
    }

    public void setExtension(String extension)
    {
        this.extension = extension;
    }

    public String getPrimaryType()
    {
        return primaryType;
    }

    public void setPrimaryType(String primaryType)
    {
        this.primaryType = primaryType;
    }

    public String getSecondaryType()
    {
        return secondaryType;
    }

    public void setSecondaryType(String secondaryType)
    {
        this.secondaryType = secondaryType;
    }

    /**
     * @return a DBMimeTypes table or null if nothing found or if an error occured
     **/
    public static DBMimeTypes[] load(DBMimeTypes mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBMimeTypes table or null if nothing found or if an error occured
     **/
    public static DBMimeTypes[] load(SQLConstraint sqlconstraint, DBMimeTypes mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getMimetypesId() != null) filter.set(MIMETYPESID, mask.getMimetypesId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getPrimaryType() != null) filter.set(PRIMARYTYPE, mask.getPrimaryType());
            if(mask.getSecondaryType() != null) filter.set(SECONDARYTYPE, mask.getSecondaryType());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{MIMETYPESID, EXTENSION, PRIMARYTYPE, SECONDARYTYPE}, filter);
            if(pvp == null) return null;
            else
            {
                DBMimeTypes[] result = new DBMimeTypes[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBMimeTypes();
                    result[i].setMimetypesId(pvp[i].get(MIMETYPESID));
                    result[i].setExtension(pvp[i].get(EXTENSION));
                    result[i].setPrimaryType(pvp[i].get(PRIMARYTYPE));
                    result[i].setSecondaryType(pvp[i].get(SECONDARYTYPE));
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
     * @return a DBMimeTypes object or null if nothing found or if an error occured
     **/
    public static DBMimeTypes loadByKey(DBMimeTypes mask)
    {
        DBMimeTypes[] res = DBMimeTypes.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBMimeTypes object or null if nothing found or if an error occured
     **/
    public static DBMimeTypes loadByKey(DBMimeTypes mask, SQLConstraint sqlconstraint)
    {
        DBMimeTypes[] res = DBMimeTypes.load(sqlconstraint, mask);
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
        toinsert.set(MIMETYPESID,mimetypesId);
        toinsert.set(EXTENSION,extension);
        toinsert.set(PRIMARYTYPE,primaryType);
        toinsert.set(SECONDARYTYPE,secondaryType);

        // Store a new entry
        if(mimetypesId == null)
        {
            try
            {
                mimetypesId = db.insert(TABLE, toinsert);
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
            filter.set(MIMETYPESID, mimetypesId);

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
        if(mimetypesId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(MIMETYPESID, mimetypesId);

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
    public static void delete(DBMimeTypes mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getMimetypesId() != null) filter.set(MIMETYPESID, mask.getMimetypesId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getPrimaryType() != null) filter.set(PRIMARYTYPE, mask.getPrimaryType());
            if(mask.getSecondaryType() != null) filter.set(SECONDARYTYPE, mask.getSecondaryType());
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
    public static int count(DBMimeTypes mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getMimetypesId() != null) filter.set(MIMETYPESID, mask.getMimetypesId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getPrimaryType() != null) filter.set(PRIMARYTYPE, mask.getPrimaryType());
            if(mask.getSecondaryType() != null) filter.set(SECONDARYTYPE, mask.getSecondaryType());
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
    public static double avg(String field, DBMimeTypes mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getMimetypesId() != null) filter.set(MIMETYPESID, mask.getMimetypesId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getPrimaryType() != null) filter.set(PRIMARYTYPE, mask.getPrimaryType());
            if(mask.getSecondaryType() != null) filter.set(SECONDARYTYPE, mask.getSecondaryType());
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
    public static double min(String field, DBMimeTypes mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getMimetypesId() != null) filter.set(MIMETYPESID, mask.getMimetypesId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getPrimaryType() != null) filter.set(PRIMARYTYPE, mask.getPrimaryType());
            if(mask.getSecondaryType() != null) filter.set(SECONDARYTYPE, mask.getSecondaryType());
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
    public static double max(String field, DBMimeTypes mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getMimetypesId() != null) filter.set(MIMETYPESID, mask.getMimetypesId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getPrimaryType() != null) filter.set(PRIMARYTYPE, mask.getPrimaryType());
            if(mask.getSecondaryType() != null) filter.set(SECONDARYTYPE, mask.getSecondaryType());
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
    public static double std(String field, DBMimeTypes mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getMimetypesId() != null) filter.set(MIMETYPESID, mask.getMimetypesId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getPrimaryType() != null) filter.set(PRIMARYTYPE, mask.getPrimaryType());
            if(mask.getSecondaryType() != null) filter.set(SECONDARYTYPE, mask.getSecondaryType());
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
    public static String[] distinct(String field, DBMimeTypes mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getMimetypesId() != null) filter.set(MIMETYPESID, mask.getMimetypesId());
            if(mask.getExtension() != null) filter.set(EXTENSION, mask.getExtension());
            if(mask.getPrimaryType() != null) filter.set(PRIMARYTYPE, mask.getPrimaryType());
            if(mask.getSecondaryType() != null) filter.set(SECONDARYTYPE, mask.getSecondaryType());
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
