package openplatform.taglibs;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import openplatform.session.*;
import openplatform.tools.*;
import openplatform.database.dbean.*;

/**
 * Class Head
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Head extends OPTagSupport
{
    private String title;


    public void initVars()
    {
        title = null;
    }

    
    public int doStartTag() throws JspException
    {
        try
        {
        	if("".equals(title)) title = null;
        	
        	
            JspWriter out = getOut();
            String terminalType = getTerminalType();

            if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
            {
                out.print("<head>");
                return SKIP_BODY;
            }
            else if(DBTerminal.NAVTYPE_PC.equals(terminalType))
            {
                out.print("<head>");
                
                if(title != null)
                    out.print("<title>"+title+"</title>");
                
                out.print("<meta http-equiv=\"CACHE-CONTROL\" content=\"NO-CACHE\"/>");
                out.print("<meta http-equiv=\"PRAGMA\" content=\"NO-CACHE\"/>");  

            }
            else
            {
                out.print("<head>");
                
                if(title != null)
                    out.print("<title>"+title+"</title>");
            }

            

            
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException
    {
        try
        {
            JspWriter out = getOut();
            out.print("</head>");
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        finally { initVars(); }

        return EVAL_PAGE;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String atitle)
    {
        title=atitle;
    }
}
