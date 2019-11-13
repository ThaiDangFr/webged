package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBGroups
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBGroups
{
    protected boolean inError = false;
    protected String groupId;
    protected String name;
    protected String comment;

    public static String GROUPID = "groupId";
    public static String NAME = "name";
    public static String COMMENT = "comment";
    public static String TABLE = "Groups";

    private static Database db = Database.getInstance();


    public void clear()
    {
        groupId = null;
        name = null;
        comment = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBGroups a = (DBGroups)obj;
        return (groupId==null?a.getGroupId()==null:groupId.equals(a.getGroupId())) && (name==null?a.getName()==null:name.equals(a.getName())) && (comment==null?a.getComment()==null:comment.equals(a.getComment()));    }

    public int hashCode()
    {
        return (groupId!=null?groupId.hashCode():0) + (name!=null?name.hashCode():0) + (comment!=null?comment.hashCode():0);
    }

    public String toString()
    {
        return "groupId="+groupId+"|"+"name="+name+"|"+"comment="+comment;
    }

    public void refresh()
    {
        if(groupId != null)
        {
            DBGroups mask = new DBGroups();
            mask.setGroupId(groupId);
            DBGroups var = DBGroups.loadByKey(mask);
            if(var != null)
            {
                groupId = var.getGroupId();
                name = var.getName();
                comment = var.getComment();
            }
        }
    }

    public void initBean(DBGroups db)
    {
        this.groupId = db.getGroupId();
        this.name = db.getName();
        this.comment = db.getComment();
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    /**
     * @return a DBGroups table or null if nothing found or if an error occured
     **/
    public static DBGroups[] load(DBGroups mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBGroups table or null if nothing found or if an error occured
     **/
    public static DBGroups[] load(SQLConstraint sqlconstraint, DBGroups mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{GROUPID, NAME, COMMENT}, filter);
            if(pvp == null) return null;
            else
            {
                DBGroups[] result = new DBGroups[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBGroups();
                    result[i].setGroupId(pvp[i].get(GROUPID));
                    result[i].setName(pvp[i].get(NAME));
                    result[i].setComment(pvp[i].get(COMMENT));
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
     * @return a DBGroups object or null if nothing found or if an error occured
     **/
    public static DBGroups loadByKey(DBGroups mask)
    {
        DBGroups[] res = DBGroups.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBGroups object or null if nothing found or if an error occured
     **/
    public static DBGroups loadByKey(DBGroups mask, SQLConstraint sqlconstraint)
    {
        DBGroups[] res = DBGroups.load(sqlconstraint, mask);
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
        toinsert.set(GROUPID,groupId);
        toinsert.set(NAME,name);
        toinsert.set(COMMENT,comment);

        // Store a new entry
        if(groupId == null)
        {
            try
            {
                groupId = db.insert(TABLE, toinsert);
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
            filter.set(GROUPID, groupId);

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
        if(groupId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(GROUPID, groupId);

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
    public static void delete(DBGroups mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static int count(DBGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static double avg(String field, DBGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static double min(String field, DBGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static double max(String field, DBGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static double std(String field, DBGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
    public static String[] distinct(String field, DBGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
            if(mask.getName() != null) filter.set(NAME, mask.getName());
            if(mask.getComment() != null) filter.set(COMMENT, mask.getComment());
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
