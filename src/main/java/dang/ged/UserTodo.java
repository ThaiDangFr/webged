package dang.ged;

import java.util.ArrayList;

import openplatform.database.Database;
import openplatform.database.ParamValuePair;
import openplatform.database.dbean.DBGedFiles;
import openplatform.tools.Debug;

public class UserTodo
{
	protected String userId;
	
	/**
	 * 
	 * @return the number of expired files
	 */
	public int getCountExpiredGedFiles()
	{
		try
		{
			if(userId == null) return 0;
			
			Database db = Database.getInstance();
			String query = db.getSpecificSQL("CountExpiredGedFiles");
		
			ArrayList param = new ArrayList();
			param.add(userId);
			
			ArrayList res = db.loadSQL(query, param);
			
			if(res == null) return 0;
			
			ParamValuePair pvp = (ParamValuePair)res.get(0);
			String id = pvp.get("COUNT");
			return Integer.parseInt(id);
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
			return 0;
		}
	}
	
	
	/**
	 * 
	 * @return an array of expired GedFiles
	 */
	public ArrayList getExpiredGedFiles()
	{
		ArrayList array = new ArrayList();
		
		try
		{
			if(userId == null) return array;
			
			Database db = Database.getInstance();
			String query = db.getSpecificSQL("ExpiredGedFiles");
			
			ArrayList param = new ArrayList();
			param.add(userId);
			
			ArrayList res = db.loadSQL(query, param);
			
			if(res == null) return array;
			
			DBGedFiles gfmask = new DBGedFiles();
			
			int len = res.size();
			for(int i=0; i<len; i++)
			{
				gfmask.clear();
				
				ParamValuePair pvp = (ParamValuePair)res.get(i);
				String id = pvp.get("gedfilesid");
				gfmask.setId(id);
				DBGedFiles dbgf = DBGedFiles.loadByKey(gfmask);
				
				GedFiles gf = new GedFiles(dbgf, userId);
				
				array.add(gf);
			}
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
		}
		
		return array;
	}

	
	/**
	 * 
	 * @return ArrayList of GedFiles
	 */
	public ArrayList getWorkflowGedFiles()
	{
		ArrayList array = new ArrayList();
		
		try
		{
			if(userId == null) return array;
			
			Database db = Database.getInstance();
			String query = db.getSpecificSQL("WorkflowGedFiles");
			
			ArrayList param = new ArrayList();
			param.add(userId);
			
			ArrayList res = db.loadSQL(query, param);
			
			if(res == null) return array;
			
			DBGedFiles gfmask = new DBGedFiles();
			
			if(res == null) return array;
			
			int len = res.size();
			for(int i=0; i<len; i++)
			{
				gfmask.clear();
				
				ParamValuePair pvp = (ParamValuePair)res.get(i);
				String id = pvp.get("gedfilesid");
				gfmask.setId(id);
				DBGedFiles dbgf = DBGedFiles.loadByKey(gfmask);
				
				GedFiles gf = new GedFiles(dbgf, userId);
				
				array.add(gf);
			}
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
		}
		
		return array;
	}
	
	public int getCountWorkflowGedFiles()
	{	
		try
		{
			if(userId == null) return 0;

			Database db = Database.getInstance();
			
			String query = db.getSpecificSQL("CountWorkflowGedFiles");
			
			ArrayList param = new ArrayList();
			param.add(userId);
			
			ArrayList res = db.loadSQL(query, param);
			
			if(res == null) return 0;
			
			ParamValuePair pvp = (ParamValuePair)res.get(0);
			String id = pvp.get("COUNT");
			return Integer.parseInt(id);
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
			return 0;
		}
	}
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/*
	public static void main(String[] args)
	{
		UserTodo utd = new UserTodo();
		utd.setUserId("1");
		ArrayList al = utd.getExpiredGedFiles();
		for(int i=0; i<al.size(); i++)
			System.out.println(((GedFiles)al.get(i)).getName());
		
		System.out.println(utd.getCountExpiredGedFiles());
		
		al = utd.getWorkflowGedFiles();
		for(int i=0; i<al.size(); i++)
			System.out.println(((GedFiles)al.get(i)).getName());
		
		System.out.println(utd.getCountWorkflowGedFiles());
	}
	*/
}
