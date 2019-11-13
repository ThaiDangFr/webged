package dang.websnmp;

import java.util.StringTokenizer;

import openplatform.database.Database;
import openplatform.database.SQLConstraint;
import openplatform.database.dbean.DBSnmpOID;
import openplatform.tools.Debug;
import openplatform.tools.StringUtils;
import dang.cms.CmsLanguage;


/**
 * Class AdminMibHelpBean
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminMibHelpBean
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private static final int RESULTS_PER_PAGE = 10;
    private static final int NUMBER_WINDOW = 10;
    private boolean searchOnMibname;
    private boolean searchOnCaptorName;
    private boolean searchOnDescription;
    private String searchtext;
    private int page;
    private int beginPage;
    private int endPage;
    private int totalPage;
    private int previousPage;
    private int nextPage;
    private DBSnmpOID[] results;

    private SQLConstraint constr = new SQLConstraint();
    private DBSnmpOID oidmask = new DBSnmpOID();


    public AdminMibHelpBean()
        throws Exception
    {
        doClear();
    }


    // DO BEG
    /**
     * Once the searchtext, the page and the options filled, call that method to perform the search
     */
    public String doSearch()
        throws Exception
    {
        if(searchtext == null || searchtext.trim().equals(""))
        {
            doClear();
            throw new Exception(cmsLang.translate("need.keyword"));
        }
                
        if(!searchOnMibname && !searchOnCaptorName && !searchOnDescription)
        {
            doClear();
            throw new Exception(cmsLang.translate("need.option"));
        }


        String customWhere = buildCustomWhere();
        
        
        // Calculate the number of pages
        int nb;

        constr.clear();
        constr.setCustomWhere(customWhere);
        


        nb = DBSnmpOID.count(null, constr);

        int modulo = nb % RESULTS_PER_PAGE;
        int pages = nb / RESULTS_PER_PAGE;

        if(modulo!=0)
            pages += 1;

        totalPage = pages;




        // retrieve the results
        int offset = (page-1)*RESULTS_PER_PAGE;

        constr.clear();
        constr.setCustomWhere(customWhere);
        constr.setLimitOffset(offset);
        constr.setLimit(RESULTS_PER_PAGE);
        constr.setOrderBy(DBSnmpOID.MIBNAME);

        results = DBSnmpOID.load(constr, null);
        
        
        if(results == null)
        {
            doClear();
            throw new Exception(cmsLang.translate("no.result"));
        }



        // calculate begin and end page
        this.beginPage = roundDecimal(this.page) + 1;
        
        if(this.totalPage > (this.beginPage + NUMBER_WINDOW))
            this.endPage = this.beginPage + NUMBER_WINDOW;
        else
            this.endPage = totalPage;


        // calculate previous and next
        this.previousPage = this.page - 1;
        
        if(this.page < this.totalPage) this.nextPage = this.page + 1;
        else this.nextPage = 0;


        dumpVars();   

        return null;
    }

    public String doClear()
        throws Exception
    {
        page = 1;
        beginPage = 1;
        endPage = 0;
        totalPage = 0;
        previousPage = 0;
        nextPage = 0;
        results = null;

        return null;
    }
    // DO END



    private void dumpVars()
    {
        Debug.println(this, Debug.DEBUG,
                      "page="+page+
                      " beginPage="+beginPage+
                      " endPage="+endPage+
                      " totalPage="+totalPage+
                      " previousPage="+previousPage+
                      " nextPage="+nextPage);
    }


    public int getPrevious()
    {
        return previousPage;
    }
    

    public int getNext()
    {
        return nextPage;
    }


    public int getEndPage()
    {
        return endPage;
    }
    

    public int getBeginPage()
    {
        return beginPage;
    }


    public String getSearchtext()
    {
        return searchtext;
    }

    /**
     * Set the text to search
     */
    public void setSearchtext(String asearchtext)
    {
        searchtext=asearchtext;
    }


    /**
     * Set the search on description option
     */
    public void setSearchOnDescription(String asearchOnDescription)
    {
        searchOnDescription = Boolean.valueOf(asearchOnDescription).booleanValue();
    }


    /**
     * Set the search on captor name option
     */
    public void setSearchOnCaptorName(String asearchOnCaptorName)
    {
       searchOnCaptorName = Boolean.valueOf(asearchOnCaptorName).booleanValue();
    }


    /**
     * Set the search on mib name option
     */
    public void setSearchOnMibname(String asearchOnMibname)
    {
        searchOnMibname = Boolean.valueOf(asearchOnMibname).booleanValue();
    }
    

    public String getPage()
    {
        return Integer.toString(this.page);
    }


    /**
     * Set the page where you are
     */
    public void setPage(String page)
    {
        try
        {
            this.page = Integer.parseInt(page);           
        }
        catch(Exception e)
        {
            this.page = 1;
        }
    }

    
    /**
     * The results
     */
    public DBSnmpOID[] getSnmpOID()
    {
        return results;
    }


    /**
     * 1..10 => 0
     * 11 .. 20 => 10
     * etc.
     */
    private int roundDecimal(int nb)
    {
        float f = (float)(nb-1)/(float)NUMBER_WINDOW;
        int n = (int)Math.floor(f);
        return n*NUMBER_WINDOW;
    }


    private String buildCustomWhere()
    {
        StringTokenizer st = new StringTokenizer(searchtext);

        StringBuffer mibnameQ = new StringBuffer();
        StringBuffer oidnameQ = new StringBuffer();
        StringBuffer descriptQ = new StringBuffer();

        while(st.hasMoreTokens())
        {
            String txt = StringUtils.replaceAll(st.nextToken(), "*", "%");
            
            if(searchOnMibname)
            {
                if(mibnameQ.length() != 0) mibnameQ.append(SQLConstraint.AND);

                mibnameQ.append(DBSnmpOID.MIBNAME).append(SQLConstraint.LIKE).append("'%").append(Database.cleanChars(txt)).append("%'");
            }

            if(searchOnCaptorName)
            {
                if(oidnameQ.length() != 0) oidnameQ.append(SQLConstraint.AND);

                oidnameQ.append(DBSnmpOID.CAPTORNAME).append(SQLConstraint.LIKE).append("'%").append(Database.cleanChars(txt)).append("%'");
            }

            if(searchOnDescription)
            {
                if(descriptQ.length() != 0) descriptQ.append(SQLConstraint.AND);

                descriptQ.append(DBSnmpOID.DESCRIPTION).append(SQLConstraint.LIKE).append("'%").append(Database.cleanChars(txt)).append("%'");
            }
        }

        StringBuffer res = new StringBuffer();

        if(mibnameQ.length() != 0)
            res.append("(").append(mibnameQ).append(")");

        if(oidnameQ.length() != 0)
        {
            if(res.length() != 0) res.append(SQLConstraint.OR);
            res.append("(").append(oidnameQ).append(")");
        }

        if(descriptQ.length() != 0)
        {
            if(res.length() != 0) res.append(SQLConstraint.OR);
            res.append("(").append(descriptQ).append(")");
        }

        return res.toString();
    }
}
