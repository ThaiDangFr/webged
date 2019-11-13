package dang.applimgt;

import openplatform.database.dbean.*;
import java.util.*;
import openplatform.tools.*;
import java.sql.*;
import java.text.*;

import dang.cms.CmsLanguage;

/**
 * Class AdminUser
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminUser
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private final static String DEFAULTGROUPID = "2";

    private DBUser umask = new DBUser();

    private String userId;
    private String login;
    private String password;
    private String email;
    private String expiryTime;

    private static NumberFormat nf = NumberFormat.getInstance();
    
    static
    {
        nf.setMaximumFractionDigits(2);
    }


    public AdminUser() throws Exception { doReset(); }


    // DO BEG

    public String doStore()
        throws Exception
    {
        if(login == null || password == null
           || login.trim().equals("") || password.trim().equals(""))
            throw new Exception(
            		cmsLang.translate("login.or.password") +
            		" " +
            		cmsLang.translate("error.empty"));

        //if(email == null || email.trim().equals(""))
        //    throw new Exception("Email "+cmsLang.translate("error.empty"));

        umask.clear();
        umask.setUserId(userId);
        umask.setLogin(login);
        umask.setPassword(password);
        umask.setMail(email);
        umask.setFwdMsgToMail("0");
        umask.setCpMsgToSMS("0");

        if(expiryTime != null && !expiryTime.trim().equals("")
            && !expiryTime.trim().equals("0"))
        {
            double d = Double.parseDouble(expiryTime) * 24 * 60 * 60 * 1000;
            long l = Math.round(d);
            Timestamp ts = new Timestamp(System.currentTimeMillis()+l);
            umask.setExpiryTime(ts.toString());
        }
        else
            umask.setExpiryTime(null);

        umask.store();
        
        if(userId == null) // Put the new user in the DEFAULT GROUP
        {
        	userId = umask.getUserId();

        	DBUserGroups ug = new DBUserGroups();
        	ug.setUserId(userId);
        	ug.setGroupId(DEFAULTGROUPID);
        	ug.store();        
        }
        
        return login+" "+cmsLang.translate("ok.saved");
    }

    public String doNewUser() throws Exception { return doReset(); }

    public String doReset()
        throws Exception
    {
        login = null;
        password = null;
        email = null;
        expiryTime = null;
        userId = null;

        return cmsLang.translate("form.reset");
    }

    public String doDelete()
        throws Exception
    {
        if(userId == null) throw new Exception(cmsLang.translate("error.found"));

        umask.clear();
        umask.setUserId(userId);
        DBUser.delete(umask);

        doReset();

        return cmsLang.translate("item.deleted");
    }
    // DO END








    // GET BEG


    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getExpiryTime() { return expiryTime; }
    public String getUserId() { return userId; }



    public HashMap getUserListOptions()
    {
        HashMap hm = new OrdHashMap();
        DBUser[] ul = DBUser.load(null);
        if(ul == null) return hm;
        else
        {
            int len = ul.length;
            for(int i=0; i<len; i++)
            {
                hm.put(ul[i].getUserId(), ul[i].getLogin());
            }
        }

        return hm;
    }
    // GET END



    // SET BEG
    public void setLogin(String s) { this.login = s; }
    public void setPassword(String s) { this.password = s; }
    public void setEmail(String s) { this.email = s; }
    public void setExpiryTime(String s) { this.expiryTime = s; }
 
    public void setUserId(String userId)
        throws Exception
    {
        umask.clear();
        umask.setUserId(userId);
        DBUser user = DBUser.loadByKey(umask);
        if(user == null) doReset();
        else
        {
            login = user.getLogin();
            password = user.getPassword();
            email = user.getMail();

            String date = user.getExpiryTime();
            
            if(date != null)
            {
                Timestamp ts = Timestamp.valueOf(date);
                long l = ts.getTime() - System.currentTimeMillis();
                double days = (double)l/(double)(24 * 60 * 60 * 1000);
                expiryTime = nf.format(days);
            }
            else expiryTime = null;

            this.userId = userId;
        }
    }
    // SET END
}
