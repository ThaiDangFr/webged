package openplatform.mail;

import java.net.*;

/**
 * Class ImapHost
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ImapHost extends ReceiveHostInterface
{
    public static final int DEFAULT_PORT = 143;
    protected boolean deleteFetched = false;


    public ImapHost(String address)
        throws UnknownHostException
    {
        super(address, DEFAULT_PORT);
    }


    public ImapHost(String address, int port)
        throws UnknownHostException
    {
        super(address, port);
    }
}
