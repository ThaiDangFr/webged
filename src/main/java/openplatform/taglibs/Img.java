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
public class Img extends OPTagSupport
{
	private String title;
	private String fileBaseId;
	private String extension;
	private String param;
	
	
	
	public void initVars()
	{
		title = null;
		fileBaseId = null;
		extension = null;
		param = null;
	}
	
    public int doStartTag()
    {
        return EVAL_BODY_INCLUDE;
    }
	
    public int doEndTag() throws JspException
    {
    	if("".equals(title)) 		title = null;
    	if("".equals(fileBaseId)) 	fileBaseId = null;
    	if("".equals(extension)) 	extension = null;
    	if("".equals(param)) 		param = null;
    	
		StringBuffer newhref = new StringBuffer();
		StringBuffer res = new StringBuffer();
		
        String delim = "&";
        String terminalType = getTerminalType();
        if(DBTerminal.NAVTYPE_WAP.equals(terminalType)) delim = "&amp;";
    	
        try
        {
        	if(fileBaseId != null)
        	{
        		newhref.append("/openplatform/common/dl.jsp?");
        		newhref.append("fileBaseId=").append(fileBaseId);
            	newhref.append(delim).append("disposition=inline");
            	
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
        	
        	
            	res.append("<img src=\"");
            	String navig = NavigCodec.code(newhref.toString());
            	res.append(navig).append("\"");
            	
            	if(DBTerminal.NAVTYPE_PC.equals(terminalType))
            	{
            		if(title != null) res.append(" title=\"").append(title).append("\"");
            	}
            	
            	res.append("/>");
        	}
            
    		JspWriter out = getOut();
    		out.print(res.toString()); 
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        
        return EVAL_PAGE;
    }

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFileBaseId() {
		return fileBaseId;
	}

	public void setFileBaseId(String fileBaseId) {
		this.fileBaseId = fileBaseId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
	
	
	
	
	
	/*
    private int adaptationImageId;
    private String src;
    private String fileBaseId;
    private String title;
    private boolean adaptation;
    private DBFileBase fmask = new DBFileBase();
    private OpHttp ophttp = new OpHttp();
    private DBImage imask = new DBImage();
    private Hashtable paramtable = new Hashtable();
*/
	
	/*
    public void initVars()
    {
    	adaptationImageId = -1;
        src = null;
        fileBaseId = null;
        title = null;
        adaptation = true;
    }

    public String getTitle() { return title; }
    public void setTitle(String s) { this.title = s; }

    public int getAdaptationImageId() { return adaptationImageId; }
    public void setAdaptationImageId(int w) { this.adaptationImageId = w; }

    public boolean getAdaptation() { return adaptation; }
    public void setAdaptation(boolean b) { this.adaptation = b; }

    public void addParam(String name, String value)
    {
        paramtable.put(name, value);
    }


    public String getFileBaseId()
    {
        return fileBaseId;
    }

    public void setFileBaseId(String afileBaseId)
    {   	
        fileBaseId=afileBaseId;
    }
    
    public String getSrc()
    {
        return src;
    }

    public void setSrc(String asrc)
    {
        src=asrc;
    }


    public int doStartTag()
    {
        return EVAL_BODY_INCLUDE;
    }


    public int doEndTag() throws JspException
    {
        try
        {
            if("".equals(src)) src = null;
            if("".equals(fileBaseId)) fileBaseId = null;
            if("".equals(title)) title = null;
        	
            
            
            
            
            JspWriter out = getOut();
            String terminalType = getTerminalType();
            String imageId; // = getImageId();

            if(adaptationImageId != -1) imageId = Integer.toString(adaptationImageId);
            else imageId = getImageId();

            StringBuffer sb = new StringBuffer();
            StringBuffer attrib = new StringBuffer();

            String delim = "&";
            boolean first = true;
        
            if(DBTerminal.NAVTYPE_WAP.equals(terminalType)) delim = "&amp;";


            if(src != null)
            {
                HttpRequest req = new HttpRequest();
                req.setURL(src);

//                 Hashtable ht = getParamTable();
                Enumeration enum0 = paramtable.keys();

                while(enum0.hasMoreElements())
                {
                    String name = (String)enum0.nextElement();
                    String value =  (String)paramtable.get(name);
            
                    req.addParamValuePair(name, value);
                }

                HttpResponse response = ophttp.GET(req);
                int status = response.getStatusCode();

                if(status >= 200 && status <= 300)
                {
                    byte[] data = response.getData();
                    String ct = response.getContentType();

                    if(ct != null && data!=null )
                    {
                        DBMimeTypes[] mt = MimeTypes.getInstance().getMimetypesFromString(ct);
                        if(mt != null)
                        {
                            String dummy = "dummy."+mt[0].getExtension();
                            fileBaseId = Repository.storeFile(dummy, new ByteArrayInputStream(data), false, null);
                            Debug.println(this, Debug.DEBUG, "Storing image in database for adaptation:"+dummy+" from src:"+src);
                        }
                    }

                }
                else
                {
                    Debug.println(this, Debug.WARNING, "Image not found, status:"+status+" src:"+src);
                }
            }

        
            if(fileBaseId != null)
            {
                fmask.clear();
                fmask.setFileBaseId(fileBaseId);

                DBFileBase fb = DBFileBase.loadByKey(fmask);

                if(fb == null) return EVAL_PAGE;

                String fileName = fb.getFilename();

                int idx = fileName.lastIndexOf(".");
                if(idx != -1 && imageId!=null)
                {
                    imask.clear();
                    imask.setImageId(imageId);
                    DBImage image = DBImage.loadByKey(imask);

                    String ext = image.getExtension();
                    fileName = fileName.substring(0, idx)+"."+ext;
                }
                
                String actioncode;
                if(adaptation) actioncode="1";
                else actioncode="0";

                if(DBTerminal.NAVTYPE_WAP.equals(terminalType) || DBTerminal.NAVTYPE_IMODE.equals(terminalType))
                {
                    sb.append("<img alt=\"loading...\" src=\"/openplatform/common/APACHEREWRITE/");
                    sb.append(encodeURL(URLEncoder.encode(fileName,"UTF-8")));
                    sb.append("?actionCode=").append(actioncode).append(delim).append("imageId=").append(imageId)
                    .append(delim).append("fileBaseId=").append(fileBaseId).append(delim).append("attachment=0")
                    .append(delim).append("mimeCode=0"); 

                    sb.append("\"");
                    
                    if(DBTerminal.NAVTYPE_WAP.equals(terminalType))
                        sb.append("/>");
                    else
                        sb.append(">");
                }
                else
                {
                    sb.append("<img alt=\"loading...\" src=\"");
                    
                    StringBuffer jsp = new StringBuffer();
                    jsp.append("/openplatform/common/download.jsp?")
                    .append("actionCode=").append(actioncode).append(delim).append("imageId=")
                    .append(imageId).append(delim).append("fileBaseId=").append(fileBaseId)
                    .append(delim).append("attachment=0").append(delim).append("mimeCode=0");
                    
                    String navig = NavigCodec.code(jsp.toString());
                    
                    sb.append(navig).append("\"");

                    if(title != null)
                        sb.append(" title=\"").append(StringFilter.replaceSpecialChars(title)).append("\"");

                    sb.append(">");
                }
            }

            if(sb.length() != 0)
                out.print(sb.toString());
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            throw new JspTagException(e.getMessage());
        }
        finally
        {
            initVars();
        }

        return EVAL_PAGE;
    }
    */
}
