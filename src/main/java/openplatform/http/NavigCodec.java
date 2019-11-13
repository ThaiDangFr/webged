package openplatform.http;




public class NavigCodec {

	private static QueryCrypt qc = new QueryCrypt();
	
	public static String code(String jsp)
	{
		StringBuffer res = new StringBuffer();
        String[] r = extractQuery(jsp);
        res.append(convert2Navig(r[0]));

        if(r[1] != null && !"".equals(r[1]))
        	res.append("?").append(qc.encodeQuery(r[1]));

        return  res.toString();
	}
	
	public static String decode(String navig)
	{		
		StringBuffer res = new StringBuffer();
		String[] r = extractQuery(navig);
		res.append(convert2Jsp(r[0]));
		
        if(r[1] != null && !"".equals(r[1]))
        	res.append("?").append(qc.decodeQuery(r[1]));
        
        return res.toString();
	}
	
	
	   /**
     * 
     * 0:begin of link
     * 1:query
     * 
     * @param s
     * @return
     */
    private static String[] extractQuery(String s)
    {
    	if(s == null) return null;
    	
    	String[] r = new String[2];
    	int idx = s.indexOf("?");

    	if(idx == -1)
    	{
    		r[0] = s;
    		r[1] = "";
    	}
    	else
    	{
    		r[0] = s.substring(0,idx);
    		r[1] = s.substring(idx+1);
    	}
    	
    	return r;
    }
    
    private static String convert2Navig(String url)
    {
    	return convert2suffix(url, "navig");
    }
    
    private static String convert2Jsp(String url)
    {
    	return convert2suffix(url, "jsp");
    }
    
    private static String convert2suffix(String url, String suffix)
    {
    	if(url == null) return null;
    	
    	int idx = url.lastIndexOf(".");
    	if(idx == -1) return url;
    	
    	int idx2 = url.lastIndexOf("?");
    	
    	String res = url.substring(0,idx) + "." + suffix;
    	
    	if(idx2 != -1)
    	{
    		res += url.substring(idx2);
    	}
    	
    	return res;
    }
}
