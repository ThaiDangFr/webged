package dang.mailarchive;

import openplatform.database.dbean.*;
import openplatform.tools.*;

/**
 * UserView.java
 *
 *
 * Created: Fri Oct 28 17:20:09 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class UserView 
{
    private String id;
    private String userId;

    public MailArchive getMail()
    {
        if(userId == null || id == null) return null;

        DBUser umask = new DBUser();
        umask.setUserId(userId);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;

        return ArchivelQuery.getMail(u.getLogin(), u.getPassword(), id);
    }


    public String getRfc822Id()
    {
        if(userId == null || id == null) return null;

        DBUser umask = new DBUser();
        umask.setUserId(userId);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;

        DBMailArchive mamask = new DBMailArchive();

        DBUserGroups ugmask = new DBUserGroups();
        ugmask.setUserId(userId);
        ugmask.setGroupId("1");
        if(DBUserGroups.count(ugmask,null) == 0) // not admin
            mamask.setMailAccountId(userId);

        mamask.setId(id);
        DBMailArchive ma = DBMailArchive.loadByKey(mamask);
        if(ma == null) return null;
        else return ma.getRawId();
    }
    

    // GET - SET


    /**
     * Gets the value of id
     *
     * @return the value of id
     */
    public final String getId()
    {
        return this.id;
    }

    /**
     * Sets the value of id
     *
     * @param argId Value to assign to this.id
     */
    public final void setId(final String argId)
    {
        this.id = argId;
    }

    /**
     * Gets the value of userId
     *
     * @return the value of userId
     */
    public final String getUserId()
    {
        return this.userId;
    }

    /**
     * Sets the value of userId
     *
     * @param argUserId Value to assign to this.userId
     */
    public final void setUserId(final String argUserId)
    {
        this.userId = argUserId;
    }

}
