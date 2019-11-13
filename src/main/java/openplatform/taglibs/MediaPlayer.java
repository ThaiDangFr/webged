package openplatform.taglibs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import openplatform.database.dbean.DBFileBase;
import openplatform.database.dbean.DBMimeTypes;
import openplatform.database.dbeanext.MimeTypes;
import openplatform.document.FormatConverter;
import openplatform.http.NavigCodec;
import openplatform.tools.Debug;
import dang.cms.CmsLanguage;

public class MediaPlayer extends OPTagSupport
{
	private String fileBaseId;

	
	public void initVars()
	{
		fileBaseId = null;
	}
	
	private boolean isImage(String filename)
	{
    	String extension = MimeTypes.getInstance().getExtension(filename);
    	if(extension == null) return false;
    	DBMimeTypes mt = MimeTypes.getInstance().getMimetypes(extension);
    	if(mt == null) return false;
    	
    	String pt = mt.getPrimaryType();
    	if(pt == null) return false;
    	
    	if(MimeTypes.IMAGE.equalsIgnoreCase(pt)) return true;
    	else return false;
	}
	
	
	private boolean isVideo(String filename)
	{
		return false;
	}
	
	private boolean isSound(String filename)
	{
		String extension = MimeTypes.getInstance().getExtension(filename);
		
		if("mp3".equalsIgnoreCase(extension)) return true;
		else return false;
	}
	
	/*
	private boolean isText(String filename)
	{
		String ext = MimeTypes.getInstance().getExtension(filename);
		
		if("ppt".equalsIgnoreCase(ext)) return false; // does not support yet
		
		return FormatConverter.isConvertible(ext,"html");
	}
	*/
	
	private boolean isPdf(String filename)
	{
		String ext = MimeTypes.getInstance().getExtension(filename);
		if("pdf".equalsIgnoreCase(ext)) return true;
		else return false;
	}
	
	
	private boolean isPdfConvertible(String filename)
	{
		String ext = MimeTypes.getInstance().getExtension(filename);
		return FormatConverter.isConvertible(ext,"pdf");
	}
	
	
	public int doStartTag() throws JspException
	{
		if("".equals(fileBaseId)) fileBaseId = null;
		
		StringBuffer res = new StringBuffer();
		
		
        String delim = "&";
        //String terminalType = getTerminalType();
        //if(DBTerminal.NAVTYPE_WAP.equals(terminalType)) delim = "&amp;";
		
    	try
    	{    		
    		if(fileBaseId != null)
    		{
    			String filename = null;
    			DBFileBase fbmask = new DBFileBase();
    			fbmask.setFileBaseId(fileBaseId);
    			DBFileBase fb = DBFileBase.loadByKey(fbmask);
    			if(fb != null) filename = fb.getFilename();
    				
    			/**
    			 * IMAGE
    			 */
    			if(isImage(filename))
    			{
    				res.append("<a href=\"#\" onmouseover=\"showImg('")
    				.append(fileBaseId).append("');\" onmouseout=")
    				.append("\"hideImg('").append(fileBaseId).append("');\">");
    				
    				StringBuffer newhref = new StringBuffer();
    				newhref.append("/openplatform/common/dl.jsp?");
    				newhref.append("fileBaseId=").append(fileBaseId);
    				newhref.append(delim).append("disposition=inline");
    				newhref.append(delim).append("convert=true");
    				newhref.append(delim).append("extension=jpg");
    				newhref.append(delim).append("param=80x60");
    				res.append("<img src=\"");
    				String navig = NavigCodec.code(newhref.toString());
                	res.append(navig).append("\"/>");
                	
                	res.append("</a>");
    				
            		res.append("<div id=\"").append(fileBaseId)
            		.append("\" style=\"display:none;z-index:200;position:absolute;background-color:#000;border:5px solid;\">");
    				
            		StringBuffer newhref2 = new StringBuffer();
            		newhref2.append("/openplatform/common/dl.jsp?");
            		newhref2.append("fileBaseId=").append(fileBaseId);
            		newhref2.append(delim).append("disposition=inline");
            		newhref2.append(delim).append("convert=true");
            		newhref2.append(delim).append("extension=jpg");
            		newhref2.append(delim).append("param=400x300");
    				res.append("<img style=\"margin:0;padding:0;\" src=\"");
    				String navig2 = NavigCodec.code(newhref2.toString());
                	res.append(navig2).append("\"/>");
    				res.append("</div>");
    			}
    			
    			
    			
    			/**
    			 * VIDEO
    			 */
    			else if(isVideo(filename))
    			{
    			}
    			
    			
    			/**
    			 * SOUND
    			 */
    			else if(isSound(filename))
    			{
    				/*
    				res.append("<APPLET code=\"javazoom.jlGui.TinyPlayer\"")
    				.append(" archive=\"/openplatform/common/mp3player/tinyplayer.jar,/openplatform/common/mp3player/jl020.jar\"")
    				.append(" width=\"59\" height=\"32\" name=\"playerid\">")
    				.append("<param name=\"skin\" value=\"/openplatform/common/mp3player/skins/Digitalized\">")
    				.append("<param name=\"bgcolor\" value=\"638182\">")
    				.append("<param name=\"autoplay\" value=\"no\">")
    				.append("<param name=\"scriptable\" value=\"true\">")
    				.append("<param name=\"audioURL\" value=\"");
    				*/	
    				
    				
    				StringBuffer newhref = new StringBuffer();
    				newhref.append("/openplatform/common/dl.jsp?");
    				newhref.append("fileBaseId=").append(fileBaseId);
    				newhref.append(delim).append("disposition=inline");
    				newhref.append(delim).append("convert=false");
    				String navig = NavigCodec.code(newhref.toString());
    			
    				/*
    				res.append(
    				"<object type=\"application/x-shockwave-flash\" data=\"/openplatform/common/mp3player/dewplayer.swf?son=/openplatform/common/dl.navig?ypVr0xfCOneH5EemKrMu/8wWB7CX3ePgWdTk4i/GzKe3i9dMMrudniB7ojlp0UGnlDxUmZ3EBzU=\" width=\"200\" height=\"20\">"+ 
    				"<param name=\"movie\" value=\"/openplatform/common/mp3player/dewplayer.swf?son=/openplatform/common/dl.navig?ypVr0xfCOneH5EemKrMu/8wWB7CX3ePgWdTk4i/GzKe3i9dMMrudniB7ojlp0UGnlDxUmZ3EBzU=\" />" +
    				"</object>"
    				 );
    				 */
    				
    				 
    				res.append("<object type=\"application/x-shockwave-flash\" data=\"/openplatform/common/mp3player/dewplayer.swf?son=").append(navig).append("\" width=\"150\" height=\"20\">");
    				res.append("<param name=\"wmode\" value=\"transparent\" /> ");
    				res.append("<param name=\"movie\" value=\"/openplatform/common/mp3player/dewplayer.swf?son=");
    				res.append(navig).append("\" /></object>");
    				
    				 
    				//System.out.println(res);
    				
    				/*
    				res.append(navig)
    				.append("\">")
    				.append("</APPLET>");
    				*/
    			}
    			
    			/**
    			 * PDF
    			 */
    			else if(isPdf(filename) || isPdfConvertible(filename))
    			{
    				CmsLanguage cmsLang = new CmsLanguage();
    				
    				StringBuffer newhref = new StringBuffer();
    				newhref.append("/openplatform/common/pdf.jsp?");
    				newhref.append("fileBaseId=").append(fileBaseId);
    				
    				String navig = NavigCodec.code(newhref.toString());
                	
    				res.append("<a href=\"#\" class=\"consult\" onclick=\"window.open('")
    				.append(navig).append("','','menubar=no,scrollbars=yes,resizable=yes');\">")
    				.append(cmsLang.translate("consult")).append("</a>");
    			}
    			
    			/**
    			 * TEXT
    			 */
    			/*
    			else if(isText(filename))
    			{
    				CmsLanguage cmsLang = new CmsLanguage();
    				
    				StringBuffer newhref = new StringBuffer();
    				newhref.append("/openplatform/common/dl.jsp?");
    				newhref.append("fileBaseId=").append(fileBaseId);
    				newhref.append(delim).append("disposition=inline");
    				newhref.append(delim).append("convert=true");
    				newhref.append(delim).append("extension=html");
    				
    				String navig = NavigCodec.code(newhref.toString());
                	
    				res.append("<a href=\"#\" class=\"consult\" onclick=\"window.open('")
    				.append(navig).append("','','menubar=no,scrollbars=yes,resizable=yes');\">")
    				.append(cmsLang.translate("consult")).append("</a>");
    			}
    			*/
    		}
    		
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
        return EVAL_PAGE;
    }

	public String getFileBaseId() {
		return fileBaseId;
	}

	public void setFileBaseId(String fileBaseId) {
		this.fileBaseId = fileBaseId;
	}
	
	
}
