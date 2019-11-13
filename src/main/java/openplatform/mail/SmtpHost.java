package openplatform.mail;

import java.net.*;

/**
 * Class SmtpHost
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class SmtpHost extends SendHostInterface
{
    public SmtpHost(String address)
        throws UnknownHostException
    {
        super(address);
    }

    public SmtpHost(String address, int port)
        throws UnknownHostException
    {
        super(address, port);
    }
}
