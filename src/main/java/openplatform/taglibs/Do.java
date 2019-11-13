package openplatform.taglibs;

import javax.servlet.jsp.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import java.lang.reflect.Method;
import openplatform.tools.Debug;
import javax.servlet.*;
import openplatform.session.*;
import javax.servlet.http.*;

/**
 * Do.java
 *
 *
 * Created: Thu Apr  7 15:17:10 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class Do extends OPTagSupport 
{
    private Object bean;
    private String property;
    private String redirectOk;
    private String redirectError;


    public void initVars()
    {
        bean = null;
        property = null;
        redirectOk = null;
        redirectError = null;
    }

    public int doStartTag() throws JspException
    {       
        try
        {  
        	if("".equals(bean)) bean = null;
            if("".equals(property)) property = null;
            if("".equals(redirectOk)) redirectOk = null;
            if("".equals(redirectError)) redirectError = null;
        	
            
            HttpSession hs = pageContext.getSession();
            Object obj = hs.getAttribute("message");

            if(obj == null) throw new Exception("Can't find message attribute");

            JSPMessage jspM = (JSPMessage)obj;

            Class cl = bean.getClass();
            Method me = cl.getMethod("do"+capitalize(property), null);
            try
            {
                obj = me.invoke(bean, null);
                //if(obj == null) return SKIP_BODY;
                if(obj != null)
                {
                    String txt = (String)obj;
                    jspM.setStatus("info");
                    jspM.setText(txt);
                }
                
                if(redirectOk != null)
                {
                    HttpServletResponse hsr = (HttpServletResponse)pageContext.getResponse();
                    hsr.sendRedirect(hsr.encodeRedirectURL(redirectOk)); // pageContext.forward(encodeURL(redirectOk));
                }

            }
            catch(Exception e)
            {
                Throwable t = e.getCause();
                String dbgtxt = t.toString();
                Debug.println(this, Debug.WARNING, "exception text:"+dbgtxt+" bean:"+bean.getClass().getName()+" property:"+property);
                Debug.println(this, Debug.WARNING, e);

                String txt = t.getMessage();
                if(txt == null) txt = dbgtxt;

                jspM.setStatus("error");
                jspM.setText(txt);

                if(redirectError != null)
                {
                    HttpServletResponse hsr = (HttpServletResponse)pageContext.getResponse();
                    hsr.sendRedirect(hsr.encodeRedirectURL(redirectError)); // pageContext.forward(encodeURL(redirectError));
                }
            }
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }

        return SKIP_BODY;
    }


    private String capitalize(String st)
    {
        if(st == null) return null;

        char[] ca = st.toCharArray();
        ca[0] = Character.toUpperCase(ca[0]);
        
        return new String(ca);
    }
    // GET - SET


    /**
     * Gets the value of bean
     *
     * @return the value of bean
     */
    public final Object getBean()
    {
        return this.bean;
    }

    /**
     * Sets the value of bean
     *
     * @param argBean Value to assign to this.bean
     */
    public final void setBean(final Object argBean)
    {
        this.bean = argBean;
    }

    /**
     * Gets the value of property
     *
     * @return the value of property
     */
    public final String getProperty()
    {
        return this.property;
    }

    /**
     * Sets the value of property
     *
     * @param argProperty Value to assign to this.property
     */
    public final void setProperty(final String argProperty)
    {
        this.property = argProperty;
    }

    /**
     * Gets the value of redirectOk
     *
     * @return the value of redirectOk
     */
    public final String getRedirectOk()
    {
        return this.redirectOk;
    }

    /**
     * Sets the value of redirectOk
     *
     * @param argRedirectOk Value to assign to this.redirectOk
     */
    public final void setRedirectOk(final String argRedirectOk)
    {
        this.redirectOk = argRedirectOk;
    }

    /**
     * Gets the value of redirectError
     *
     * @return the value of redirectError
     */
    public final String getRedirectError()
    {
        return this.redirectError;
    }

    /**
     * Sets the value of redirectError
     *
     * @param argRedirectError Value to assign to this.redirectError
     */
    public final void setRedirectError(final String argRedirectError)
    {
        this.redirectError = argRedirectError;
    }

}
