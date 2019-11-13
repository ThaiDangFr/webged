package dang.mailarchive;

import java.util.*;
import javax.mail.internet.*;
import openplatform.database.dbean.*;
import openplatform.database.*;
import openplatform.tools.*;
import openplatform.index.*;
import openplatform.file.*;
import java.io.*;

/**
 * ArchivelQuery.java
 *
 *
 * Created: Mon Oct 24 16:01:47 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class ArchivelQuery 
{
    public static final int MAX_RESPONSE = 2500;


    /**
     * @return Array of MailArchive
     */
    public static ArrayList getHeaders(String login, String password, Date begin, Date end
                                       , InternetAddress from, InternetAddress to
                                       , String subject, String text)
    {
        ArrayList al = new ArrayList();
        DBMailArchive amask = new DBMailArchive();


        // 1 - GET GROUPS
        boolean isAdmin = false;
        if(login == null || password == null) return al;
        DBUser umask = new DBUser();
        umask.setLogin(login);
        umask.setPassword(password);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return al;

        DBUserGroups ugmask = new DBUserGroups();
        ugmask.setUserId(u.getUserId());
        ugmask.setGroupId("1");
        if(DBUserGroups.count(ugmask,null) != 0) isAdmin = true;


        // 2 - SEARCH IN DATABASE
        DBMailArchive[] ma = null;
        if(begin!=null || end!=null || from!=null || to!=null || subject!=null)
        {
            SQLConstraint constr = new SQLConstraint();

            if(begin != null || end!=null)
            {
                StringBuffer sb = new StringBuffer();
                if(begin != null) sb.append(DBMailArchive.DATE).append(SQLConstraint.GREATER_EQ_THAN).append("'").append(SQLTime.getSQLTime(begin.getTime())).append("'");
                if(end != null)
                {
                    if(begin!=null) sb.append(SQLConstraint.AND);

                    sb.append(DBMailArchive.DATE).append(SQLConstraint.LOWER_EQ_THAN).append("'").append(SQLTime.getSQLTime(end.getTime())).append("'");
                }
                constr.setCustomWhere(sb.toString());
            }
            
            constr.setLimit(2*MAX_RESPONSE);
            constr.setOrderBy(DBMailArchive.DATE);
            constr.setOrderByBiggestFirst(true);

            
            amask.clear();
            if(!isAdmin) amask.setMailAccountId(u.getUserId());

            if(from != null)
                amask.setFromMail("%"+from.getAddress()+"%");

            if(to != null)
                amask.setToMail("%"+to.getAddress()+"%");

            if(subject != null)
                amask.setSubject("%"+subject+"%");

            ma = DBMailArchive.load(constr, amask);
        }


        if(from!=null || to!=null || subject!=null || begin!=null
           || end!=null)
        {
            if(ma == null) // no need to do a full text search
                return al;
        }


        // 3 - SEARCH IN FULL TEXT
        ArrayList ft = null;
        if(text!=null)
        {
            ft = Engine.search(text, MAX_RESPONSE, Engine.APPLI_MAILARCHIVE);
            Debug.println(null, Debug.DEBUG, "FULL TEXT FILEBASEID="+ft);
        }


        
        // 4 - FILTERS
        // 4 - 1 filter if ma not null
        if(ma != null)
        {
            DBMailArchiveAttach maamask = new DBMailArchiveAttach();

            int len = ma.length;
            for(int i=0; i<len; i++)
            {
                if(ft != null)
                {
                    if(ft.contains(ma[i].getMessageId()))
                    {
                        al.add(new MailArchive(ma[i]));
                        continue;
                    }

                    maamask.setMailArchiveId(ma[i].getId());
                    DBMailArchiveAttach[] maa = DBMailArchiveAttach.load(maamask);
                    if(maa == null) continue;
                    int lenmaa = maa.length;
                maa:
                    for(int j=0; j<lenmaa; j++)
                    {
                        if(ft.contains(maa[j].getAttachmentId()))
                        {
                            al.add(new MailArchive(ma[i]));
                            break maa;
                        }
                    }
                }
                else
                    al.add(new MailArchive(ma[i]));
            }
        }
        else if(ft!=null)
        {
            int lenft = ft.size();
            DBMailArchiveAttach maamask = new DBMailArchiveAttach();
            amask.clear();
            for(int i=0; i<lenft; i++)
            {
                String fileBaseId = (String)ft.get(i);

                amask.clear();
                amask.setMessageId(fileBaseId);
                if(!isAdmin) amask.setMailAccountId(u.getUserId());
                DBMailArchive a = DBMailArchive.loadByKey(amask);
                if(a != null) al.add(new MailArchive(a));

                maamask.setAttachmentId(fileBaseId);
                DBMailArchiveAttach maa = DBMailArchiveAttach.loadByKey(maamask);
                if(maa != null)
                {
                    amask.clear();
                    amask.setId(maa.getMailArchiveId());
                    a = DBMailArchive.loadByKey(amask);
                    if(a != null) al.add(new MailArchive(a));
                }
            }
        }
     
        return al;
    }


    public static MailArchive getMail(String login, String password, String id)
    {
        // 1 - GET GROUPS
        boolean isAdmin = false;
        if(login == null || password == null) return null;
        DBUser umask = new DBUser();
        umask.setLogin(login);
        umask.setPassword(password);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;

        DBUserGroups ugmask = new DBUserGroups();
        ugmask.setUserId(u.getUserId());
        ugmask.setGroupId("1");
        if(DBUserGroups.count(ugmask,null) != 0) isAdmin = true;

        DBMailArchive amask = new DBMailArchive();
        amask.setId(id);
        if(!isAdmin) amask.setMailAccountId(u.getUserId());

        DBMailArchive a = DBMailArchive.loadByKey(amask);
        if(a == null) return null;
        else return new MailArchive(a);
    }

    
    /**
     * @return base64 encoded mail in rfc922 format
     */
    public static String getRfc822(String login, String password, String id)
    {
        // 1 - GET GROUPS
        boolean isAdmin = false;
        if(login == null || password == null) return null;
        DBUser umask = new DBUser();
        umask.setLogin(login);
        umask.setPassword(password);
        DBUser u = DBUser.loadByKey(umask);
        if(u == null) return null;

        DBUserGroups ugmask = new DBUserGroups();
        ugmask.setUserId(u.getUserId());
        ugmask.setGroupId("1");
        if(DBUserGroups.count(ugmask,null) != 0) isAdmin = true;

        DBMailArchive amask = new DBMailArchive();
        amask.setId(id);
        if(!isAdmin) amask.setMailAccountId(u.getUserId());

        DBMailArchive a = DBMailArchive.loadByKey(amask);
        if(a == null) return null;

        String rawId = a.getRawId();
        InputStream in = Repository.getStream(rawId);
        byte[] b = StreamConverter.inputStreamToBytes(in);
        return openplatform.tools.Base64.encodeBytes(b);
    }


    
    public static void main(String[] args)
        throws Exception
    {
        ArrayList array = getHeaders("admin","admin",null,null,null,null,null,"webdesign");
        if(array == null) return;
        int len = array.size();
        for(int i=0; i<len; i++)
        {
            DBMailArchive ma = (DBMailArchive)array.get(i);
            String b64 = getRfc822("admin","admin",ma.getId());
            System.out.println(openplatform.tools.Base64.decodeString(b64));
        }
    }
    
}
