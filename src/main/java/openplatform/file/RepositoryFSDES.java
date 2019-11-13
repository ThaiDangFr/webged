package openplatform.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import openplatform.crypto.MyDes;
import openplatform.database.Database;
import openplatform.database.ParamValuePair;
import openplatform.database.dbean.DBFileBase;
import openplatform.database.dbean.DBGedFiles;
import openplatform.database.dbean.DBGedFilesVersion;
import openplatform.index.Engine;
import openplatform.tools.Configuration;
import openplatform.tools.Debug;
import openplatform.tools.SQLTime;
import openplatform.tools.StreamConverter;
import openplatform.tools.ZlibUtils;

/**
 * 
 * Files are stored under FILES/ and renamed to their fileBaseId
 * They are DES encrypted
 * 
 * @author Thai DANG
 *
 */
public class RepositoryFSDES implements RepositoryInterf
{
	//private final String FILES = System.getProperty("user.home") + "/.openplatform/_FILES/";
	private final String FILES = Configuration.getROOT() + "/_FILES/";
	
	private MyDes myDes = new MyDes();
	
	

	protected RepositoryFSDES()
	{
        try
        {
        	File file = new File(FILES);
        	if(!file.isDirectory())
        		file.mkdirs();
        	
        	myDes.setSecretKey(new byte[] {1,1,0,7,1,9,7,6});
        }
        catch(Exception e)
        {
        	Debug.println(this, Debug.ERROR, e);
        }
	}
	
	
	public void init()
	{
		try
		{
			Database db = Database.getInstance();	
			
			
			/**
			 * Remove GEDFILES without at least one GEDFILESVERSION
			 */
			StringBuffer query = new StringBuffer();
			query.append("SELECT ").append(DBGedFiles.ID).append(" FROM ")
				.append(DBGedFiles.TABLE).append(" WHERE ").append(DBGedFiles.ID)
				.append(" NOT IN (SELECT ").append(DBGedFilesVersion.FILEID)
				.append(" FROM ").append(DBGedFilesVersion.TABLE).append(")");
			ArrayList al = db.loadSQL(query);
			if(al != null)
			{
				DBGedFiles fmask = new DBGedFiles();
				int len = al.size();
				for(int i=0; i<len; i++)
				{
					ParamValuePair pvp = (ParamValuePair)al.get(i);
					String id = pvp.get(DBGedFiles.ID);
					if(id != null)
					{
						fmask.setId(id);
						DBGedFiles.delete(fmask);
						Debug.println(this, Debug.DEBUG, "Delete GedFiles id:"+id);
					}
				}
			}
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
		}
	}
	
	
	
	public String cloneFile(String fileBaseId)
	{
    	long t1=0;
    	long t2=0;
    	
		try
		{
			if(fileBaseId == null) return null;
			
			DBFileBase fbmask = new DBFileBase();
			fbmask.setFileBaseId(fileBaseId);
			
			DBFileBase fb = DBFileBase.loadByKey(fbmask);
			if(fb == null) return null;
			
			fb.setFileBaseId(null);
			fb.setDate(SQLTime.getSQLTime(System.currentTimeMillis()));
			fb.store();
			
			String fbi = fb.getFileBaseId();
			
			fb.clear();
			fb = null;
			
			File copy = new File(FILES+fbi);
			InputStream input = getStream(fbi);
			StreamConverter.writeToDisk(copy,input);
			input.close();
			
			return fbi;
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
			return null;
		}
		finally
		{    		
			long diff = t2-t1;
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			String diffst = format.format(new Date(diff));
			Debug.println(this, Debug.DEBUG, "File cloned in:"+diffst);
		}
	}

	
	public InputStream getStream(String fileBaseId)
	{
    	long t1=0;
    	long t2=0;
    	
		try
		{
			t1 = System.currentTimeMillis();
			FileInputStream fis = new FileInputStream(FILES+fileBaseId);
			InputStream input = myDes.decrypt(fis);
			t2 = System.currentTimeMillis();
			
			return input;
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
			return null;
		}
		finally
		{    		
			long diff = t2-t1;
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			String diffst = format.format(new Date(diff));
			Debug.println(this, Debug.DEBUG, "File load from DB in:"+diffst);
		}
	}

	
	public void deleteFile(String fileBaseId)
	{
		if(fileBaseId == null) return;
		
		DBFileBase fbmask = new DBFileBase();
		fbmask.setFileBaseId(fileBaseId);
		DBFileBase fb = DBFileBase.loadByKey(fbmask);
		if(fb == null) return;
		
		fb.delete();
		Engine.delete(fileBaseId);
	}

	
	public String processFile(String filename, InputStream input,
			Date expiryDate, boolean index, String application)
	{
		long t1=0;
		long t2=0;
		String fileBaseId = null;
    	
    	try
    	{	
    		t1 = System.currentTimeMillis();
    		    		
    		DBFileBase fb = new DBFileBase();
    		fb.setFilename(filename);
    		fb.setApplication(application);
    		fb.setDate(SQLTime.getSQLTime(System.currentTimeMillis()));
    		if(expiryDate != null)
    			fb.setExpired(SQLTime.getSQLTime(expiryDate.getTime()));
    		fb.store();
    		
    		fileBaseId = fb.getFileBaseId();

    		// Releasing memory
    		fb.clear();
    		fb = null;

    		
    		File target = new File(FILES+fileBaseId);
    		FileOutputStream fos = new FileOutputStream(target);
    		myDes.encrypt(input, fos);
    		fos.flush();
    		fos.close();
    		input.close();
    		

            if(index)
                Engine.index(fileBaseId, application); // indexing the file
            
            t2 = System.currentTimeMillis();
            
            return fileBaseId;
    	}
    	catch(Exception e)
    	{
    		Debug.println(this, Debug.ERROR, e);
    		if(fileBaseId != null) deleteFile(fileBaseId);
    		
    		return null;
    	}
    	catch(OutOfMemoryError oe)
    	{
    		Debug.println(this, Debug.ERROR, oe);
    		if(fileBaseId != null) deleteFile(fileBaseId);
    		
    		return null;
    	}
    	finally
    	{
			long diff = t2-t1;
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
			format.setTimeZone(TimeZone.getTimeZone("GMT"));
			String diffst = format.format(new Date(diff));
			Debug.println(this, Debug.DEBUG, "File stored in:"+diffst);
    	}
	}

}
