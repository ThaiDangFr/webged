package dang.mailarchive;

import openplatform.tools.*;
import javax.mail.internet.*;
import java.io.*;
import java.util.*;
import javax.mail.*;
import openplatform.database.dbean.*;
import openplatform.file.*;
import openplatform.index.*;

/**
 * Rfc822Decode.java
 *
 *
 * Created: Mon Oct 10 15:45:58 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class Rfc822Decode
{
    public static final int USER_IS_SENDER = 0;
    public static final int USER_IS_RECEIVER = 1;

    //private String message;
    private InputStream input;
    private MimeMessage mm;
    private DBUser u;




    //public Rfc822Decode(String message, int user)
    public Rfc822Decode(InputStream input, int user)
    {
        try
        {
            //this.message = message;
            //mm = new MimeMessage(null, new ByteArrayInputStream(message.getBytes()));
        	this.input = input;
        	mm = new MimeMessage(null, input);
            Address[] addr = null;

            // smtp
            if(user == USER_IS_SENDER)
            {
                // Fetch or create User
                Address[] from = mm.getFrom();
                addr = from;
            }
            else if(user == USER_IS_RECEIVER)// pop imap
            {
                Address[] addrTo = mm.getAllRecipients();
                addr = addrTo;
                
            }


            if(addr != null && addr.length > 0)
            {
                int len = addr.length;
                
                DBUser umask = new DBUser();

                for(int i=0; i<len; i++)
                {
                    InternetAddress iaddr = (InternetAddress)addr[i];

                    umask.clear();
                    umask.setMail(iaddr.getAddress());
                    u = DBUser.loadByKey(umask);
                    if(u == null)
                    {
                        u = umask;
                        u.setFirstName(iaddr.getPersonal());
                        u.setLogin(iaddr.getAddress());
                        u.setPassword(iaddr.getAddress());
                        u.store();
                    }

                    process();
                }
            }
            else // load a default user
            {
                DBUser umask = new DBUser();
                umask.setUserId("1"); // admin
                u = DBUser.loadByKey(umask);
                process();
            }           
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }


    private void process()
        throws Exception
    {                   
        // Check if the message is already present in the database
        String headerMessageId = mm.getMessageID();
        DBMailArchive mmask = new DBMailArchive();
        mmask.setHeaderMessageId(headerMessageId);
        if(DBMailArchive.count(mmask,null) != 0 && headerMessageId!=null)
        {
            Debug.println(this, Debug.WARNING, "Message-ID already in database, skipping "+headerMessageId + " subject:"+mm.getSubject());
            return;
        }
        
     
        // Store the rfc message
        String rawId = Repository.storeFile(u.getMail(), input, false, Repository.APPLI_MAILARCHIVE);

        // Vars
        String messageId = null;
        ArrayList array = new ArrayList(); // array of MailArchiveAttach


        // Store the message
        Object obj = mm.getContent();
        if(obj instanceof String)
        {
            Debug.println(this, Debug.DEBUG, "STRING");
            String msg = (String)obj;
            messageId = Repository.storeFile("message.txt", new ByteArrayInputStream(msg.getBytes())
            		, true, Repository.APPLI_MAILARCHIVE);
        }
        else if(obj instanceof Multipart)
        {
            Debug.println(this, Debug.DEBUG, "MULTIPART");
            Multipart mp = (Multipart)obj;
            int len = mp.getCount();

            for(int i=0; i<len; i++)
            {
                BodyPart bp = mp.getBodyPart(i);
                Object bpobj = bp.getContent();

                if(bpobj instanceof String)
                {
                    if(messageId == null)
                    {
                        String msg = (String)bpobj;
                        messageId = Repository.storeFile("message.txt", new ByteArrayInputStream(msg.getBytes())
                        		, true, Repository.APPLI_MAILARCHIVE);
                        continue; // next bodypart
                    }
                }
                    
                // save as attachments
                String attachId = Repository.storeFile(MimeUtility.decodeText(bp.getFileName())
                		, bp.getInputStream(), true, Repository.APPLI_MAILARCHIVE);
                DBMailArchiveAttach maa = new DBMailArchiveAttach();
                maa.setAttachmentId(attachId);
                array.add(maa); // fill the arraylist
            }
        }
        else if(obj instanceof InputStream)
        {
            Debug.println(this, Debug.WARNING, "INPUTSTREAM : SKIP !");
        }



        // Create MailArchive
        Date date = mm.getSentDate();

        StringBuffer from = new StringBuffer();
        Address[] addrfrom = mm.getFrom();
        if(addrfrom != null)
        {
            int len = addrfrom.length;
            for(int i=0; i<len; i++)
            {
                InternetAddress ia = (InternetAddress)addrfrom[i];
                from.append(ia.getAddress());
                if(i!=len-1) from.append(",");
            }
        }




        StringBuffer to = new StringBuffer();
        Address[] addrTo = mm.getAllRecipients();
        if(addrTo != null)
        {
            int len = addrTo.length;
            for(int i=0; i<len; i++)
            {
                InternetAddress ia = (InternetAddress)addrTo[i];
                to.append(ia.getAddress());
                if(i!=len-1) to.append(",");
            }
        }

        String subject = mm.getSubject();

        DBMailArchive ma = new DBMailArchive();
        ma.setHeaderMessageId(headerMessageId);
        ma.setMailAccountId(u.getUserId());
        if(date != null) ma.setDate(SQLTime.getSQLTime(date.getTime()));
        ma.setFromMail(from.toString());
        ma.setToMail(to.toString());
        ma.setSubject(subject);
        if(messageId!=null) ma.setMessageId(messageId);
        ma.setRawId(rawId);
        ma.store();


        // Create MailArchiveAttach
        int size = array.size();
        for(int i=0; i<size; i++)
        {
            DBMailArchiveAttach maa = (DBMailArchiveAttach)array.get(i);
            maa.setMailArchiveId(ma.getId());
            maa.store();
        }   
    }
}
