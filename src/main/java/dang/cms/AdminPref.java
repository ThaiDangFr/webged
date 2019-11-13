package dang.cms;

import openplatform.database.dbean.*;
import java.util.*;
import javax.servlet.http.*;
import openplatform.http.*;
import openplatform.file.*;
import openplatform.tools.*;

/**
 * Class AdminPref
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminPref extends DBCmsPreferences
{
	private CmsLanguage cmsLang = new CmsLanguage();
    private HashMap fpOpt = new HashMap();
    private HttpServletRequest request;
    private String adminMail;






    public HashMap getCmsLangOpt()
    {
    	HashMap map = new HashMap();
    	Iterator it = DBCmsPreferences._CMSLANG_MAP.keySet().iterator();
    	while(it.hasNext())
    	{
    		String key = (String)it.next();
    		String val = (String)DBCmsPreferences._CMSLANG_MAP.get(key);
    		map.put(key, cmsLang.translate(val));
    	}
    	return map;
    }
    
    
    // GET BEG
    public HashMap getTrueFalse()
    {
    	HashMap trueFalse = new HashMap();
        trueFalse.put("0", cmsLang.translate("false"));
        trueFalse.put("1", cmsLang.translate("true"));
    	return trueFalse;
    }
    public boolean getDeleteLogo()
    {
        String logoId = getLogoId(); /*pref.getLogoId();*/
        Repository.deleteFile(logoId);
        return true;
    }


    public boolean getLoad()
    {
    	DBCmsPreferences pref = DBCmsPreferences.loadByKey(null);
    	initBean(pref);
    	
        DBUser umask = new DBUser();
        umask.setUserId("1");
        DBUser u = DBUser.loadByKey(umask);
        if(u != null)
        {
            adminMail = u.getMail();
        }
        else adminMail = null;

        return true;
    }

    public String getAdminMail() { return adminMail; }

    public HashMap getFirstPageOption()
    {
        fpOpt.clear();
        DBCmsModule[] m = DBCmsModule.load(null);
        if(m!=null)
        {
            int len = m.length;
            for(int i=0; i<len; i++)
            {
                fpOpt.put(m[i].getId(), m[i].getName());
            }
        }
        return fpOpt;
    }


    public String doStore()
    	throws Exception
    {
    	store();
        Debug.refresh();
        

        DBUser umask = new DBUser();
        umask.setUserId("1");
        DBUser u = DBUser.loadByKey(umask);
        if(u != null)
        {
            u.setMail(this.adminMail);
            u.store();
        }
        
        return cmsLang.translate("ok.saved");
    }

    public boolean getUploadLogo()
    {
        UploadedFile u = OpHttp.uploadFileHtmlForm(request, null);
        
        if(u!=null && u.getSize() != 0)
        {
            String oldId = getLogoId();
            if(oldId != null) Repository.deleteFile(oldId);
            
            String newId = Repository.storeFile(u.getFileName(), u.getInput(), false, null);
            setLogoId(newId);
            store();
        }
        else
        {
            Debug.println(this, Debug.WARNING, "No data to upload");
            return false;
        }

        return true;
    }

    public String getDebugOn() { return getDebugLog(); }
    // GET END



    // SET BEG
    public void setAdminMail(String m) 					{ adminMail = m; }
    public void setRequest(HttpServletRequest request) 	{ this.request = request; }
    public void setDebugOn(String s) 					{ setDebugLog(s); }
    // SET END  
}
