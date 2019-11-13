package openplatform.file;

import java.io.InputStream;



/**
 * Class Repository
 * 
 * To change the way to store files, juste change to
 * RepositoryDB or RepositoryFSDES
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Repository implements openplatform.index.Constants
{
	private static RepositoryInterf repository = new RepositoryFSDES();
	
	
	public static void init()
	{
		repository.init();
	}
	
	public static String cloneFile(String fileBaseId)
	{
		return repository.cloneFile(fileBaseId);
	}
	
	public static InputStream getStream(String fileBaseId)
	{
		return repository.getStream(fileBaseId);
	}
	
	public static void deleteFile(String fileBaseId)
	{
		repository.deleteFile(fileBaseId);
	}
	
	
    public static String storeTempFile(InputStream input)
    {
        return storeTempFile("_temp_1_day", input);  
    }


    public static String storeTempFile(String filename, InputStream input)
    {
        return repository.processFile(filename, input, null, false, null);
    }

    /**
     * Store an inputstream to database and return the ID (can be null if troubles)
     * (If the file already exists, return the ID)
     */
    public static String storeFile(String filename, InputStream input, boolean index, String application)
    {
    	return repository.processFile(filename, input, null, index, application);
    }
 
}
