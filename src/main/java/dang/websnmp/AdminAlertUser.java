package dang.websnmp;

import openplatform.database.dbean.*;
import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.tools.*;

/**
 * AdminAlertUser.java
 *
 *
 * Created: Thu Jun 16 15:09:40 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class AdminAlertUser extends DBSnmpAlertUser
{
    private CmsLanguage cmsLang = new CmsLanguage();
	
	
    public ArrayList getGroups()
    {
        ArrayList groups = new ArrayList();

        DBSnmpAlertUser[] au = DBSnmpAlertUser.load(null);

        if(au == null) return groups;

        int len = au.length;
        DBGroups gmask = new DBGroups();
        for(int i=0; i<len; i++)
        {
            gmask.clear();
            gmask.setGroupId(au[i].getGroupId());
            DBGroups g = DBGroups.loadByKey(gmask);
            if(g!= null && !groups.contains(g))
                groups.add(g);
        }

        return groups;
    }


    public String doRemove()
        throws Exception
    {
        if(this.groupId == null)
            throw new Exception(cmsLang.translate("error.empty"));

        DBSnmpAlertUser umask = new DBSnmpAlertUser();
        umask.setGroupId(this.groupId);
        DBSnmpAlertUser.delete(umask);

        return null;
    }


    public String doAdd()
        throws Exception
    {
        if(this.groupId == null)
            throw new Exception(cmsLang.translate("error.empty"));

        DBSnmpAlertUser u = new DBSnmpAlertUser();
        u.setGroupId(this.groupId);
        u.store();

        return null;
    }



    public HashMap getGroupsOption()
    {
        HashMap map = new OrdHashMap();
        DBGroups[] g = DBGroups.load(null);
        if(g == null) return map;

        int len = g.length;

        ArrayList groups = getGroups();

        for(int i=0; i<len; i++)
            if(!groups.contains(g[i]))
            {
                map.put(g[i].getGroupId(), g[i].getName());
            }

        return map;
    }



}

