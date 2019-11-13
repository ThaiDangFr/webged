package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBSnmpParam
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBSnmpParam
{
    protected boolean inError = false;
    protected String snmpParamId;
    protected String snmpHostId;
    protected String oid;

    public static String SNMPPARAMID = "snmpParamId";
    public static String SNMPHOSTID = "snmpHostId";
    public static String OID = "oid";
    public static String TABLE = "SnmpParam";

    private static Database db = Database.getInstance();


    public void clear()
    {
        snmpParamId = null;
        snmpHostId = null;
        oid = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBSnmpParam a = (DBSnmpParam)obj;
        return (snmpParamId==null?a.getSnmpParamId()==null:snmpParamId.equals(a.getSnmpParamId())) && (snmpHostId==null?a.getSnmpHostId()==null:snmpHostId.equals(a.getSnmpHostId())) && (oid==null?a.getOid()==null:oid.equals(a.getOid()));    }

    public int hashCode()
    {
        return (snmpParamId!=null?snmpParamId.hashCode():0) + (snmpHostId!=null?snmpHostId.hashCode():0) + (oid!=null?oid.hashCode():0);
    }

    public String toString()
    {
        return "snmpParamId="+snmpParamId+"|"+"snmpHostId="+snmpHostId+"|"+"oid="+oid;
    }

    public void refresh()
    {
        if(snmpParamId != null)
        {
            DBSnmpParam mask = new DBSnmpParam();
            mask.setSnmpParamId(snmpParamId);
            DBSnmpParam var = DBSnmpParam.loadByKey(mask);
            if(var != null)
            {
                snmpParamId = var.getSnmpParamId();
                snmpHostId = var.getSnmpHostId();
                oid = var.getOid();
            }
        }
    }

    public void initBean(DBSnmpParam db)
    {
        this.snmpParamId = db.getSnmpParamId();
        this.snmpHostId = db.getSnmpHostId();
        this.oid = db.getOid();
    }

    public String getSnmpParamId()
    {
        return snmpParamId;
    }

    public void setSnmpParamId(String snmpParamId)
    {
        this.snmpParamId = snmpParamId;
    }

    public String getSnmpHostId()
    {
        return snmpHostId;
    }

    public void setSnmpHostId(String snmpHostId)
    {
        this.snmpHostId = snmpHostId;
    }

    public String getOid()
    {
        return oid;
    }

    public void setOid(String oid)
    {
        this.oid = oid;
    }

    /**
     * @return a DBSnmpParam table or null if nothing found or if an error occured
     **/
    public static DBSnmpParam[] load(DBSnmpParam mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBSnmpParam table or null if nothing found or if an error occured
     **/
    public static DBSnmpParam[] load(SQLConstraint sqlconstraint, DBSnmpParam mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{SNMPPARAMID, SNMPHOSTID, OID}, filter);
            if(pvp == null) return null;
            else
            {
                DBSnmpParam[] result = new DBSnmpParam[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBSnmpParam();
                    result[i].setSnmpParamId(pvp[i].get(SNMPPARAMID));
                    result[i].setSnmpHostId(pvp[i].get(SNMPHOSTID));
                    result[i].setOid(pvp[i].get(OID));
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
     * @return a DBSnmpParam object or null if nothing found or if an error occured
     **/
    public static DBSnmpParam loadByKey(DBSnmpParam mask)
    {
        DBSnmpParam[] res = DBSnmpParam.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBSnmpParam object or null if nothing found or if an error occured
     **/
    public static DBSnmpParam loadByKey(DBSnmpParam mask, SQLConstraint sqlconstraint)
    {
        DBSnmpParam[] res = DBSnmpParam.load(sqlconstraint, mask);
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
        toinsert.set(SNMPPARAMID,snmpParamId);
        toinsert.set(SNMPHOSTID,snmpHostId);
        toinsert.set(OID,oid);

        // Store a new entry
        if(snmpParamId == null)
        {
            try
            {
                snmpParamId = db.insert(TABLE, toinsert);
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
            filter.set(SNMPPARAMID, snmpParamId);

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
        if(snmpParamId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(SNMPPARAMID, snmpParamId);

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
    public static void delete(DBSnmpParam mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static int count(DBSnmpParam mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static double avg(String field, DBSnmpParam mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static double min(String field, DBSnmpParam mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static double max(String field, DBSnmpParam mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static double std(String field, DBSnmpParam mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
    public static String[] distinct(String field, DBSnmpParam mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getSnmpParamId() != null) filter.set(SNMPPARAMID, mask.getSnmpParamId());
            if(mask.getSnmpHostId() != null) filter.set(SNMPHOSTID, mask.getSnmpHostId());
            if(mask.getOid() != null) filter.set(OID, mask.getOid());
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
