package openplatform.file;

import openplatform.database.SQLConstraint;
import openplatform.database.dbean.DBFileBase;
import openplatform.tools.Debug;
import openplatform.tools.SQLTime;

/**
 * TempFileDaemon.java
 *
 *
 * Created: Fri Jun 10 14:14:16 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class TempFileDaemon 
{
    public void checkExpiry()
    {
        String time = SQLTime.getSQLTime(System.currentTimeMillis());
        SQLConstraint constr = new SQLConstraint();
        StringBuffer sb = new StringBuffer();
        sb.append(DBFileBase.EXPIRED).append(SQLConstraint.LOWER_EQ_THAN).append("'").append(time).append("'");
        constr.setCustomWhere(sb.toString());

        DBFileBase[] fb = DBFileBase.load(constr, null);
        if(fb != null)
        {
            int len = fb.length;
            for(int i=0; i<len; i++)
            {
                Debug.println(this, Debug.DEBUG, "fileBaseId="+fb[i].getFileBaseId()+" expired, it will be delete");
                fb[i].delete();
            }
        }
    }
    
}
