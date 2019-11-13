package dang.article;

import openplatform.taglibs.*;
import openplatform.tools.*;

/**
 * ArticleTrace.java
 *
 *
 * Created: Mon Nov 21 19:24:57 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class ArticleTrace 
{
    public static void writeTrace (String name, String action, String statusMsg,
                                   String statusUserId, String statusGroupId)
    {
        try
        {
            CmsTrace cmstrace = new CmsTrace();
            cmstrace.setModuleId("3");
            cmstrace.setObject(name);
            cmstrace.setAction(action);
            cmstrace.setStatusMsg(statusMsg);
            cmstrace.setStatusUserId(statusUserId);
            cmstrace.setStatusGroupId(statusGroupId);
            cmstrace.doStartTag();
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }
}
