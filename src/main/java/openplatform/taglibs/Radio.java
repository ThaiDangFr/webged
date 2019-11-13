package openplatform.taglibs;

import openplatform.database.dbean.*;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import openplatform.tools.*;
import java.util.*;


/**
 * Class Select
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Radio extends OPTagSupport
{
    private String name;
    private Map options;
    private String defaultoption;

    public String getName() { return name; }
    public Map getOptions() { return options; }
    public String getDefaultOption() { return defaultoption; }

    public void setName(String name) { this.name = name; }
    public void setOptions(Map options) { this.options = options; }
    public void setDefaultOption(String defaultoption) { this.defaultoption = defaultoption; }

    public void initVars()
    {
        name = null;
        options = null;
        defaultoption = null;
    }

    public int doStartTag() throws JspException
    {
        try
        {
        	if("".equals(name)) name = null;
        	if("".equals(defaultoption)) defaultoption = null;
        	
            JspWriter out = getOut();
            String terminalType = getTerminalType();
            StringBuffer sb = new StringBuffer();

            if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
            {
                sb.append("<select");
                sb.append(" name=\"").append(name).append("\"");

                if(defaultoption!=null) sb.append(" value=\"").append(defaultoption).append("\"");

                sb.append(" multiple=\"false\"");
                sb.append(">");

                java.util.Set set = options.keySet();
                Iterator it = set.iterator();
                while(it.hasNext())
                {
                    String key = (String)it.next();
                    String value = (String)options.get(key);

                    sb.append("<option value=\"").append(key).append("\">");
                    sb.append(value).append("</option>");
                }
                sb.append("</select>");
            }
            else
            {
                java.util.Set set = options.keySet();
                Iterator it = set.iterator();
                while(it.hasNext())
                {
                    String key = (String)it.next();
                    String value = (String)options.get(key);

                    if(key.equals(defaultoption))
                    {
                        sb.append("<input type=\"radio\" name=\"").append(name).append("\" value=\"").append(key).append("\" checked>").append(value).append("<br>");
                    }
                    else
                    {
                        sb.append("<input type=\"radio\" name=\"").append(name).append("\" value=\"").append(key).append("\">").append(value).append("<br>");
                    }
                }
            }

            out.print(sb.toString());

//             Tag tag = getParent();
            Tag tag = findAncestorWithClass(this, Form.class);

//             if(tag != null && tag instanceof Form)
            if(tag != null)
            {
                Form f = (Form)tag;
                f.addPostfield(name, "$("+name+")"); 
            }
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }

        return SKIP_BODY;
    }
}
