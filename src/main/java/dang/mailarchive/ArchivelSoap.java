package dang.mailarchive;

import javax.servlet.*;
import java.io.*;
import openplatform.tools.*;
import javax.xml.soap.*;
import java.util.*;
import java.text.*;
import javax.mail.internet.*;
import openplatform.database.*;
import openplatform.database.dbean.*;

/**
 * ArchivelSoap.java
 *
 *
 * Created: Fri Oct 21 17:37:54 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class ArchivelSoap 
{
    public static final int TYPE_GET_MAIL_HEADER = 0;
    public static final int TYPE_GET_MAIL = 1;
    public static final int TYPE_GET_RFC822 = 2;

    private int type = -1;
    private Object responseObj = null;


    /**
     * receive the request from servletrequest and process it
     */
    public void setRequest(ServletRequest req)
    {
        try
        {
            ServletInputStream in = req.getInputStream();
            MessageFactory mf = MessageFactory.newInstance();
            SOAPMessage sm = mf.createMessage(null, in);
            
            sm.writeTo(System.out);

            SOAPHeader header = sm.getSOAPHeader();
            HashMap authent = extractChildsValue("Authent", header);
            String login = (String)authent.get("Login");
            String password  = (String)authent.get("Password");

            SOAPBody body = sm.getSOAPBody();
            HashMap getMailHeader = extractChildsValue("GetMailHeader", body);
            HashMap getMail = extractChildsValue("GetMail", body);
            HashMap getMailRfc822 = extractChildsValue("GetMailRfc822", body);

            // REQUEST HEADER
            if(getMailHeader.size() > 0)
            {
                type = TYPE_GET_MAIL_HEADER;

                Date begin = null;
                Date end = null;
                InternetAddress from = null;
                InternetAddress to = null;
                String subject = null;
                String text = null;

                Object objBegin = getMailHeader.get("DateBegin");
                if(objBegin != null) begin = extractDate((String)objBegin);

                Object objEnd = getMailHeader.get("DateEnd");
                if(objEnd != null) end = extractDate((String)objEnd);

                Object objFrom = getMailHeader.get("FromEmail");
                if(objFrom != null) from = new InternetAddress((String)objFrom);

                Object objTo = getMailHeader.get("ToEmail");
                if(objTo != null) to = new InternetAddress((String)objTo);

                Object objSubject = getMailHeader.get("Subject");
                if(objSubject != null) subject = (String)objSubject;

                Object objText = getMailHeader.get("Text");
                if(objText != null) text = (String)objText;

                responseObj = ArchivelQuery.getHeaders(login, password, begin, end,
                                                       from, to, subject, text);
            }

            // REQUEST A MAIL
            else if(getMail.size() > 0)
            {
                type = TYPE_GET_MAIL;
                
                String id = null;

                Object objId = getMail.get("Id");
                if(objId != null) id = (String)objId;

                if(id != null)
                    responseObj = ArchivelQuery.getMail(login, password, id);
            }

            // REQUEST THE RFC822
            else if(getMailRfc822.size() > 0)
            {
                type = TYPE_GET_RFC822;

                String id = null;

                Object objId = getMailRfc822.get("Id");
                if(objId != null) id = (String)objId;

                if(id != null)
                    responseObj = ArchivelQuery.getRfc822(login, password, id);                
            }
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    public String getResponse()
        throws Exception
    {
        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage sm = mf.createMessage();
        SOAPFactory sf = SOAPFactory.newInstance();

        SOAPPart sp = sm.getSOAPPart();
        SOAPEnvelope se = sp.getEnvelope();
        SOAPHeader sh = se.getHeader();
        SOAPBody sb = se.getBody();


        if(responseObj == null)
        {
        }
        else if(type == TYPE_GET_MAIL_HEADER)
        {
            ArrayList al = (ArrayList)responseObj;
            int len = al.size();
            for(int i=0; i<len; i++)
            {
                DBMailArchive ma = (DBMailArchive)al.get(i);
                SOAPBodyElement sbe = sb.addBodyElement(sf.createName("MailHeader", "sr", "http://www.dangconsulting.fr"));

                if(ma.getId() != null)
                    sbe.addChildElement("Id", "sr").addTextNode(ma.getId());

                if(ma.getDate() != null)
                    sbe.addChildElement("Date", "sr").addTextNode(formatDate(SQLTime.getJavaTime(ma.getDate())));

                if(ma.getFromMail() != null)
                    sbe.addChildElement("From", "sr").addTextNode(ma.getFromMail());

                InternetAddress[] ia = InternetAddress.parse(ma.getToMail());
                if(ia != null)
                {
                    int lenia = ia.length;
                    for(int j=0; j<lenia; j++)
                        sbe.addChildElement("To", "sr").addTextNode(ia[j].getAddress());
                }

                if(ma.getSubject() != null)
                    sbe.addChildElement("Subject", "sr").addTextNode(ma.getSubject());
            }
        }
        else if(type == TYPE_GET_MAIL)
        {
            MailArchive ma = (MailArchive)responseObj;
            SOAPBodyElement sbe = sb.addBodyElement(sf.createName("Mail", "sr", "http://www.dangconsulting.fr"));

            if(ma.getId() != null)
                sbe.addChildElement("Id", "sr").addTextNode(ma.getId());

            if(ma.getDate() != null)
                sbe.addChildElement("Date", "sr").addTextNode(formatDate(ma.getDate().getTime()));

            if(ma.getFromMail() != null)
                sbe.addChildElement("From", "sr").addTextNode(ma.getFromMail().getAddress());

            if(ma.getToMail() != null)
            {
                InternetAddress[] ie = ma.getToMail();
                int len = ie.length;
                for(int i=0; i<len; i++)
                    sbe.addChildElement("To", "sr").addTextNode(ie[i].getAddress());
            }

            if(ma.getSubject() != null)
                sbe.addChildElement("Subject", "sr").addTextNode(ma.getSubject());

            sbe.addChildElement("Message", "sr").addTextNode(ma.getB64Message());

            ArrayList att = ma.getAttachments();
            if(att != null)
            {
                int len = att.size();
                for(int i=0; i<len; i++)
                {
                    MailArchiveAttachments maa = (MailArchiveAttachments)att.get(i);
                    SOAPElement el = sbe.addChildElement("Attachment", "sr").addTextNode(maa.getB64message());
                    el.addAttribute(sf.createName("filename"), maa.getFilename());
                }
            }
        }
        else if(type == TYPE_GET_RFC822)
        {
            String rfc = (String)responseObj;
            SOAPBodyElement sbe = sb.addBodyElement(sf.createName("MailRfc822", "sr", "http://www.dangconsulting.fr"));
            sbe.addTextNode(rfc);
        }


        ByteArrayOutputStream out = new ByteArrayOutputStream();
        sm.saveChanges();
        sm.writeTo(out);
        
        String response = new String(out.toByteArray());
        Debug.println(this, Debug.DEBUG, response);
        return response;
    }


    /**
     * @return map with param/values in lowercase
     */
    private HashMap extractChildsValue(String tag, SOAPElement elem)
    {
        HashMap map = new HashMap();

        try
        {
            SOAPFactory sf = SOAPFactory.newInstance();
            Iterator it0 = elem.getChildElements(sf.createName(tag, "sr", "http://www.dangconsulting.fr"));

            SOAPElement se0 = (SOAPElement)(it0.next());
            Iterator it = se0.getChildElements();
            while(it.hasNext())
            {
                SOAPElement se = (SOAPElement)(it.next());
                String name = se.getElementName().getLocalName();
                String value = se.getFirstChild().getNodeValue();
                map.put(name, value);
            }
        }
        catch(Exception e)
        { }
        
        return map;
    }


    private Date extractDate(String strDate)
    {
        try
        {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm");
            return (Date)formatter.parse(strDate.trim());
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return null;
        }
    }


    private String formatDate(long date)
    {
        try
        {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm");
            StringBuffer sb = formatter.format(new Date(date), new StringBuffer(), new FieldPosition(0));
            return sb.toString();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return null;
        }
    }



    // http://www-128.ibm.com/developerworks/xml/library/x-jaxmsoap/
    public static void main(String args[])
        throws Exception
    {

        MessageFactory mf = MessageFactory.newInstance();
        SOAPMessage sm = mf.createMessage();
        SOAPFactory sf = SOAPFactory.newInstance();
        
        SOAPPart sp = sm.getSOAPPart();
        SOAPEnvelope se = sp.getEnvelope();
        SOAPHeader sh = se.getHeader();
        SOAPBody sb = se.getBody();

       
        Name authent = sf.createName("Authent", "sr", "http://www.dangconsulting.fr");
        SOAPHeaderElement she = sh.addHeaderElement(authent);
        she.addChildElement("Login","sr").addTextNode("admin");
        she.addChildElement("Password","sr").addTextNode("admin");


        Name getMailRfc822 = sf.createName("GetMailRfc822", "sr", "http://www.dangconsulting.fr");
        SOAPBodyElement sbe = sb.addBodyElement(getMailRfc822);
        sbe.addChildElement("Id", "sr").addTextNode("12");        



        SOAPConnectionFactory scf = SOAPConnectionFactory.newInstance();
        SOAPConnection sc = scf.createConnection();
        SOAPMessage soapresponse = sc.call(sm, "http://localhost/openplatform/cms/user/pc/modules/mailarchive/soap.jsp");
        sc.close();
    }
    
}
