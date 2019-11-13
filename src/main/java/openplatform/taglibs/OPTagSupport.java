package openplatform.taglibs;

import java.util.HashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import openplatform.session.UserSession;


/**
 * Class OPTagSupport
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public abstract class OPTagSupport extends TagSupport
{
    private static HashMap entities = new HashMap();
    

    public abstract void initVars();

    public OPTagSupport()
    {
        initVars();
    }


    public void release()
    {
        super.release();
        initVars();
    }

    /**
     * Encode the URL and include JSESSIONID if cookies disabled
     */
    public String encodeURL(String url)
    {
        ServletResponse response = pageContext.getResponse();
        if(response != null && response instanceof HttpServletResponse)
        {
            HttpServletResponse httpr = (HttpServletResponse)response;
            return httpr.encodeURL(url);
        }
        else return url;
    }

    public void setContentType(String str)
    {
        ServletResponse response = pageContext.getResponse();
        if(response != null) response.setContentType(str);
    }

    public void setCharacterEncoding(String str)
        throws Exception
    {
        ServletResponse response = pageContext.getResponse();
        if(response != null) response.setCharacterEncoding(str);

        ServletRequest request = pageContext.getRequest();
        if(request != null) request.setCharacterEncoding(str);
    }


    public void setHeader(String name, String value)
    {
        ServletResponse response = pageContext.getResponse();
        if(response != null && response instanceof HttpServletResponse)
        {
            HttpServletResponse httpr = (HttpServletResponse)response;
            httpr.setHeader(name, value);
        }
    }

    public JspWriter getOut()
    {
        return pageContext.getOut();
    }

    public String getTerminalType()
    {
        HttpSession session = pageContext.getSession();

        if(session == null) return null;

        Object obj = session.getAttribute(UserSession.SESSION_TERMINAL_TYPE);
        if(obj == null) return null;
        else return (String)obj;
    }

    public String getImageId()
    {
        HttpSession session = pageContext.getSession();
        
        if(session == null) return null;

        Object obj = session.getAttribute(UserSession.SESSION_IMAGE_ID);
        if(obj == null) return null;
        else return (String)obj;
    }

}
