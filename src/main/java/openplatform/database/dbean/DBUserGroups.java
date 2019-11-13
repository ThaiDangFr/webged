package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBUserGroups
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBUserGroups
{
    protected boolean inError = false;
    protected String userGroupsId;
    protected String userId;
    protected String groupId;

    public static String USERGROUPSID = "userGroupsId";
    public static String USERID = "userId";
    public static String GROUPID = "groupId";
    public static String TABLE = "UserGroups";

    private static Database db = Database.getInstance();


    public void clear()
    {
        userGroupsId = null;
        userId = null;
        groupId = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBUserGroups a = (DBUserGroups)obj;
        return (userGroupsId==null?a.getUserGroupsId()==null:userGroupsId.equals(a.getUserGroupsId())) && (userId==null?a.getUserId()==null:userId.equals(a.getUserId())) && (groupId==null?a.getGroupId()==null:groupId.equals(a.getGroupId()));    }

    public int hashCode()
    {
        return (userGroupsId!=null?userGroupsId.hashCode():0) + (userId!=null?userId.hashCode():0) + (groupId!=null?groupId.hashCode():0);
    }

    public String toString()
    {
        return "userGroupsId="+userGroupsId+"|"+"userId="+userId+"|"+"groupId="+groupId;
    }

    public void refresh()
    {
        if(userGroupsId != null)
        {
            DBUserGroups mask = new DBUserGroups();
            mask.setUserGroupsId(userGroupsId);
            DBUserGroups var = DBUserGroups.loadByKey(mask);
            if(var != null)
            {
                userGroupsId = var.getUserGroupsId();
                userId = var.getUserId();
                groupId = var.getGroupId();
            }
        }
    }

    public void initBean(DBUserGroups db)
    {
        this.userGroupsId = db.getUserGroupsId();
        this.userId = db.getUserId();
        this.groupId = db.getGroupId();
    }

    public String getUserGroupsId()
    {
        return userGroupsId;
    }

    public void setUserGroupsId(String userGroupsId)
    {
        this.userGroupsId = userGroupsId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    /**
     * @return a DBUserGroups table or null if nothing found or if an error occured
     **/
    public static DBUserGroups[] load(DBUserGroups mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBUserGroups table or null if nothing found or if an error occured
     **/
    public static DBUserGroups[] load(SQLConstraint sqlconstraint, DBUserGroups mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserGroupsId() != null) filter.set(USERGROUPSID, mask.getUserGroupsId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{USERGROUPSID, USERID, GROUPID}, filter);
            if(pvp == null) return null;
            else
            {
                DBUserGroups[] result = new DBUserGroups[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBUserGroups();
                    result[i].setUserGroupsId(pvp[i].get(USERGROUPSID));
                    result[i].setUserId(pvp[i].get(USERID));
                    result[i].setGroupId(pvp[i].get(GROUPID));
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
     * @return a DBUserGroups object or null if nothing found or if an error occured
     **/
    public static DBUserGroups loadByKey(DBUserGroups mask)
    {
        DBUserGroups[] res = DBUserGroups.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBUserGroups object or null if nothing found or if an error occured
     **/
    public static DBUserGroups loadByKey(DBUserGroups mask, SQLConstraint sqlconstraint)
    {
        DBUserGroups[] res = DBUserGroups.load(sqlconstraint, mask);
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
        toinsert.set(USERGROUPSID,userGroupsId);
        toinsert.set(USERID,userId);
        toinsert.set(GROUPID,groupId);

        // Store a new entry
        if(userGroupsId == null)
        {
            try
            {
                userGroupsId = db.insert(TABLE, toinsert);
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
            filter.set(USERGROUPSID, userGroupsId);

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
        if(userGroupsId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(USERGROUPSID, userGroupsId);

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
    public static void delete(DBUserGroups mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserGroupsId() != null) filter.set(USERGROUPSID, mask.getUserGroupsId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static int count(DBUserGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserGroupsId() != null) filter.set(USERGROUPSID, mask.getUserGroupsId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static double avg(String field, DBUserGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserGroupsId() != null) filter.set(USERGROUPSID, mask.getUserGroupsId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static double min(String field, DBUserGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserGroupsId() != null) filter.set(USERGROUPSID, mask.getUserGroupsId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static double max(String field, DBUserGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserGroupsId() != null) filter.set(USERGROUPSID, mask.getUserGroupsId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static double std(String field, DBUserGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserGroupsId() != null) filter.set(USERGROUPSID, mask.getUserGroupsId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
    public static String[] distinct(String field, DBUserGroups mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserGroupsId() != null) filter.set(USERGROUPSID, mask.getUserGroupsId());
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getGroupId() != null) filter.set(GROUPID, mask.getGroupId());
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
