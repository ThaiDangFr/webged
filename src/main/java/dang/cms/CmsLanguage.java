package dang.cms;

import java.util.Locale;
import java.util.ResourceBundle;

import openplatform.database.dbean.DBCmsPreferences;
import openplatform.tools.Debug;

public class CmsLanguage {

	private ResourceBundle resource;
	
	public CmsLanguage()
	{
		DBCmsPreferences pref = DBCmsPreferences.loadByKey(null);
		if(pref != null)
		{
			resource = ResourceBundle.getBundle("common", new Locale(pref.getCmsLang()));
		}
	}
	
	public String translate(String word)
	{
		try
		{
			if(resource != null)
				return resource.getString(word);
		}
		catch(Exception e) { Debug.println(this, Debug.ERROR, e); }
				
		
		return "?"+word+"?";
	}
	
	/*
	public static void main(String[] args)
	{
		CmsLanguage cl = new CmsLanguage();
		System.out.println(cl.translate("user.connects"));
	}
	*/
}
