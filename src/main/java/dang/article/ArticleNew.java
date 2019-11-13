package dang.article;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import openplatform.database.dbean.DBArticle;
import openplatform.database.dbean.DBArticleCategory;
import openplatform.database.dbean.DBArticleFile;
import openplatform.database.dbean.DBArticleLink;
import openplatform.database.dbean.DBArticleModerator;
import openplatform.database.dbean.DBFileBase;
import openplatform.database.dbean.DBTrace;
import openplatform.file.Repository;
import openplatform.http.OpHttp;
import openplatform.http.UploadedFile;
import openplatform.tools.Debug;
import dang.cms.CmsLanguage;
import dang.cmsbase.Notificator;


/**
 * Class ArticleNew
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ArticleNew
{
	protected CmsLanguage cmsLang = new CmsLanguage();
	
    protected DBArticle article = new DBArticle(); // current article
    protected String authorId;
    protected DBArticle amask = new DBArticle();
    protected HttpServletRequest request;

    protected Object image; // UploadedFile or String(imageId)
    protected ArrayList joinFiles = new ArrayList(); // list of UploadedFile or DBArticleFile
    protected ArrayList joinLink = new  ArrayList(); // list of String or DBArticleLink
    protected ArrayList toRemove = new ArrayList(); // can be DBArticleLink or DBArticleFile

    protected DBArticleFile afmask = new DBArticleFile();
    protected DBArticleLink almask = new DBArticleLink();
    protected DBFileBase fbmask = new DBFileBase();
    protected DBArticleModerator ammask = new DBArticleModerator();


    protected void reset()
    {
        image = null;
        joinFiles.clear();
        joinLink.clear();
        toRemove.clear();
        article.clear();
    }


    // GET BEG //
    public boolean getDoInit() { reset(); return true; }
    public String getTitle() { return article.getTitle(); }
    public String getCategoryId() { return article.getCategoryId(); }
    public String getSummary() { return article.getSummary(); }
    public String getText() { return article.getText(); }
    public String getImageId() { return article.getImageId(); }
    public String getImageLegend() { return article.getImageLegend(); }

    public String doSave()
        throws Exception
    {    	
        if(image != null && image instanceof UploadedFile)
        {
            UploadedFile u = (UploadedFile)image;
            String filename = u.getFileName();
            InputStream input = u.getInput();

            String id = Repository.storeFile(filename,input, false, Repository.APPLI_ARTICLE);
            article.setImageId(id);
        }
                

        article.setDate(new Timestamp(System.currentTimeMillis()).toString());
        article.setAuthorId(authorId);
        article.setValidate("0");
        
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
                                                 u.getInput(), false, Repository.APPLI_ARTICLE);
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


        // Send an internal notification to the moderator
        ammask.clear();
        ammask.setCategoryId(article.getCategoryId());
        DBArticleModerator[] amod = DBArticleModerator.load(ammask);
        if(amod != null)
        {
            len = amod.length;
            for(int i=0; i<len; i++)
            {
                String fromId = article.getAuthorId();
                String toId = amod[i].getUserId();
                String message = cmsLang.translate("article.validation.message")
                +" [url=/openplatform/cms/user/pc/modules/article/articlemoderate.jsp?articleId="
                +article.getId()
                +"]"
                +cmsLang.translate("click.here")
                +"[/url]";
                
                Notificator.send(fromId,
                		toId,
                		Notificator.PRIORITY_HIGH,
                		cmsLang.translate("article.validation.subject"),
                		message);
            }
        }

        ArticleTrace.writeTrace(article.getTitle(), DBTrace.ACTION_SAVE,
                                DBTrace.STATUSMSG_OK, authorId, null);

        return cmsLang.translate("item.added");
    }

    public HashMap getCategoryOptions()
    {
        HashMap map = new HashMap();
        DBArticleCategory[] cat = DBArticleCategory.load(null);
        if(cat != null)
        {
            int len = cat.length;
            for(int i=0; i<len; i++) map.put(cat[i].getId(), cat[i].getName());
        }
        return map;
    }

    public String doAttachImage()
        throws Exception
    {
        if(request == null) throw new Exception(cmsLang.translate("error.empty"));
        UploadedFile uf = OpHttp.uploadFileHtmlForm(request, null);
        
        // bizarrement il semble que cette methode soit appelee 2 fois sur firefox
        if(uf != null) image = uf;
        
        return null;        
    }

    public String doRemoveImage()
        throws Exception
    {
        if(image instanceof String)
        {
            fbmask.clear();
            fbmask.setFileBaseId((String)image);
            DBFileBase f = DBFileBase.loadByKey(fbmask);
            if(f != null) toRemove.add(f);
        }
        image = null;
        return null;
    }

    public String getImageName()
    {
        if(image == null) return null;
        else
        {
            if(image instanceof UploadedFile)
            {
                UploadedFile u = (UploadedFile)image;
                return u.getFileName();
            }
            else if(image instanceof String)
            {
                fbmask.clear();
                fbmask.setFileBaseId((String)image);
                DBFileBase fb = DBFileBase.loadByKey(fbmask);
                if(fb == null) return null;
                else return fb.getFilename();
            }
            else return null;
        }
    }

    public String doJoinFile()
        throws Exception
    {
        if(request == null) throw new Exception(cmsLang.translate("error.empty"));
        UploadedFile u = OpHttp.uploadFileHtmlForm(request, null);
        joinFiles.add(u);
        return null;
    }
    
    public ArrayList getJoinFileName()
    {
        ArrayList array = new ArrayList();
        int len = joinFiles.size();
        for(int i=0; i<len; i++)
        {
            Object obj = joinFiles.get(i);

            if(obj instanceof UploadedFile)
            {
                UploadedFile u = (UploadedFile)obj;
                array.add(u.getFileName());
            }
            else if(obj instanceof DBArticleFile)
            {
                DBArticleFile a = (DBArticleFile)obj;
                String id = a.getFileBaseId();
                if(id != null)
                {
                    fbmask.clear();
                    fbmask.setFileBaseId(id);
                    DBFileBase f = DBFileBase.loadByKey(fbmask);
                    if(f != null) array.add(f.getFilename());
                }
            }
        }

        return array;
    }

    public ArrayList getJoinLink()
    {
        ArrayList array = new ArrayList();
        int len = joinLink.size();
        for(int i=0; i<len; i++)
        {
            Object obj = joinLink.get(i);
            if(obj instanceof String) array.add(obj);
            else if(obj instanceof DBArticleLink)
            {
                DBArticleLink l = (DBArticleLink)obj;
                array.add(l.getLink());
            }
        }

        return array;
    }
    // GET END //



    // SET BEG //
    public void setAuthorId(String id) { this.authorId = id; }

    public void setDoRemoveLink(int index)
    {
        try
        {
            Object obj = joinLink.remove(index-1);
            if(obj instanceof DBArticleLink) toRemove.add(obj);
        }
        catch(Exception e) { Debug.println(this, Debug.ERROR, e); }
    }

    public void setDoJoinLink(String link)
    {
        if(link == null) return;
        joinLink.add(link);
    }

    public void setDoRemoveJoinFile(int index)
    {
        try
        {
            Object obj = joinFiles.remove(index-1);
            if(obj instanceof DBArticleFile) toRemove.add(obj);
        }
        catch(Exception e) { Debug.println(this, Debug.ERROR, e); }
    }

    public void setTitle(String s) { article.setTitle(s); }
    public void setCategoryId(String s) { article.setCategoryId(s); }
    public void setSummary(String s) { article.setSummary(s); }
    public void setText(String s) { article.setText(s); }
    public void setImageLegend(String s) { article.setImageLegend(s); }
    public void setRequest(HttpServletRequest request) { this.request = request; }
    // SET END //
}
