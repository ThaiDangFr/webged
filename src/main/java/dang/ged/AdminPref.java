package dang.ged;

import java.util.HashMap;

import openplatform.database.dbean.DBGedPref;
import openplatform.index.Engine;
import openplatform.tools.OrdHashMap;
import dang.cms.CmsLanguage;

/**
 * AdminPref.java
 *
 *
 * Created: Tue Jul  5 16:28:07 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class AdminPref extends DBGedPref
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
	public AdminPref()
	{
		setId("1");
		refresh();
	}
	
	
    // DO BEG
    public String doReIndex()
        throws Exception
    {
        Engine.reIndexAll();
        return cmsLang.translate("ok.reindex");
    }


    public String doSave()
        throws Exception
    {    	
    	store();
    	
        return cmsLang.translate("pref.saved");
    }

    // DO END

    public HashMap getTrashExpiryOption()
    {
        long month1 = 2678400000L;
        HashMap map = new OrdHashMap();
        for(int i=1; i<=12; i++)
        {
            String m = Long.toString(i*month1);
            map.put(m, i + " " + cmsLang.translate("month").toLowerCase());
        }

        return map;
    }
    
    /*
    public HashMap getDlTxtAsPdfOption()
    {
    	HashMap map = new OrdHashMap();
    	map.put("0",cmsLang.translate("no"));
    	map.put("1",cmsLang.translate("yes"));
    	return map;
    }
    */
}
