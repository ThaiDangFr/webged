package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedFilesDescr
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedFilesDescr
{
    protected boolean inError = false;
    protected String id;
    protected String templateItemId;
    protected String value;
    protected String fileId;

    public static String ID = "id";
    public static String TEMPLATEITEMID = "templateItemId";
    public static String VALUE = "value";
    public static String FILEID = "fileId";
    public static String TABLE = "GedFilesDescr";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        templateItemId = null;
        value = null;
        fileId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedFilesDescr a = (DBGedFilesDescr)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (templateItemId==null?a.getTemplateItemId()==null:templateItemId.equals(a.getTemplateItemId())) && (value==null?a.getValue()==null:value.equals(a.getValue())) && (fileId==null?a.getFileId()==null:fileId.equals(a.getFileId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (templateItemId!=null?templateItemId.hashCode():0) + (value!=null?value.hashCode():0) + (fileId!=null?fileId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"templateItemId="+templateItemId+"|"+"value="+value+"|"+"fileId="+fileId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedFilesDescr mask = new DBGedFilesDescr();
            mask.setId(id);
            DBGedFilesDescr var = DBGedFilesDescr.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                templateItemId = var.getTemplateItemId();
                value = var.getValue();
                fileId = var.getFileId();
            }
        }
    }

    public void initBean(DBGedFilesDescr db)
    {
        this.id = db.getId();
        this.templateItemId = db.getTemplateItemId();
        this.value = db.getValue();
        this.fileId = db.getFileId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTemplateItemId()
    {
        return templateItemId;
    }

    public void setTemplateItemId(String templateItemId)
    {
        this.templateItemId = templateItemId;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getFileId()
    {
        return fileId;
    }

    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    /**
     * @return a DBGedFilesDescr table or null if nothing found or if an error occured
     **/
    public static DBGedFilesDescr[] load(DBGedFilesDescr mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedFilesDescr table or null if nothing found or if an error occured
     **/
    public static DBGedFilesDescr[] load(SQLConstraint sqlconstraint, DBGedFilesDescr mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTemplateItemId() != null) filter.set(TEMPLATEITEMID, mask.getTemplateItemId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, TEMPLATEITEMID, VALUE, FILEID}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedFilesDescr[] result = new DBGedFilesDescr[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedFilesDescr();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setTemplateItemId(pvp[i].get(TEMPLATEITEMID));
                    result[i].setValue(pvp[i].get(VALUE));
                    result[i].setFileId(pvp[i].get(FILEID));
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
     * @return a DBGedFilesDescr object or null if nothing found or if an error occured
     **/
    public static DBGedFilesDescr loadByKey(DBGedFilesDescr mask)
    {
        DBGedFilesDescr[] res = DBGedFilesDescr.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedFilesDescr object or null if nothing found or if an error occured
     **/
    public static DBGedFilesDescr loadByKey(DBGedFilesDescr mask, SQLConstraint sqlconstraint)
    {
        DBGedFilesDescr[] res = DBGedFilesDescr.load(sqlconstraint, mask);
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
        toinsert.set(TEMPLATEITEMID,templateItemId);
        toinsert.set(VALUE,value);
        toinsert.set(FILEID,fileId);

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
    public static void delete(DBGedFilesDescr mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTemplateItemId() != null) filter.set(TEMPLATEITEMID, mask.getTemplateItemId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
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
    public static int count(DBGedFilesDescr mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTemplateItemId() != null) filter.set(TEMPLATEITEMID, mask.getTemplateItemId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
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
    public static double avg(String field, DBGedFilesDescr mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTemplateItemId() != null) filter.set(TEMPLATEITEMID, mask.getTemplateItemId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
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
    public static double min(String field, DBGedFilesDescr mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTemplateItemId() != null) filter.set(TEMPLATEITEMID, mask.getTemplateItemId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
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
    public static double max(String field, DBGedFilesDescr mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTemplateItemId() != null) filter.set(TEMPLATEITEMID, mask.getTemplateItemId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
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
    public static double std(String field, DBGedFilesDescr mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTemplateItemId() != null) filter.set(TEMPLATEITEMID, mask.getTemplateItemId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
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
    public static String[] distinct(String field, DBGedFilesDescr mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getTemplateItemId() != null) filter.set(TEMPLATEITEMID, mask.getTemplateItemId());
            if(mask.getValue() != null) filter.set(VALUE, mask.getValue());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
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
