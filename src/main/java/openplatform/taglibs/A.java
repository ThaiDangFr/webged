package openplatform.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import openplatform.database.dbean.DBTerminal;
import openplatform.http.NavigCodec;
import openplatform.tools.Debug;

/**
 * Class Img
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class A extends OPTagSupport
{
    private String href;
    private String fileBaseId;
    private String target;
    private String onclick;
    private String onmousedown;
    private String title;
    private String myclass;
    
    private String extension;
    private String param;


    

    public void initVars()
    {
        href = null;
        fileBaseId = null;
        target = null;
        onclick = null;
        onmousedown = null;
        title = null;
        myclass = null;
        extension = null;
        param = null;
    }

    
	public int doStartTag() throws JspException
    {
		StringBuffer newhref = new StringBuffer();
		
        String delim = "&";
        String terminalType = getTerminalType();
        if(DBTerminal.NAVTYPE_WAP.equals(terminalType)) delim = "&amp;";
		
	       try
	        {
	            if("".equals(href)) href = null;
	            if("".equals(fileBaseId)) fileBaseId = null;
	            if("".equals(target)) target = null;
	            if("".equals(onclick)) onclick = null;
	            if("".equals(onmousedown)) onmousedown = null;
	            if("".equals(title)) title = null;
	            if("".equals(myclass)) myclass = null;
	            if("".equals(extension)) extension = null;
	            if("".equals(param)) param = null;
	        
	            if(fileBaseId != null)
	            {
	            	newhref.append("/openplatform/common/dl.jsp?");
	            	newhref.append("fileBaseId=").append(fileBaseId);
	            	newhref.append(delim).append("disposition=attachment");
	            	
	            	if(extension != null)
	            	{
	            		newhref.append(delim).append("convert=true");
	            		newhref.append(delim).append("extension=").append(extension);
	            		
		            	if(param != null)
		            	{
		            		newhref.append(delim).append("param=").append(param);
		            	}
	            	}
	            	else
	            	{
	            		newhref.append(delim).append("convert=false");
	            	}
	            }
	            else if(href != null)
	            {
	            	newhref.append(href);
	            }	           
	            
	            
	            StringBuffer res = new StringBuffer();
	            res.append("<a href=\"");
	            String navig = NavigCodec.code(newhref.toString());
	            res.append(navig).append("\"");
	            
                if(DBTerminal.NAVTYPE_PC.equals(terminalType))
                {
                	if(target != null) res.append(" target=\"").append(target).append("\"");
                	if(onclick != null) res.append(" onclick=\"").append(onclick).append("\"");
                	if(onmousedown != null) res.append(" onmousedown=\"").append(onmousedown).append("\"");
                	if(title != null) res.append(" title=\"").append(title).append("\"");
                	if(myclass != null) res.append(" class=\"").append(myclass).append("\"");
                }
	            
                res.append(">");
	            
	    		JspWriter out = getOut();
	    		out.print(res.toString());  
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
    		out.print("</a>");
    	}
    	catch(Exception e)
    	{
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
    	}
    	
        return EVAL_PAGE;
    }
    
    
	public String getFileBaseId() {
		return fileBaseId;
	}
	
	public void setFileBaseId(String fileBaseId) {
		this.fileBaseId = fileBaseId;
	}
	
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMyclass() {
		return myclass;
	}

	public void setMyclass(String myclass) {
		this.myclass = myclass;
	}


	public String getExtension() {
		return extension;
	}


	public void setExtension(String extension) {
		this.extension = extension;
	}


	public String getOnclick() {
		return onclick;
	}


	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}


	public String getParam() {
		return param;
	}


	public void setParam(String param) {
		this.param = param;
	}


	public String getOnmousedown() {
		return onmousedown;
	}


	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}
}
