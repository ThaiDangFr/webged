package dang.websnmp;

import openplatform.database.dbean.*;
import java.util.*;

import dang.cms.CmsLanguage;

/**
 * AdminAction.java
 *
 *
 * Created: Wed Jun 15 15:32:17 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class AdminAction extends SnmpAction
{
	private CmsLanguage cmsLang = new CmsLanguage();
    private String[] actionIds;

    // DO BEG
    public String doDelete()
        throws Exception
    {
        if(this.actionIds == null)
        	throw new Exception(cmsLang.translate("no.item.selected"));

        int len = actionIds.length;

        DBSnmpAction amask = new DBSnmpAction();
        
        for(int i=0; i<len; i++)
        {
            amask.setId(actionIds[i]);
            DBSnmpAction.delete(amask);
            amask.clear();
        }

        return cmsLang.translate("item.deleted");
    }

    public String doNew()
        throws Exception
    {
        super.clear();
        super.mibName = null;
        super.oidString = null;
        this.actionIds = null;

        return null;
    }

    public String doSave()
        throws Exception
    {
        if(this.name == null || this.name.trim().equals(""))
            throw new Exception(cmsLang.translate("error.empty.name"));

        if(this.hostId == null || this.hostId.trim().equals(""))
            throw new Exception(cmsLang.translate("error.empty"));

        if(this.type == null || this.type.trim().equals(""))
            throw new Exception(cmsLang.translate("error.empty"));

        if(this.oid == null || this.oid.trim().equals(""))
            throw new Exception(cmsLang.translate("error.empty"));

        super.store();
        return null;
    }

    public String doEdit()
        throws Exception
    {
        if(this.id == null) throw new Exception(cmsLang.translate("error.empty"));
        
        DBSnmpAction amask = new DBSnmpAction();
        amask.setId(this.id);
        DBSnmpAction a = DBSnmpAction.loadByKey(amask);

        if(a == null) throw new Exception(cmsLang.translate("error.empty"));

        SnmpAction sa = new SnmpAction(a);

        this.name = sa.getName();
        this.hostId = sa.getHostId();
        this.type = sa.getType();
        this.oid = sa.getOid();
        this.mibName = sa.getMibName();
        this.oidString = sa.getOidString();

        return null;
    }
    // DO END
    

    public ArrayList getSnmpAction()
    {
        ArrayList array = new ArrayList();
        
        DBSnmpAction[] a = DBSnmpAction.load(null);

        if(a == null) return array;

        int len = a.length;

        for(int i=0; i<len; i++)
        {
            array.add(new SnmpAction(a[i]));
        }

        return array;
    }

    // GET / SET

    /**
     * Gets the value of actionIds
     *
     * @return the value of actionIds
     */
    public final String[] getActionIds()
    {
        return this.actionIds;
    }

    /**
     * Sets the value of actionIds
     *
     * @param argActionIds Value to assign to this.actionIds
     */
    public final void setActionIds(final String[] argActionIds)
    {
        this.actionIds = argActionIds;
    }

}
