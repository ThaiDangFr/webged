package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBCmsInboxFriends
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBCmsInboxFriends
{
    protected boolean inError = false;
    protected String id;
    protected String userId;
    protected String friendId;

    public static String ID = "id";
    public static String USERID = "userId";
    public static String FRIENDID = "friendId";
    public static String TABLE = "CmsInboxFriends";

    private static Database db = Database.getInstance();


    public void clear()
    {
        id = null;
        userId = null;
        friendId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBCmsInboxFriends a = (DBCmsInboxFriends)obj;
        return (id==null?a.getId()==null:id.equals(a.getId())) && (userId==null?a.getUserId()==null:userId.equals(a.getUserId())) && (friendId==null?a.getFriendId()==null:friendId.equals(a.getFriendId()));    }

    public int hashCode()
    {
        return (id!=null?id.hashCode():0) + (userId!=null?userId.hashCode():0) + (friendId!=null?friendId.hashCode():0);
    }

    public String toString()
    {
        return "id="+id+"|"+"userId="+userId+"|"+"friendId="+friendId;
    }

    public void refresh()
    {
        if(id != null)
        {
            DBCmsInboxFriends mask = new DBCmsInboxFriends();
            mask.setId(id);
            DBCmsInboxFriends var = DBCmsInboxFriends.loadByKey(mask);
            if(var != null)
            {
                id = var.getId();
                userId = var.getUserId();
                friendId = var.getFriendId();
            }
        }
    }

    public void initBean(DBCmsInboxFriends db)
    {
        this.id = db.getId();
        this.userId = db.getUserId();
        this.friendId = db.getFriendId();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getFriendId()
    {
        return friendId;
    }

    public void setFriendId(String friendId)
    {
        this.friendId = friendId;
    }

    /**
     * @return a DBCmsInboxFriends table or null if nothing found or if an error occured
     **/
    public static DBCmsInboxFriends[] load(DBCmsInboxFriends mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBCmsInboxFriends table or null if nothing found or if an error occured
     **/
    public static DBCmsInboxFriends[] load(SQLConstraint sqlconstraint, DBCmsInboxFriends mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getFriendId() != null) filter.set(FRIENDID, mask.getFriendId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{ID, USERID, FRIENDID}, filter);
            if(pvp == null) return null;
            else
            {
                DBCmsInboxFriends[] result = new DBCmsInboxFriends[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBCmsInboxFriends();
                    result[i].setId(pvp[i].get(ID));
                    result[i].setUserId(pvp[i].get(USERID));
                    result[i].setFriendId(pvp[i].get(FRIENDID));
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
     * @return a DBCmsInboxFriends object or null if nothing found or if an error occured
     **/
    public static DBCmsInboxFriends loadByKey(DBCmsInboxFriends mask)
    {
        DBCmsInboxFriends[] res = DBCmsInboxFriends.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBCmsInboxFriends object or null if nothing found or if an error occured
     **/
    public static DBCmsInboxFriends loadByKey(DBCmsInboxFriends mask, SQLConstraint sqlconstraint)
    {
        DBCmsInboxFriends[] res = DBCmsInboxFriends.load(sqlconstraint, mask);
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
        toinsert.set(USERID,userId);
        toinsert.set(FRIENDID,friendId);

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
    public static void delete(DBCmsInboxFriends mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getFriendId() != null) filter.set(FRIENDID, mask.getFriendId());
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
    public static int count(DBCmsInboxFriends mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getFriendId() != null) filter.set(FRIENDID, mask.getFriendId());
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
    public static double avg(String field, DBCmsInboxFriends mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getFriendId() != null) filter.set(FRIENDID, mask.getFriendId());
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
    public static double min(String field, DBCmsInboxFriends mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getFriendId() != null) filter.set(FRIENDID, mask.getFriendId());
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
    public static double max(String field, DBCmsInboxFriends mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getFriendId() != null) filter.set(FRIENDID, mask.getFriendId());
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
    public static double std(String field, DBCmsInboxFriends mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getFriendId() != null) filter.set(FRIENDID, mask.getFriendId());
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
    public static String[] distinct(String field, DBCmsInboxFriends mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getId() != null) filter.set(ID, mask.getId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getFriendId() != null) filter.set(FRIENDID, mask.getFriendId());
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
