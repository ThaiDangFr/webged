package dang.article;

import java.util.HashMap;

import openplatform.database.dbean.DBArticlePref;
import openplatform.tools.OrdHashMap;
import dang.cms.CmsLanguage;

/**
 * Class AdminPref
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminPref extends DBArticlePref
{
	private CmsLanguage cmsLang = new CmsLanguage();
	   

    public AdminPref()
    {
        DBArticlePref pmask = new DBArticlePref();
        pmask.setId("1");
        DBArticlePref pref = DBArticlePref.loadByKey(pmask);
        if(pref != null)
        	initBean(pref);
    }

    
    public String getCommentsActivated() { return getComments(); }
    
    public String doSave() throws Exception
    {       
        store();

        return cmsLang.translate("ok.saved");
    }
    
    public final HashMap getYesNoOpt()
    {
    	HashMap yesNoOpt = new OrdHashMap();
        yesNoOpt.put("0", cmsLang.translate("no"));
        yesNoOpt.put("1", cmsLang.translate("yes"));
        return yesNoOpt;
    }
    
    public final HashMap getArticlePerPageOption()
    {
    	HashMap articlePerPageOption = new OrdHashMap();
        articlePerPageOption.put("1", "1");
        articlePerPageOption.put("5", "5");
        articlePerPageOption.put("10", "10");
        articlePerPageOption.put("15", "15");
        articlePerPageOption.put("20", "20");
        articlePerPageOption.put("25", "25");
        articlePerPageOption.put("30", "30");
        return articlePerPageOption;
    }


    public void setCommentsActivated(String c) { setComments(c); }



  

}
