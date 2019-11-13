package openplatform.taglibs;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import openplatform.session.*;
import openplatform.tools.*;
import java.util.*;
import openplatform.database.dbean.*;

/**
 * Class Form
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Form extends OPTagSupport
{
    private String method;
    private String action;
    private String submitstring;

    private HashMap postField = new HashMap();


    public void initVars()
    {
        if(postField!=null) postField.clear();
        method = null;
        action = null;
        submitstring = null;
    }

    public int doStartTag() throws JspException
    {
        try
        {
        	if("".equals(method)) method = null;
            if("".equals(action)) action = null;
            if("".equals(submitstring)) submitstring = null;
        	
            JspWriter out = getOut();
            String terminalType = getTerminalType();
            StringBuffer sb = new StringBuffer();

            if(!DBTerminal.NAVTYPE_WAP.equals(terminalType))
            {
                sb.append("<form action=\"").append(encodeURL(action)).append("\" method=\"").append(method).append("\">");
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
            StringBuffer sb = new StringBuffer();

            if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
            {
                sb.append("<p>");
                sb.append("<anchor>");
                sb.append("<go href=\"").append(encodeURL(action)).append("\" method=\"").append(method).append("\">");

                java.util.Set set = postField.keySet();
                Iterator it = set.iterator();
                while(it.hasNext())
                {
                    Object obj = it.next();
                    String name = (String)obj;
                    String value = (String)postField.get(name);

                    sb.append("<postfield name=\"").append(name).append("\" value=\"").append(value).append("\"/>");
                }


                sb.append("</go>");
                sb.append(StringFilter.replaceSpecialChars(submitstring));
                sb.append("</anchor>");
                sb.append("</p>");
            }
            else
            {
                sb.append("<input type=\"submit\" value=\"").append(submitstring).append("\">");
                sb.append("</form>");
            }

            out.print(sb.toString());
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        finally { initVars(); }

        return EVAL_PAGE;
    }




    public String getSubmitstring()
    {
        return submitstring;
    }

    public void setSubmitstring(String asubmitstring)
    {
        submitstring=asubmitstring;
    }
    public String getAction()
    {
        return action;
    }

    public void setAction(String aaction)
    {
        action=aaction;
    }
    public String getMethod()
    {
        return method;
    }

    public void setMethod(String amethod)
    {
        method=amethod;
    }

    public void addPostfield(String name, String value)
    {
        postField.put(name, value);
    }


}
