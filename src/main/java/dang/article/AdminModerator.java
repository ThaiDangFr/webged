package dang.article;

import openplatform.database.dbean.*;
import openplatform.tools.OrdHashMap;

import java.util.*;

import dang.cms.CmsLanguage;

/**
 * Class AdminModerator
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminModerator
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    //private DBArticleCategory[] categories;
    //private HashMap userOption = new HashMap();
    
    private String categoryId;
    private String userId;

    private DBArticleModerator mmask = new DBArticleModerator();


    // GET BEG
    /*
    public boolean getRefresh()
    {
        categories = DBArticleCategory.load(null);

        userOption.clear();
        DBUser[] users = DBUser.load(null);
        if(users != null)
        {
            int len = users.length;
            for(int i=0; i<len; i++)
            {
                userOption.put(users[i].getUserId(), users[i].getLogin());
            }
        }
        
        categoryId = null;
        userId = null;

        return true;
    }
    */

    public DBArticleCategory[] getCategory() { return DBArticleCategory.load(null); }
    
    public HashMap getUserOption()
    {
    	HashMap userOption = new OrdHashMap();
        DBUser[] users = DBUser.load(null);
        if(users != null)
        {
            int len = users.length;
            for(int i=0; i<len; i++)
            {
                userOption.put(users[i].getUserId(), users[i].getLogin());
            }
        }
        return userOption;
    }

    public String doSaveModerator() throws Exception
    {
        if(categoryId == null || userId == null) throw new Exception("not.found");

        mmask.clear();
        mmask.setCategoryId(categoryId);
        DBArticleModerator m = DBArticleModerator.loadByKey(mmask);

        if(m == null)
        {
            mmask.setUserId(userId);
            mmask.store();
        }
        else
        {
            m.setUserId(userId);
            m.store();
        }
        
        return cmsLang.translate("ok.saved");
    }
    	
    	
    
    /*
    public boolean getSaveModerator() 
    {
        if(categoryId == null || userId == null) return false;

        mmask.clear();
        mmask.setCategoryId(categoryId);
        DBArticleModerator m = DBArticleModerator.loadByKey(mmask);

        if(m == null)
        {
            mmask.setUserId(userId);
            mmask.store();
            return !mmask.isInError();
        }
        else
        {
            m.setUserId(userId);
            m.store();
            return !m.isInError();
        }
    }
*/
    
    /**
     * @return the userid according to the categoryId
     */
    public String getUserIdFromCategoryId()
    {
        if(categoryId == null) return null;

        mmask.clear();
        mmask.setCategoryId(categoryId);
        DBArticleModerator m = DBArticleModerator.loadByKey(mmask);
        if(m == null) return null;
        else return m.getUserId();
    }
    // GET END

    // SET BEG
    public void setCategoryId(String id) { this.categoryId = id; }
    public void setUserId(String id) { this.userId = id; }
    // SET END
}
