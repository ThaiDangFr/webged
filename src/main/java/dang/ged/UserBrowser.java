package dang.ged;

import openplatform.database.dbean.*;
import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.tools.*;

/**
 * UserBrowser.java
 *
 *
 * Created: Fri Apr 15 16:17:40 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class UserBrowser
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    public DBGedDirectory dmask = new DBGedDirectory();
    public DBGedDirectoryRights drmask = new DBGedDirectoryRights();
    public DBUserGroups ugmask = new DBUserGroups();
    public DBGedFiles fmask = new DBGedFiles();

    private String directoryId;
    private String parentDirId;
    private String name;
    private String userId; // mandatory for rights


    // DO B

    /**
     * load the directory
     */
    
    public String doBrowse() throws Exception
    {
        if(directoryId == null)
        	throw new Exception(cmsLang.translate("error.selected"));
     
        // TODO : PUT A CONTROL HERE
        Debug.println(this, Debug.DEBUG, "Controling userId="+userId+" directoryId="+directoryId);
        if(userId!=null && !canAccessDir(directoryId))
        	throw new Exception(cmsLang.translate("error.access"));
        
        dmask.clear();
        dmask.setId(directoryId);
        DBGedDirectory d = DBGedDirectory.loadByKey(dmask);

        if(d == null) throw new Exception(cmsLang.translate("not.found"));

        directoryId = d.getId();
        parentDirId = d.getParentDirId();
        name = d.getName();

        return null;
    }
    
    
    // DO E

    /**
     * @return array of GedDirectory genealogy
     */
    public final ArrayList getGenea()
    {
        ArrayList array = new ArrayList();

        if(directoryId == null) return array;

        String currentId = directoryId;
        
        while(currentId != null)
        {
            dmask.clear();
            dmask.setId(currentId);
            DBGedDirectory g = DBGedDirectory.loadByKey(dmask);

            if(g == null) break;

            GedDirectory lgd = new GedDirectory(g);
            lgd.setUserId(this.userId);
            array.add(0,lgd);

            currentId = g.getParentDirId();
        }

        return array;
    }


    /**
     * @return an array of GedDirectory
     */
    public final ArrayList getSubdirectories()
    {
        ArrayList array = new ArrayList();

        if(directoryId == null) return array;

        dmask.clear();
        dmask.setParentDirId(directoryId);
        //dmask.setVisible("1");
        DBGedDirectory[] gd = DBGedDirectory.load(dmask);

        if(gd != null)
        {
            int len = gd.length;
            for(int i=0; i<len; i++)
            {
                if(canAccessDir(gd[i].getId()))
                {
                    GedDirectory lgd = new GedDirectory(gd[i]);
                    lgd.setUserId(this.userId);
                    array.add(lgd);
                    // array.add(gd[i]);
                }
            }
        }

        return array;
    }

    /**
     * @return array of GedFiles
     */
    public final ArrayList getBrotherDirectories()
    {
        ArrayList array = new ArrayList();

        if(parentDirId == null) return array;
        
        dmask.clear();
        dmask.setParentDirId(parentDirId);
        //dmask.setVisible("1");
        
        DBGedDirectory[] ds = DBGedDirectory.load(dmask);

        if(ds == null) return array;

        ugmask.clear();
        ugmask.setUserId(this.userId);
        DBUserGroups ug[] = DBUserGroups.load(ugmask);

        if(ug == null) return array;

        int lenug = ug.length;

        int len = ds.length;
        for(int i=0; i<len; i++)
        {
            if(canAccessDir(ds[i].getId()))
                array.add(new GedDirectory(ds[i]));
        }

        return array;
    }


    /**
     * @return an array of GedFiles
     */
    public final ArrayList getFiles()
    {
        ArrayList array = new ArrayList();

        if(directoryId == null) return array;
        if(userId == null) return array;

        fmask.clear();
        fmask.setDirectoryId(directoryId);
        //fmask.setVisible("1");

        DBGedFiles[] f = DBGedFiles.load(fmask);

        if(f == null) return array;

        int len = f.length;

        for(int i=0; i<len; i++)
        {
            GedFiles gf = new GedFiles(f[i], userId);

            if(gf.getId() != null) // id is null if there is no available version
                array.add(gf);
        }

        return array;
    }


    public boolean isAddDirs()
    {
        return canWrite(directoryId);
    }

    public boolean isAddTabs()
    {
        return canWrite(parentDirId);
    }


    public boolean isAddFiles()
    {
        return canWrite(directoryId);
    }


    private boolean canAccessDir(String dirId)
    {
        if(userId == null) return false;
        if(dirId == null) return false;
        if("1".equals(dirId)) return true;
        
        // ADMIN
        ugmask.clear();
        ugmask.setUserId(userId);
        ugmask.setGroupId("1");
        if(DBUserGroups.count(ugmask, null) != 0) return true;

        ugmask.clear();
        ugmask.setUserId(userId);

        DBUserGroups[] ug = DBUserGroups.load(ugmask);

        if(ug == null) return false;

        int len = ug.length;

        drmask.clear();
        for(int i=0; i<len; i++)
        {
            String groupId = ug[i].getGroupId();
            drmask.setDirectoryId(dirId);
            drmask.setGroupId(groupId);
            drmask.setReadRight("1");

            if(DBGedDirectoryRights.count(drmask,null) != 0)
                return true;
        }

        return false;
    }

    private boolean canWrite(String dirId)
    {
        if(userId == null || dirId == null) return false;
        
        // ADMIN
        ugmask.clear();
        ugmask.setUserId(userId);
        ugmask.setGroupId("1");
        if(DBUserGroups.count(ugmask, null) != 0) return true;

        ugmask.clear();
        ugmask.setUserId(userId);

        DBUserGroups[] ug = DBUserGroups.load(ugmask);

        if(ug == null) return false;

        int len = ug.length;

        drmask.clear();
        for(int i=0; i<len; i++)
        {
            String groupId = ug[i].getGroupId();
            drmask.setDirectoryId(dirId);
            drmask.setGroupId(groupId);
            drmask.setWriteRight("1");

            if(DBGedDirectoryRights.count(drmask,null) != 0)
                return true;
        }

        return false;
    }

    /**
     * Used by movefile.jsp
     */
    public final HashMap getWritableDirectoryOption()
    {
        dmask.clear();
        dmask.setId("1");
        //dmask.setVisible("1");
        DBGedDirectory gd = DBGedDirectory.loadByKey(dmask);
        if(gd == null) return null;

        return childDirs("1", gd.getName());
    }

    private final HashMap childDirs(String dirId, String name)
    {
        HashMap map = new OrdHashMap();

        if(dirId == null) return map;

        dmask.clear();
        dmask.setParentDirId(dirId);
        DBGedDirectory[] gd = DBGedDirectory.load(dmask);

        if(gd != null)
        {
            int len = gd.length;
            for(int i=0; i<len; i++)
            {
                StringBuffer newName = new StringBuffer();
                newName.append(name).append("/").append(gd[i].getName());

                if(canWrite(gd[i].getId()))
                {
                    map.put(gd[i].getId(), newName.toString());
                }

                HashMap cmap = childDirs(gd[i].getId(), newName.toString());
                Set set = cmap.keySet();
                Iterator it = set.iterator();
                while(it.hasNext())
                {
                    String key = (String)it.next();
                    String value = (String)cmap.get(key);
                    map.put(key, value);
                }
            }
        }

        return map;
    }


    // GET SET


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

    /**
     * Gets the value of parentDirId
     *
     * @return the value of parentDirId
     */
    public final String getParentDirId()
    {
        return this.parentDirId;
    }

    /**
     * Sets the value of parentDirId
     *
     * @param argParentDirId Value to assign to this.parentDirId
     */
    public final void setParentDirId(final String argParentDirId)
    {
        this.parentDirId = argParentDirId;
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


    /**
     * Gets the value of name
     *
     * @return the value of name
     */
    public final String getName()
    {
        return this.name;
    }

    /**
     * Sets the value of name
     *
     * @param argName Value to assign to this.name
     */
    public final void setName(final String argName)
    {
        this.name = argName;
    }

}
