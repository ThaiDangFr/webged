package dang.article;

import openplatform.database.dbean.*;
import openplatform.database.*;
import openplatform.tools.*;


/**
 * Class ArticleList
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ArticleList
{
    private DBArticle amask = new DBArticle();
    private SQLConstraint constr = new SQLConstraint();
    private int index = 0;
    private DBArticlePref pref;
    
    private final static int DEFAULT = 10;
    private boolean comments = false;
    
    private int mpp;

    private String searchText;


    public ArticleList()
    {
        DBArticlePref pmask = new DBArticlePref();
        pmask.setId("1");
        pref = DBArticlePref.loadByKey(pmask);
        
        getDoInit();
    }


    // PRIV BEG
    private int getMaxPerPage()
    {
       
        if(pref == null) return DEFAULT;
        else
        {
            String mpp = pref.getArticlePerPage();
            try
            {
                return Integer.parseInt(mpp);
            }
            catch(Exception e)
            {
                return DEFAULT;
            }
        }
    }
    // PRIV END


    // GET BEG
    public boolean getDoInit()
    {
        amask.clear();
        amask.setValidate("1");

        constr.clear();
        searchText = null;


        pref.refresh();
        mpp = getMaxPerPage();

        if("1".equals(pref.getComments())) comments = true;
        else comments = false;

        return true;
    }

    public DBArticle[] getArticle()
    {
        constr.setLimitOffset(index*mpp);
        constr.setLimit(mpp);
        constr.setOrderBy(DBArticle.DATE);
        constr.setOrderByBiggestFirst(true);

        StringBuffer sb = new StringBuffer();
        if(searchText != null)
        {
            //String motif = StringUtils.replaceAll(searchText, "*", "%");
        	String motif = Database.cleanChars(searchText);
        	
            sb.append(DBArticle.TITLE).append(SQLConstraint.LIKE).append("'%").append(motif).append("%'").append(SQLConstraint.OR);
            sb.append(DBArticle.SUMMARY).append(SQLConstraint.LIKE).append("'%").append(motif).append("%'").append(SQLConstraint.OR);
            sb.append(DBArticle.TEXT).append(SQLConstraint.LIKE).append("'%").append(motif).append("%'");
            constr.setCustomWhere(sb.toString());
        }

        
        return DBArticle.load(constr,amask);
    }

    public int getNumberOfPage()
    {
        SQLConstraint countconstr = new SQLConstraint();
        StringBuffer sb = new StringBuffer();
        if(searchText != null)
        {
            //String motif = StringUtils.replaceAll(searchText, "*", "%");
        	String motif = Database.cleanChars(searchText);
        	
            sb.append(DBArticle.TITLE).append(SQLConstraint.LIKE).append("'%").append(motif).append("%'").append(SQLConstraint.OR);
            sb.append(DBArticle.SUMMARY).append(SQLConstraint.LIKE).append("'%").append(motif).append("%'").append(SQLConstraint.OR);
            sb.append(DBArticle.TEXT).append(SQLConstraint.LIKE).append("'%").append(motif).append("%'");
            countconstr.setCustomWhere(sb.toString());
        }
       
        int c = DBArticle.count(amask, countconstr);
        
        int p = c/mpp;
        if(c%mpp != 0) p = p+1;

        return p;
    }

    public boolean isCommentsActivated() { return comments; }

    public int getPage() { return index+1; }
    // GET END


    // SET BEG
    public void setPage(int idx)
    {
        index = idx-1;
    }

    public void setCategoryId(String id)
    {
        amask.setCategoryId(id);
    }

    public void setSearchText(String s) { this.searchText = s; }
    // SET END
}
