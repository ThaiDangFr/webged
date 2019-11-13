package dang.mailarchive;

import javax.mail.internet.*;

import dang.cms.CmsLanguage;

import java.text.*;
import openplatform.database.dbean.*;
import java.util.*;


/**
 * UserSearch.java
 *
 *
 * Created: Thu Oct 27 17:29:49 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class UserSearch 
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private String begin;
    private String end;
    private String fromMail;
    private String toMail;
    private String subject;
    private String text;

    private ArrayList headers; // array of DBMailArchive
    private String userId;

    // DO BEGIN
    public String doSearch()
        throws Exception
    {
        if(userId == null) throw new Exception(cmsLang.translate("error.empty"));

        DBUser umask = new DBUser();
        umask.setUserId(userId);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) throw new Exception(cmsLang.translate("error.empty"));
        

        if(begin!=null && begin.trim().equals("")) begin=null;
        if(end!=null && end.trim().equals("")) end=null;
        if(fromMail!=null && fromMail.trim().equals("")) fromMail=null;
        if(toMail!=null && toMail.trim().equals("")) toMail=null;
        if(subject!=null && subject.trim().equals("")) subject=null;
        if(text!=null && text.trim().equals("")) text=null;

        Date beginDate = null;
        Date endDate = null;
        InternetAddress from = null;
        InternetAddress to = null;

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
        
        if(begin != null)
            beginDate = (Date)formatter.parse(begin.trim());

        if(end != null)
            endDate = (Date)formatter.parse(end.trim());

        if(fromMail != null)
            from = new InternetAddress(fromMail);

        if(toMail != null)
            to = new InternetAddress(toMail);


        headers = ArchivelQuery.getHeaders(u.getLogin(), u.getPassword(),
                                           beginDate, endDate, from, to,
                                           subject, text);

        return null;
    }
    

    public String doReset()
        throws Exception
    {
        begin=null;
        end=null;
        fromMail=null;
        toMail=null;
        subject=null;
        text=null;
        headers=null;

        return null;
    }

    // DO END



    /**
     * Gets the value of begin
     *
     * @return the value of begin
     */
    public final String getBegin()
    {
        return this.begin;
    }

    /**
     * Sets the value of begin
     *
     * @param argBegin Value to assign to this.begin
     */
    public final void setBegin(final String argBegin)
    {
        this.begin = argBegin;
    }

    /**
     * Gets the value of end
     *
     * @return the value of end
     */
    public final String getEnd()
    {
        return this.end;
    }

    /**
     * Sets the value of end
     *
     * @param argEnd Value to assign to this.end
     */
    public final void setEnd(final String argEnd)
    {
        this.end = argEnd;
    }

    /**
     * Gets the value of fromMail
     *
     * @return the value of fromMail
     */
    public final String getFromMail()
    {
        return this.fromMail;
    }

    /**
     * Sets the value of fromMail
     *
     * @param argFromMail Value to assign to this.fromMail
     */
    public final void setFromMail(final String argFromMail)
    {
        this.fromMail = argFromMail;
    }

    /**
     * Gets the value of toMail
     *
     * @return the value of toMail
     */
    public final String getToMail()
    {
        return this.toMail;
    }

    /**
     * Sets the value of toMail
     *
     * @param argToMail Value to assign to this.toMail
     */
    public final void setToMail(final String argToMail)
    {
        this.toMail = argToMail;
    }

    /**
     * Gets the value of subject
     *
     * @return the value of subject
     */
    public final String getSubject()
    {
        return this.subject;
    }

    /**
     * Sets the value of subject
     *
     * @param argSubject Value to assign to this.subject
     */
    public final void setSubject(final String argSubject)
    {
        this.subject = argSubject;
    }

    /**
     * Gets the value of text
     *
     * @return the value of text
     */
    public final String getText()
    {
        return this.text;
    }

    /**
     * Sets the value of text
     *
     * @param argText Value to assign to this.text
     */
    public final void setText(final String argText)
    {
        this.text = argText;
    }

    /**
     * Gets the value of headers
     *
     * @return the value of headers
     */
    public final ArrayList getHeaders()
    {
        return this.headers;
    }

    /**
     * Sets the value of headers
     *
     * @param argHeaders Value to assign to this.headers
     */
    public final void setHeaders(final ArrayList argHeaders)
    {
        this.headers = argHeaders;
    }

    /**
     * Gets the value of userId
     *
     * @return the value of userId
     */
    public final String getUserId()
    {
        return this.userId;
    }

    /**
     * Sets the value of userId
     *
     * @param argUserId Value to assign to this.userId
     */
    public final void setUserId(final String argUserId)
    {
        this.userId = argUserId;
    }


}
