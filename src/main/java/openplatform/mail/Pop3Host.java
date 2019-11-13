package openplatform.mail;

import java.net.*;

/**
 * Class Pop3Host
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Pop3Host extends ReceiveHostInterface
{
    public static final int DEFAULT_PORT = 110;
    protected boolean deleteFetched = true;

    public Pop3Host(String address)
        throws UnknownHostException
    {
        super(address, DEFAULT_PORT);
    }


    public Pop3Host(String address, int port)
        throws UnknownHostException
    {
        super(address, port);
    }
}
