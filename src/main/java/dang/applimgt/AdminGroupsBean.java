package dang.applimgt;

import java.util.HashMap;

import openplatform.database.dbean.DBGroups;
import openplatform.tools.OrdHashMap;
import dang.cms.CmsLanguage;


/**
 * Class AdminGroupsBean
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminGroupsBean extends DBGroups
{
	private CmsLanguage cmslang = new CmsLanguage();
	
    public HashMap getGroupsOption()
    {
        HashMap map = new OrdHashMap();
        DBGroups[] g = DBGroups.load(null);

        if(g == null) return map;

        int len = g.length;

        for(int i=0; i<len; i++)
        {
            map.put(g[i].getGroupId(), g[i].getName());
        }

        return map;
    }



    public String doEdit()
        throws Exception
    {
        if(this.groupId == null) throw new Exception(cmslang.translate("no.item.selected"));
        
        DBGroups gmask = new DBGroups();
        gmask.setGroupId(this.groupId);
        DBGroups g = DBGroups.loadByKey(gmask);
        if(g != null)
            initBean(g);
        
        return null;
    }


    public String doDelete()
        throws Exception
    {
        if(this.groupId == null) throw new Exception(cmslang.translate("no.item.selected"));
        
        if(this.groupId.equals("1") || this.groupId.equals("2"))
            throw new Exception(cmslang.translate("cannot.delete.item"));

        DBGroups gmask = new DBGroups();
        gmask.setGroupId(this.groupId);
        DBGroups.delete(gmask);
        
        doNew();

        return cmslang.translate("item.deleted");
    }


    public String doNew()
        throws Exception
    {
        this.clear();
        return cmslang.translate("can.create.new.item");
    }  


    public String doSave()
        throws Exception
    {
        if(this.name == null || this.name.equals(""))
            throw new Exception(cmslang.translate("error.empty"));

        if(this.comment == null || this.comment.equals(""))
            throw new Exception(cmslang.translate("error.empty"));


        this.store();
        return name+" "+cmslang.translate("ok.saved");
    }

  


}
