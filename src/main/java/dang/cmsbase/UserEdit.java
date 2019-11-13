package dang.cmsbase;

import dang.cms.CmsLanguage;
import openplatform.database.dbean.*;
import openplatform.regexp.*;
import openplatform.tools.Debug;

/**
 * Class UserEdit
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class UserEdit
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private DBUser user;
    private DBUser umask = new DBUser();

    // GET BEG //
    public String getLogin() { return user.getLogin(); }
    public String getPassword() { return user.getPassword(); }
    public String getFirstName() { return user.getFirstName(); }
    public String getLastName() { return user.getLastName(); }
    public String getMail() { return user.getMail(); }
    public String getOfficePhone() { return user.getOfficePhone(); }
    public String getMobilePhone() { return user.getMobilePhone(); }
    public String getIcq() { return user.getIcq(); }
    public String getJabber() { return user.getJabber(); }
    public String getMsn() { return user.getMsn(); }

    public boolean getFwMsgToMail()
    {
        if("1".equals(user.getFwdMsgToMail()))
            return true;
        else
            return false;
    }

    public boolean getCpMsgToSMS()
    {
        if("1".equals(user.getCpMsgToSMS()))
            return true;
        else
            return false;
    }

    public String doSave()
        throws Exception
    {
        Regexp phone = new Regexp("(\\d*)");
        if(!phone.matches(user.getOfficePhone())
           || !phone.matches(user.getMobilePhone()))
            throw new Exception(cmsLang.translate("phone.int.format"));

        user.store();
        return cmsLang.translate("ok.saved");
    }


    // GET END //


    // SET BEG //
    public void setUserId(String id)
    {
    	Debug.println(this,Debug.DEBUG, "UserId="+id);
    	if(id == null)
    	{
    		user = new DBUser();
    	}
    	else
    	{
    		umask.setUserId(id);
    		user = DBUser.loadByKey(umask);
    	}
    }
    
    public void setLogin(String s) { user.setLogin(s); }
    public void setPassword(String p) { if(p!=null && !p.trim().equals("")) user.setPassword(p); }
    public void setFirstName(String s) { user.setFirstName(s); }
    public void setLastName(String s) { user.setLastName(s); }
    public void setMail(String s) { user.setMail(s); }
    public void setOfficePhone(String s) { user.setOfficePhone(s); }
    public void setMobilePhone(String s) { user.setMobilePhone(s); }
    public void setIcq(String s) { user.setIcq(s); }
    public void setJabber(String s) { user.setJabber(s); }
    public void setMsn(String s) { user.setMsn(s); }

    public void setFwMsgToMail(boolean b)
    {
        if(b)
            user.setFwdMsgToMail("1"); 
        else
            user.setFwdMsgToMail("0"); 
    }


    public void setCpMsgToSMS(boolean b)
    {
        if(b)
            user.setCpMsgToSMS("1"); 
        else
            user.setCpMsgToSMS("0"); 
    }
    // SET END //
}
