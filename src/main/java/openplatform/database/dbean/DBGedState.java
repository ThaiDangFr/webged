package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGedState
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGedState
{
    protected boolean inError = false;
    protected String id;
    protected String generalColumn;
    protected String gedTemplateItem;
    protected String next;

    public static String ID = "id";
    public static String GENERALCOLUMN = "generalColumn";
    /** filename **/
    public static final String GENERALCOLUMN_FILENAME = "filename";
    /** dirname **/
    public static final String GENERALCOLUMN_DIRNAME = "dirname";
    /** author **/
    public static final String GENERALCOLUMN_AUTHOR = "author";
    /** date **/
    public static final String GENERALCOLUMN_DATE = "date";
    /** reference **/
    public static final String GENERALCOLUMN_REFERENCE = "reference";
    /** version **/
    public static final String GENERALCOLUMN_VERSION = "version";
    /** procstatus **/
    public static final String GENERALCOLUMN_PROCSTATUS = "procstatus";
    /** expirydate **/
    public static final String GENERALCOLUMN_EXPIRYDATE = "expirydate";
    /** procstep **/
    public static final String GENERALCOLUMN_PROCSTEP = "procstep";
    /** proccurrentuser **/
    public static final String GENERALCOLUMN_PROCCURRENTUSER = "proccurrentuser";
    /** lockby **/
    public static final String GENERALCOLUMN_LOCKBY = "lockby";
    /** Contains the SQL enumeration for GENERALCOLUMN **/
    public static final ArrayList _GENERALCOLUMN_LIST = new ArrayList();
    static {_GENERALCOLUMN_LIST.add(GENERALCOLUMN_FILENAME);_GENERALCOLUMN_LIST.add(GENERALCOLUMN_DIRNAME);_GENERALCOLUMN_LIST.add(GENERALCOLUMN_AUTHOR);_GENERALCOLUMN_LIST.add(GENERALCOLUMN_DATE);_GENERALCOLUMN_LIST.add(GENERALCOLUMN_REFERENCE);_GENERALCOLUMN_LIST.add(GENERALCOLUMN_VERSION);_GENERALCOLUMN_LIST.add(GENERALCOLUMN_PROCSTATUS);_GENERALCOLUMN_LIST.add(GENERALCOLUMN_EXPIRYDATE);_GENERALCOLUMN_LIST.add(GENERALCOLUMN_PROCSTEP);_GENERALCOLUMN_LIST.add(GENERALCOLUMN_PROCCURRENTUSER);_GENERALCOLUMN_LIST.add(GENERALCOLUMN_LOCKBY);}
    /** Contains the SQL HASHMAP for GENERALCOLUMN **/
    public static final OrdHashMap _GENERALCOLUMN_MAP = new OrdHashMap();
    static {_GENERALCOLUMN_MAP.put(GENERALCOLUMN_FILENAME,GENERALCOLUMN_FILENAME);_GENERALCOLUMN_MAP.put(GENERALCOLUMN_DIRNAME,GENERALCOLUMN_DIRNAME);_GENERALCOLUMN_MAP.put(GENERALCOLUMN_AUTHOR,GENERALCOLUMN_AUTHOR);_GENERALCOLUMN_MAP.put(GENERALCOLUMN_DATE,GENERALCOLUMN_DATE);_GENERALCOLUMN_MAP.put(GENERALCOLUMN_REFERENCE,GENERALCOLUMN_REFERENCE);_GENERALCOLUMN_MAP.put(GENERALCOLUMN_VERSION,GENERALCOLUMN_VERSION);_GENERALCOLUMN_MAP.put(GENERALCOLUMN_PROCSTATUS,GENERALCOLUMN_PROCSTATUS);_GENERALCOLUMN_MAP.put(GENERALCOLUMN_EXPIRYDATE,GENERALCOLUMN_EXPIRYDATE);_GENERALCOLUMN_MAP.put(GENERALCOLUMN_PROCSTEP,GENERALCOLUMN_PROCSTEP);_GENERALCOLUMN_MAP.put(GENERALCOLUMN_PROCCURRENTUSER,GENERALCOLUMN_PROCCURRENTUSER);_GENERALCOLUMN_MAP.put(GENERALCOLUMN_LOCKBY,GENERALCOLUMN_LOCKBY);}
    public static String GEDTEMPLATEITEM = "gedTemplateItem";
    public static String NEXT = "next";
    public static String TABLE = "GedState";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        generalColumn = null;
        gedTemplateItem = null;
        next = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGedState a = (DBGedState)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (generalColumn==null?a.getGeneralColumn()==null:generalColumn.equals(a.getGeneralColumn())) && (gedTemplateItem==null?a.getGedTemplateItem()==null:gedTemplateItem.equals(a.getGedTemplateItem())) && (next==null?a.getNext()==null:next.equals(a.getNext()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (generalColumn!=null?generalColumn.hashCode():0) + (gedTemplateItem!=null?gedTemplateItem.hashCode():0) + (next!=null?next.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"generalColumn="+generalColumn+"|"+"gedTemplateItem="+gedTemplateItem+"|"+"next="+next;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBGedState mask = new DBGedState();
            mask.setId(id);
            DBGedState var = DBGedState.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                generalColumn = var.getGeneralColumn();
                gedTemplateItem = var.getGedTemplateItem();
                next = var.getNext();
            }
        }
    }

    public void initBean(DBGedState db)
    {
        this.id = db.getId();
        this.generalColumn = db.getGeneralColumn();
        this.gedTemplateItem = db.getGedTemplateItem();
        this.next = db.getNext();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getGeneralColumn()
    {
        return generalColumn;
    }

    public void setGeneralColumn(String generalColumn)
    {
        this.generalColumn = generalColumn;
    }

    public String getGedTemplateItem()
    {
        return gedTemplateItem;
    }

    public void setGedTemplateItem(String gedTemplateItem)
    {
        this.gedTemplateItem = gedTemplateItem;
    }

    public String getNext()
    {
        return next;
    }

    public void setNext(String next)
    {
        this.next = next;
    }

    /**
     * @return a DBGedState table or null if nothing found or if an error occured
     **/
    public static DBGedState[] load(DBGedState mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGedState table or null if nothing found or if an error occured
     **/
    public static DBGedState[] load(SQLConstraint sqlconstraint, DBGedState mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGeneralColumn() != null) filter.set(GENERALCOLUMN, mask.getGeneralColumn());
            if(mask.getGedTemplateItem() != null) filter.set(GEDTEMPLATEITEM, mask.getGedTemplateItem());
            if(mask.getNext() != null) filter.set(NEXT, mask.getNext());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, GENERALCOLUMN, GEDTEMPLATEITEM, NEXT}, filter);
            if(pvp == null) return null;
            else
            {
                DBGedState[] result = new DBGedState[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGedState();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setGeneralColumn(pvp[i].get(GENERALCOLUMN));
                    result[i].setGedTemplateItem(pvp[i].get(GEDTEMPLATEITEM));
                    result[i].setNext(pvp[i].get(NEXT));
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
     * @return a DBGedState object or null if nothing found or if an error occured
     **/
    public static DBGedState loadByKey(DBGedState mask)
    {
        DBGedState[] res = DBGedState.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGedState object or null if nothing found or if an error occured
     **/
    public static DBGedState loadByKey(DBGedState mask, SQLConstraint sqlconstraint)
    {
        DBGedState[] res = DBGedState.load(sqlconstraint, mask);
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
        toinsert.set(GENERALCOLUMN,generalColumn);
        toinsert.set(GEDTEMPLATEITEM,gedTemplateItem);
        toinsert.set(NEXT,next);

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
    public static void delete(DBGedState mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGeneralColumn() != null) filter.set(GENERALCOLUMN, mask.getGeneralColumn());
            if(mask.getGedTemplateItem() != null) filter.set(GEDTEMPLATEITEM, mask.getGedTemplateItem());
            if(mask.getNext() != null) filter.set(NEXT, mask.getNext());
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
    public static int count(DBGedState mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGeneralColumn() != null) filter.set(GENERALCOLUMN, mask.getGeneralColumn());
            if(mask.getGedTemplateItem() != null) filter.set(GEDTEMPLATEITEM, mask.getGedTemplateItem());
            if(mask.getNext() != null) filter.set(NEXT, mask.getNext());
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
    public static double avg(String field, DBGedState mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGeneralColumn() != null) filter.set(GENERALCOLUMN, mask.getGeneralColumn());
            if(mask.getGedTemplateItem() != null) filter.set(GEDTEMPLATEITEM, mask.getGedTemplateItem());
            if(mask.getNext() != null) filter.set(NEXT, mask.getNext());
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
    public static double min(String field, DBGedState mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGeneralColumn() != null) filter.set(GENERALCOLUMN, mask.getGeneralColumn());
            if(mask.getGedTemplateItem() != null) filter.set(GEDTEMPLATEITEM, mask.getGedTemplateItem());
            if(mask.getNext() != null) filter.set(NEXT, mask.getNext());
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
    public static double max(String field, DBGedState mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGeneralColumn() != null) filter.set(GENERALCOLUMN, mask.getGeneralColumn());
            if(mask.getGedTemplateItem() != null) filter.set(GEDTEMPLATEITEM, mask.getGedTemplateItem());
            if(mask.getNext() != null) filter.set(NEXT, mask.getNext());
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
    public static double std(String field, DBGedState mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGeneralColumn() != null) filter.set(GENERALCOLUMN, mask.getGeneralColumn());
            if(mask.getGedTemplateItem() != null) filter.set(GEDTEMPLATEITEM, mask.getGedTemplateItem());
            if(mask.getNext() != null) filter.set(NEXT, mask.getNext());
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
    public static String[] distinct(String field, DBGedState mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getGeneralColumn() != null) filter.set(GENERALCOLUMN, mask.getGeneralColumn());
            if(mask.getGedTemplateItem() != null) filter.set(GEDTEMPLATEITEM, mask.getGedTemplateItem());
            if(mask.getNext() != null) filter.set(NEXT, mask.getNext());
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
