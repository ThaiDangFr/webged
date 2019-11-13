package openplatform.mail;

import java.util.*;

/**
 * Class MailMessageList
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class MailMessageList
{
    private ArrayList array = new ArrayList();

    public void addMailMessage(MailMessage msg)
    {
        array.add(msg);
    }

    public int size()
    {
        return array.size();
    }

    public MailMessage getMailMessage(int idx)
    {
        try
        {
            return (MailMessage)array.get(idx);
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
