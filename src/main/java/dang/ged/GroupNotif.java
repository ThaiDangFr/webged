package dang.ged;

import openplatform.database.dbean.*;
import openplatform.tools.*;
import dang.cms.CmsLanguage;
import dang.cmsbase.*;
import java.util.*;

/**
 * GroupNotif.java
 *
 *
 * Created: Tue Jul 12 19:53:04 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class GroupNotif 
{
    public static void notifyUpdated(String fileId)
    {
    	CmsLanguage cmsLang = new CmsLanguage();
    	
        if(fileId == null) return;

        DBGedFiles fmask = new DBGedFiles();
        fmask.setId(fileId);

        DBGedFiles f = DBGedFiles.loadByKey(fmask);
        
        String dirId = f.getDirectoryId();

        if(dirId == null) return;

        DBGedNotification nmask = new DBGedNotification();
        nmask.setDirectoryId(dirId);

        DBGedNotification[] n = DBGedNotification.load(nmask);
        if(n == null) return;

        int len = n.length;

        ArrayList userIdList = new ArrayList(); // to check that a message is not send many times to a user

        DBUserGroups ugmask = new DBUserGroups();
        for(int i=0; i<len; i++)
        {
            String groupId = n[i].getGroupId();
            
            ugmask.clear();
            ugmask.setGroupId(groupId);

            DBUserGroups[] ug = DBUserGroups.load(ugmask);

            if(ug == null) continue;

            int lenug =ug.length;

            for(int j=0; j<lenug; j++)
            {
                String userId = ug[j].getUserId();
                if(!userIdList.contains(userId))
                {
                    userIdList.add(userId);
                    
                    String subject = cmsLang.translate("notify.created.updated");
                    String message = 
                    	cmsLang.translate("hello")+"\n"
                    	+cmsLang.translate("notify.created.updated")+":\n"
                    	+"[url=/openplatform/cms/user/pc/modules/ged/browser.jsp?action=seeFile&fileId="+fileId+"]"+
                    	(new GedFiles(f,"1")).getPathName()
                    	+"[/url]";

                    Notificator.send("1", userId, Notificator.PRIORITY_NORMAL, subject, message);
                }  
            }
        } 
    }





    public static void notifyRevised(String fileId, String userId)
    {
    	CmsLanguage cmsLang = new CmsLanguage();
    	
        if(fileId == null) return;

        DBGedFiles fmask = new DBGedFiles();
        fmask.setId(fileId);
        DBGedFiles f = DBGedFiles.loadByKey(fmask);

        if(f == null) return;
        GedFiles gf = new GedFiles(f, userId);

        DBUser umask = new DBUser();
        umask.setUserId(userId);
        DBUser u = DBUser.loadByKey(umask);

        if(u == null) return;

        String message = 
        	cmsLang.translate("hello")+"\n"
        	+ cmsLang.translate("file.revised")+":\n"
            + gf.getPathName()
            + " " + cmsLang.translate("by")
            + u.getLogin();

        String subject = cmsLang.translate("file.revised") + ":" + gf.getName();

        DBGedDirectory dmask = new DBGedDirectory();
        dmask.setId(f.getDirectoryId());
        DBGedDirectory d = DBGedDirectory.loadByKey(dmask);

        if(d == null) return;

        String groupId = d.getFileExpiryGroup();
        if(groupId == null) return;

        DBUserGroups ugmask = new DBUserGroups();
        ugmask.setGroupId(groupId);
        DBUserGroups[] ug = DBUserGroups.load(ugmask);

        if(ug == null) return;

        int len = ug.length;
        
        for(int i=0; i<len; i++)
        {
            Notificator.send(userId, ug[i].getUserId(), Notificator.PRIORITY_NORMAL, subject, message);
        }
    }
}
