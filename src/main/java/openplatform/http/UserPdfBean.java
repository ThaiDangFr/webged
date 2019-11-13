package openplatform.http;

import openplatform.database.dbean.DBConversionCache;

public class UserPdfBean {

	private final String IMG_EXT = "png";
	
	private String fileBaseId;
	private int currentPage;
	
	
	public String getImgLink()
	{
		String delim="&";
		
		StringBuffer newhref = new StringBuffer();
	    newhref.append("/openplatform/common/dl.jsp?");
	    newhref.append("fileBaseId=").append(fileBaseId);
	    newhref.append(delim).append("disposition=inline");
	    newhref.append(delim).append("convert=true");
	    newhref.append(delim).append("extension=").append(IMG_EXT);
	    newhref.append(delim).append("param=").append(currentPage);
	    
	    String navig = NavigCodec.code(newhref.toString());
	    return navig;
	}
	
	
	public int getTotalPage()
	{
		DBConversionCache ccmask = new DBConversionCache();
		ccmask.setOrifile(fileBaseId);
		ccmask.setExtension(IMG_EXT);
		
		return DBConversionCache.count(ccmask, null);
	}
	
	public String getNextPage()
	{
		if(currentPage < (getTotalPage()-1)) return Integer.toString(currentPage+1);
		else return "0";
	}

	public String getPreviousPage()
	{
		if(currentPage > 0) return Integer.toString(currentPage-1);
		else return "0";
	}

	// GET SET
	
	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public String getFileBaseId() {
		return fileBaseId;
	}


	public void setFileBaseId(String fileBaseId) {
		this.fileBaseId = fileBaseId;
	}
	
	
	
}
