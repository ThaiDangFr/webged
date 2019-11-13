package openplatform.proxy;

import java.net.*;
import java.io.*;
import java.util.*;
import openplatform.tools.*;

/**
 * Proxy.java
 *
 *
 * Created: Fri Oct 14 19:38:30 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public abstract class Proxy implements Runnable
{
    protected ArrayList array = new ArrayList();
    private boolean endCalled = false;
    private Socket client;
    private String serverAddr;
    private int port;


    public Proxy(Socket client, String serverAddr, int port)
    {
        this.client = client;
        this.serverAddr = serverAddr;
        this.port = port;

        Thread t = new Thread(this);
        t.setDaemon(false);
        t.start();
    }
    
    
    public abstract void endHandler();
    

    public void run()
    {
        try
        {
            Socket server = new Socket(serverAddr, port);
            server.setSoTimeout(0);
            server.setKeepAlive(true);
            server.setReceiveBufferSize(4096);
            server.setReuseAddress(false);
            server.setSendBufferSize(4096);
            server.setTcpNoDelay(true);
            server.setKeepAlive(true);
            
            client.setSoTimeout(0);
            client.setKeepAlive(true);
            client.setReceiveBufferSize(4096);
            client.setReuseAddress(false);
            client.setSendBufferSize(4096);
            client.setTcpNoDelay(true);
            client.setKeepAlive(true);

            DataInputStream client_in = new DataInputStream(client.getInputStream());
            DataOutputStream client_out = new DataOutputStream(client.getOutputStream());
            DataInputStream server_in = new DataInputStream(server.getInputStream());
            DataOutputStream server_out = new DataOutputStream(server.getOutputStream());
            new Listener("SERVER", server_in, client_out, this);
            new Listener("CLIENT", client_in, server_out, this);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }

    
    protected void end()
    {
        if(!endCalled)
        {
            endCalled = true;
            endHandler();
        }
    }


    public void addMessage(String msg)
    {
        array.add(msg);
    }


    // GET SET


    /**
     * Gets the value of endCalled
     *
     * @return the value of endCalled
     */
    public final boolean isEndCalled()
    {
        return this.endCalled;
    }

    /**
     * Sets the value of endCalled
     *
     * @param argEndCalled Value to assign to this.endCalled
     */
    public final void setEndCalled(final boolean argEndCalled)
    {
        this.endCalled = argEndCalled;
    }


    

}
