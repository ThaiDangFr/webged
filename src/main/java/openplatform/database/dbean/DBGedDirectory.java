package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGeddirectory
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedDirectory
{
    protected boolean inError = false;
    protected String id;
    protected String name;
    protected String parentDirId;
    protected String templateId;
    protected String fileExpiryTime;
    protected String fileExpiryGroup;
    protected String fileAddModifyWorkflow;
    protected String reference;
    protected String path;
    protected String storeType;

    public static String ID = "id";
    public static String NAME = "name";
    public static String PARENTDIRID = "parentDirId";
    public static String TEMPLATEID = "templateId";
    public static String FILEEXPIRYTIME = "fileExpiryTime";
    public static String FILEEXPIRYGROUP = "fileExpiryGroup";
    public static String FILEADDMODIFYWORKFLOW = "fileAddModifyWorkflow";
    public static String REFERENCE = "reference";
    public static String PATH = "path";
    public static String STORETYPE = "storeType";
    /** normal **/
    public static final String STORETYPE_NORMAL = "normal";
    /** readonlyinpdf **/
    public static final String STORETYPE_READONLYINPDF = "readonlyinpdf";
    /** allinpdf **/
    public static final String STORETYPE_ALLINPDF = "allinpdf";
    /** Contains the SQL enumeration for STORETYPE **/
    public static final ArrayList _STORETYPE_LIST = new ArrayList();
    static {_STORETYPE_LIST.add(STORETYPE_NORMAL);_STORETYPE_LIST.add(STORETYPE_READONLYINPDF);_STORETYPE_LIST.add(STORETYPE_ALLINPDF);}
    /** Contains the SQL HASHMAP for STORETYPE **/
    public static final OrdHashMap _STORETYPE_MAP = new OrdHashMap();
    static {_STORETYPE_MAP.put(STORETYPE_NORMAL,STORETYPE_NORMAL);_STORETYPE_MAP.put(STORETYPE_READONLYINPDF,STORETYPE_READONLYINPDF);_STORETYPE_MAP.put(STORETYPE_ALLINPDF,STORETYPE_ALLINPDF);}
    public static String TABLE = "GedDirectory";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        name = null;
        parentDirId = null;
        templateId = null;
        fileExpiryTime = null;
        fileExpiryGroup = null;
        fileAddModifyWorkflow = null;
        reference = null;
        path = null;
        storeType = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedDirectory a = (DBGedDirectory)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (name==null?a.getName()==null:name.equals(a.getName())) && (parentDirId==null?a.getParentDirId()==null:parentDirId.equals(a.getParentDirId())) && (templateId==null?a.getTemplateId()==null:templateId.equals(a.getTemplateId())) && (fileExpiryTime==null?a.getFileExpiryTime()==null:fileExpiryTime.equals(a.getFileExpiryTime())) && (fileExpiryGroup==null?a.getFileExpiryGroup()==null:fileExpiryGroup.equals(a.getFileExpiryGroup())) && (fileAddModifyWorkflow==null?a.getFileAddModifyWorkflow()==null:fileAddModifyWorkflow.equals(a.getFileAddModifyWorkflow())) && (reference==null?a.getReference()==null:reference.equals(a.getReference())) && (path==null?a.getPath()==null:path.equals(a.getPath())) && (storeType==null?a.getStoreType()==null:storeType.equals(a.getStoreType()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (name!=null?name.hashCode():0) + (parentDirId!=null?parentDirId.hashCode():0) + (templateId!=null?templateId.hashCode():0) + (fileExpiryTime!=null?fileExpiryTime.hashCode():0) + (fileExpiryGroup!=null?fileExpiryGroup.hashCode():0) + (fileAddModifyWorkflow!=null?fileAddModifyWorkflow.hashCode():0) + (reference!=null?reference.hashCode():0) + (path!=null?path.hashCode():0) + (storeType!=null?storeType.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"name="+name+"|"+"parentDirId="+parentDirId+"|"+"templateId="+templateId+"|"+"fileExpiryTime="+fileExpiryTime+"|"+"fileExpiryGroup="+fileExpiryGroup+"|"+"fileAddModifyWorkflow="+fileAddModifyWorkflow+"|"+"reference="+reference+"|"+"path="+path+"|"+"storeType="+storeType;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedDirectory mask = new DBGedDirectory();
            mask.setId(id);
            DBGedDirectory var = DBGedDirectory.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                name = var.getName();
                parentDirId = var.getParentDirId();
                templateId = var.getTemplateId();
                fileExpiryTime = var.getFileExpiryTime();
                fileExpiryGroup = var.getFileExpiryGroup();
                fileAddModifyWorkflow = var.getFileAddModifyWorkflow();
                reference = var.getReference();
                path = var.getPath();
                storeType = var.getStoreType();
            }
        }
    }

    public void initBean(DBGedDirectory db)
    {
        this.id = db.getId();
        this.name = db.getName();
        this.parentDirId = db.getParentDirId();
        this.templateId = db.getTemplateId();
        this.fileExpiryTime = db.getFileExpiryTime();
        this.fileExpiryGroup = db.getFileExpiryGroup();
        this.fileAddModifyWorkflow = db.getFileAddModifyWorkflow();
        this.reference = db.getReference();
        this.path = db.getPath();
        this.storeType = db.getStoreType();
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

    public String getParentDirId()
    {
        return parentDirId;
    }

    public void setParentDirId(String parentDirId)
    {
        this.parentDirId = parentDirId;
    }

    public String getTemplateId()
    {
        return templateId;
    }

    public void setTemplateId(String templateId)
    {
        this.templateId = templateId;
    }

    public String getFileExpiryTime()
    {
        return fileExpiryTime;
    }

    public void setFileExpiryTime(String fileExpiryTime)
    {
        this.fileExpiryTime = fileExpiryTime;
    }

    public String getFileExpiryGroup()
    {
        return fileExpiryGroup;
    }

    public void setFileExpiryGroup(String fileExpiryGroup)
    {
        this.fileExpiryGroup = fileExpiryGroup;
    }

    public String getFileAddModifyWorkflow()
    {
        return fileAddModifyWorkflow;
    }

    public void setFileAddModifyWorkflow(String fileAddModifyWorkflow)
    {
        this.fileAddModifyWorkflow = fileAddModifyWorkflow;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getStoreType()
    {
        return storeType;
    }

    public void setStoreType(String storeType)
    {
        this.storeType = storeType;
    }

    /**
     * @return a DBGedDirectory table or null if nothing found or if an error occured
     **/
    public static DBGedDirectory[] load(DBGedDirectory mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedDirectory table or null if nothing found or if an error occured
     **/
    public static DBGedDirectory[] load(SQLConstraint sqlconstraint, DBGedDirectory mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentDirId() != null) filter.set(PARENTDIRID, mask.getParentDirId());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
            if(mask.getFileExpiryTime() != null) filter.set(FILEEXPIRYTIME, mask.getFileExpiryTime());
            if(mask.getFileExpiryGroup() != null) filter.set(FILEEXPIRYGROUP, mask.getFileExpiryGroup());
            if(mask.getFileAddModifyWorkflow() != null) filter.set(FILEADDMODIFYWORKFLOW, mask.getFileAddModifyWorkflow());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getPath() != null) filter.set(PATH, mask.getPath());
            if(mask.getStoreType() != null) filter.set(STORETYPE, mask.getStoreType());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, NAME, PARENTDIRID, TEMPLATEID, FILEEXPIRYTIME, FILEEXPIRYGROUP, FILEADDMODIFYWORKFLOW, REFERENCE, PATH, STORETYPE}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedDirectory[] result = new DBGedDirectory[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedDirectory();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setName(pvp[i].get(NAME));
                    result[i].setParentDirId(pvp[i].get(PARENTDIRID));
                    result[i].setTemplateId(pvp[i].get(TEMPLATEID));
                    result[i].setFileExpiryTime(pvp[i].get(FILEEXPIRYTIME));
                    result[i].setFileExpiryGroup(pvp[i].get(FILEEXPIRYGROUP));
                    result[i].setFileAddModifyWorkflow(pvp[i].get(FILEADDMODIFYWORKFLOW));
                    result[i].setReference(pvp[i].get(REFERENCE));
                    result[i].setPath(pvp[i].get(PATH));
                    result[i].setStoreType(pvp[i].get(STORETYPE));
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
     * @return a DBGedDirectory object or null if nothing found or if an error occured
     **/
    public static DBGedDirectory loadByKey(DBGedDirectory mask)
    {
        DBGedDirectory[] res = DBGedDirectory.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedDirectory object or null if nothing found or if an error occured
     **/
    public static DBGedDirectory loadByKey(DBGedDirectory mask, SQLConstraint sqlconstraint)
    {
        DBGedDirectory[] res = DBGedDirectory.load(sqlconstraint, mask);
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
        toinsert.set(PARENTDIRID,parentDirId);
        toinsert.set(TEMPLATEID,templateId);
        toinsert.set(FILEEXPIRYTIME,fileExpiryTime);
        toinsert.set(FILEEXPIRYGROUP,fileExpiryGroup);
        toinsert.set(FILEADDMODIFYWORKFLOW,fileAddModifyWorkflow);
        toinsert.set(REFERENCE,reference);
        toinsert.set(PATH,path);
        toinsert.set(STORETYPE,storeType);

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
    public static void delete(DBGedDirectory mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentDirId() != null) filter.set(PARENTDIRID, mask.getParentDirId());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
            if(mask.getFileExpiryTime() != null) filter.set(FILEEXPIRYTIME, mask.getFileExpiryTime());
            if(mask.getFileExpiryGroup() != null) filter.set(FILEEXPIRYGROUP, mask.getFileExpiryGroup());
            if(mask.getFileAddModifyWorkflow() != null) filter.set(FILEADDMODIFYWORKFLOW, mask.getFileAddModifyWorkflow());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getPath() != null) filter.set(PATH, mask.getPath());
            if(mask.getStoreType() != null) filter.set(STORETYPE, mask.getStoreType());
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
    public static int count(DBGedDirectory mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentDirId() != null) filter.set(PARENTDIRID, mask.getParentDirId());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
            if(mask.getFileExpiryTime() != null) filter.set(FILEEXPIRYTIME, mask.getFileExpiryTime());
            if(mask.getFileExpiryGroup() != null) filter.set(FILEEXPIRYGROUP, mask.getFileExpiryGroup());
            if(mask.getFileAddModifyWorkflow() != null) filter.set(FILEADDMODIFYWORKFLOW, mask.getFileAddModifyWorkflow());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getPath() != null) filter.set(PATH, mask.getPath());
            if(mask.getStoreType() != null) filter.set(STORETYPE, mask.getStoreType());
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
    public static double avg(String field, DBGedDirectory mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentDirId() != null) filter.set(PARENTDIRID, mask.getParentDirId());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
            if(mask.getFileExpiryTime() != null) filter.set(FILEEXPIRYTIME, mask.getFileExpiryTime());
            if(mask.getFileExpiryGroup() != null) filter.set(FILEEXPIRYGROUP, mask.getFileExpiryGroup());
            if(mask.getFileAddModifyWorkflow() != null) filter.set(FILEADDMODIFYWORKFLOW, mask.getFileAddModifyWorkflow());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getPath() != null) filter.set(PATH, mask.getPath());
            if(mask.getStoreType() != null) filter.set(STORETYPE, mask.getStoreType());
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
    public static double min(String field, DBGedDirectory mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentDirId() != null) filter.set(PARENTDIRID, mask.getParentDirId());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
            if(mask.getFileExpiryTime() != null) filter.set(FILEEXPIRYTIME, mask.getFileExpiryTime());
            if(mask.getFileExpiryGroup() != null) filter.set(FILEEXPIRYGROUP, mask.getFileExpiryGroup());
            if(mask.getFileAddModifyWorkflow() != null) filter.set(FILEADDMODIFYWORKFLOW, mask.getFileAddModifyWorkflow());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getPath() != null) filter.set(PATH, mask.getPath());
            if(mask.getStoreType() != null) filter.set(STORETYPE, mask.getStoreType());
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
    public static double max(String field, DBGedDirectory mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentDirId() != null) filter.set(PARENTDIRID, mask.getParentDirId());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
            if(mask.getFileExpiryTime() != null) filter.set(FILEEXPIRYTIME, mask.getFileExpiryTime());
            if(mask.getFileExpiryGroup() != null) filter.set(FILEEXPIRYGROUP, mask.getFileExpiryGroup());
            if(mask.getFileAddModifyWorkflow() != null) filter.set(FILEADDMODIFYWORKFLOW, mask.getFileAddModifyWorkflow());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getPath() != null) filter.set(PATH, mask.getPath());
            if(mask.getStoreType() != null) filter.set(STORETYPE, mask.getStoreType());
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
    public static double std(String field, DBGedDirectory mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentDirId() != null) filter.set(PARENTDIRID, mask.getParentDirId());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
            if(mask.getFileExpiryTime() != null) filter.set(FILEEXPIRYTIME, mask.getFileExpiryTime());
            if(mask.getFileExpiryGroup() != null) filter.set(FILEEXPIRYGROUP, mask.getFileExpiryGroup());
            if(mask.getFileAddModifyWorkflow() != null) filter.set(FILEADDMODIFYWORKFLOW, mask.getFileAddModifyWorkflow());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getPath() != null) filter.set(PATH, mask.getPath());
            if(mask.getStoreType() != null) filter.set(STORETYPE, mask.getStoreType());
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
    public static String[] distinct(String field, DBGedDirectory mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getParentDirId() != null) filter.set(PARENTDIRID, mask.getParentDirId());
            if(mask.getTemplateId() != null) filter.set(TEMPLATEID, mask.getTemplateId());
            if(mask.getFileExpiryTime() != null) filter.set(FILEEXPIRYTIME, mask.getFileExpiryTime());
            if(mask.getFileExpiryGroup() != null) filter.set(FILEEXPIRYGROUP, mask.getFileExpiryGroup());
            if(mask.getFileAddModifyWorkflow() != null) filter.set(FILEADDMODIFYWORKFLOW, mask.getFileAddModifyWorkflow());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getPath() != null) filter.set(PATH, mask.getPath());
            if(mask.getStoreType() != null) filter.set(STORETYPE, mask.getStoreType());
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
