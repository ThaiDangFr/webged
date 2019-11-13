package openplatform.mail;

import java.util.*;
import javax.mail.*;
import openplatform.tools.*;
import java.io.*;


/**
 * Class MailReceiver
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class MailReceiver
{
    private Properties props = new Properties();
    private Session session;
    private ReceiveHostInterface host;
    private Store store;

    private static final Flags DELETE_FLAG = new Flags(Flags.Flag.DELETED);
    private static final Flags SEEN_FLAG = new Flags(Flags.Flag.SEEN);

    public MailReceiver(ReceiveHostInterface host)
    {
        this.host = host;

        try
        {
            session = Session.getInstance(props, null);
            if(Debug.DEBUGON)
            {
                session.setDebug(true);
                session.setDebugOut(Debug.debugStream());
            }

            if(host instanceof Pop3Host)
            {
                store = session.getStore("pop3");
            }
            else if(host instanceof ImapHost)
            {
                store = session.getStore("imap");  
            }
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }

    /**
     * Fetch email
     * Default behavior for POP3 and IMAP (it can be changed!) :
     * For POP3, it will try to fetch messages and delete fetched messages on the server.
     * For IMAP4, it will fetch messages and let them on the server.
     */
    public MailMessageList fetch()
    {
        MailMessageList mml = new MailMessageList();

        try
        {
//             Debug.println(this, Debug.DEBUG, host.getAddress().getHostAddress()+" "+host.getPort()+" "+host.getLogin()+" "+host.getPassword());
            store.connect(host.getAddress().getHostAddress(), host.getPort(),
                          host.getLogin(), host.getPassword());

            if(host instanceof Pop3Host)
            {
                Folder root = store.getDefaultFolder();

                Folder[] folders = root.list();

                if(folders == null || folders.length == 0)
                    throw new Exception("No folder found !");

                Folder folder = folders[0];

                try
                {
                    folder.open(Folder.READ_WRITE);
                    Debug.println(this, Debug.DEBUG, "Folder opened in RW");
                }
                catch(MessagingException me)
                {
                    folder.open(Folder.READ_ONLY);
                    Debug.println(this, Debug.DEBUG, "Folder opened in RO");
                }

                Message[] message = folder.getMessages();

                if(message != null)
                {
                    int len = message.length;
                    for(int i=0; i<len; i++)
                    {
                        mml.addMailMessage(convert(message[i]));
                    }
                }

                try
                {
                    if(host.isDeleteFetched())
                        folder.setFlags(message, DELETE_FLAG, true);
                    else
                        folder.setFlags(message, SEEN_FLAG, true);
                }
                catch(Exception e)
                {
                    Debug.println(this, Debug.WARNING, "Can't put flag on pop server : "+e);
                }

                folder.close(true); // delete messages marked with DELETE FLAG
                store.close();
            }
            else if(host instanceof ImapHost)
            {
                Folder root = store.getDefaultFolder();

                Folder[] folders = root.list();

                if(folders == null || folders.length == 0)
                    throw new Exception("No folder found !");

                int len = folders.length;
                for(int i=0; i<len; i++)
                {
                    ArrayList al = mailMessages(folders[i], host.isDeleteFetched());
                    int msglen = al.size();
                    for(int j=0; j<msglen; j++)
                    {
                        MailMessage  msg = (MailMessage)al.get(j);
                        mml.addMailMessage(msg);
                    }
                }
            }
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }

        return mml;
    }


    /**
     * @return an array of MailMessage
     * Reccursive method
     */
    private ArrayList mailMessages(Folder folder, boolean deleteSeen)
        throws Exception
    {
        ArrayList al = new ArrayList();
        if(folder == null) return al;

        try
        {
            folder.open(Folder.READ_WRITE);
            Debug.println(this, Debug.DEBUG, "Folder opened in RW");
        }
        catch(MessagingException me)
        {
            folder.open(Folder.READ_ONLY);
            Debug.println(this, Debug.DEBUG, "Folder opened in RO");
        }

        int len = folder.getMessageCount();

        Debug.println(this, Debug.DEBUG, len + " message(s) found in folder "+folder.getFullName());

        for(int i=1; i<=len; i++)
        {
            Message msg = folder.getMessage(i);
            MailMessage mmsg = convert(msg);
            al.add(mmsg);
        }
        
        try
        {
            if(deleteSeen)
                folder.setFlags(1, len, DELETE_FLAG, true);
            else
                folder.setFlags(1, len, SEEN_FLAG, true);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.WARNING, "Can't put flag on imap server : "+e);
        }
        
        folder.close(true); // expunge

        Folder[] folders = folder.list();
        if(folders != null)
        {
            len = folders.length;
            for(int i=0; i<len; i++)
            {
                ArrayList sal = mailMessages(folders[i], deleteSeen);
                al.addAll(sal);
            }
        }

        return al;
    }



    /**
     * Convert the javax.mail.Message to openplatform.mail.MailMessage
     * @return a MailMesage not null
     */
    private MailMessage convert(Message msg)
        throws MessagingException, IOException
    {
        Flags.Flag[] ff = msg.getFlags().getSystemFlags();
        if(ff != null && ff.length != 0) Debug.println(this, Debug.DEBUG, "Message is flagged");

        MailMessage mm = new MailMessage();

        Address[] from = msg.getFrom();
        if(from != null)
            mm.setFrom(from[0].toString());

        Address[] address = msg.getRecipients(Message.RecipientType.TO);
        if(address != null)
        {
            int len = address.length;
            for(int i=0; i<len; i++)
            {
                mm.addTo(address[i].toString());
            }
        }

        address = msg.getRecipients(Message.RecipientType.CC);
        if(address != null)
        {
            int len = address.length;
            for(int i=0; i<len; i++)
            {
                mm.addCC(address[i].toString());
            }
        }

        address = msg.getRecipients(Message.RecipientType.BCC);
        if(address != null)
        {
            int len = address.length;
            for(int i=0; i<len; i++)
            {
                mm.addBCC(address[i].toString());
            }
        }

        mm.setSubject(msg.getSubject());
        
        if(msg.isMimeType("text/plain"))
        {
            Debug.println(this, Debug.DEBUG, "text/plain detected");
            MailTextPart text = new MailTextPart();
            text.setText((String)msg.getContent());
            mm.addPart(text);
        }
        else if(msg.isMimeType("multipart/*"))
        {
            Debug.println(this, Debug.DEBUG, "multipart/* detected");
            Multipart mp = (Multipart)msg.getContent();
            int len = mp.getCount();
            for(int i=0; i<len; i++)
            {
                BodyPart bpart = mp.getBodyPart(i);
                if(bpart.isMimeType("text/plain"))
                {
                    MailTextPart text = new MailTextPart();
                    text.setText((String)bpart.getContent());
                    text.setFileName(bpart.getFileName());
                    text.setDescription(bpart.getDescription());
                    text.setDisposition(bpart.getDisposition());

                    Enumeration headers = bpart.getAllHeaders();
                    while(headers.hasMoreElements())
                    {
                        Header h = (Header)headers.nextElement();
                        text.setHeader(h.getName(), h.getValue());
                    }

                    mm.addPart(text);
                }
                else
                {
                    MailAttachmentPart att = new MailAttachmentPart();
                    att.setData(StreamConverter.inputStreamToBytes(bpart.getInputStream()));
                    att.setFileName(bpart.getFileName());
                    att.setDescription(bpart.getDescription());
                    att.setDisposition(bpart.getDisposition());

                    Enumeration headers = bpart.getAllHeaders();
                    while(headers.hasMoreElements())
                    {
                        Header h = (Header)headers.nextElement();
                        att.setHeader(h.getName(), h.getValue());
                    }

                    mm.addPart(att);
                }
            }
        }
        else if(msg.isMimeType("message/rfc822"))
        {
            Debug.println(this, Debug.DEBUG, "message/rfc822 detected");
            Part part = (Part)msg.getContent();
            if(part.isMimeType("text/plain"))
            {
                MailTextPart text = new MailTextPart();
                text.setText((String)part.getContent());
                text.setFileName(part.getFileName());
                text.setDescription(part.getDescription());
                text.setDisposition(part.getDisposition());

                Enumeration headers = part.getAllHeaders();
                while(headers.hasMoreElements())
                {
                    Header h = (Header)headers.nextElement();
                    text.setHeader(h.getName(), h.getValue());
                }

                mm.addPart(text);
            }
            else
            {
                MailAttachmentPart att = new MailAttachmentPart();
                att.setData(StreamConverter.inputStreamToBytes(part.getInputStream()));
                att.setFileName(part.getFileName());
                att.setDescription(part.getDescription());
                att.setDisposition(part.getDisposition());

                Enumeration headers = part.getAllHeaders();
                while(headers.hasMoreElements())
                {
                    Header h = (Header)headers.nextElement();
                    att.setHeader(h.getName(), h.getValue());
                }

                mm.addPart(att);
            }
        }
        else
        {
            Debug.println(this, Debug.DEBUG, "unknown mimetypes, trying to match javatypes");
            Object o = msg.getContent();
            if(o instanceof String)
            {
                Debug.println(this, Debug.DEBUG,"match with String");
                MailTextPart text = new MailTextPart();
                text.setText((String)o);
                mm.addPart(text);
            }
            else if(o instanceof InputStream)
            {
                Debug.println(this, Debug.DEBUG,"match with InputStream");
                byte[] data = StreamConverter.inputStreamToBytes((InputStream)o);
                MailAttachmentPart att = new MailAttachmentPart();
                att.setData(data);
                att.setFileName("Unknown");
                mm.addPart(att);
            }
            else
            {
                Debug.println(this, Debug.WARNING, "no match found, put as it !");
                MailTextPart text = new MailTextPart();
                text.setText(o.toString());
                mm.addPart(text);       
            }
        }
        
        return mm;
    }
}
