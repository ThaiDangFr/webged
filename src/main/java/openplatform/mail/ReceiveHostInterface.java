package openplatform.mail;

import java.net.*;


/**
 * Class interface
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public abstract class ReceiveHostInterface
{
    protected InetAddress address;
    protected int port;
    protected String login;
    protected String password;

    /** when mail is fetched, do I delete it ?**/
    protected boolean deleteFetched;



    public ReceiveHostInterface(String address, int port)
        throws UnknownHostException
    {
        this.address = InetAddress.getByName(address);
        this.port = port;
    }

    public ReceiveHostInterface(InetAddress address, int port)
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

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getLogin()
    {
        return login;
    }


    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean isDeleteFetched()
    {
        return deleteFetched;
    }

    public void setDeleteFetched(boolean adeleteFetched)
    {
        deleteFetched=adeleteFetched;
    }
}
