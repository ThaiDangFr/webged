package dang.ged;

import openplatform.database.dbean.*;
import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.tools.*;

/**
 * GedDirectory.java
 *
 *
 * Created: Mon Apr 11 20:47:22 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class GedDirectory extends DBGedDirectory
{
    // MASK
    public DBGedDirectoryRights gdrmask = new DBGedDirectoryRights();
    public DBGroups gmask = new DBGroups();
    public DBThesaurus tmask = new DBThesaurus();
    public DBGedDirThesaurus gdtmask = new DBGedDirThesaurus();
    public DBUserGroups ugmask = new DBUserGroups();
    public DBGedFiles fmask = new DBGedFiles();
    public DBGedDirectory gdmask = new DBGedDirectory();
    public DBGedNotification gnmask = new DBGedNotification();


    // AUTO FILLED
    private HashMap groupOption = new OrdHashMap();
    private HashMap templateOption = new OrdHashMap();
    private HashMap workflowOption = new OrdHashMap();
    private HashMap thesaurusOption = new OrdHashMap();
    private HashMap storeTypeOption = new OrdHashMap();
    


    // MANUAL FILLED
    protected ArrayList readRights = new ArrayList(); // array of DBGroups
    protected ArrayList writeRights = new ArrayList(); // array of DBGroups
    protected ArrayList thesaurus = new ArrayList(); // array of DBThesaurus
    protected ArrayList notifGroups = new ArrayList(); // array of DBGroups


    protected String userId;
	protected CmsLanguage cmsLang = new CmsLanguage();



    public GedDirectory(DBGedDirectory d)
    {
        super();
        init(d);
    }

    public GedDirectory()
    {
        super();
    }
    

    public void clear()
    {
        super.clear();

        readRights.clear();
        writeRights.clear();
        thesaurus.clear();
        notifGroups.clear();
    }

    /**
     * fill the variable
     */
    public void init(DBGedDirectory d)
    {
        initBean(d);
    }


    protected StringBuffer pathName(DBGedDirectory d, StringBuffer sb)
    {
        StringBuffer path = new StringBuffer();
        path.append(d.getName()).append("/").append(sb);

        String dirId = d.getParentDirId();

        DBGedDirectory dmask = new DBGedDirectory();
        dmask.setId(dirId);
        DBGedDirectory parent = DBGedDirectory.loadByKey(dmask);
        if(parent != null)
        {
            StringBuffer psb = pathName(parent, path);
            return psb;
        }
        else return path;
    }


    protected void fillThesaurus()
    {
        if(id == null) return;

        // clearing
        thesaurus.clear();
        

        gdtmask.clear();
        gdtmask.setDirectoryId(id);

        DBGedDirThesaurus[] gdt = DBGedDirThesaurus.load(gdtmask);
        if(gdt == null) return;

        int len = gdt.length;

        tmask.clear();
        for(int i=0; i<len; i++)
        {
            tmask.setId(gdt[i].getThesaurusId());
            DBThesaurus t = DBThesaurus.loadByKey(tmask);
            thesaurus.add(t);
        }
    }


    protected void fillRights()
    {
        if(id == null) return;

        // clearing
        readRights.clear();
        writeRights.clear();

        // READ RIGHTS
        gdrmask.clear();
        gdrmask.setDirectoryId(id);
        gdrmask.setReadRight("1");
        DBGedDirectoryRights[] gdr = DBGedDirectoryRights.load(gdrmask);
        if(gdr != null) 
        {
            int len = gdr.length;
        
            gmask.clear();
            for(int i=0; i<len; i++)
            {
                String groupId = gdr[i].getGroupId();
                gmask.setGroupId(groupId);
                DBGroups g = DBGroups.loadByKey(gmask);
                readRights.add(g);
            }
        }


        // WRITE FILES RIGHTS
        gdrmask.clear();
        gdrmask.setDirectoryId(id);
        gdrmask.setWriteRight("1");
        gdr = DBGedDirectoryRights.load(gdrmask);
        if(gdr != null)
        {
            int len = gdr.length;
        
            gmask.clear();
            for(int i=0; i<len; i++)
            {
                String groupId = gdr[i].getGroupId();
                gmask.setGroupId(groupId);
                DBGroups g = DBGroups.loadByKey(gmask);
                writeRights.add(g);
            }        
        }

    }

    
    protected void fillNotifGroups()
    {
        if(this.id == null) return;

        notifGroups.clear();
        gnmask.clear();
        gnmask.setDirectoryId(this.id);

        DBGedNotification[] gn = DBGedNotification.load(gnmask);

        if(gn == null) return;

        int len = gn.length;

        for(int i=0; i<len; i++)
        {
            gmask.clear();
            gmask.setGroupId(gn[i].getGroupId());
            DBGroups g = DBGroups.loadByKey(gmask);

            if(g != null)
                notifGroups.add(g);
        }
    }


    protected void fillOptions()
    {
        // clearing
        groupOption.clear();
        templateOption.clear();
        workflowOption.clear();
        thesaurusOption.clear();


        ugmask.clear();
        ugmask.setUserId(userId);
        ugmask.setGroupId("1"); // admin group
        if(userId!=null && DBUserGroups.count(ugmask,null) == 1)
        {
            DBGroups[] g = DBGroups.load(null);
            if(g != null)
            {
                int len = g.length;
                for(int i=0; i<len; i++)
                {
                    if(!g[i].getGroupId().equals("1"))
                        groupOption.put(g[i].getGroupId(), g[i].getName());
                }
            }
        }
        else if(userId!=null)
        {
        	ugmask.clear();
        	ugmask.setUserId(userId);
        	DBUserGroups[] ug = DBUserGroups.load(ugmask);
        	if(ug != null)
        	{
        		int len = ug.length;
                for(int i=0; i<len; i++)
                {
                	gmask.clear();
                	gmask.setGroupId(ug[i].getGroupId());
                	DBGroups g = DBGroups.loadByKey(gmask);
                    if(!g.getGroupId().equals("1"))
                        groupOption.put(g.getGroupId(), g.getName());
                }
        	}
        }
        

        DBGedTemplate[] t = DBGedTemplate.load(null);
        if(t != null)
        {
            int len = t.length;
            for(int i=0; i<len; i++)
                templateOption.put(t[i].getId(), t[i].getName());
        }

        DBGedWorkflow[] w = DBGedWorkflow.load(null);
        if(w != null)
        {
            int len = w.length;
            for(int i=0; i<len; i++)
                workflowOption.put(w[i].getId(), w[i].getName());
        }

        DBThesaurus[] th = DBThesaurus.load(null);
        if(th != null)
        {
            int len = th.length;
            for(int i=0; i<len; i++)
                thesaurusOption.put(th[i].getId(), th[i].getName());
        }
        
        
        ArrayList al = DBGedDirectory._STORETYPE_LIST;
        if(al != null)
        {
        	int len = al.size();
        	for(int i=0; i<len; i++)
        	{
        		String st = (String)al.get(i);
        		storeTypeOption.put(st, cmsLang.translate(st));
        	}
        }
    }

 

    /**
     * with the userId, can he edit the directory ?
     */
    public boolean isWriteRight()
    {
        if(userId == null || id == null) return false;

        ugmask.clear();
        ugmask.setUserId(userId);
        ugmask.setGroupId("1");
        if(DBUserGroups.count(ugmask,null) != 0) return true; // ADMIN

        gdrmask.clear();
        gdrmask.setDirectoryId(id);
        gdrmask.setWriteRight("1");
        DBGedDirectoryRights[] gdr = DBGedDirectoryRights.load(gdrmask);
        
        if(gdr == null) return false;

        int len = gdr.length;

        ugmask.clear();
        ugmask.setUserId(userId);
        for(int i=0; i<len; i++)
        {
            String groupId = gdr[i].getGroupId();
            ugmask.setGroupId(groupId);
            if(DBUserGroups.count(ugmask,null) != 0) return true;
        }

        return false;
    }

    
    /**
     * with the userId, can he read the directory ?
     */
    public boolean isReadRight()
    {
        if(userId == null || id == null) return false;

        ugmask.clear();
        ugmask.setUserId(userId);
        ugmask.setGroupId("1");
        if(DBUserGroups.count(ugmask,null) != 0) return true; // ADMIN

        gdrmask.clear();
        gdrmask.setDirectoryId(id);
        gdrmask.setReadRight("1");
        DBGedDirectoryRights[] gdr = DBGedDirectoryRights.load(gdrmask);
        
        if(gdr == null) return false;

        int len = gdr.length;

        ugmask.clear();
        ugmask.setUserId(userId);
        for(int i=0; i<len; i++)
        {
            String groupId = gdr[i].getGroupId();
            ugmask.setGroupId(groupId);
            if(DBUserGroups.count(ugmask,null) != 0) return true;
        }

        return false;
    }
    
    
    /**
     * Check that there is no subfolder or files
     */
    public boolean isDeletable()
    {
        fmask.clear();
        fmask.setDirectoryId(this.id);
        if(DBGedFiles.count(fmask, null) != 0)
            return false;

        gdmask.clear();
        gdmask.setParentDirId(this.id);
        if(DBGedDirectory.count(gdmask, null) != 0)
            return false;
        
        return true;
    }



    public HashMap getDaysOption()
    {
        int year1 = 365;

        HashMap map = new OrdHashMap();

        map.put("31", "1/12");
        map.put("93", "3/12");
        map.put("186", "6/12");

        for(int i=1; i<101; i++)
            map.put(Integer.toString(i*year1), Integer.toString(i));

        return map;
    }


    public final boolean isTemplate()
    {
        if(templateId != null) return true;
        else return false;
    }


    public final boolean isExpiration()
    {
        if(fileExpiryTime != null || fileExpiryGroup != null) return true;
        else return false;
    }

    public final boolean isWriteWorkflow()
    {
        if(fileAddModifyWorkflow != null) return true;
        else return false;
    }



    // GET SET


    /**
     * Gets the value of groupOption
     *
     * @return the value of groupOption
     */
    public final HashMap getGroupOption()
    {
        return this.groupOption;
    }

    /**
     * Sets the value of groupOption
     *
     * @param argGroupOption Value to assign to this.groupOption
     */
    public final void setGroupOption(final HashMap argGroupOption)
    {
        this.groupOption = argGroupOption;
    }

    /**
     * Gets the value of templateOption
     *
     * @return the value of templateOption
     */
    public final HashMap getTemplateOption()
    {
        return this.templateOption;
    }

    /**
     * Sets the value of templateOption
     *
     * @param argTemplateOption Value to assign to this.templateOption
     */
    public final void setTemplateOption(final HashMap argTemplateOption)
    {
        this.templateOption = argTemplateOption;
    }

    /**
     * Gets the value of workflowOption
     *
     * @return the value of workflowOption
     */
    public final HashMap getWorkflowOption()
    {
        return this.workflowOption;
    }

    /**
     * Sets the value of workflowOption
     *
     * @param argWorkflowOption Value to assign to this.workflowOption
     */
    public final void setWorkflowOption(final HashMap argWorkflowOption)
    {
        this.workflowOption = argWorkflowOption;
    }

    /**
     * Gets the value of thesaurusOption
     *
     * @return the value of thesaurusOption
     */
    public final HashMap getThesaurusOption()
    {
        return this.thesaurusOption;
    }

    /**
     * Sets the value of thesaurusOption
     *
     * @param argThesaurusOption Value to assign to this.thesaurusOption
     */
    public final void setThesaurusOption(final HashMap argThesaurusOption)
    {
        this.thesaurusOption = argThesaurusOption;
    }

    /**
     * Gets the value of readRights
     *
     * @return the value of readRights
     */
    public final ArrayList getReadRights()
    {
        return this.readRights;
    }

    /**
     * Sets the value of readRights
     *
     * @param argReadRights Value to assign to this.readRights
     */
    public final void setReadRights(final ArrayList argReadRights)
    {
        this.readRights = argReadRights;
    }

    /**
     * Gets the value of writeRights
     *
     * @return the value of writeRights
     */
    public final ArrayList getWriteRights()
    {
        return this.writeRights;
    }

    /**
     * Sets the value of writeRights
     *
     * @param argWriteRights Value to assign to this.writeRights
     */
    public final void setWriteRights(final ArrayList argWriteRights)
    {
        this.writeRights = argWriteRights;
    }

    /**
     * Gets the value of thesaurus
     *
     * @return the value of thesaurus
     */
    public final ArrayList getThesaurus()
    {
        return this.thesaurus;
    }

    /**
     * Sets the value of thesaurus
     *
     * @param argThesaurus Value to assign to this.thesaurus
     */
    public final void setThesaurus(final ArrayList argThesaurus)
    {
        this.thesaurus = argThesaurus;
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
     * Gets the value of notifGroups
     *
     * @return the value of notifGroups
     */
    public final ArrayList getNotifGroups()
    {
        return this.notifGroups;
    }

    /**
     * Sets the value of notifGroups
     *
     * @param argNotifGroups Value to assign to this.notifGroups
     */
    public final void setNotifGroups(final ArrayList argNotifGroups)
    {
        this.notifGroups = argNotifGroups;
    }

	public HashMap getStoreTypeOption() {
		return storeTypeOption;
	}

	public void setStoreTypeOption(HashMap storeTypeOption) {
		this.storeTypeOption = storeTypeOption;
	}

}
