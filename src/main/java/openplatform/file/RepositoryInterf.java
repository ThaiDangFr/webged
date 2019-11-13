package openplatform.file;

import java.io.InputStream;
import java.util.Date;

public interface RepositoryInterf
{
	public void init();
	
	public String cloneFile(String fileBaseId);
	
	public InputStream getStream(String fileBaseId);
	
	public void deleteFile(String fileBaseId);
	
	public String processFile(String filename, InputStream input, 
    		Date expiryDate, boolean index, String application);
}
