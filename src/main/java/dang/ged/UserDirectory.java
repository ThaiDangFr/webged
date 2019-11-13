package dang.ged;

import openplatform.database.dbean.DBGedDirThesaurus;
import openplatform.database.dbean.DBGedDirectory;
import openplatform.database.dbean.DBGedDirectoryRights;
import openplatform.database.dbean.DBGedFiles;
import openplatform.database.dbean.DBGedNotification;
import openplatform.database.dbean.DBGroups;
import openplatform.database.dbean.DBThesaurus;
import openplatform.database.dbean.DBTrace;
import openplatform.tools.Debug;

/**
 * UserDirectory.java
 *
 *
 * Created: Tue Apr 12 11:19:46 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class UserDirectory extends GedDirectory
{
	private WDaemon wdaemon = new WDaemon();
	
    private String groupId;
    private String thesaurusId;
    private int index;



    public UserDirectory()
    {
        super();
    }
    
    // DO B
    public String doEditDir() throws Exception
    {
        if(id == null) throw new Exception(cmsLang.translate("error.selected"));

        gdmask.clear();
        gdmask.setId(id);

        DBGedDirectory d = DBGedDirectory.loadByKey(gdmask);
        init(d);
        fillOptions();
        fillRights();
        fillNotifGroups();
        fillThesaurus();
        
        return null;
    }

    
    /**
     * Update the path for the directoryId
     * @param id
     * @return
     */
    private void updatePath(DBGedDirectory gd)
    {   	
    	if(gd == null) return;

        StringBuffer sb = pathName(gd, new StringBuffer());
        gd.setPath(sb.toString());
        gd.store();
        
        // find childs
        
        gdmask.clear();
        gdmask.setParentDirId(gd.getId());
        DBGedDirectory[] gds = DBGedDirectory.load(gdmask);
        if(gds != null)
        {
        	int len = gds.length;
        	for(int i=0; i<len; i++) updatePath(gds[i]);
        }
    }
    
    
    public String doSaveDir() throws Exception
    {   	
        if(this.name == null || this.name.trim().equals(""))
            throw new Exception(cmsLang.translate("error.empty.name"));

        setPath("dummy");
        store();
        
        updatePath(this);

        // initialise
        DBGroups[] gps = DBGroups.load(null);
        if(gps != null)
        {
            int len = gps.length;
            for(int i=0; i<len; i++)
            {
                if(gps[i].getGroupId().equals("1")) continue; // pass the admin

                gdrmask.clear();
                gdrmask.setGroupId(gps[i].getGroupId());
                gdrmask.setDirectoryId(id);
                if(DBGedDirectoryRights.count(gdrmask, null) == 0)
                {
                    gdrmask.setReadRight("0");
                    gdrmask.setWriteRight("0");
                    gdrmask.store();
                }
            }
        }

        // populate
        gdrmask.clear();
        gdrmask.setDirectoryId(id);
        DBGedDirectoryRights[] gdr = DBGedDirectoryRights.load(gdrmask);
        if(gdr != null)
        {
            int len = gdr.length;
            for(int i=0; i<len; i++)
            {
                String groupId = gdr[i].getGroupId();
                gmask.clear();
                gmask.setGroupId(groupId);
                DBGroups g = DBGroups.loadByKey(gmask);
                
                if(readRights.contains(g))
                    gdr[i].setReadRight("1");
                else
                    gdr[i].setReadRight("0");

                if(writeRights.contains(g))
                    gdr[i].setWriteRight("1");
                else
                    gdr[i].setWriteRight("0");

                gdr[i].store();
            }
        }


        int len = thesaurus.size();
        for(int i=0; i<len; i++)
        {
            gdtmask.clear();
            DBThesaurus t = (DBThesaurus)thesaurus.get(i);
            gdtmask.setThesaurusId(t.getId());
            gdtmask.setDirectoryId(id);
            if(DBGedDirThesaurus.count(gdtmask,null) == 0)
                gdtmask.store();
        }


        // save notifGroups (recreate all)
        len = notifGroups.size();
        gnmask.clear();
        gnmask.setDirectoryId(this.id);
        DBGedNotification.delete(gnmask);

        for(int i=0; i<len; i++)
        {
            gnmask.clear();
            gnmask.setDirectoryId(this.id);
            DBGroups g = (DBGroups)notifGroups.get(i);
            gnmask.setGroupId(g.getGroupId());
            gnmask.store();
        }

        wdaemon.writeTrace(getPath() , DBTrace.ACTION_SAVE, DBTrace.STATUSMSG_OK,
                           userId, null);

        return null;
    }

    public String doDeleteDir() throws Exception
    {
        if(id == null) throw new Exception(cmsLang.translate("error.selected"));

        fmask.clear();
        fmask.setDirectoryId(this.id);
        if(DBGedFiles.count(fmask, null) != 0)
            throw new Exception(cmsLang.translate("directory.need.empty"));

        gdmask.clear();
        gdmask.setParentDirId(this.id);
        if(GedDirectory.count(gdmask, null) != 0)
            throw new Exception(cmsLang.translate("directory.need.empty"));

        this.delete();

        return cmsLang.translate("ok.deleted");
    }

    public String doNewDir() throws Exception
    {
    	String savedParentDirId = this.parentDirId;
    	
        clear();
        
        this.reference = "[id]/[version]/[year]"; // default value
        this.parentDirId = savedParentDirId; // reput the value because all was cleared
        
        fillOptions(); // file the options list
        
        // Default value for directory rights
        if(parentDirId != null)
        {
        	gdrmask.clear();
        	gdrmask.setDirectoryId(parentDirId);
        	DBGedDirectoryRights[] gdr = DBGedDirectoryRights.load(gdrmask);
        	if(gdr != null)
        	{
        		int len = gdr.length;
        		gmask.clear();
        		for(int i=0; i<len; i++)
        		{
        			if("1".equals(gdr[i].getReadRight()))
        			{
        				String groupId = gdr[i].getGroupId();
        				gmask.setGroupId(groupId);
        				DBGroups g = DBGroups.loadByKey(gmask);
        				if(g != null)
        					readRights.add(g);
        			}
        			
        			if("1".equals(gdr[i].getWriteRight()))
        			{
        				String groupId = gdr[i].getGroupId();
        				gmask.setGroupId(groupId);
        				DBGroups g = DBGroups.loadByKey(gmask);
        				if(g != null)
        					writeRights.add(g);
        			}
        		}
        	}
        }
        
        // PUT DEFAULT STORETYPE FROM PARENT DIRECTORY
        if(this.parentDirId != null)
        {
        	gdmask.clear();
        	gdmask.setId(this.parentDirId);
        	DBGedDirectory gd = DBGedDirectory.loadByKey(gdmask);

        	if(gd != null)
        		this.storeType = gd.getStoreType();
        }
    	
        return null;
    }

    public String doAddReadGroup() throws Exception
    {
        if(groupId == null) throw new Exception(cmsLang.translate("error.selected"));

        gmask.clear();
        gmask.setGroupId(groupId);
        DBGroups g = DBGroups.loadByKey(gmask);

        if(g == null) throw new Exception(cmsLang.translate("error.found"));

        if(!readRights.contains(g))
            readRights.add(g);

        Debug.println(this, Debug.WARNING, readRights);
        Debug.println(this, Debug.WARNING,"g.equals(readRights.get(0))="+g.equals(readRights.get(0)));

        return null;
    }

    public String doDeleteReadGroup() throws Exception
    {
        readRights.remove(index);
        return null;
    }

    public String doAddWriteGroup() throws Exception
    {
        if(groupId == null) throw new Exception(cmsLang.translate("error.selected"));

        gmask.clear();
        gmask.setGroupId(groupId);
        DBGroups g = DBGroups.loadByKey(gmask);

        if(g == null) throw new Exception(cmsLang.translate("error.found"));

        if(!writeRights.contains(g))
            writeRights.add(g);

        return null;        
    }

    public String doDeleteWriteGroup() throws Exception
    {
        writeRights.remove(index);
        return null;
    }

    public String doAddThesaurus() throws Exception
    {
        if(thesaurusId == null) throw new Exception(cmsLang.translate("error.selected"));
        tmask.clear();
        tmask.setId(thesaurusId);
        DBThesaurus t = DBThesaurus.loadByKey(tmask);

        if(t == null) throw new Exception(cmsLang.translate("error.found"));

        thesaurus.add(t);

        return null;
    }

    public String doDeleteThesaurus() throws Exception
    {
        thesaurus.remove(index);
        return null;
    }

    public String doAddNotifGroup()
        throws Exception
    {
        if(groupId == null) throw new Exception(cmsLang.translate("error.selected"));

        gmask.clear();
        gmask.setGroupId(groupId);
        DBGroups g = DBGroups.loadByKey(gmask);

        if(g == null) throw new Exception(cmsLang.translate("error.found"));

        if(!notifGroups.contains(g))
            notifGroups.add(g);

        return null;
    }

    public String doDeleteNotifGroup()
        throws Exception
    {
        notifGroups.remove(index);
        return null;
    }

    // DO E


    // GET - SET
    /**
     * Gets the value of thesaurusId
     *
     * @return the value of thesaurusId
     */
    public final String getThesaurusId()
    {
        return this.thesaurusId;
    }

    /**
     * Sets the value of thesaurusId
     *
     * @param argThesaurusId Value to assign to this.thesaurusId
     */
    public final void setThesaurusId(final String argThesaurusId)
    {
        this.thesaurusId = argThesaurusId;
    }

    /**
     * Gets the value of index
     *
     * @return the value of index
     */
    public final int getIndex()
    {
        return this.index;
    }

    /**
     * Sets the value of index
     *
     * @param argIndex Value to assign to this.index
     */
    public final void setIndex(final int argIndex)
    {
        this.index = argIndex;
    }

    /**
     * Gets the value of groupId
     *
     * @return the value of groupId
     */
    public final String getGroupId()
    {
        return this.groupId;
    }

    /**
     * Sets the value of groupId
     *
     * @param argGroupId Value to assign to this.groupId
     */
    public final void setGroupId(final String argGroupId)
    {
        this.groupId = argGroupId;
    }

}
