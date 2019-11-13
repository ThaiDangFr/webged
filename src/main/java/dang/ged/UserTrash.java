package dang.ged;

import openplatform.database.dbean.*;
import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.database.*;

/**
 * UserTrash.java
 *
 *
 * Created: Tue Jul  5 17:13:51 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class UserTrash 
{
	private WDaemon wdaemon = new WDaemon();
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private String userId;
    private String[] fileIds;
    private String directoryId;


    public String doRestore()
        throws Exception
    {
        if(fileIds == null || directoryId == null)
            throw new Exception(cmsLang.translate("error.empty"));

        DBGedFiles fmask = new DBGedFiles();
        DBGedTrash tmask = new DBGedTrash();

        int len = fileIds.length;

        for(int i=0; i<len; i++)
        {
            fmask.clear();
            fmask.setId(fileIds[i]);
            DBGedFiles f = DBGedFiles.loadByKey(fmask);
            if(f == null) continue;
            
            f.setDirectoryId(this.directoryId);
            f.store();

            tmask.clear();
            tmask.setFileId(fileIds[i]);
            DBGedTrash t = DBGedTrash.loadByKey(tmask);
            if(t != null) t.delete();

            GedFiles gf = new GedFiles(f, userId);
            wdaemon.writeTrace(gf.getName(), DBTrace.ACTION_RECOVER,
                               DBTrace.STATUSMSG_OK, userId, null);
        }

        return len+" "+cmsLang.translate("file.restored");
    }

    
    /**
     * @return arraylist of GedFiles
     */
    public ArrayList getTrash()
    {
        if(this.userId == null) return null;

        boolean isAdmin = false;
        
        DBUserGroups ugmask = new DBUserGroups();
        ugmask.setUserId(this.userId);
        ugmask.setGroupId("1");
        if(DBUserGroups.count(ugmask, null) != 0) isAdmin = true;


        ArrayList array = new ArrayList();

        DBGedTrash tmask = new DBGedTrash();
        
        if(!isAdmin)
            tmask.setOwnerId(this.userId);
        
        DBGedTrash[] gt = DBGedTrash.load(tmask);
        if(gt == null) return array;

        int len = gt.length;
        
        DBGedFiles fmask = new DBGedFiles();
        for(int i=0; i<len; i++)
        {
            fmask.clear();
            fmask.setId(gt[i].getFileId());
            
            DBGedFiles f = DBGedFiles.loadByKey(fmask);
            if(f == null) continue;

            array.add(new GedFiles(f, this.userId));
        }

        return array;
    }

    // GET / SET


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



    /**
     * Gets the value of fileIds
     *
     * @return the value of fileIds
     */
    public final String[] getFileIds()
    {
        return this.fileIds;
    }

    /**
     * Sets the value of fileIds
     *
     * @param argFileIds Value to assign to this.fileIds
     */
    public final void setFileIds(final String[] argFileIds)
    {
        this.fileIds = argFileIds;
    }

    /**
     * Gets the value of directoryId
     *
     * @return the value of directoryId
     */
    public final String getDirectoryId()
    {
        return this.directoryId;
    }

    /**
     * Sets the value of directoryId
     *
     * @param argDirectoryId Value to assign to this.directoryId
     */
    public final void setDirectoryId(final String argDirectoryId)
    {
        this.directoryId = argDirectoryId;
    }

}
