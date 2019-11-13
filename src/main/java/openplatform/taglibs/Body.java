package openplatform.taglibs;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import openplatform.session.*;
import openplatform.tools.*;
import openplatform.database.dbean.*;

/**
 * Class Body
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Body extends OPTagSupport
{
    private String title;
    private String attributesText;

    public void initVars()
    {
        title = null;
        attributesText = null;
    }

    
    public int doStartTag() throws JspException
    {
        try
        {
        	if("".equals(title)) title = null;
            if("".equals(attributesText)) attributesText = null;
        	
            JspWriter out = getOut();
            String terminalType = getTerminalType();

            StringBuffer sb = new StringBuffer();

            if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
            {
                sb.append("<card id=\"main\"");

                if(title!=null) sb.append(" title=\"").append(StringFilter.replaceSpecialChars(title)).append("\"");

                sb.append(">");
            }
            else if(DBTerminal.NAVTYPE_PDA.equals(terminalType)
                    || DBTerminal.NAVTYPE_IMODE.equals(terminalType))
            {
                sb.append("<body");
                if(attributesText != null) sb.append(" ").append(attributesText);
                sb.append(">");
                sb.append(StringFilter.replaceSpecialChars(title)).append("<hr>");
            }
            else
            {
                sb.append("<body onload=\"setHover()\"");
                if(attributesText != null) sb.append(" ").append(attributesText);
                sb.append(">");                
            }

            out.print(sb.toString());
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
            String terminalType = getTerminalType();
            if(DBTerminal.NAVTYPE_WAP.equals(terminalType)) out.print("</card>");
            else out.print("</body>");
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        finally { initVars(); }
        
        return EVAL_PAGE;
    }

    public String getAttributesText()
    {
        return attributesText;
    }

    public void setAttributesText(String aattributesText)
    {
        attributesText=aattributesText;
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
