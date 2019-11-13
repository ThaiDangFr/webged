package dang.cmsbase;

import openplatform.database.dbean.*;
import java.util.*;

/**
 * Class InboxContact
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class InboxContact
{
    private String userId;
    private DBCmsInboxFriends fmask = new DBCmsInboxFriends();
    private DBUser umask = new DBUser();

    // GET BEG //
    public HashMap getAllUsersOption()
    {
        HashMap map = new HashMap();
        DBUser[] u = DBUser.load(null);
        if(u == null) return map;

        int len = u.length;
        for(int i=0; i<len; i++)
        {
            map.put(u[i].getUserId(), u[i].getLogin());
        }
        
        return map;
    }

    public DBUser[] getAllUsers() { return DBUser.load(null); }
    
    public ArrayList getFriends()
    {
        if(userId == null) return null;

        ArrayList array = new ArrayList();

        fmask.clear();
        fmask.setUserId(userId);
        DBCmsInboxFriends[] f = DBCmsInboxFriends.load(fmask);
        if(f!=null)
        {
            int len = f.length;
            for(int i=0; i<len; i++)
            {
                umask.clear();
                umask.setUserId(f[i].getFriendId());
                DBUser user = DBUser.loadByKey(umask);
                if(user != null) array.add(user);
            }
        }

        return array;
    }
    // GET END //

    // SET BEG //
    public void setUserId(String id) { this.userId = id; }
    
    public void setRemoveFriend(String userId)
    {
        if(this.userId == null || userId == null) return;

        fmask.clear();
        fmask.setUserId(this.userId);
        fmask.setFriendId(userId);
        DBCmsInboxFriends.delete(fmask);
    }

    public void setAddFriends(String[] userIds)
    {
        if(userIds == null || userId == null) return;

        int len = userIds.length;
        for(int i=0; i<len; i++)
        {
            fmask.clear();
            fmask.setUserId(userId);
            fmask.setFriendId(userIds[i]);

            if(DBCmsInboxFriends.count(fmask, null) == 0)
                fmask.store();
        }
    }
    // SET END //
}
