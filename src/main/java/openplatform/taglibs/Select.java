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
public class Select extends OPTagSupport
{
    private String name;
    private String multiple; // true - false
    private Map options;
    private String defaultoption;
    private List defaultoptionlist;
    private String[] defaultoptionarray;
    private String size;
    private String attributesText;

    public String getAttributesText() { return this.attributesText; }
    public String getName() { return name; }
    public String getMultiple() { return multiple; }
    public Map getOptions() { return options; }
    public String getDefaultOption() { return defaultoption; }
    public String getSize() { return size; }
    public List getDefaultOptionList() { return  defaultoptionlist; }
    public String[] getDefaultOptionArray() { return defaultoptionarray; }

    public void setAttributesText(String at) { this.attributesText = at; }
    public void setName(String name) { this.name = name; }
    public void setMultiple(String multiple) { this.multiple = multiple; }
    public void setOptions(Map options) { this.options = options; }
    public void setDefaultOption(String defaultoption) { this.defaultoption = defaultoption; }
    public void setSize(String size) { this.size = size; }
    public void setDefaultOptionList(List defaultoptionlist) { this.defaultoptionlist = defaultoptionlist; }
    public void setDefaultOptionArray(String[] defaultoptionarray)
    {
        this.defaultoptionarray = defaultoptionarray;
        if(defaultoptionarray != null)
        {
            defaultoptionlist = new ArrayList();
            int len = defaultoptionarray.length;
            for(int i=0; i<len; i++)
            {
                defaultoptionlist.add(defaultoptionarray[i]);
            }
        }
    }

    public void initVars()
    {
        name = null;
        multiple = null;
        options = null;
        defaultoption = null;
        defaultoptionlist = null;
        defaultoptionarray = null;
        size = null;
        attributesText = null;
    }

    public int doStartTag() throws JspException
    {
        try
        {
        	if("".equals(name)) name = null;
        	if("".equals(multiple)) multiple = null;
        	if("".equals(defaultoption)) defaultoption = null;
        	if("".equals(size)) size = null;
        	if("".equals(attributesText)) attributesText = null;
        	
        	
        	
            JspWriter out = getOut();
            String terminalType = getTerminalType();
            StringBuffer sb = new StringBuffer();

            // carefull : no multiple preselection for wap 
            if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
            {
                sb.append("<select");
                sb.append(" name=\"").append(name).append("\"");

                if("true".equalsIgnoreCase(multiple)) sb.append(" multiple=\"true\"");
                else sb.append(" multiple=\"false\"");

                if(defaultoption!=null) sb.append(" value=\"").append(defaultoption).append("\"");
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
            }
            else
            {
                sb.append("<select");
                sb.append(" name=\"").append(name).append("\"");
                if("true".equalsIgnoreCase(multiple)) sb.append(" multiple");
                if(size!=null) sb.append(" size=\"").append(size).append("\"");
                if(attributesText!=null) sb.append(" ").append(attributesText);
                sb.append(">");

                java.util.Set set = options.keySet();
                Iterator it = set.iterator();
                while(it.hasNext())
                {
                    String key = (String)it.next();
                    String value = (String)options.get(key);

                    if(key.equals(defaultoption) || (defaultoptionlist!=null && defaultoptionlist.contains(key)))
                    {
                        sb.append("<option value=\"").append(key).append("\" selected>");
                    }
                    else
                    {
                        sb.append("<option value=\"").append(key).append("\">");
                    }

                        sb.append(value).append("</option>");
                }
            }

            sb.append("</select>");
            out.print(sb.toString());

            Tag tag = findAncestorWithClass(this, Form.class);

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
