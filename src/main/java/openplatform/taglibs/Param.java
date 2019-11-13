package openplatform.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.Tag;

import openplatform.tools.Debug;

/**
 * Class Param
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Param extends OPTagSupport
{
    private String name;
    private String value;


    public void initVars()
    {
        name = null;
        value = null;
    }

    public int doStartTag() throws JspException
    {
        try
        {        
        	if("".equals(name)) name = null;
        	if("".equals(value)) value = null;

        	
            Tag tag = getParent();
            /*
            if(tag instanceof Img)
            {
//                 Debug.println(this, Debug.DEBUG, "Parent tag is Img");
                Img img = (Img)tag;
                img.addParam(name, value);
            }
            */
            
            /*
            else if(tag instanceof A)
            {
//                 Debug.println(this, Debug.DEBUG, "Parent tag is A");
                A a = (A)tag;
                a.addParam(name, value);
            }
            
            else throw new Exception();
            */
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException("Param tag must be enclosed in a img or a tag ! "+e.getMessage());
        }
        finally { initVars(); }
    
        return SKIP_BODY;
        
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String avalue)
    {
        value=avalue;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String aname)
    {
        name=aname;
    }
}
