package dang.cms;

import openplatform.database.dbean.*;
import java.util.*;

/**
 * Class AdminModule
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminModule
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private DBCmsModule cmask = new DBCmsModule();
    private DBCmsModuleAuthorization mamask = new DBCmsModuleAuthorization();
    private DBCmsModule mod;

    private HashMap authOption = new HashMap();
    
    private HashMap map = new HashMap();
    private ArrayList array = new ArrayList();


    private final static String AUTH_ANONYMOUS = "-1";
    private final static String AUTH_ALLGROUPS = "-2";




    

    // SET METHODS BEGIN //
    public void setModuleId(String moduleId)
    {
        cmask.clear();
        cmask.setId(moduleId);
        mod = DBCmsModule.loadByKey(cmask);
    }

    public void setCSS(String css) { if(mod != null) mod.setCss(css); }

    public void setVisible(String visible) { if(mod != null) mod.setVisible(visible); }

    public void setAuth(String[] auths)
    {
        if(mod != null && auths != null)
        {
            array.clear();
            boolean anonymous = false;
            boolean allgroups = false;

            int len = auths.length;
            for(int i=0; i<len; i++)
            {
                String auth = auths[i];
                
                if(AUTH_ANONYMOUS.equals(auth)) anonymous=true;
                else if(AUTH_ALLGROUPS.equals(auth)) allgroups=true;
                else
                {
                    array.add(auth);
                }
            }

            if(anonymous) mod.setAuth(DBCmsModule.AUTH_ANONYMOUS);
            else if(allgroups) mod.setAuth(DBCmsModule.AUTH_ALLGROUPS);
            else
            {
                mod.setAuth(DBCmsModule.AUTH_SPECIFIC);
                
                mamask.clear();
                mamask.setModuleId(mod.getId());

                DBCmsModuleAuthorization.delete(mamask);

                len = array.size();
                for(int i=0; i<len; i++)
                {
                    mamask.clear();
                    mamask.setModuleId(mod.getId());
                    mamask.setGroupId((String)array.get(i));
                    mamask.store();
                }
            }

            if(anonymous || allgroups)
            {
                mamask.clear();
                mamask.setModuleId(mod.getId());
                DBCmsModuleAuthorization.delete(mamask);
            }
        }
    }

    public void setName(String n) { if(mod != null) mod.setName(n); }
    // SET METHODS END //








    // GET METHODS BEGIN //
    public String getId()
    {
        if(mod != null)
            return mod.getId();
        else
            return null;
    }

    public DBCmsModule[] getListModule() { return DBCmsModule.load(null); }

    public String getCSS()
    {
        if(mod == null) return null;
        else return mod.getCss();
    }

    public String getVisible()
    {
        if(mod != null) return mod.getVisible();
        else return null;
    }

    public ArrayList getAuthList()
    {
        array.clear();
        if(mod != null)
        {
            if(DBCmsModule.AUTH_ANONYMOUS.equals(mod.getAuth())) array.add(AUTH_ANONYMOUS);
            else if(DBCmsModule.AUTH_ALLGROUPS.equals(mod.getAuth())) array.add(AUTH_ALLGROUPS);
            else if(mod.getAuth() != null)
            {
                mamask.clear();
                mamask.setModuleId(mod.getId());

                DBCmsModuleAuthorization[] m = DBCmsModuleAuthorization.load(mamask);
                if(m != null)
                {
                    int len = m.length;
                    for(int i=0; i<len; i++)
                        array.add(m[i].getGroupId());
                }
            }
        }

        return array;
    }

    public boolean isAdvancedConf()
    {
        if("0".equals(mod.getAdvConf())) return false;
        else return true;
    }

    public String getAdvancedConfURL()
    {
        if(mod != null) return mod.getAdvUrl();
        else return null;
    }

    public String getName()
    {
        if(mod != null) return mod.getName();
        else return null;
    }

    public HashMap getVisibleOption()
    {
    	HashMap visibleOption = new HashMap();
        visibleOption.put("0", cmsLang.translate("not.visible"));
        visibleOption.put("1", cmsLang.translate("visible"));
    	
    	return visibleOption;
    }
    
    
    public HashMap getAuthOption()
    {
        authOption.clear();
        authOption.put(AUTH_ANONYMOUS, "[ "+cmsLang.translate("all.groups.and.anonymous")+" ]");
        authOption.put(AUTH_ALLGROUPS, "[ "+cmsLang.translate("all.groups")+" ]");

        DBGroups[] g = DBGroups.load(null);
        if(g != null)
        {
            int len = g.length;
            for(int i=0; i<len; i++)
                authOption.put(g[i].getGroupId(), g[i].getName());
        }

        return authOption;
    }

    public HashMap getGroupOption()
    {
        map.clear();
        DBGroups[] g = DBGroups.load(null);
        if(g == null) return map;

        int len = g.length;
        for(int i=0; i<len; i++)
        {
            map.put(g[i].getGroupId(), g[i].getName());
        }

        return map;
    }

    public String doSave()
    	throws Exception
    {
        if(mod != null)
            mod.store();

        return cmsLang.translate("ok.saved");
    }
    // GET METHODS END //
}
