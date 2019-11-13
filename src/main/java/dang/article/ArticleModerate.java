package dang.article;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import openplatform.database.dbean.DBArticle;
import openplatform.database.dbean.DBArticleCategory;
import openplatform.database.dbean.DBArticleFile;
import openplatform.database.dbean.DBArticleLink;
import openplatform.database.dbean.DBArticleModerator;
import openplatform.database.dbean.DBFileBase;
import openplatform.file.Repository;
import openplatform.http.UploadedFile;
import dang.cms.CmsLanguage;


/**
 * Class ArticleModerate
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ArticleModerate extends ArticleNew
{
    private ArrayList buffer = new ArrayList();
    private CmsLanguage cmsLang = new CmsLanguage();






    // GET BEG //
    public boolean getLoadArticle()
    {
        String id = article.getId();

        if(id == null) return false;

        image = article.getImageId();

        afmask.clear();
        afmask.setArticleId(id);
        DBArticleFile[] afile = DBArticleFile.load(afmask);
        if(afile != null)
        {
            int len = afile.length;
            for(int i=0; i<len; i++) joinFiles.add(afile[i]);
        }

        almask.clear();
        almask.setArticleId(id);
        DBArticleLink[] alink = DBArticleLink.load(almask);
        if(alink != null)
        {
            int len = alink.length;
            for(int i=0; i<len; i++) joinLink.add(alink[i]);
        }

        return true;
    }

    public HashMap getValidateOption()
    {
    	HashMap validateOption = new HashMap();
        validateOption.put("0", cmsLang.translate("Disapproved"));
        validateOption.put("1", cmsLang.translate("Approved"));
        
    	return validateOption;
    }
    
    public String getValidate() { return article.getValidate(); }
    public String getDate() { return article.getDate(); }

    /**
     * New method save (does not store the current authorid)
     */
    public String doSave()
        throws Exception
    {    	   	   	
        if(image != null)
        {
            if(image instanceof UploadedFile)
            {
                UploadedFile u = (UploadedFile)image;
                String filename = u.getFileName();
                InputStream input = u.getInput();

                String id = Repository.storeFile(filename, input, false, Repository.APPLI_ARTICLE);
                article.setImageId(id);
            }
        }

        article.setDate(new Timestamp(System.currentTimeMillis()).toString());
        
        article.store(); // store the article

        // store the join files
        DBArticleFile af = new DBArticleFile();
        String articleId = article.getId();
        int len = joinFiles.size();
        for(int i=0; i<len; i++)
        {
            Object obj = joinFiles.get(i);

            if(obj instanceof UploadedFile)
            {
                UploadedFile u = (UploadedFile)obj;
                String id = Repository.storeFile(u.getFileName(),
                                                 u.getInput(), false, "article");
                af.clear();
                af.setArticleId(articleId);
                af.setFileBaseId(id);
                af.store();
            }
        }

        // store the join link
        DBArticleLink al = new DBArticleLink();
        len = joinLink.size();
        for(int i=0; i<len; i++)
        {
            Object obj = joinLink.get(i);
            if(obj instanceof String)
            {
                al.clear();
                al.setArticleId(articleId);
                al.setLink((String)obj);
                al.store();
            }
        }

        // Remove DBArticleLink or DBArticleFile
        len = toRemove.size();
        for(int i=0; i<len; i++)
        {
            Object obj = toRemove.get(i);
            if(obj instanceof DBArticleLink) ((DBArticleLink)obj).delete();
            else if(obj instanceof DBArticleFile) ((DBArticleFile)obj).delete();
            else if(obj instanceof DBFileBase) ((DBFileBase)obj).delete();
        }

        reset();
        return cmsLang.translate("item.updated");
    }


    /**
     * The user has access to the document
     */
    public boolean getIsModerator()
    {
        String categoryId = article.getCategoryId();
        String modId = article.getModeratorId();

        if(categoryId == null || modId == null) return false;

        ammask.clear();
        ammask.setCategoryId(categoryId);
        ammask.setUserId(modId);
        if(DBArticleModerator.count(ammask,null) == 0) return false;
        else return true;
    }

    /**
     * Load only the moderator categories
     */
    public HashMap getCategoryOptions()
    {
        HashMap map = new HashMap();

        ammask.clear();
        ammask.setUserId(article.getModeratorId());
        DBArticleModerator[] am = DBArticleModerator.load(ammask);
        
        if(am != null)
        {
            int len = am.length;
            buffer.clear();
            for(int i=0; i<len; i++) buffer.add(am[i].getCategoryId());
        }


        DBArticleCategory[] cat = DBArticleCategory.load(null);
        if(cat != null)
        {
            int len = cat.length;
            for(int i=0; i<len; i++)
            {
                if(buffer.contains(cat[i].getId()))
                    map.put(cat[i].getId(), cat[i].getName());
            }
        }
        return map;
    }

    /**
     * Delete the article
     */
    public String doDelete()
        throws Exception
    {
        if(article.getId() == null)
        	throw new Exception(article.getId()+" "+cmsLang.translate("error.empty"));
        article.delete();
        return cmsLang.translate("item.deleted");
    }

    public String getArticleId()
    {
        return article.getId();
    }
    // GET END //



    // SET BEG //
    /**
     * Load a new article and do initialisation
     */
    public void setArticleId(String id)
    {
        if(id == null || id.trim().equals("")) return;
        
        reset();
        amask.clear();
        amask.setId(id);
        DBArticle a = DBArticle.loadByKey(amask);
        if(a != null) article = a;
    }

    public void setDate(String s) { article.setDate(s); }
    public void setModeratorId(String id) { article.setModeratorId(id); }
    public void setValidate(String s) { article.setValidate(s); }
    // SET END //
}
