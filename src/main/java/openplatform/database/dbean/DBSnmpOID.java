package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpOID
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpOID
{
    protected boolean inError = false;
    protected String id;
    protected String oid;
    protected String mibName;
    protected String captorName;
    protected String description;
    protected String parentId;
    protected String mibFileId;

    public static String ID = "id";
    public static String OID = "oid";
    public static String MIBNAME = "mibName";
    public static String CAPTORNAME = "captorName";
    public static String DESCRIPTION = "description";
    public static String PARENTID = "parentId";
    public static String MIBFILEID = "mibFileId";
    public static String TABLE = "SnmpOID";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        oid = null;
        mibName = null;
        captorName = null;
        description = null;
        parentId = null;
        mibFileId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBSnmpOID a = (DBSnmpOID)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (oid==null?a.getOid()==null:oid.equals(a.getOid())) && (mibName==null?a.getMibName()==null:mibName.equals(a.getMibName())) && (captorName==null?a.getCaptorName()==null:captorName.equals(a.getCaptorName())) && (description==null?a.getDescription()==null:description.equals(a.getDescription())) && (parentId==null?a.getParentId()==null:parentId.equals(a.getParentId())) && (mibFileId==null?a.getMibFileId()==null:mibFileId.equals(a.getMibFileId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (oid!=null?oid.hashCode():0) + (mibName!=null?mibName.hashCode():0) + (captorName!=null?captorName.hashCode():0) + (description!=null?description.hashCode():0) + (parentId!=null?parentId.hashCode():0) + (mibFileId!=null?mibFileId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"oid="+oid+"|"+"mibName="+mibName+"|"+"captorName="+captorName+"|"+"description="+description+"|"+"parentId="+parentId+"|"+"mibFileId="+mibFileId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBSnmpOID mask = new DBSnmpOID();
            mask.setId(id);
            DBSnmpOID var = DBSnmpOID.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                oid = var.getOid();
                mibName = var.getMibName();
                captorName = var.getCaptorName();
                description = var.getDescription();
                parentId = var.getParentId();
                mibFileId = var.getMibFileId();
            }
        }
    }

    public void initBean(DBSnmpOID db)
    {
        this.id = db.getId();
        this.oid = db.getOid();
        this.mibName = db.getMibName();
        this.captorName = db.getCaptorName();
        this.description = db.getDescription();
        this.parentId = db.getParentId();
        this.mibFileId = db.getMibFileId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOid()
    {
        return oid;
    }

    public void setOid(String oid)
    {
        this.oid = oid;
    }

    public String getMibName()
    {
        return mibName;
    }

    public void setMibName(String mibName)
    {
        this.mibName = mibName;
    }

    public String getCaptorName()
    {
        return captorName;
    }

    public void setCaptorName(String captorName)
    {
        this.captorName = captorName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getMibFileId()
    {
        return mibFileId;
    }

    public void setMibFileId(String mibFileId)
    {
        this.mibFileId = mibFileId;
    }

    /**
     * @return a DBSnmpOID table or null if nothing found or if an error occured
     **/
    public static DBSnmpOID[] load(DBSnmpOID mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpOID table or null if nothing found or if an error occured
     **/
    public static DBSnmpOID[] load(SQLConstraint sqlconstraint, DBSnmpOID mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
            if(mask.getMibName() != null) filter.set(MIBNAME, mask.getMibName());
            if(mask.getCaptorName() != null) filter.set(CAPTORNAME, mask.getCaptorName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getMibFileId() != null) filter.set(MIBFILEID, mask.getMibFileId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, OID, MIBNAME, CAPTORNAME, DESCRIPTION, PARENTID, MIBFILEID}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpOID[] result = new DBSnmpOID[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpOID();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setOid(pvp[i].get(OID));
                    result[i].setMibName(pvp[i].get(MIBNAME));
                    result[i].setCaptorName(pvp[i].get(CAPTORNAME));
                    result[i].setDescription(pvp[i].get(DESCRIPTION));
                    result[i].setParentId(pvp[i].get(PARENTID));
                    result[i].setMibFileId(pvp[i].get(MIBFILEID));
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
     * @return a DBSnmpOID object or null if nothing found or if an error occured
     **/
    public static DBSnmpOID loadByKey(DBSnmpOID mask)
    {
        DBSnmpOID[] res = DBSnmpOID.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpOID object or null if nothing found or if an error occured
     **/
    public static DBSnmpOID loadByKey(DBSnmpOID mask, SQLConstraint sqlconstraint)
    {
        DBSnmpOID[] res = DBSnmpOID.load(sqlconstraint, mask);
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
        toinsert.set(OID,oid);
        toinsert.set(MIBNAME,mibName);
        toinsert.set(CAPTORNAME,captorName);
        toinsert.set(DESCRIPTION,description);
        toinsert.set(PARENTID,parentId);
        toinsert.set(MIBFILEID,mibFileId);

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
    public static void delete(DBSnmpOID mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
            if(mask.getMibName() != null) filter.set(MIBNAME, mask.getMibName());
            if(mask.getCaptorName() != null) filter.set(CAPTORNAME, mask.getCaptorName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getMibFileId() != null) filter.set(MIBFILEID, mask.getMibFileId());
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
    public static int count(DBSnmpOID mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
            if(mask.getMibName() != null) filter.set(MIBNAME, mask.getMibName());
            if(mask.getCaptorName() != null) filter.set(CAPTORNAME, mask.getCaptorName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getMibFileId() != null) filter.set(MIBFILEID, mask.getMibFileId());
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
    public static double avg(String field, DBSnmpOID mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
            if(mask.getMibName() != null) filter.set(MIBNAME, mask.getMibName());
            if(mask.getCaptorName() != null) filter.set(CAPTORNAME, mask.getCaptorName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getMibFileId() != null) filter.set(MIBFILEID, mask.getMibFileId());
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
    public static double min(String field, DBSnmpOID mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
            if(mask.getMibName() != null) filter.set(MIBNAME, mask.getMibName());
            if(mask.getCaptorName() != null) filter.set(CAPTORNAME, mask.getCaptorName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getMibFileId() != null) filter.set(MIBFILEID, mask.getMibFileId());
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
    public static double max(String field, DBSnmpOID mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
            if(mask.getMibName() != null) filter.set(MIBNAME, mask.getMibName());
            if(mask.getCaptorName() != null) filter.set(CAPTORNAME, mask.getCaptorName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getMibFileId() != null) filter.set(MIBFILEID, mask.getMibFileId());
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
    public static double std(String field, DBSnmpOID mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
            if(mask.getMibName() != null) filter.set(MIBNAME, mask.getMibName());
            if(mask.getCaptorName() != null) filter.set(CAPTORNAME, mask.getCaptorName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getMibFileId() != null) filter.set(MIBFILEID, mask.getMibFileId());
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
    public static String[] distinct(String field, DBSnmpOID mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
            if(mask.getMibName() != null) filter.set(MIBNAME, mask.getMibName());
            if(mask.getCaptorName() != null) filter.set(CAPTORNAME, mask.getCaptorName());
            if(mask.getDescription() != null) filter.set(DESCRIPTION, mask.getDescription());
            if(mask.getParentId() != null) filter.set(PARENTID, mask.getParentId());
            if(mask.getMibFileId() != null) filter.set(MIBFILEID, mask.getMibFileId());
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
