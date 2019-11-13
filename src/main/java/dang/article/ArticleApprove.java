package dang.article;

import java.util.*;

import openplatform.database.Database;
import openplatform.database.ParamValuePair;
import openplatform.database.dbean.*;
import openplatform.tools.Debug;

/**
 * Class ArticleApprove
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ArticleApprove
{
    private String userId; // mandatory

    private DBArticleModerator ammask = new DBArticleModerator();
    private DBArticle amask = new DBArticle();
    private DBArticleCategory cmask = new DBArticleCategory();


    // GET BEG //

    /**
     * ArrayList of DBArticle
     */
    public ArrayList getArticle()
    {      
        ArrayList array = new ArrayList();

        if(userId == null) return array;
        
        ammask.clear();
        ammask.setUserId(userId);
        DBArticleModerator[] mod = DBArticleModerator.load(ammask);
        if(mod == null) return array;
        int len = mod.length;
        
        for(int i=0; i<len; i++)
        {
            String catId = mod[i].getCategoryId();
            amask.clear();
            amask.setCategoryId(catId);
            DBArticle[] art = DBArticle.load(amask);
            if(art != null)
            {
                int len2 = art.length;
                for(int j=0; j<len2; j++) array.add(art[j]);
            }
        }

        return array;
    }

    public String getCategoryName()
    {
        DBArticleCategory c = DBArticleCategory.loadByKey(cmask);
        if(c == null) return null;
        else return c.getName();
    }

    public boolean isModerator()
    {
        if(userId == null) return false;
        ammask.clear();
        ammask.setUserId(userId);
        if(DBArticleModerator.count(ammask, null) == 0) return false;
        else return true;
    }
    
    public int getCountArticleToModerate()
    {
    	if(userId == null) return 0;
    	
    	try
    	{
    		Database db = Database.getInstance();
    		String q = db.getSpecificSQL("CountArticleToModerate");
    		ArrayList param = new ArrayList();
    		param.add(userId);
    		ArrayList res = db.loadSQL(q, param);
    		if(res == null || res.size() == 0) return 0;
    		
    		ParamValuePair pvp = (ParamValuePair)res.get(0);
    		String nb = pvp.get("COUNT");
    		return Integer.parseInt(nb);
    	}
    	catch(Exception e)
    	{
    		Debug.println(this, Debug.ERROR, e);
    		return 0;
    	}
    }
    // GET END //




    // SET BEG //
    public void setUserId(String id) { this.userId = id; }
    public void setCategoryId(String id) { cmask.clear(); cmask.setId(id); }
    // SET END //
}
