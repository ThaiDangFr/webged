package dang.article;

import java.util.ArrayList;
import java.util.HashMap;

import openplatform.database.dbean.DBArticleCategory;
import openplatform.database.dbean.DBArticleComment;
import openplatform.database.dbean.DBArticleFile;
import openplatform.database.dbean.DBArticleLink;
import openplatform.database.dbean.DBFileBase;
import openplatform.database.dbean.DBUser;

/**
 * Class ArticleView
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ArticleView extends ArticleModerate
{
    private DBUser umask = new DBUser();
    private DBFileBase fbmask = new DBFileBase();
    private DBArticleComment cmask = new DBArticleComment();
    private DBArticleCategory catmask = new DBArticleCategory();

    // GET BEG //
    public boolean getSave() { return false; }
    public boolean getIsModerator() { return false; }
    public HashMap getCategoryOptions() { return null; }
    public boolean getDoDelete() { return false; }

    public String getAuthorLogin()
    {
        String id = article.getAuthorId();
        if(id == null) return null;

        umask.setUserId(id);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;
        else return u.getLogin();
    }

    public String getModeratorLogin()
    {
        String id = article.getModeratorId();
        if(id == null) return null;

        umask.setUserId(id);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;
        else return u.getLogin();
    }

    /**
     * @return an array of String
     */
    public ArrayList getJoinLink()
    {
        ArrayList array = new ArrayList();

        int len = joinLink.size();
        for(int i=0; i<len; i++)
        {
            DBArticleLink al = (DBArticleLink)joinLink.get(i);
            array.add(al.getLink());
        }

        return array;
    }

    /**
     * @return an array of DBFileBase
     */
    public ArrayList getJoinFile()
    {
        ArrayList array = new ArrayList();
        
        int len = joinFiles.size();
        for(int i=0; i<len; i++)
        {
            DBArticleFile af = (DBArticleFile)joinFiles.get(i);
            String fbid = af.getFileBaseId();
            fbmask.clear();
            fbmask.setFileBaseId(fbid);

            DBFileBase fb = DBFileBase.loadByKey(fbmask);
            if(fb == null) continue;
            else array.add(fb);
        }

        return array;
    }


    public int getNumberOfComments()
    {
        cmask.clear();
        cmask.setArticleId(article.getId());
        return DBArticleComment.count(cmask, null);
    }

    public String getArticleId() { return article.getId(); }

    public String getCategoryName()
    {
        catmask.clear();
        catmask.setId(article.getCategoryId());
        DBArticleCategory cat = DBArticleCategory.loadByKey(catmask);
        if(cat == null) return null;
        else return cat.getName();
    }
    // GET END //



    // SET BEG //
    public void setArticleId(String id)
    {
        super.setArticleId(id); // reset() and load DBArticle
        super.getLoadArticle();
    }
    // SET END //
}
