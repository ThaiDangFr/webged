package openplatform.mail;

import java.net.*;

/**
 * Class SendHostInterface
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public abstract class SendHostInterface
{
    public static final int DEFAULT_PORT = 25;

    protected InetAddress address;
    protected int port;

    public SendHostInterface(String address)
        throws UnknownHostException
    {
        this.address = InetAddress.getByName(address);
        this.port = DEFAULT_PORT;
    }

    public SendHostInterface(String address, int port)
        throws UnknownHostException
    {
        this.address = InetAddress.getByName(address);
        this.port = port;
    }

    public SendHostInterface(InetAddress address)
    {
        this.address = address;
        this.port = DEFAULT_PORT;
    }

    public SendHostInterface(InetAddress address, int port)
    {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress()
    {
        return this.address;
    }

    public int getPort()
    {
        return this.port;
    }
}
