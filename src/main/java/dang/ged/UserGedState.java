package dang.ged;

import java.util.ArrayList;

import openplatform.database.Database;
import openplatform.database.ParamValuePair;
import openplatform.database.dbean.DBGedFiles;
import openplatform.tools.Debug;

public class UserGedState extends AdminGedState 
{
	protected String userId; // current userId

	
	
	/**
	 * 
	 * return an arraylist of GedStateFiles
	 * (template information are inside)
	 * 
	 * @return
	 */
	public ArrayList getSearchResult()
	{
		ArrayList array = new ArrayList();
		
		try
		{
			if(userId == null) return array;

			Database db = Database.getInstance();
			
			String query = db.getSpecificSQL("UserGedState");
			ArrayList param = new ArrayList();
			param.add(userId);
			param.add(userId);
			
			ArrayList res = db.loadSQL(query, param);
			
			DBGedFiles gfmask = new DBGedFiles();
			//ArrayList res = db.loadSQL(sb);
			
			if(res == null) return array;
			
			int len = res.size();
			for(int i=0; i<len; i++)
			{
				gfmask.clear();
				
				ParamValuePair pvp = (ParamValuePair)res.get(i);
				String id = pvp.get("gedfilesid");
				gfmask.setId(id);
				DBGedFiles gf = DBGedFiles.loadByKey(gfmask);
				GedStateFiles gsf = new GedStateFiles(gf);
				
				// Add to the result
				array.add(gsf);
			}
		
		}
		catch(Exception e)
		{
			Debug.println(this, Debug.ERROR, e);
		}
		
		return array; 
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
		UserGedState ugs = new UserGedState();
		ugs.setUserId("3");		
		ArrayList al = ugs.getSearchResult();
		int len = al.size();
		for(int i=0; i<len; i++)
		{
			GedStateFiles gsf = (GedStateFiles)al.get(i);
			System.out.println(gsf.getName()+","+gsf.getFileBaseId());
		}
	}
	*/
}
