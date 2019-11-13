package openplatform.mail;

import java.util.*;
import openplatform.tools.*;

/**
 * Class MailMessage
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class MailMessage
{
    private String subject;
    private String from;
    private StringArrayList toArray = new StringArrayList();
    private StringArrayList ccArray = new StringArrayList();
    private StringArrayList bccArray = new StringArrayList();

    private ArrayList array = new ArrayList();


    public void addCC(String cc)
    {
        ccArray.add(cc);
    }

    public StringArrayList getCC()
    {
        return ccArray;
    }

    public void addBCC(String bcc)
    {
        bccArray.add(bcc);
    }

    public StringArrayList getBCC()
    {
        return bccArray;
    }

    public void addTo(String to)
    {
        toArray.add(to);
    }

    public StringArrayList getTo()
    {
        return toArray;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String afrom)
    {
        from=afrom;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String asubject)
    {
        subject=asubject;
    }

    public void addPart(MailPartInterface part)
    {
        array.add(part);
    }

  
    public MailPartInterface deletePart(int idx)
    {
        try
        {
            return (MailPartInterface)array.remove(idx);
        }
        catch(Exception e)
        {
            return null;
        }
    }


    public MailPartInterface getPart(int idx)
    {
        try
        {
            return (MailPartInterface)array.get(idx);
        }
        catch(Exception e)
        {
            return null;
        }        
    }
    

    public int size()
    {
        return array.size();
    }
}
