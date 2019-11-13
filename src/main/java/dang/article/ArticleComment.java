package dang.article;

import openplatform.database.dbean.*;
import java.sql.*;

import dang.cms.CmsLanguage;

/**
 * Class ArticleComment
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ArticleComment
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private String articleId; // mandatory
    private DBArticleComment acmask = new DBArticleComment();
    private DBUser umask = new DBUser();
    private String authorId;
    private String comment;
    private String title;

    // GET BEG
    public DBArticleComment[] getComments()
    {
        if(articleId == null) return null;

        acmask.clear();
        acmask.setArticleId(articleId);
        
        return DBArticleComment.load(acmask);
    }

    public String getAuthorLogin()
    {
        if(authorId == null) return null;
        umask.clear();
        umask.setUserId(authorId);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;
        else return u.getLogin();
    }

    public String doSave()
        throws Exception
    {
        if(comment == null || title == null
           || comment.trim().equals("")
           || title.trim().equals(""))
            throw new Exception(cmsLang.translate("error.empty"));

        acmask.clear();
        acmask.setArticleId(articleId);
        acmask.setAuthorId(authorId);
        acmask.setComment(comment);
        acmask.setTitle(title);
        acmask.setDate(new Timestamp(System.currentTimeMillis()).toString());
        acmask.store();
        return null;
    }
    // GET END


    // SET BEG
    public void setArticleId(String id) { this.articleId = id; }
    public void setAuthorId(String id) { this.authorId = id; }
    public void setComment(String c) { this.comment = c; }
    public void setTitle(String t) { this.title = t; }
    // SET END
}
