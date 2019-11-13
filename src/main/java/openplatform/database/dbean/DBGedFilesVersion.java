package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedFilesVersion
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedFilesVersion
{
    protected boolean inError = false;
    protected String id;
    protected String fileId;
    protected String fileBaseId;
    protected String visible;
    protected String version;
    protected String reference;
    protected String keywords;
    protected String name;
    protected String size;
    protected String authorId;
    protected String versionDate;

    public static String ID = "id";
    public static String FILEID = "fileId";
    public static String FILEBASEID = "fileBaseId";
    public static String VISIBLE = "visible";
    public static String VERSION = "version";
    public static String REFERENCE = "reference";
    public static String KEYWORDS = "keywords";
    public static String NAME = "name";
    public static String SIZE = "size";
    public static String AUTHORID = "authorId";
    public static String VERSIONDATE = "versionDate";
    public static String TABLE = "GedFilesVersion";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        fileId = null;
        fileBaseId = null;
        visible = null;
        version = null;
        reference = null;
        keywords = null;
        name = null;
        size = null;
        authorId = null;
        versionDate = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedFilesVersion a = (DBGedFilesVersion)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (fileId==null?a.getFileId()==null:fileId.equals(a.getFileId())) && (fileBaseId==null?a.getFileBaseId()==null:fileBaseId.equals(a.getFileBaseId())) && (visible==null?a.getVisible()==null:visible.equals(a.getVisible())) && (version==null?a.getVersion()==null:version.equals(a.getVersion())) && (reference==null?a.getReference()==null:reference.equals(a.getReference())) && (keywords==null?a.getKeywords()==null:keywords.equals(a.getKeywords())) && (name==null?a.getName()==null:name.equals(a.getName())) && (size==null?a.getSize()==null:size.equals(a.getSize())) && (authorId==null?a.getAuthorId()==null:authorId.equals(a.getAuthorId())) && (versionDate==null?a.getVersionDate()==null:versionDate.equals(a.getVersionDate()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (fileId!=null?fileId.hashCode():0) + (fileBaseId!=null?fileBaseId.hashCode():0) + (visible!=null?visible.hashCode():0) + (version!=null?version.hashCode():0) + (reference!=null?reference.hashCode():0) + (keywords!=null?keywords.hashCode():0) + (name!=null?name.hashCode():0) + (size!=null?size.hashCode():0) + (authorId!=null?authorId.hashCode():0) + (versionDate!=null?versionDate.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"fileId="+fileId+"|"+"fileBaseId="+fileBaseId+"|"+"visible="+visible+"|"+"version="+version+"|"+"reference="+reference+"|"+"keywords="+keywords+"|"+"name="+name+"|"+"size="+size+"|"+"authorId="+authorId+"|"+"versionDate="+versionDate;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedFilesVersion mask = new DBGedFilesVersion();
            mask.setId(id);
            DBGedFilesVersion var = DBGedFilesVersion.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                fileId = var.getFileId();
                fileBaseId = var.getFileBaseId();
                visible = var.getVisible();
                version = var.getVersion();
                reference = var.getReference();
                keywords = var.getKeywords();
                name = var.getName();
                size = var.getSize();
                authorId = var.getAuthorId();
                versionDate = var.getVersionDate();
            }
        }
    }

    public void initBean(DBGedFilesVersion db)
    {
        this.id = db.getId();
        this.fileId = db.getFileId();
        this.fileBaseId = db.getFileBaseId();
        this.visible = db.getVisible();
        this.version = db.getVersion();
        this.reference = db.getReference();
        this.keywords = db.getKeywords();
        this.name = db.getName();
        this.size = db.getSize();
        this.authorId = db.getAuthorId();
        this.versionDate = db.getVersionDate();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getFileId()
    {
        return fileId;
    }

    public void setFileId(String fileId)
    {
        this.fileId = fileId;
    }

    public String getFileBaseId()
    {
        return fileBaseId;
    }

    public void setFileBaseId(String fileBaseId)
    {
        this.fileBaseId = fileBaseId;
    }

    public String getVisible()
    {
        return visible;
    }

    public void setVisible(String visible)
    {
        this.visible = visible;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getReference()
    {
        return reference;
    }

    public void setReference(String reference)
    {
        this.reference = reference;
    }

    public String getKeywords()
    {
        return keywords;
    }

    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public String getAuthorId()
    {
        return authorId;
    }

    public void setAuthorId(String authorId)
    {
        this.authorId = authorId;
    }

    public String getVersionDate()
    {
        return versionDate;
    }

    public void setVersionDate(String versionDate)
    {
        this.versionDate = versionDate;
    }

    /**
     * @return a DBGedFilesVersion table or null if nothing found or if an error occured
     **/
    public static DBGedFilesVersion[] load(DBGedFilesVersion mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedFilesVersion table or null if nothing found or if an error occured
     **/
    public static DBGedFilesVersion[] load(SQLConstraint sqlconstraint, DBGedFilesVersion mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getVersion() != null) filter.set(VERSION, mask.getVersion());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getKeywords() != null) filter.set(KEYWORDS, mask.getKeywords());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getVersionDate() != null) filter.set(VERSIONDATE, mask.getVersionDate());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, FILEID, FILEBASEID, VISIBLE, VERSION, REFERENCE, KEYWORDS, NAME, SIZE, AUTHORID, VERSIONDATE}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedFilesVersion[] result = new DBGedFilesVersion[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedFilesVersion();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setFileId(pvp[i].get(FILEID));
                    result[i].setFileBaseId(pvp[i].get(FILEBASEID));
                    result[i].setVisible(pvp[i].get(VISIBLE));
                    result[i].setVersion(pvp[i].get(VERSION));
                    result[i].setReference(pvp[i].get(REFERENCE));
                    result[i].setKeywords(pvp[i].get(KEYWORDS));
                    result[i].setName(pvp[i].get(NAME));
                    result[i].setSize(pvp[i].get(SIZE));
                    result[i].setAuthorId(pvp[i].get(AUTHORID));
                    result[i].setVersionDate(pvp[i].get(VERSIONDATE));
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
     * @return a DBGedFilesVersion object or null if nothing found or if an error occured
     **/
    public static DBGedFilesVersion loadByKey(DBGedFilesVersion mask)
    {
        DBGedFilesVersion[] res = DBGedFilesVersion.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedFilesVersion object or null if nothing found or if an error occured
     **/
    public static DBGedFilesVersion loadByKey(DBGedFilesVersion mask, SQLConstraint sqlconstraint)
    {
        DBGedFilesVersion[] res = DBGedFilesVersion.load(sqlconstraint, mask);
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
        toinsert.set(FILEID,fileId);
        toinsert.set(FILEBASEID,fileBaseId);
        toinsert.set(VISIBLE,visible);
        toinsert.set(VERSION,version);
        toinsert.set(REFERENCE,reference);
        toinsert.set(KEYWORDS,keywords);
        toinsert.set(NAME,name);
        toinsert.set(SIZE,size);
        toinsert.set(AUTHORID,authorId);
        toinsert.set(VERSIONDATE,versionDate);

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
    public static void delete(DBGedFilesVersion mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getVersion() != null) filter.set(VERSION, mask.getVersion());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getKeywords() != null) filter.set(KEYWORDS, mask.getKeywords());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getVersionDate() != null) filter.set(VERSIONDATE, mask.getVersionDate());
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
    public static int count(DBGedFilesVersion mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getVersion() != null) filter.set(VERSION, mask.getVersion());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getKeywords() != null) filter.set(KEYWORDS, mask.getKeywords());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getVersionDate() != null) filter.set(VERSIONDATE, mask.getVersionDate());
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
    public static double avg(String field, DBGedFilesVersion mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getVersion() != null) filter.set(VERSION, mask.getVersion());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getKeywords() != null) filter.set(KEYWORDS, mask.getKeywords());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getVersionDate() != null) filter.set(VERSIONDATE, mask.getVersionDate());
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
    public static double min(String field, DBGedFilesVersion mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getVersion() != null) filter.set(VERSION, mask.getVersion());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getKeywords() != null) filter.set(KEYWORDS, mask.getKeywords());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getVersionDate() != null) filter.set(VERSIONDATE, mask.getVersionDate());
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
    public static double max(String field, DBGedFilesVersion mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getVersion() != null) filter.set(VERSION, mask.getVersion());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getKeywords() != null) filter.set(KEYWORDS, mask.getKeywords());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getVersionDate() != null) filter.set(VERSIONDATE, mask.getVersionDate());
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
    public static double std(String field, DBGedFilesVersion mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getVersion() != null) filter.set(VERSION, mask.getVersion());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getKeywords() != null) filter.set(KEYWORDS, mask.getKeywords());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getVersionDate() != null) filter.set(VERSIONDATE, mask.getVersionDate());
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
    public static String[] distinct(String field, DBGedFilesVersion mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getFileId() != null) filter.set(FILEID, mask.getFileId());
            if(mask.getFileBaseId() != null) filter.set(FILEBASEID, mask.getFileBaseId());
            if(mask.getVisible() != null) filter.set(VISIBLE, mask.getVisible());
            if(mask.getVersion() != null) filter.set(VERSION, mask.getVersion());
            if(mask.getReference() != null) filter.set(REFERENCE, mask.getReference());
            if(mask.getKeywords() != null) filter.set(KEYWORDS, mask.getKeywords());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getSize() != null) filter.set(SIZE, mask.getSize());
            if(mask.getAuthorId() != null) filter.set(AUTHORID, mask.getAuthorId());
            if(mask.getVersionDate() != null) filter.set(VERSIONDATE, mask.getVersionDate());
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
