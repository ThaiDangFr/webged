

package openplatform.snmp.stack;

import java.io.*;
import java.net.*;

/**
 * This is a wrapper class around the standard DatagramSocket.
 *
 * @see DatagramSocket
 */
public class StandardSocket implements ContextSocketFace 
{

    private DatagramSocket  soc=null;
    private InetAddress     hostAddr;
    private int             hostPort;

public StandardSocket()
{
}

public void create(int port) throws IOException
{
    hostPort = port;
    try
    {
        soc = new DatagramSocket(hostPort);
    }
    catch (SocketException exc)
    {
        String str = "Socket problem " + exc.getMessage();
        throw (new IOException(str));
    }
}


public void create(String host, int port) throws IOException
{
    hostPort = port;
    try
    {
        hostAddr = InetAddress.getByName(host);
        soc = new DatagramSocket();
    }
    catch (SocketException exc)
    {
        String str = "Socket problem " + exc.getMessage();
        throw (new IOException(str));
    }
    catch (UnknownHostException exc)
    {
        String str = "Cannot find host " + exc.getMessage();
        throw (new IOException(str));
    }
}

public String getHostAddress()
{
    String res = null;
    if (hostAddr != null)
    {
        res = hostAddr.getHostAddress();
    }
    return res;
}

public ByteArrayInputStream receive(int maxRecvSize) throws IOException
{
    ByteArrayInputStream in = null;
    if (soc != null)
    {
        byte [] data = new byte[maxRecvSize];
        DatagramPacket p = new DatagramPacket(data,maxRecvSize);

        // timeout will throw an exception
        // every 1000 secs whilst idle
        // it is caught and ignored
        // but as a side effect it loops,
        // checking 'me'
        soc.setSoTimeout(1000);

        soc.receive(p);
        hostAddr = p.getAddress();
        in = new ByteArrayInputStream(p.getData(), 0, p.getLength());
    }
    return in;
}

public void send(byte[] packet) throws IOException
{
    if (soc != null)
    {
        DatagramPacket pack = new DatagramPacket(packet, packet.length, 
              hostAddr, hostPort);
        soc.send(pack);
    }
}

public void close()
{
    if (soc != null)
    {
        soc.close();
    }
}

}
