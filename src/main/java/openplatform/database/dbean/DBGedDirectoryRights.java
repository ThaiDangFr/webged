package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedDirectoryRights
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedDirectoryRights
{
    protected boolean inError = false;
    protected String id;
    protected String directoryId;
    protected String groupId;
    protected String readRight;
    protected String writeRight;

    public static String ID = "id";
    public static String DIRECTORYID = "directoryId";
    public static String GROUPID = "groupId";
    public static String READRIGHT = "readRight";
    public static String WRITERIGHT = "writeRight";
    public static String TABLE = "GedDirectoryRights";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        directoryId = null;
        groupId = null;
        readRight = null;
        writeRight = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedDirectoryRights a = (DBGedDirectoryRights)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (directoryId==null?a.getDirectoryId()==null:directoryId.equals(a.getDirectoryId())) && (groupId==null?a.getGroupId()==null:groupId.equals(a.getGroupId())) && (readRight==null?a.getReadRight()==null:readRight.equals(a.getReadRight())) && (writeRight==null?a.getWriteRight()==null:writeRight.equals(a.getWriteRight()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (directoryId!=null?directoryId.hashCode():0) + (groupId!=null?groupId.hashCode():0) + (readRight!=null?readRight.hashCode():0) + (writeRight!=null?writeRight.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"directoryId="+directoryId+"|"+"groupId="+groupId+"|"+"readRight="+readRight+"|"+"writeRight="+writeRight;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedDirectoryRights mask = new DBGedDirectoryRights();
            mask.setId(id);
            DBGedDirectoryRights var = DBGedDirectoryRights.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                directoryId = var.getDirectoryId();
                groupId = var.getGroupId();
                readRight = var.getReadRight();
                writeRight = var.getWriteRight();
            }
        }
    }

    public void initBean(DBGedDirectoryRights db)
    {
        this.id = db.getId();
        this.directoryId = db.getDirectoryId();
        this.groupId = db.getGroupId();
        this.readRight = db.getReadRight();
        this.writeRight = db.getWriteRight();
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

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getReadRight()
    {
        return readRight;
    }

    public void setReadRight(String readRight)
    {
        this.readRight = readRight;
    }

    public String getWriteRight()
    {
        return writeRight;
    }

    public void setWriteRight(String writeRight)
    {
        this.writeRight = writeRight;
    }

    /**
     * @return a DBGedDirectoryRights table or null if nothing found or if an error occured
     **/
    public static DBGedDirectoryRights[] load(DBGedDirectoryRights mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedDirectoryRights table or null if nothing found or if an error occured
     **/
    public static DBGedDirectoryRights[] load(SQLConstraint sqlconstraint, DBGedDirectoryRights mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getReadRight() != null) filter.set(READRIGHT, mask.getReadRight());
            if(mask.getWriteRight() != null) filter.set(WRITERIGHT, mask.getWriteRight());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, DIRECTORYID, GROUPID, READRIGHT, WRITERIGHT}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedDirectoryRights[] result = new DBGedDirectoryRights[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedDirectoryRights();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setDirectoryId(pvp[i].get(DIRECTORYID));
                    result[i].setGroupId(pvp[i].get(GROUPID));
                    result[i].setReadRight(pvp[i].get(READRIGHT));
                    result[i].setWriteRight(pvp[i].get(WRITERIGHT));
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
     * @return a DBGedDirectoryRights object or null if nothing found or if an error occured
     **/
    public static DBGedDirectoryRights loadByKey(DBGedDirectoryRights mask)
    {
        DBGedDirectoryRights[] res = DBGedDirectoryRights.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedDirectoryRights object or null if nothing found or if an error occured
     **/
    public static DBGedDirectoryRights loadByKey(DBGedDirectoryRights mask, SQLConstraint sqlconstraint)
    {
        DBGedDirectoryRights[] res = DBGedDirectoryRights.load(sqlconstraint, mask);
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
        toinsert.set(GROUPID,groupId);
        toinsert.set(READRIGHT,readRight);
        toinsert.set(WRITERIGHT,writeRight);

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
    public static void delete(DBGedDirectoryRights mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getReadRight() != null) filter.set(READRIGHT, mask.getReadRight());
            if(mask.getWriteRight() != null) filter.set(WRITERIGHT, mask.getWriteRight());
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
    public static int count(DBGedDirectoryRights mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getReadRight() != null) filter.set(READRIGHT, mask.getReadRight());
            if(mask.getWriteRight() != null) filter.set(WRITERIGHT, mask.getWriteRight());
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
    public static double avg(String field, DBGedDirectoryRights mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getReadRight() != null) filter.set(READRIGHT, mask.getReadRight());
            if(mask.getWriteRight() != null) filter.set(WRITERIGHT, mask.getWriteRight());
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
    public static double min(String field, DBGedDirectoryRights mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getReadRight() != null) filter.set(READRIGHT, mask.getReadRight());
            if(mask.getWriteRight() != null) filter.set(WRITERIGHT, mask.getWriteRight());
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
    public static double max(String field, DBGedDirectoryRights mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getReadRight() != null) filter.set(READRIGHT, mask.getReadRight());
            if(mask.getWriteRight() != null) filter.set(WRITERIGHT, mask.getWriteRight());
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
    public static double std(String field, DBGedDirectoryRights mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getReadRight() != null) filter.set(READRIGHT, mask.getReadRight());
            if(mask.getWriteRight() != null) filter.set(WRITERIGHT, mask.getWriteRight());
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
    public static String[] distinct(String field, DBGedDirectoryRights mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getDirectoryId() != null) filter.set(DIRECTORYID, mask.getDirectoryId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getReadRight() != null) filter.set(READRIGHT, mask.getReadRight());
            if(mask.getWriteRight() != null) filter.set(WRITERIGHT, mask.getWriteRight());
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
