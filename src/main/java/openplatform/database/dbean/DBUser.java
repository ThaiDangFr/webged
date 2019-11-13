package openplatform.database.dbean;
import openplatform.tools.*;
import openplatform.database.*;
import java.util.*;

/**
 * Class DBUser
 *
 *@author Thai DANG<BR>
 * Source code is copyright
 **/
public class DBUser
{
    protected boolean inError = false;
    protected String userId;
    protected String login;
    protected String password;
    protected String firstName;
    protected String lastName;
    protected String mail;
    protected String officePhone;
    protected String mobilePhone;
    protected String icq;
    protected String jabber;
    protected String msn;
    protected String expiryTime;
    protected String fwdMsgToMail;
    protected String cpMsgToSMS;

    public static String USERID = "userId";
    public static String LOGIN = "login";
    public static String PASSWORD = "password";
    public static String FIRSTNAME = "firstName";
    public static String LASTNAME = "lastName";
    public static String MAIL = "mail";
    public static String OFFICEPHONE = "officePhone";
    public static String MOBILEPHONE = "mobilePhone";
    public static String ICQ = "icq";
    public static String JABBER = "jabber";
    public static String MSN = "msn";
    public static String EXPIRYTIME = "expiryTime";
    public static String FWDMSGTOMAIL = "fwdMsgToMail";
    public static String CPMSGTOSMS = "cpMsgToSMS";
    public static String TABLE = "User";

    private static Database db = Database.getInstance();


    public void clear()
    {
        userId = null;
        login = null;
        password = null;
        firstName = null;
        lastName = null;
        mail = null;
        officePhone = null;
        mobilePhone = null;
        icq = null;
        jabber = null;
        msn = null;
        expiryTime = null;
        fwdMsgToMail = null;
        cpMsgToSMS = null;
    }

    public boolean isInError()
    {
        return inError;
    }

    public boolean equals(Object obj)
    {
        if(this == obj) return true;
        if((obj == null) || (obj.getClass() != this.getClass())) return false;
        DBUser a = (DBUser)obj;
        return (userId==null?a.getUserId()==null:userId.equals(a.getUserId())) && (login==null?a.getLogin()==null:login.equals(a.getLogin())) && (password==null?a.getPassword()==null:password.equals(a.getPassword())) && (firstName==null?a.getFirstName()==null:firstName.equals(a.getFirstName())) && (lastName==null?a.getLastName()==null:lastName.equals(a.getLastName())) && (mail==null?a.getMail()==null:mail.equals(a.getMail())) && (officePhone==null?a.getOfficePhone()==null:officePhone.equals(a.getOfficePhone())) && (mobilePhone==null?a.getMobilePhone()==null:mobilePhone.equals(a.getMobilePhone())) && (icq==null?a.getIcq()==null:icq.equals(a.getIcq())) && (jabber==null?a.getJabber()==null:jabber.equals(a.getJabber())) && (msn==null?a.getMsn()==null:msn.equals(a.getMsn())) && (expiryTime==null?a.getExpiryTime()==null:expiryTime.equals(a.getExpiryTime())) && (fwdMsgToMail==null?a.getFwdMsgToMail()==null:fwdMsgToMail.equals(a.getFwdMsgToMail())) && (cpMsgToSMS==null?a.getCpMsgToSMS()==null:cpMsgToSMS.equals(a.getCpMsgToSMS()));    }

    public int hashCode()
    {
        return (userId!=null?userId.hashCode():0) + (login!=null?login.hashCode():0) + (password!=null?password.hashCode():0) + (firstName!=null?firstName.hashCode():0) + (lastName!=null?lastName.hashCode():0) + (mail!=null?mail.hashCode():0) + (officePhone!=null?officePhone.hashCode():0) + (mobilePhone!=null?mobilePhone.hashCode():0) + (icq!=null?icq.hashCode():0) + (jabber!=null?jabber.hashCode():0) + (msn!=null?msn.hashCode():0) + (expiryTime!=null?expiryTime.hashCode():0) + (fwdMsgToMail!=null?fwdMsgToMail.hashCode():0) + (cpMsgToSMS!=null?cpMsgToSMS.hashCode():0);
    }

    public String toString()
    {
        return "userId="+userId+"|"+"login="+login+"|"+"password="+password+"|"+"firstName="+firstName+"|"+"lastName="+lastName+"|"+"mail="+mail+"|"+"officePhone="+officePhone+"|"+"mobilePhone="+mobilePhone+"|"+"icq="+icq+"|"+"jabber="+jabber+"|"+"msn="+msn+"|"+"expiryTime="+expiryTime+"|"+"fwdMsgToMail="+fwdMsgToMail+"|"+"cpMsgToSMS="+cpMsgToSMS;
    }

    public void refresh()
    {
        if(userId != null)
        {
            DBUser mask = new DBUser();
            mask.setUserId(userId);
            DBUser var = DBUser.loadByKey(mask);
            if(var != null)
            {
                userId = var.getUserId();
                login = var.getLogin();
                password = var.getPassword();
                firstName = var.getFirstName();
                lastName = var.getLastName();
                mail = var.getMail();
                officePhone = var.getOfficePhone();
                mobilePhone = var.getMobilePhone();
                icq = var.getIcq();
                jabber = var.getJabber();
                msn = var.getMsn();
                expiryTime = var.getExpiryTime();
                fwdMsgToMail = var.getFwdMsgToMail();
                cpMsgToSMS = var.getCpMsgToSMS();
            }
        }
    }

    public void initBean(DBUser db)
    {
        this.userId = db.getUserId();
        this.login = db.getLogin();
        this.password = db.getPassword();
        this.firstName = db.getFirstName();
        this.lastName = db.getLastName();
        this.mail = db.getMail();
        this.officePhone = db.getOfficePhone();
        this.mobilePhone = db.getMobilePhone();
        this.icq = db.getIcq();
        this.jabber = db.getJabber();
        this.msn = db.getMsn();
        this.expiryTime = db.getExpiryTime();
        this.fwdMsgToMail = db.getFwdMsgToMail();
        this.cpMsgToSMS = db.getCpMsgToSMS();
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public String getOfficePhone()
    {
        return officePhone;
    }

    public void setOfficePhone(String officePhone)
    {
        this.officePhone = officePhone;
    }

    public String getMobilePhone()
    {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    public String getIcq()
    {
        return icq;
    }

    public void setIcq(String icq)
    {
        this.icq = icq;
    }

    public String getJabber()
    {
        return jabber;
    }

    public void setJabber(String jabber)
    {
        this.jabber = jabber;
    }

    public String getMsn()
    {
        return msn;
    }

    public void setMsn(String msn)
    {
        this.msn = msn;
    }

    public String getExpiryTime()
    {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime)
    {
        this.expiryTime = expiryTime;
    }

    public String getFwdMsgToMail()
    {
        return fwdMsgToMail;
    }

    public void setFwdMsgToMail(String fwdMsgToMail)
    {
        this.fwdMsgToMail = fwdMsgToMail;
    }

    public String getCpMsgToSMS()
    {
        return cpMsgToSMS;
    }

    public void setCpMsgToSMS(String cpMsgToSMS)
    {
        this.cpMsgToSMS = cpMsgToSMS;
    }

    /**
     * @return a DBUser table or null if nothing found or if an error occured
     **/
    public static DBUser[] load(DBUser mask)
    {
        return load(null, mask);
    }

    /**
     * @return a DBUser table or null if nothing found or if an error occured
     **/
    public static DBUser[] load(SQLConstraint sqlconstraint, DBUser mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getLogin() != null) filter.set(LOGIN, mask.getLogin());
            if(mask.getPassword() != null) filter.set(PASSWORD, mask.getPassword());
            if(mask.getFirstName() != null) filter.set(FIRSTNAME, mask.getFirstName());
            if(mask.getLastName() != null) filter.set(LASTNAME, mask.getLastName());
            if(mask.getMail() != null) filter.set(MAIL, mask.getMail());
            if(mask.getOfficePhone() != null) filter.set(OFFICEPHONE, mask.getOfficePhone());
            if(mask.getMobilePhone() != null) filter.set(MOBILEPHONE, mask.getMobilePhone());
            if(mask.getIcq() != null) filter.set(ICQ, mask.getIcq());
            if(mask.getJabber() != null) filter.set(JABBER, mask.getJabber());
            if(mask.getMsn() != null) filter.set(MSN, mask.getMsn());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getFwdMsgToMail() != null) filter.set(FWDMSGTOMAIL, mask.getFwdMsgToMail());
            if(mask.getCpMsgToSMS() != null) filter.set(CPMSGTOSMS, mask.getCpMsgToSMS());
        }

        try
        {
            ParamValuePair[] pvp = db.select(TABLE, sqlconstraint, new String[]{USERID, LOGIN, PASSWORD, FIRSTNAME, LASTNAME, MAIL, OFFICEPHONE, MOBILEPHONE, ICQ, JABBER, MSN, EXPIRYTIME, FWDMSGTOMAIL, CPMSGTOSMS}, filter);
            if(pvp == null) return null;
            else
            {
                DBUser[] result = new DBUser[pvp.length];
                for(int i=0; i<result.length; i++)
                {
                    result[i] = new DBUser();
                    result[i].setUserId(pvp[i].get(USERID));
                    result[i].setLogin(pvp[i].get(LOGIN));
                    result[i].setPassword(pvp[i].get(PASSWORD));
                    result[i].setFirstName(pvp[i].get(FIRSTNAME));
                    result[i].setLastName(pvp[i].get(LASTNAME));
                    result[i].setMail(pvp[i].get(MAIL));
                    result[i].setOfficePhone(pvp[i].get(OFFICEPHONE));
                    result[i].setMobilePhone(pvp[i].get(MOBILEPHONE));
                    result[i].setIcq(pvp[i].get(ICQ));
                    result[i].setJabber(pvp[i].get(JABBER));
                    result[i].setMsn(pvp[i].get(MSN));
                    result[i].setExpiryTime(pvp[i].get(EXPIRYTIME));
                    result[i].setFwdMsgToMail(pvp[i].get(FWDMSGTOMAIL));
                    result[i].setCpMsgToSMS(pvp[i].get(CPMSGTOSMS));
                }
                return result;
            }
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
            return null;
        }
    }

    /**
     * @return a DBUser object or null if nothing found or if an error occured
     **/
    public static DBUser loadByKey(DBUser mask)
    {
        DBUser[] res = DBUser.load(mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * @return a DBUser object or null if nothing found or if an error occured
     **/
    public static DBUser loadByKey(DBUser mask, SQLConstraint sqlconstraint)
    {
        DBUser[] res = DBUser.load(sqlconstraint, mask);
        if(res != null && res.length == 1) return res[0];
        else return null;
    }

    /**
     * Store the object in database
     **/
    public void store()
    {
        inError = false;
        ParamValuePair toinsert = new ParamValuePair();
        toinsert.set(USERID,userId);
        toinsert.set(LOGIN,login);
        toinsert.set(PASSWORD,password);
        toinsert.set(FIRSTNAME,firstName);
        toinsert.set(LASTNAME,lastName);
        toinsert.set(MAIL,mail);
        toinsert.set(OFFICEPHONE,officePhone);
        toinsert.set(MOBILEPHONE,mobilePhone);
        toinsert.set(ICQ,icq);
        toinsert.set(JABBER,jabber);
        toinsert.set(MSN,msn);
        toinsert.set(EXPIRYTIME,expiryTime);
        toinsert.set(FWDMSGTOMAIL,fwdMsgToMail);
        toinsert.set(CPMSGTOSMS,cpMsgToSMS);

        // Store a new entry
        if(userId == null)
        {
            try
            {
                userId = db.insert(TABLE, toinsert);
            }
            catch(Exception e)
            {
                inError=true;
                Debug.println(this, Debug.ERROR, e);
            }
        }
        // update entry
        else
        {
            ParamValuePair filter = new ParamValuePair();
            filter.set(USERID, userId);

            try
            {
                db.update(TABLE, filter, toinsert);
            }
            catch(Exception e)
            {
                inError=true;
                Debug.println(this, Debug.ERROR, e);
            }
        }
    }

    /**
     * Delete the object in database (if primary key is not null)
     **/
    public void delete()
    {
        if(userId != null)
        {
            inError = false;
            ParamValuePair filter = new ParamValuePair();
            filter.set(USERID, userId);

            try
            {
                db.delete(TABLE, filter);
            }
            catch(Exception e)
            {
                inError=true;
                Debug.println(this, Debug.ERROR, e);
            }
        }
    }

    /**
     * Delete many files
     **/
    public static void delete(DBUser mask)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getLogin() != null) filter.set(LOGIN, mask.getLogin());
            if(mask.getPassword() != null) filter.set(PASSWORD, mask.getPassword());
            if(mask.getFirstName() != null) filter.set(FIRSTNAME, mask.getFirstName());
            if(mask.getLastName() != null) filter.set(LASTNAME, mask.getLastName());
            if(mask.getMail() != null) filter.set(MAIL, mask.getMail());
            if(mask.getOfficePhone() != null) filter.set(OFFICEPHONE, mask.getOfficePhone());
            if(mask.getMobilePhone() != null) filter.set(MOBILEPHONE, mask.getMobilePhone());
            if(mask.getIcq() != null) filter.set(ICQ, mask.getIcq());
            if(mask.getJabber() != null) filter.set(JABBER, mask.getJabber());
            if(mask.getMsn() != null) filter.set(MSN, mask.getMsn());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getFwdMsgToMail() != null) filter.set(FWDMSGTOMAIL, mask.getFwdMsgToMail());
            if(mask.getCpMsgToSMS() != null) filter.set(CPMSGTOSMS, mask.getCpMsgToSMS());
        }

        try
        {
            db.delete(TABLE, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }

    /**
     * Delete many files
     **/
    public static int count(DBUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getLogin() != null) filter.set(LOGIN, mask.getLogin());
            if(mask.getPassword() != null) filter.set(PASSWORD, mask.getPassword());
            if(mask.getFirstName() != null) filter.set(FIRSTNAME, mask.getFirstName());
            if(mask.getLastName() != null) filter.set(LASTNAME, mask.getLastName());
            if(mask.getMail() != null) filter.set(MAIL, mask.getMail());
            if(mask.getOfficePhone() != null) filter.set(OFFICEPHONE, mask.getOfficePhone());
            if(mask.getMobilePhone() != null) filter.set(MOBILEPHONE, mask.getMobilePhone());
            if(mask.getIcq() != null) filter.set(ICQ, mask.getIcq());
            if(mask.getJabber() != null) filter.set(JABBER, mask.getJabber());
            if(mask.getMsn() != null) filter.set(MSN, mask.getMsn());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getFwdMsgToMail() != null) filter.set(FWDMSGTOMAIL, mask.getFwdMsgToMail());
            if(mask.getCpMsgToSMS() != null) filter.set(CPMSGTOSMS, mask.getCpMsgToSMS());
        }

        try
        {
            return db.count(TABLE, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return an average
     **/
    public static double avg(String field, DBUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getLogin() != null) filter.set(LOGIN, mask.getLogin());
            if(mask.getPassword() != null) filter.set(PASSWORD, mask.getPassword());
            if(mask.getFirstName() != null) filter.set(FIRSTNAME, mask.getFirstName());
            if(mask.getLastName() != null) filter.set(LASTNAME, mask.getLastName());
            if(mask.getMail() != null) filter.set(MAIL, mask.getMail());
            if(mask.getOfficePhone() != null) filter.set(OFFICEPHONE, mask.getOfficePhone());
            if(mask.getMobilePhone() != null) filter.set(MOBILEPHONE, mask.getMobilePhone());
            if(mask.getIcq() != null) filter.set(ICQ, mask.getIcq());
            if(mask.getJabber() != null) filter.set(JABBER, mask.getJabber());
            if(mask.getMsn() != null) filter.set(MSN, mask.getMsn());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getFwdMsgToMail() != null) filter.set(FWDMSGTOMAIL, mask.getFwdMsgToMail());
            if(mask.getCpMsgToSMS() != null) filter.set(CPMSGTOSMS, mask.getCpMsgToSMS());
        }

        try
        {
            return db.average(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return a minimum
     **/
    public static double min(String field, DBUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getLogin() != null) filter.set(LOGIN, mask.getLogin());
            if(mask.getPassword() != null) filter.set(PASSWORD, mask.getPassword());
            if(mask.getFirstName() != null) filter.set(FIRSTNAME, mask.getFirstName());
            if(mask.getLastName() != null) filter.set(LASTNAME, mask.getLastName());
            if(mask.getMail() != null) filter.set(MAIL, mask.getMail());
            if(mask.getOfficePhone() != null) filter.set(OFFICEPHONE, mask.getOfficePhone());
            if(mask.getMobilePhone() != null) filter.set(MOBILEPHONE, mask.getMobilePhone());
            if(mask.getIcq() != null) filter.set(ICQ, mask.getIcq());
            if(mask.getJabber() != null) filter.set(JABBER, mask.getJabber());
            if(mask.getMsn() != null) filter.set(MSN, mask.getMsn());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getFwdMsgToMail() != null) filter.set(FWDMSGTOMAIL, mask.getFwdMsgToMail());
            if(mask.getCpMsgToSMS() != null) filter.set(CPMSGTOSMS, mask.getCpMsgToSMS());
        }

        try
        {
            return db.min(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return a max
     **/
    public static double max(String field, DBUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getLogin() != null) filter.set(LOGIN, mask.getLogin());
            if(mask.getPassword() != null) filter.set(PASSWORD, mask.getPassword());
            if(mask.getFirstName() != null) filter.set(FIRSTNAME, mask.getFirstName());
            if(mask.getLastName() != null) filter.set(LASTNAME, mask.getLastName());
            if(mask.getMail() != null) filter.set(MAIL, mask.getMail());
            if(mask.getOfficePhone() != null) filter.set(OFFICEPHONE, mask.getOfficePhone());
            if(mask.getMobilePhone() != null) filter.set(MOBILEPHONE, mask.getMobilePhone());
            if(mask.getIcq() != null) filter.set(ICQ, mask.getIcq());
            if(mask.getJabber() != null) filter.set(JABBER, mask.getJabber());
            if(mask.getMsn() != null) filter.set(MSN, mask.getMsn());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getFwdMsgToMail() != null) filter.set(FWDMSGTOMAIL, mask.getFwdMsgToMail());
            if(mask.getCpMsgToSMS() != null) filter.set(CPMSGTOSMS, mask.getCpMsgToSMS());
        }

        try
        {
            return db.max(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return a standard deviation (french ecart type)
     **/
    public static double std(String field, DBUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getLogin() != null) filter.set(LOGIN, mask.getLogin());
            if(mask.getPassword() != null) filter.set(PASSWORD, mask.getPassword());
            if(mask.getFirstName() != null) filter.set(FIRSTNAME, mask.getFirstName());
            if(mask.getLastName() != null) filter.set(LASTNAME, mask.getLastName());
            if(mask.getMail() != null) filter.set(MAIL, mask.getMail());
            if(mask.getOfficePhone() != null) filter.set(OFFICEPHONE, mask.getOfficePhone());
            if(mask.getMobilePhone() != null) filter.set(MOBILEPHONE, mask.getMobilePhone());
            if(mask.getIcq() != null) filter.set(ICQ, mask.getIcq());
            if(mask.getJabber() != null) filter.set(JABBER, mask.getJabber());
            if(mask.getMsn() != null) filter.set(MSN, mask.getMsn());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getFwdMsgToMail() != null) filter.set(FWDMSGTOMAIL, mask.getFwdMsgToMail());
            if(mask.getCpMsgToSMS() != null) filter.set(CPMSGTOSMS, mask.getCpMsgToSMS());
        }

        try
        {
            return db.std(TABLE, field, sqlconst, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return 0;
    }

    /**
     * Return distinct entries)
     **/
    public static String[] distinct(String field, DBUser mask, SQLConstraint sqlconst)
    {
        ParamValuePair filter = new ParamValuePair();

        if(mask != null)
        {
            if(mask.getUserId() != null) filter.set(USERID, mask.getUserId());
            if(mask.getLogin() != null) filter.set(LOGIN, mask.getLogin());
            if(mask.getPassword() != null) filter.set(PASSWORD, mask.getPassword());
            if(mask.getFirstName() != null) filter.set(FIRSTNAME, mask.getFirstName());
            if(mask.getLastName() != null) filter.set(LASTNAME, mask.getLastName());
            if(mask.getMail() != null) filter.set(MAIL, mask.getMail());
            if(mask.getOfficePhone() != null) filter.set(OFFICEPHONE, mask.getOfficePhone());
            if(mask.getMobilePhone() != null) filter.set(MOBILEPHONE, mask.getMobilePhone());
            if(mask.getIcq() != null) filter.set(ICQ, mask.getIcq());
            if(mask.getJabber() != null) filter.set(JABBER, mask.getJabber());
            if(mask.getMsn() != null) filter.set(MSN, mask.getMsn());
            if(mask.getExpiryTime() != null) filter.set(EXPIRYTIME, mask.getExpiryTime());
            if(mask.getFwdMsgToMail() != null) filter.set(FWDMSGTOMAIL, mask.getFwdMsgToMail());
            if(mask.getCpMsgToSMS() != null) filter.set(CPMSGTOSMS, mask.getCpMsgToSMS());
        }

        try
        {
            return db.selectDistinct(TABLE, sqlconst, field, filter);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }

        return null;
    }
}
