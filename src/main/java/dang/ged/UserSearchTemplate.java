package dang.ged;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import openplatform.database.Database;
import openplatform.database.ParamValuePair;
import openplatform.database.SQLConstraint;
import openplatform.database.dbean.DBGedDirectory;
import openplatform.database.dbean.DBGedDirectoryRights;
import openplatform.database.dbean.DBGedFilesVersion;
import openplatform.database.dbean.DBGedTemplate;
import openplatform.database.dbean.DBGedTemplateItem;
import openplatform.database.dbean.DBUser;
import openplatform.database.dbean.DBUserGroups;
import openplatform.index.Engine;
import openplatform.tools.Debug;
import openplatform.tools.OrdHashMap;

public class UserSearchTemplate {

	public final int MAX_RESULT_FULLTEXT = 1000;

	private DBGedTemplate tmask = new DBGedTemplate();
	private DBUserGroups ugmask = new DBUserGroups();
	private DBGedDirectoryRights drmask = new DBGedDirectoryRights();
	private DBGedDirectory dmask = new DBGedDirectory();
	private DBGedFilesVersion gfvmask = new DBGedFilesVersion();

	//private String templateItemId;
	private String userId;
	private ArrayList resultVersion = new ArrayList();


	// CRITERIA
	//private String template;
	private String filename;
	private String directoryId;
	private String authorId;
	private String reference;
	private String text;

	
	private String templateItemId1;
	private String template1;
	private String templateItemId2;
	private String template2;
	private String templateItemId3;
	private String template3;
	private String templateItemId4;
	private String template4;
	private String templateItemId5;
	private String template5;
	
	
	
	
	public String doInit()
	throws Exception
	{
		//templateItemId = null;
		//template = null;
		filename = null;
		directoryId = null;
		authorId = null;
		reference = null;
		text = null;
		
		templateItemId1 = null;
		template1 = null;
		templateItemId2 = null;
		template2 = null;
		templateItemId3 = null;
		template3 = null;
		templateItemId4 = null;
		template4 = null;
		templateItemId5 = null;
		template5 = null;

		resultVersion.clear();
		return null;
	}


	/**
	 * return an array of fileBaseId
	 * @param txt
	 * @return
	 */
	private ArrayList fullTextSearch(String txt)
	{
		ArrayList ids = Engine.search(txt,MAX_RESULT_FULLTEXT, Engine.APPLI_GED);
		return ids;
	}



	public String doSearch()
	throws Exception
	{
		resultVersion.clear();

		
		// #BUG 080307.1 if there is no template, the search engine does not work
		//if(templateItemId!=null && templateItemId.trim().equals("")) 	templateItemId=null;
		//if(template!=null 		&& template.trim().equals("")) 			template=null;
		if(filename!=null 		&& filename.trim().equals("")) 			filename=null;
		if(directoryId!=null 	&& directoryId.trim().equals("")) 		directoryId=null;
		if(authorId!=null 		&& authorId.trim().equals("")) 			authorId=null;
		if(reference!=null 		&& reference.trim().equals("")) 		reference=null;
		if(text!=null 			&& text.trim().equals("")) 				text=null;
		
		if(templateItemId1!=null && templateItemId1.trim().equals("")) 	templateItemId1=null;
		if(template1!=null 		&& template1.trim().equals("")) 		template1=null;
		if(templateItemId2!=null && templateItemId2.trim().equals("")) 	templateItemId2=null;
		if(template2!=null 		&& template2.trim().equals("")) 		template2=null;
		if(templateItemId3!=null && templateItemId3.trim().equals("")) 	templateItemId3=null;
		if(template3!=null 		&& template3.trim().equals("")) 		template3=null;
		if(templateItemId4!=null && templateItemId4.trim().equals("")) 	templateItemId4=null;
		if(template4!=null 		&& template4.trim().equals("")) 		template4=null;
		if(templateItemId5!=null && templateItemId5.trim().equals("")) 	templateItemId5=null;
		if(template5!=null 		&& template5.trim().equals("")) 		template5=null;
		
		
		/*
		System.out.println(templateItemId+" "+template+" "+filename
				+" "+directoryId+" "+authorId+" "+reference
				+" "+text);
				*/
		
		// if all fields are blank do not search
		if(		template1==null
				&& template2==null
				&& template3==null
				&& template4==null
				&& template5==null
				&& filename==null 
				&& directoryId==null 
				&& authorId==null
				&& reference==null 
				&& text==null) 
			return null;
		
		
		ArrayList fullTextList = null;
		if(text != null)
		{
			fullTextList = fullTextSearch(text);
		}
		
		Database db = Database.getInstance();
		String query = db.getSpecificSQL("UserSearchTemplate");
		ArrayList param = new ArrayList();
		
		//userId
		if(userId!=null && !"1".equals(userId))
		{
			param.add("1");
			param.add(userId);
		}
		else { param.add("0"); param.add("0"); }
		
		//filename
		if(filename!=null)
		{
			param.add("1");
			param.add("%"+filename+"%");
		}
		else { param.add("0"); param.add("0"); }
		
		//directoryId
		if(directoryId!=null)
		{
			param.add("1");
			param.add(directoryId);
		}
		else { param.add("0"); param.add("0");}
		
		//authorId
		if(authorId!=null)
		{
			param.add("1");
			param.add(authorId);
		}
		else { param.add("0"); param.add("0"); }
		
		//reference
		if(reference!=null)
		{
			param.add("1");
			param.add("%"+reference+"%");
		}
		else { param.add("0"); param.add("0"); }
		
		//templateItemId+template
		/*
		if(templateItemId != null && template!=null)
		{
			param.add("1");
			param.add(templateItemId);
			param.add("%"+template+"%");
		}
		else { param.add("0"); param.add("0"); param.add("0"); }
		*/
		if(templateItemId1 != null && template1!=null)
		{
			param.add("1");
			param.add(templateItemId1);
			param.add("%"+template1+"%");
		}
		else { param.add("0"); param.add("0"); param.add("0"); }
		if(templateItemId2 != null && template2!=null)
		{
			param.add("1");
			param.add(templateItemId2);
			param.add("%"+template2+"%");
		}
		else { param.add("0"); param.add("0"); param.add("0"); }
		if(templateItemId3 != null && template3!=null)
		{
			param.add("1");
			param.add(templateItemId3);
			param.add("%"+template3+"%");
		}
		else { param.add("0"); param.add("0"); param.add("0"); }
		if(templateItemId4 != null && template4!=null)
		{
			param.add("1");
			param.add(templateItemId4);
			param.add("%"+template4+"%");
		}
		else { param.add("0"); param.add("0"); param.add("0"); }
		if(templateItemId5 != null && template5!=null)
		{
			param.add("1");
			param.add(templateItemId5);
			param.add("%"+template5+"%");
		}
		else { param.add("0"); param.add("0"); param.add("0"); }
		
		
		
		
		if(Debug.DEBUGON)
		{
			Debug.println(this, Debug.DEBUG, "Request is:"+query+" with: "+param);
			Debug.println(this, Debug.DEBUG, "Fulltext on "+text+" give:"+fullTextList);
		}
		
		ArrayList res = db.loadSQL(query, param);
		
		if(res != null)
		{

			int len = res.size();
			for(int i=0; i<len; i++)
			{
				ParamValuePair pvp = (ParamValuePair)res.get(i);
				String id = pvp.get("gedfilesversionid");

				if(id == null) continue;
				
				gfvmask.clear();
				gfvmask.setId(id);

				DBGedFilesVersion gfv = DBGedFilesVersion.loadByKey(gfvmask);

				if(text!=null)
				{
					if(!fullTextList.contains(gfv.getFileBaseId()))
						continue;
				}

				// Add to the result
				resultVersion.add(new GedFilesVersion(gfv));
			}
		}


		return null;
	}





	public HashMap getTemplateItemOption()
	{
		HashMap map = new OrdHashMap();

		SQLConstraint constr = new SQLConstraint();
		constr.setOrderBy(DBGedTemplateItem.TEMPLATEID);

		DBGedTemplateItem[] ti = DBGedTemplateItem.load(constr, null);
		if(ti == null) return map;

		int len = ti.length;
		for(int i=0; i<len; i++)
		{
			tmask.clear();
			tmask.setId(ti[i].getTemplateId());
			DBGedTemplate t = DBGedTemplate.loadByKey(tmask);

			if(t != null)
				map.put(ti[i].getId(), t.getName()+"/"+ti[i].getFieldname());
		}

		return map;
	}


	public HashMap getAuthorOptions()
	{
		HashMap map = new OrdHashMap();
		map.put("","---");


		DBUser[] u = DBUser.load(null);
		if(u==null) return map;

		int len = u.length;

		for(int i=0; i<len; i++)
		{
			map.put(u[i].getUserId(), u[i].getLogin());
		}

		return map;
	}


	public Map getDirectoryOptions()
	{
		HashMap map = new OrdHashMap();

		map.put("","---");

		if(this.userId == null) return map;

		ugmask.clear();
		ugmask.setUserId(userId);
		DBUserGroups[] ug = DBUserGroups.load(ugmask);
		if(ug == null) return map;

		int len = ug.length;
		for(int i=0; i<len; i++)
		{
			String groupId = ug[i].getGroupId();
			drmask.clear();
			if(!groupId.equals("1"))
			{
				drmask.setGroupId(groupId);
				drmask.setReadRight("1");
			}

			DBGedDirectoryRights[] dr = DBGedDirectoryRights.load(drmask);
			if(dr!=null)
			{
				int lendr = dr.length;
				for(int j=0; j<lendr; j++)
				{
					String dirId = dr[j].getDirectoryId();
					dmask.clear();
					dmask.setId(dirId);
					DBGedDirectory d = DBGedDirectory.loadByKey(dmask);
					//map.put(d.getId(), (GedFiles.pathName(d, new StringBuffer())).toString());
					if(d != null)
						map.put(d.getId(), d.getPath());
				}
			}
		}

		return map;
	}







	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getDirectoryId() {
		return directoryId;
	}
	public void setDirectoryId(String directoryId) {
		this.directoryId = directoryId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	/*
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getTemplateItemId() {
		return templateItemId;
	}
	public void setTemplateItemId(String templateItemId) {
		this.templateItemId = templateItemId;
	}
	*/
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ArrayList getResultVersion() {
		return resultVersion;
	}


	public String getTemplate1() {
		return template1;
	}


	public void setTemplate1(String template1) {
		this.template1 = template1;
	}


	public String getTemplate2() {
		return template2;
	}


	public void setTemplate2(String template2) {
		this.template2 = template2;
	}


	public String getTemplate3() {
		return template3;
	}


	public void setTemplate3(String template3) {
		this.template3 = template3;
	}


	public String getTemplate4() {
		return template4;
	}


	public void setTemplate4(String template4) {
		this.template4 = template4;
	}


	public String getTemplate5() {
		return template5;
	}


	public void setTemplate5(String template5) {
		this.template5 = template5;
	}


	public String getTemplateItemId1() {
		return templateItemId1;
	}


	public void setTemplateItemId1(String templateItemId1) {
		this.templateItemId1 = templateItemId1;
	}


	public String getTemplateItemId2() {
		return templateItemId2;
	}


	public void setTemplateItemId2(String templateItemId2) {
		this.templateItemId2 = templateItemId2;
	}


	public String getTemplateItemId3() {
		return templateItemId3;
	}


	public void setTemplateItemId3(String templateItemId3) {
		this.templateItemId3 = templateItemId3;
	}


	public String getTemplateItemId4() {
		return templateItemId4;
	}


	public void setTemplateItemId4(String templateItemId4) {
		this.templateItemId4 = templateItemId4;
	}


	public String getTemplateItemId5() {
		return templateItemId5;
	}


	public void setTemplateItemId5(String templateItemId5) {
		this.templateItemId5 = templateItemId5;
	}
	
}
