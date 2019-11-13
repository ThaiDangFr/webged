package dang.mailarchive;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.mail.internet.InternetAddress;

import openplatform.database.dbean.DBFileBase;
import openplatform.database.dbean.DBMailArchive;
import openplatform.database.dbean.DBMailArchiveAttach;
import openplatform.file.Repository;
import openplatform.tools.Base64;
import openplatform.tools.OrdHashMap;
import openplatform.tools.SQLTime;
import openplatform.tools.StreamConverter;

/**
 * MailArchive.java
 *
 *
 * Created: Mon Oct 24 17:15:55 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class MailArchive
{
    private DBMailArchive mail = new DBMailArchive();

    public MailArchive(DBMailArchive ma)
    {
        mail.initBean(ma);
    }
    

    public String getId() { return mail.getId(); }
    public String getSubject() { return mail.getSubject(); }

    public InternetAddress getFromMail()
        throws Exception
    {
        return new InternetAddress(mail.getFromMail());
    }


    public InternetAddress[] getToMail()
        throws Exception
    {
        return InternetAddress.parse(mail.getToMail());
    }

    public HashMap getToMailOptions()
    {
        HashMap map = new OrdHashMap();
        try
        {
            InternetAddress[] ia = getToMail();
            if(ia == null) return map;
        
            int len = ia.length;
            for(int i=0; i<len; i++)
            {
                map.put(ia[i].getAddress(), ia[i].getAddress());
            }
            return map;
        }
        catch(Exception e)
        {
            return map;
        }
    }

    //     public String getToMailStr()
    //     {
    //         return mail.getToMail();
    //     }


    public Date getDate()
    {
        return new Date(SQLTime.getJavaTime(mail.getDate()));
    }


    public String getDateStr()
    {
        return mail.getDate();
    }

    public String getB64Message()
    {
        InputStream in = Repository.getStream(mail.getMessageId());
        byte[] b = StreamConverter.inputStreamToBytes(in);
        return Base64.encodeBytes(b);
    }

    
    public String getMessage()
    {
        InputStream in = Repository.getStream(mail.getMessageId());
        byte[] b = StreamConverter.inputStreamToBytes(in);
        return new String(b);
    }


    /**
     * @return array of MailArchiveAttachments
     */
    public ArrayList getAttachments()
    {
        ArrayList al = new ArrayList();

        DBMailArchiveAttach aamask = new DBMailArchiveAttach();
        aamask.setMailArchiveId(mail.getId());
        DBMailArchiveAttach[] aa = DBMailArchiveAttach.load(aamask);

        if(aa == null) return al;

        int len = aa.length;

        DBFileBase fmask = new DBFileBase();
        for(int i=0; i<len; i++)
        {
            fmask.setFileBaseId(aa[i].getAttachmentId());
            DBFileBase fb = DBFileBase.loadByKey(fmask);

            if(fb != null)
            {
                InputStream in = Repository.getStream(aa[i].getAttachmentId());
                byte[] b = StreamConverter.inputStreamToBytes(in);

                MailArchiveAttachments maa = new MailArchiveAttachments();
                maa.setFilename(fb.getFilename());
                maa.setB64message(Base64.encodeBytes(b));
                maa.setFileBaseId(fb.getFileBaseId());
                al.add(maa);
            }
        }

        return al;
    }
}
