package dang.ged;

import openplatform.database.dbean.*;

/**
 * GedFilesComment.java
 *
 *
 * Created: Tue Apr 26 17:44:21 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class GedFilesComment extends DBGedFilesComment
{
    public DBUser umask = new DBUser();

    public GedFilesComment(DBGedFilesComment gfc)
    {
        initBean(gfc);
    }
    

    public String getLogin()
    {
        umask.clear();
        umask.setUserId(userId);
        DBUser user = DBUser.loadByKey(umask);
        
        if(user == null) return null;
        else return user.getLogin();
    }
}
