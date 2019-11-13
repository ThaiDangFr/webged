package openplatform.taglibs;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import openplatform.session.*;
import openplatform.tools.*;
import openplatform.database.dbean.*;

/**
 * Class Xhtml
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Xhtml extends OPTagSupport
{
    public void initVars() {}

    
    public int doStartTag() throws JspException
    {
        try
        {
            JspWriter out = getOut();
            String terminalType = getTerminalType();

            if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
            {
                setHeader("Cache-Control","no-cache,no-store,must-revalidate,post-check=0,pre-check=0");
                setHeader("Pragma","no-cache");
                setContentType("text/vnd.wap.wml");
                //setCharacterEncoding("UTF-8");
                out.print("<?xml version=\"1.0\"?>");
                out.print("<!DOCTYPE wml PUBLIC \"-//WAPFORUM//DTD WML 1.1//EN\" \"http://www.wapforum.org/DTD/wml_1.1.xml\">");
                out.print("<wml>");
            }
            else
            {
                setHeader("Cache-Control","no-cache,no-store,must-revalidate,post-check=0,pre-check=0");
                setHeader("Pragma","no-cache");
                setContentType("text/html");
                //setCharacterEncoding("UTF-8");
                out.print("<html>");
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
        JspWriter out = getOut();
        String terminalType = getTerminalType();

        try
        {
            if(DBTerminal.NAVTYPE_WAP.equals(terminalType)) out.print("</wml>");
            else out.print("</html>");
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        finally { initVars(); }


        return EVAL_PAGE;
    }
}
