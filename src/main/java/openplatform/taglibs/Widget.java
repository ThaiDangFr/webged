package openplatform.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspTagException;
import openplatform.tools.Debug;
import openplatform.database.dbean.*;
import java.util.*;
import java.lang.reflect.*;
import openplatform.tools.*;


/**
 * Widget.java
 *
 * <pre>
 Create a html:table with label and (html:input or html:select or simple text or html:textarea)
 It uses the other taglibs to construct this one.
 Widget is generally used in a form to display fields from a bean.
  </pre>
 * Created: Wed Apr  6 15:27:54 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class Widget extends OPTagSupport
{
    /**
     * Describe type here.
     */
    private String type; // optional - reserved for future use
    private String title; // optional
    private Object bean; // the bean
    private String cssClass;
    private String label; // the labels of the table, separator is |
    private String property; // all the field name, used for getter method, separator is |
    private String propType; // input, select, multiple, text, textarea
    private String propSize; // -1 if not precised, separator is |

    public final static String INPUT = "input";
    public final static String SELECT = "select";
    public final static String MULTIPLE = "multiple";
    public final static String TEXT = "text";
    public final static String TEXTAREA = "textarea";


    public void initVars()
    {
        type = null;
        title = null;
        bean = null;
        cssClass = null;
        label = null;
        property = null;
        propType = null;
        propSize = null;
    }

    public int doStartTag() throws JspException
    {
        try
        {
        	if("".equals(type)) type = null;
        	if("".equals(title)) title = null;
        	if("".equals(cssClass)) cssClass = null;
        	if("".equals(label)) label = null;
        	if("".equals(property)) property = null;
        	if("".equals(propType)) propType = null;
        	if("".equals(propSize)) propSize = null;
        	
        	
            JspWriter out = getOut();
            String terminalType = getTerminalType();
            
            if(!DBTerminal.NAVTYPE_PC.equals(terminalType))
            {
                if(title!=null)
                {
                    StringBuffer sb = new StringBuffer();
                    sb.append("<b>").append(title).append("</b>");
                    out.print(sb.toString());
                }
            }

            Table table = new Table();
            table.setPageContext(pageContext);

            if(DBTerminal.NAVTYPE_PC.equals(terminalType))
                if(cssClass!=null) table.setCssClass(cssClass);

            table.doStartTag();

            if(DBTerminal.NAVTYPE_PC.equals(terminalType))
            {
                if(title!=null)
                {
                    StringBuffer sb = new StringBuffer();
                    sb.append("<caption>").append(title)
                        .append("</caption>");
                    out.print(sb.toString());
                }
            }

            ArrayList labelList = convert2List(label);
            ArrayList fieldList = convert2List(property);
            ArrayList typeList = convert2List(propType);
            ArrayList sizeList = convert2List(propSize);

            int len1 = labelList.size();
            int len2 = fieldList.size();
            int len3 = typeList.size();
            int len4 = sizeList.size();

            if(len1!=len2 || len1!=len3 || len1!=len4)
            {
                throw new Exception("Lenght of list does not match");
            }
            

            for(int i=0; i<len1; i++)
            {
                String label = (String)labelList.get(i);
                String field = (String)fieldList.get(i);
                String type = (String)typeList.get(i);
                String size = (String)sizeList.get(i);

                StringBuffer sb = new StringBuffer();
                sb.append("<tr><th>").append(StringFilter.replaceSpecialChars(label)).append("</th><td>");
                out.print(sb.toString());
                
                if(INPUT.equalsIgnoreCase(type))
                {
                    String value = valueReflect(field);

                    Input input = new Input();
                    input.setPageContext(pageContext);
                    input.setType(Input.TEXT);
                    input.setName(field);
                    input.setValue(value);
                    if(!size.equals("-1")) input.setSize(size);
                    input.doStartTag();
                }
                else if(TEXT.equalsIgnoreCase(type))
                {
                    String value = StringFilter.replaceSpecialChars(valueReflect(field));
                    if(value != null)
                        out.print(value);
                }
                else if(TEXTAREA.equalsIgnoreCase(type))
                {
                    String value = valueReflect(field);

                    Input input = new Input();
                    input.setPageContext(pageContext);
                    input.setType(Input.TEXTAREA);
                    input.setName(field);
                    input.setValue(value);
                    if(!size.equals("-1")) input.setSize(size);
                    input.doStartTag();                    
                }
                else if(SELECT.equalsIgnoreCase(type))
                {
                    Map opt = hashMapReflect(field);
                    String value = valueReflect(field);

                    Select sel = new Select();
                    sel.setPageContext(pageContext);
                    sel.setName(field);
                    sel.setMultiple("false");
                    sel.setOptions(opt);
                    sel.setDefaultOption(value);
                    if(!size.equals("-1")) sel.setSize(size);
                    sel.doStartTag();
                }
                else if(MULTIPLE.equalsIgnoreCase(type))
                {
                    Map opt = hashMapReflect(field);
                    String value = valueReflect(field);

                    Select sel = new Select();
                    sel.setPageContext(pageContext);
                    sel.setName(field);
                    sel.setMultiple("true");
                    sel.setOptions(opt);
                    sel.setDefaultOption(value);
                    if(!size.equals("-1")) sel.setSize(size);
                    sel.doStartTag();
                }               

                sb = new StringBuffer();
                sb.append("</td></tr>");
                out.print(sb.toString());
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
            Table table = new Table();
            table.setPageContext(pageContext);
            table.doEndTag();           
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        finally { initVars(); }

        return EVAL_PAGE;
    }


    private ArrayList convert2List(String list)
    {
        ArrayList array = new ArrayList();
        StringTokenizer st = new StringTokenizer(list, "|");
        while(st.hasMoreTokens())
        {
            array.add(st.nextToken().trim());
        }
        
        return array;
    }


    private String capitalize(String st)
    {
        if(st == null) return null;

        char[] ca = st.toCharArray();
        ca[0] = Character.toUpperCase(ca[0]);
        
        return new String(ca);
    }

    private String valueReflect(String field)
        throws Exception
    {
        Class cl = bean.getClass();
        Method me = cl.getMethod("get"+capitalize(field), null);
        Object obj = me.invoke(bean, null);

        if(obj == null) return null;

        String value = (String)obj;
        return value;
    }

    private Map hashMapReflect(String field)
        throws Exception
    {
        Class cl = bean.getClass();
        Method me = cl.getMethod("get"+capitalize(field)+"Option", null);
        Object obj = me.invoke(bean, null);

        if(obj == null) return null;
        
        Map map = (Map)obj;
        return map;
    }

    // GET - SET



    /**
     * Gets the value of type
     *
     * @return the value of type
     */
    public final String getType()
    {
        return this.type;
    }

    /**
     * Sets the value of type
     *
     * @param argType Value to assign to this.type
     */
    public final void setType(final String argType)
    {
        this.type = argType;
    }

    /**
     * Gets the value of title
     *
     * @return the value of title
     */
    public final String getTitle()
    {
        return this.title;
    }

    /**
     * Sets the value of title
     *
     * @param argTitle Value to assign to this.title
     */
    public final void setTitle(final String argTitle)
    {
        this.title = argTitle;
    }

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
     * Gets the value of cssClass
     *
     * @return the value of cssClass
     */
    public final String getCssClass()
    {
        return this.cssClass;
    }

    /**
     * Sets the value of cssClass
     *
     * @param argCssClass Value to assign to this.cssClass
     */
    public final void setCssClass(final String argCssClass)
    {
        this.cssClass = argCssClass;
    }

    /**
     * Gets the value of label
     *
     * @return the value of label
     */
    public final String getLabel()
    {
        return this.label;
    }

    /**
     * Sets the value of label
     *
     * @param argLabel Value to assign to this.label
     */
    public final void setLabel(final String argLabel)
    {
        this.label = argLabel;
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
     * Gets the value of propType
     *
     * @return the value of propType
     */
    public final String getPropType()
    {
        return this.propType;
    }

    /**
     * Sets the value of propType
     *
     * @param argPropType Value to assign to this.propType
     */
    public final void setPropType(final String argPropType)
    {
        this.propType = argPropType;
    }

    /**
     * Gets the value of propSize
     *
     * @return the value of propSize
     */
    public final String getPropSize()
    {
        return this.propSize;
    }

    /**
     * Sets the value of propSize
     *
     * @param argPropSize Value to assign to this.propSize
     */
    public final void setPropSize(final String argPropSize)
    {
        this.propSize = argPropSize;
    }


  

}
