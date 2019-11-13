package dang.ged;

import openplatform.database.dbean.*;
import openplatform.database.*;
import openplatform.tools.*;

/**
 * TrashUtils.java
 *
 *
 * Created: Tue Jul  5 15:30:20 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class TrashUtils 
{
    public static void checkExpiredTrash()
    {
        String datetime = SQLTime.getSQLTime(System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        sb.append(DBGedTrash.EXPIRYTIME).append(SQLConstraint.LOWER_EQ_THAN)
            .append("'").append(datetime).append("'");

        SQLConstraint constr = new SQLConstraint();
        constr.setCustomWhere(sb.toString());

        DBGedTrash[] trash = DBGedTrash.load(constr, null);

        if(trash == null) return;

        int len = trash.length;
        
        for(int i=0; i<len; i++)
        {
            DBGedFiles fmask = new DBGedFiles();
            fmask.setId(trash[i].getFileId());
            DBGedFiles.delete(fmask);
        }
    }


    private static long getTrashExpiry()
    {
        SQLConstraint constr = new SQLConstraint();
        constr.setLimit(1);

        DBGedPref pref = DBGedPref.loadByKey(null, constr);
        if(pref == null) return 0;
        else return Long.parseLong(pref.getTrashExpiry());
    }

    public static void putFileInTrash(String fileId, String userId)
        throws Exception
    {
        if(fileId == null || userId == null)
            throw new Exception("FileId="+fileId+" UserId="+userId);
        
        long exp = System.currentTimeMillis() + getTrashExpiry();

        DBGedTrash trash = new DBGedTrash();
        trash.setFileId(fileId);
        trash.setExpiryTime(SQLTime.getSQLTime(exp));
        trash.setOwnerId(userId);
        trash.store();
    }
}
