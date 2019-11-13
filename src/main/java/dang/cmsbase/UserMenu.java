package dang.cmsbase;

import openplatform.database.dbean.*;

/**
 * Class UserMenu
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class UserMenu
{
    private String userId;
    private DBUserGroups ugmask = new DBUserGroups();
    private DBCmsPreferences pref;

    public UserMenu()
    {
        DBCmsPreferences pmask = new DBCmsPreferences();
        pmask.setId("1");
        pref = DBCmsPreferences.loadByKey(pmask);
    }

    // GET BEG //
    public boolean isAdmin()
    {
        if(userId == null) return false;

        ugmask.clear();
        ugmask.setGroupId("1");
        ugmask.setUserId(userId);

        if(DBUserGroups.count(ugmask, null) != 0) return true;
        else return false;
    }
    
    /*
    public boolean isUserCanCreateAccount()
    {
        if(pref == null) return false;
        else
        {
            pref.refresh();
            if("0".equals(pref.getUserACreate())) return false;
            else return true;
        }
    }
    */
    // GET END //


    // SET BEG //
    public void setUserId(String userId) { this.userId = userId; }
    // SET END //
}
