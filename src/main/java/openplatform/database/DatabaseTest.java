package openplatform.database;

import openplatform.database.dbean.DBCmsPreferences;
import openplatform.file.Repository;

public class DatabaseTest {

	
	public static void main(String[] args)
	throws Exception
	{
		Repository.getStream("4");
		
		/*
		DBUser mask = new DBUser();
		mask.setLogin("admin");
		DBUser u = DBUser.loadByKey(mask);
		System.out.println(u);
		
		u.setFirstName("administrateur");
		u.store();
		System.out.println(u);
		
		DBUser newu = new DBUser();
		newu.setLogin("truc");
		newu.setPassword("truc");
		newu.setFwdMsgToMail("0");
		newu.setCpMsgToSMS("0");
		newu.store();
		System.out.println(newu);
		*/
		
		/*
		FileInputStream fis = new FileInputStream("/home/thai/Liste-mariage.ods");
		DBFileBase fb = new DBFileBase();
		fb.setFilename("test");
		fb.setDate(SQLTime.getSQLTime(System.currentTimeMillis()));
		fb.setData(fis);
		fb.setSecurity("ABC");
		fb.store();
		System.out.println(fb);
		*/
		
		
		/*
		DBFileBase mask = new DBFileBase();
		mask.setFilename("test");
		DBFileBase f = DBFileBase.loadByKey(mask);
		System.out.println(f);
		InputStream in = f.getData();
		byte[] b = StreamConverter.inputStreamToBytes(in);
		StreamConverter.bytesToFile(b, new File("/home/thai/out.ods"));
		*/
		
		
		/*
		InputStream in = f.getData();
		byte[] b = StreamConverter.inputStreamToBytes(in);
		System.out.println(new String(b));
		*/	
		
		DBCmsPreferences pref = DBCmsPreferences.loadByKey(null);
		System.out.println(pref);
		
		if(pref.getLogoId()==null) System.out.println("logoid est NULL");
		
	}
	
}
