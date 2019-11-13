package dang.article;

import openplatform.database.dbean.*;
import openplatform.tools.OrdHashMap;

import java.util.*;

import dang.cms.CmsLanguage;

/**
 * Class AdminCategory
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminCategory
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private DBArticleCategory cmask = new DBArticleCategory();
    private DBArticleModerator mmask = new DBArticleModerator();
    private String renameCategoryId;
    private String renameCategoryName;
    //private HashMap categories = new HashMap();

    private String[] cat; // category to remove
    
    
    // GET BEG //
    /*
    public boolean getRefresh()
    {
        renameCategoryId = null;
        renameCategoryName = null;

        categories.clear();
        DBArticleCategory[] c = DBArticleCategory.load(null);
        if(c != null)
        {
            int len = c.length;
            for(int i=0; i<len; i++)
            {
                categories.put(c[i].getId(), c[i].getName());
            }
        }

        return true;
    }
    */

    
    public String doRename()
    	throws Exception
    {
        if(renameCategoryId!=null && renameCategoryName!=null)
        {
            cmask.clear();
            cmask.setId(renameCategoryId);
            DBArticleCategory cat = DBArticleCategory.loadByKey(cmask);
            if(cat == null) throw new Exception("not.found");
            
            cat.setName(renameCategoryName);
            cat.store();

            return cmsLang.translate("item.renamed");
        }
        else throw new Exception("not.found");
    }
    
    /*
    public boolean getRename()
    {
        if(renameCategoryId!=null && renameCategoryName!=null)
        {
            cmask.clear();
            cmask.setId(renameCategoryId);
            DBArticleCategory cat = DBArticleCategory.loadByKey(cmask);
            if(cat == null) return false;
            
            cat.setName(renameCategoryName);
            cat.store();
            return !cat.isInError();
        }
        else return false;
    }
    */
    
    public HashMap getCategoryOption()
    {
    	HashMap categories = new OrdHashMap();
    	
        DBArticleCategory[] c = DBArticleCategory.load(null);
        if(c != null)
        {
            int len = c.length;
            for(int i=0; i<len; i++)
            {
                categories.put(c[i].getId(), c[i].getName());
            }
        }
        
        return categories;
    }
    // GET END //




    // SET BEG //
    public void setAddCategory(String s)
    {
        if(s == null) return;

        cmask.clear();
        cmask.setName(s);
        cmask.store();

        mmask.clear();
        mmask.setCategoryId(cmask.getId());
        mmask.setUserId("1"); // admin per default
        mmask.store();
    }


    
    public String doRemoveCategory()
    	throws Exception
    {
        if(cat == null) throw new Exception(cmsLang.translate("not.found"));

        int len = cat.length;
        cmask.clear();

        for(int i=0; i<len; i++)
        {
            cmask.setId(cat[i]);
            DBArticleCategory.delete(cmask);
        }
        
        return cmsLang.translate("item.deleted");
    }
    
    
    public void setRemoveCategory(String[] cat) { this.cat = cat; }
    public void setRenameCategoryId(String id) { this.renameCategoryId = id; }
    public void setRenameTo(String name) { this.renameCategoryName = name; }
    // SET END //
}
