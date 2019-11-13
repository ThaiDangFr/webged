package openplatform.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import openplatform.tools.Debug;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.lang.reflect.Method;
import javax.servlet.ServletRequest;

/**
 * Set.java
 *
 *
 * Created: Thu Apr  7 12:56:42 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class Set extends OPTagSupport 
{
    private Object bean;
    private String property;

    public void initVars()
    {
        bean = null;
        property = null;
    }
    
    public int doStartTag() throws JspException
    {
        try
        {
        	if("".equals(property)) property = null;
        	
        	
            ArrayList propList = convert2List(property);

            int len = propList.size();

            if(len == 0) throw new Exception("No property");         

            for(int i=0; i<len; i++)
            {
                String property = (String)propList.get(i);
                ServletRequest req = pageContext.getRequest();
                String arg = req.getParameter((String)propList.get(i));
                
                //Debug.println(this, Debug.DEBUG, "property="+property
                //              +" arg="+arg);

                setReflect(property, arg);
            }            
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }

        return SKIP_BODY;
        
    }

    // PRIVATE

    private ArrayList convert2List(String list)
    {
        ArrayList array = new ArrayList();

        if(list == null) return array;

        StringTokenizer st = new StringTokenizer(list, "|");
        while(st.hasMoreTokens())
        {
            array.add(st.nextToken().trim());
        }
        
        return array;
    }
    
    private void setReflect(String property, String arg)
        throws Exception
    {
        Class cl = bean.getClass();
        Method me = null;
        try
        {
            // try with a String parameter
            me = cl.getMethod("set"+capitalize(property), new Class[] {String.class});
            me.invoke(bean, new Object[] {arg});
        }
        catch(Exception e)
        {
            // try with an int parameter
            me = cl.getMethod("set"+capitalize(property), new Class[] {int.class});
            me.invoke(bean, new Object[] {new Integer(arg)});
        }
    }

    private String capitalize(String st)
    {
        if(st == null) return null;

        char[] ca = st.toCharArray();
        ca[0] = Character.toUpperCase(ca[0]);
        
        return new String(ca);
    }

    // GET AND SET

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
}
