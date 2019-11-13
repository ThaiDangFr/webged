package openplatform.taglibs;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import javax.servlet.http.*;
import openplatform.session.*;
import openplatform.tools.*;
import java.util.*;
import openplatform.database.dbean.*;

/**
 * Class Input
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Input extends OPTagSupport
{
    private String type;
    private String name;
    private String value;
    private String size;
    private String attributesText;
    private boolean checked;

    public final static String HIDDEN = "hidden";
    public final static String TEXT = "text";
    public final static String PASSWORD = "password";
    public final static String RADIO = "radio";
    public final static String CHECKBOX = "checkbox";
    public final static String TEXTAREA = "textarea";


    public void initVars()
    {
        type = null;
        name = null;
        value = null;
        size = null;
        attributesText = null;
        checked = false;
    }

    public int doStartTag() throws JspException
    {
        try
        {
        	if("".equals(type)) type = null;
        	if("".equals(name)) name = null;
        	if("".equals(value)) value = null;
        	if("".equals(size)) size = null;
        	if("".equals(attributesText)) attributesText = null;
        	
        	
            if(HIDDEN.equalsIgnoreCase(type)) type = HIDDEN;
            else if(TEXT.equalsIgnoreCase(type)) type = TEXT;
            else if(PASSWORD.equalsIgnoreCase(type)) type = PASSWORD;
            else if(CHECKBOX.equalsIgnoreCase(type)) type = CHECKBOX;
            else if(TEXTAREA.equalsIgnoreCase(type)) type = TEXTAREA;
            else type = TEXT;

            StringBuffer sb = new StringBuffer();
            JspWriter out = getOut();
            String terminalType = getTerminalType();

            // WAP BEGIN
            if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
            {
                if(HIDDEN.equals(type))
                {
                    Tag tag = findAncestorWithClass(this, Form.class);
                    boolean bform = (tag != null)?true:false;
                    
                    if(value!=null && bform)
                    {
                        Form f = (Form)tag;
                        f.addPostfield(name, value);
                    }
                }
                else if(TEXT.equals(type) || PASSWORD.equals(type))
                {
                    Tag tag = findAncestorWithClass(this, Form.class);
                    boolean bform = (tag != null)?true:false;

                    sb.append("<input type=\"").append(type).append("\" name=\"").append(name).append("\"");
                    if(size != null) sb.append(" size=\"").append(size).append("\"");
                    if(value != null) sb.append(" value=\"").append(StringFilter.replaceSpecialChars(value)).append("\"");
                    sb.append("/>");

                    if(bform)
                    {
                        Form f = (Form)tag;
                        f.addPostfield(name, "$("+name+")");        
                    }
                }
                else if(CHECKBOX.equals(type))
                {
                    Tag tag = findAncestorWithClass(this, Form.class);
                    boolean bform = (tag != null)?true:false;

                    sb.append("<select name=\"").append(name).append("\"");
                    if(checked) sb.append(" value=\"").append(StringFilter.replaceSpecialChars(value)).append("\"");
                    sb.append(">");
                    
                    sb.append("<option>false</option>");
                    sb.append("<option value=\"").append(StringFilter.replaceSpecialChars(value)).append("\">true</option>");
                    sb.append("</select>");

                    if(bform)
                    {
                        Form f = (Form)tag;
                        f.addPostfield(name, "$("+name+")");        
                    }
                }
                else if(TEXTAREA.equals(type))
                {
                    Tag tag = findAncestorWithClass(this, Form.class);
                    boolean bform = (tag != null)?true:false;

                    sb.append("<input type=\"text\" name=\"").append(name).append("\"");
                    if(size != null) sb.append(" size=\"").append(size).append("\"");
                    if(value != null) sb.append(" value=\"").append(StringFilter.replaceSpecialChars(value)).append("\"");
                    sb.append("/>");

                    if(bform)
                    {
                        Form f = (Form)tag;
                        f.addPostfield(name, "$("+name+")");        
                    }
                }
            }
            // WAP END

            // OTHER TERM BEGIN
            else
            {
                if(TEXTAREA.equals(type))
                {
                    sb.append("<textarea name=\"").append(name).append("\"");
                    if(attributesText != null) sb.append(" ").append(attributesText);
                    sb.append(">");
                    if(value != null) sb.append(StringFilter.replaceSpecialChars(value));
                    sb.append("</textarea>");
                }
                else
                {
                    sb.append("<input type=\"").append(type).append("\"");
                    sb.append(" name=\"").append(name).append("\"");

                    if(value != null) sb.append(" value=\"").append(StringFilter.replaceSpecialChars(value)).append("\"");
                    if(size != null) sb.append(" size=\"").append(size).append("\"");
                    if(attributesText != null) sb.append(" ").append(attributesText);
                    if(checked) sb.append(" checked");
                    sb.append(">");
                }             
            }
            // OTHER TERM END


            if(sb.length() != 0) out.print(sb.toString());
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        finally { initVars(); }

        return SKIP_BODY;
    }


    // GET / SET

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
     * Gets the value of name
     *
     * @return the value of name
     */
    public final String getName()
    {
        return this.name;
    }

    /**
     * Sets the value of name
     *
     * @param argName Value to assign to this.name
     */
    public final void setName(final String argName)
    {
        this.name = argName;
    }

    /**
     * Gets the value of value
     *
     * @return the value of value
     */
    public final String getValue()
    {
        return this.value;
    }

    /**
     * Sets the value of value
     *
     * @param argValue Value to assign to this.value
     */
    public final void setValue(final String argValue)
    {
        this.value = argValue;
    }

    /**
     * Gets the value of size
     *
     * @return the value of size
     */
    public final String getSize()
    {
        return this.size;
    }

    /**
     * Sets the value of size
     *
     * @param argSize Value to assign to this.size
     */
    public final void setSize(final String argSize)
    {
        this.size = argSize;
    }

    /**
     * Gets the value of attributesText
     *
     * @return the value of attributesText
     */
    public final String getAttributesText()
    {
        return this.attributesText;
    }

    /**
     * Sets the value of attributesText
     *
     * @param argAttributesText Value to assign to this.attributesText
     */
    public final void setAttributesText(final String argAttributesText)
    {
        this.attributesText = argAttributesText;
    }

    /**
     * Gets the value of checked
     *
     * @return the value of checked
     */
    public final boolean isChecked()
    {
        return this.checked;
    }

    /**
     * Sets the value of checked
     *
     * @param argChecked Value to assign to this.checked
     */
    public final void setChecked(final boolean argChecked)
    {
        this.checked = argChecked;
    }


}
