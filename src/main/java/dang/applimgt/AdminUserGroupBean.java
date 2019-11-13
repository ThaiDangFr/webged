package dang.applimgt;

import openplatform.tools.*;
import openplatform.database.dbean.*;
import java.util.*;

import dang.cms.CmsLanguage;


/**
 * Class AdminUserGroupBean
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminUserGroupBean
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private String groupId;
    private DBUserGroups ugmask = new DBUserGroups();
    private DBUser umask = new DBUser();
    private DBGroups gmask = new DBGroups();
    private String[] userId;

    private String groupName;


    public String getGroupName() { return groupName; }

    /**
     * All groups
     */
    public DBGroups[] getListGroups()
    {
        return DBGroups.load(null);
    }


    public void setGroup(String groupId)
    {
        this.groupId = groupId;
        gmask.setGroupId(groupId);
        DBGroups g = DBGroups.loadByKey(gmask);
        if(g != null) groupName=g.getName();
    }
    
    /**
     * All DBUsers not in current group
     */
    public HashMap getUsersNotInGroup()
    {
        HashMap map = new HashMap();

        if(groupId == null) return map;

        DBUser[] user = DBUser.load(null);
        
        if(user == null) return map;
        
        int len = user.length;
        for(int i=0; i<len; i++)
        {
            ugmask.clear();
            ugmask.setUserId(user[i].getUserId());
            ugmask.setGroupId(groupId);

            if(DBUserGroups.count(ugmask, null) == 0) map.put(user[i].getUserId(), user[i].getLogin());
        }

        return map;
    }

    /**
     * All DBUsers in current group
     */
    public HashMap getUsersInGroup()
    {
        HashMap map = new HashMap();
        
        if(groupId == null) return map;

        ugmask.clear();
        ugmask.setGroupId(groupId);

        DBUserGroups[] ug = DBUserGroups.load(ugmask);
        if(ug == null) return map;

        int len = ug.length;
        for(int i=0; i<len; i++)
        {
            String userId = ug[i].getUserId();
            
            if(userId == null) continue;
            
            umask.clear();
            umask.setUserId(userId);
            
            DBUser user = DBUser.loadByKey(umask);
            if(user != null)
            	map.put(user.getUserId(), user.getLogin());
        }

        return map;
    }
    
    /**
     * The selected users
     */
    public void setSelectedUsers(String[] userId)
    {
        this.userId = userId;
    }


    /**
     * Remove users from group
     */
    public String doRemove()
        throws Exception
    {
        if(groupId == null || this.userId == null)
            throw new Exception(cmsLang.translate("no.item.selected"));

        int len = userId.length;
        for(int i=0; i<len; i++)
        {
            ugmask.clear();
            ugmask.setGroupId(groupId);
            ugmask.setUserId(userId[i]);

            DBUserGroups.delete(ugmask);
        }
        return null;
    }


    /**
     * Add users to group
     */
    public String doAdd()
        throws Exception
    {
        if(groupId == null || this.userId == null)
            throw new Exception(cmsLang.translate("no.item.selected"));

        int len = userId.length;
        for(int i=0; i<len; i++)
        {
            ugmask.clear();
            ugmask.setGroupId(groupId);
            ugmask.setUserId(userId[i]);
            
            if(DBUserGroups.count(ugmask, null) == 0)
                ugmask.store();
        }

        return null;
    }
}
