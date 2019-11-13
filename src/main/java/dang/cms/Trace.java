package dang.cms;

import openplatform.database.dbean.*;

/**
 * Trace.java
 *
 *
 * Created: Thu Nov 17 16:59:16 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class Trace extends DBTrace
{
    public Trace(DBTrace t)
    {
        initBean(t);
    }

    public String getModuleName()
    {
        String id = this.moduleId;
        if(id == null) return null;
        
        DBCmsModule mmask = new DBCmsModule();
        mmask.setId(id);
        DBCmsModule m = DBCmsModule.loadByKey(mmask);
        if(m == null) return null;
        else return m.getName();
    }

    public String getStatusUserName()
    {
        String id = this.statusUserId;
        if(id == null) return null;

        DBUser umask = new DBUser();
        umask.setUserId(id);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;
        else return u.getLogin();
    }

    public String getStatusGroupName()
    {
        String id = this.statusGroupId;
        if(id == null) return null;

        DBGroups gmask = new DBGroups();
        gmask.setGroupId(id);
        DBGroups g = DBGroups.loadByKey(gmask);
        if(g == null) return null;
        else return g.getName();
    }
}
