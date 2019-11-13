package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBTerminal
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBTerminal
{
    protected boolean inError = false;
    protected String terminalId;
    protected String userAgent;
    protected String navType;
    protected String counter;
    protected String imageId;

    public static String TERMINALID = "terminalId";
    public static String USERAGENT = "userAgent";
    public static String NAVTYPE = "navType";
    /** wap **/
    public static final String NAVTYPE_WAP = "wap";
    /** imode **/
    public static final String NAVTYPE_IMODE = "imode";
    /** pc **/
    public static final String NAVTYPE_PC = "pc";
    /** pda **/
    public static final String NAVTYPE_PDA = "pda";
    /** Contains the SQL enumeration for NAVTYPE **/
    public static final ArrayList _NAVTYPE_LIST = new ArrayList();
    static {_NAVTYPE_LIST.add(NAVTYPE_WAP);_NAVTYPE_LIST.add(NAVTYPE_IMODE);_NAVTYPE_LIST.add(NAVTYPE_PC);_NAVTYPE_LIST.add(NAVTYPE_PDA);}
    /** Contains the SQL HASHMAP for NAVTYPE **/
    public static final OrdHashMap _NAVTYPE_MAP = new OrdHashMap();
    static {_NAVTYPE_MAP.put(NAVTYPE_WAP,NAVTYPE_WAP);_NAVTYPE_MAP.put(NAVTYPE_IMODE,NAVTYPE_IMODE);_NAVTYPE_MAP.put(NAVTYPE_PC,NAVTYPE_PC);_NAVTYPE_MAP.put(NAVTYPE_PDA,NAVTYPE_PDA);}
    public static String COUNTER = "counter";
    public static String IMAGEID = "imageId";
    public static String TABLE = "Terminal";

    private static Database db = Database.getInstance();


    public void clear()
    {
        terminalId = null;
        userAgent = null;
        navType = null;
        counter = null;
        imageId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBTerminal a = (DBTerminal)obj;
        return (terminalId==null?a.getTerminalId()==null:terminalId.equals(a.getTerminalId())) && (userAgent==null?a.getUserAgent()==null:userAgent.equals(a.getUserAgent())) && (navType==null?a.getNavType()==null:navType.equals(a.getNavType())) && (counter==null?a.getCounter()==null:counter.equals(a.getCounter())) && (imageId==null?a.getImageId()==null:imageId.equals(a.getImageId()));    }

    public int hashCode()
    {
        return (terminalId!=null?terminalId.hashCode():0) + (userAgent!=null?userAgent.hashCode():0) + (navType!=null?navType.hashCode():0) + (counter!=null?counter.hashCode():0) + (imageId!=null?imageId.hashCode():0);
    }

    public String toString()
    {
        return "terminalId="+terminalId+"|"+"userAgent="+userAgent+"|"+"navType="+navType+"|"+"counter="+counter+"|"+"imageId="+imageId;
    }

    public void refresh()
    {
        if(terminalId != null)
        {
            DBTerminal mask = new DBTerminal();
            mask.setTerminalId(terminalId);
            DBTerminal var = DBTerminal.loadByKey(mask);
            if(var != null)
            {
                terminalId = var.getTerminalId();
                userAgent = var.getUserAgent();
                navType = var.getNavType();
                counter = var.getCounter();
                imageId = var.getImageId();
            }
        }
    }

    public void initBean(DBTerminal db)
    {
        this.terminalId = db.getTerminalId();
        this.userAgent = db.getUserAgent();
        this.navType = db.getNavType();
        this.counter = db.getCounter();
        this.imageId = db.getImageId();
    }

    public String getTerminalId()
    {
        return terminalId;
    }

    public void setTerminalId(String terminalId)
    {
        this.terminalId = terminalId;
    }

    public String getUserAgent()
    {
        return userAgent;
    }

    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    public String getNavType()
    {
        return navType;
    }

    public void setNavType(String navType)
    {
        this.navType = navType;
    }

    public String getCounter()
    {
        return counter;
    }

    public void setCounter(String counter)
    {
        this.counter = counter;
    }

    public String getImageId()
    {
        return imageId;
    }

    public void setImageId(String imageId)
    {
        this.imageId = imageId;
    }

    /**
     * @return a DBTerminal table or null if nothing found or if an error occured
     **/
    public static DBTerminal[] load(DBTerminal mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBTerminal table or null if nothing found or if an error occured
     **/
    public static DBTerminal[] load(SQLConstraint sqlconstraint, DBTerminal mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getTerminalId() != null) filter.set(TERMINALID, mask.getTerminalId());
            if(mask.getUserAgent() != null) filter.set(USERAGENT, mask.getUserAgent());
            if(mask.getNavType() != null) filter.set(NAVTYPE, mask.getNavType());
            if(mask.getCounter() != null) filter.set(COUNTER, mask.getCounter());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{TERMINALID, USERAGENT, NAVTYPE, COUNTER, IMAGEID}, filter);
            if(pvp == null) return null;
            else
            {
                DBTerminal[] result = new DBTerminal[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBTerminal();
                    result[i].setTerminalId(pvp[i].get(TERMINALID));
                    result[i].setUserAgent(pvp[i].get(USERAGENT));
                    result[i].setNavType(pvp[i].get(NAVTYPE));
                    result[i].setCounter(pvp[i].get(COUNTER));
                    result[i].setImageId(pvp[i].get(IMAGEID));
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
     * @return a DBTerminal object or null if nothing found or if an error occured
     **/
    public static DBTerminal loadByKey(DBTerminal mask)
    {
        DBTerminal[] res = DBTerminal.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBTerminal object or null if nothing found or if an error occured
     **/
    public static DBTerminal loadByKey(DBTerminal mask, SQLConstraint sqlconstraint)
    {
        DBTerminal[] res = DBTerminal.load(sqlconstraint, mask);
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
        toinsert.set(TERMINALID,terminalId);
        toinsert.set(USERAGENT,userAgent);
        toinsert.set(NAVTYPE,navType);
        toinsert.set(COUNTER,counter);
        toinsert.set(IMAGEID,imageId);

        // Store a new entry
        if(terminalId == null)
        {
            try
            {
                terminalId = db.insert(TABLE, toinsert);
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
            filter.set(TERMINALID, terminalId);

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
        if(terminalId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(TERMINALID, terminalId);

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
    public static void delete(DBTerminal mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getTerminalId() != null) filter.set(TERMINALID, mask.getTerminalId());
            if(mask.getUserAgent() != null) filter.set(USERAGENT, mask.getUserAgent());
            if(mask.getNavType() != null) filter.set(NAVTYPE, mask.getNavType());
            if(mask.getCounter() != null) filter.set(COUNTER, mask.getCounter());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
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
    public static int count(DBTerminal mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getTerminalId() != null) filter.set(TERMINALID, mask.getTerminalId());
            if(mask.getUserAgent() != null) filter.set(USERAGENT, mask.getUserAgent());
            if(mask.getNavType() != null) filter.set(NAVTYPE, mask.getNavType());
            if(mask.getCounter() != null) filter.set(COUNTER, mask.getCounter());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
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
    public static double avg(String field, DBTerminal mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getTerminalId() != null) filter.set(TERMINALID, mask.getTerminalId());
            if(mask.getUserAgent() != null) filter.set(USERAGENT, mask.getUserAgent());
            if(mask.getNavType() != null) filter.set(NAVTYPE, mask.getNavType());
            if(mask.getCounter() != null) filter.set(COUNTER, mask.getCounter());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
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
    public static double min(String field, DBTerminal mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getTerminalId() != null) filter.set(TERMINALID, mask.getTerminalId());
            if(mask.getUserAgent() != null) filter.set(USERAGENT, mask.getUserAgent());
            if(mask.getNavType() != null) filter.set(NAVTYPE, mask.getNavType());
            if(mask.getCounter() != null) filter.set(COUNTER, mask.getCounter());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
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
    public static double max(String field, DBTerminal mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getTerminalId() != null) filter.set(TERMINALID, mask.getTerminalId());
            if(mask.getUserAgent() != null) filter.set(USERAGENT, mask.getUserAgent());
            if(mask.getNavType() != null) filter.set(NAVTYPE, mask.getNavType());
            if(mask.getCounter() != null) filter.set(COUNTER, mask.getCounter());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
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
    public static double std(String field, DBTerminal mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getTerminalId() != null) filter.set(TERMINALID, mask.getTerminalId());
            if(mask.getUserAgent() != null) filter.set(USERAGENT, mask.getUserAgent());
            if(mask.getNavType() != null) filter.set(NAVTYPE, mask.getNavType());
            if(mask.getCounter() != null) filter.set(COUNTER, mask.getCounter());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
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
    public static String[] distinct(String field, DBTerminal mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getTerminalId() != null) filter.set(TERMINALID, mask.getTerminalId());
            if(mask.getUserAgent() != null) filter.set(USERAGENT, mask.getUserAgent());
            if(mask.getNavType() != null) filter.set(NAVTYPE, mask.getNavType());
            if(mask.getCounter() != null) filter.set(COUNTER, mask.getCounter());
            if(mask.getImageId() != null) filter.set(IMAGEID, mask.getImageId());
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
