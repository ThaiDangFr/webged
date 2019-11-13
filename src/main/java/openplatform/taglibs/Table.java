package openplatform.taglibs;

import openplatform.database.dbean.*;
import openplatform.tools.*;
import javax.servlet.jsp.*;

/**
 * Class Table
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Table extends OPTagSupport
{
    private String align;
    private String bgcolor;
    private String width;
    private String columns;
    private String cssClass;


    public String getAlign() { return align; }
    public String getBgcolor() { return bgcolor; }
    public String getWidth() { return width; }
    public String getColumns() { return columns; }
    public String getCssClass() { return cssClass; }

    public void setAlign(String align) { this.align = align; }
    public void setBgcolor(String bgcolor) { this.bgcolor = bgcolor; }
    public void setWidth(String width) { this.width = width; }
    public void setColumns(String col) { this.columns = col; }
    public void setCssClass(String css) { this.cssClass = css; }

    public void initVars()
    {
        align = null;
        bgcolor = null;
        width = null;
        columns = "0";
    }


    public int doStartTag() throws JspException
    {
        try
        {
        	if("".equals(align)) align = null;
        	if("".equals(bgcolor)) bgcolor = null;
        	if("".equals(width)) width = null;
        	if("".equals(columns)) columns = null;
        	if("".equals(cssClass)) cssClass = null;
        	
        	
            JspWriter out = getOut();
            String terminalType = getTerminalType();
            StringBuffer sb = new StringBuffer();

            if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
            {
                sb.append("<p>");
                sb.append("<table columns=\"").append(columns).append("\"");
                if(align!=null) sb.append(" align=\"").append(align).append("\"");
                sb.append(">");
            }
            else
            {
                sb.append("<table");
                if(bgcolor!=null) sb.append(" bgcolor=\"").append(bgcolor).append("\"");
                if(width!=null) sb.append(" width=\"").append(width).append("\"");
                if(align!=null) sb.append(" align=\"").append(align).append("\"");
                
                if(DBTerminal.NAVTYPE_PC.equals(terminalType))
                    if(cssClass!=null) sb.append(" class=\"").append(cssClass).append("\"");
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
            String terminalType = getTerminalType();
            JspWriter out = getOut();

            out.print("</table>");

            if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
                out.print("</p>");
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        finally { initVars(); }

        return EVAL_PAGE;
    }
}
