package openplatform.mail;

import javax.mail.event.*;
import openplatform.tools.*;
import javax.mail.*;

/**
 * Class MailSenderListener
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class MailSenderListener implements TransportListener
{
    public void messageDelivered(TransportEvent e)
    {
        Debug.println(this, Debug.DEBUG, "Message delivered");
    }

    public void messageNotDelivered(TransportEvent e)
    {
        Debug.println(this, Debug.WARNING, "Message not delivered");
    }

    public void messagePartiallyDelivered(TransportEvent e)
    {
        Debug.println(this, Debug.WARNING, "Message partially delivered");
        Address[] add = e.getValidUnsentAddresses();
        if(add != null)
        {
            int len = add.length;
            for(int i=0; i<len; i++)
            {
                Debug.println(this, Debug.WARNING, "Unsent address="+add[i]);
            }
        }
    }
}
