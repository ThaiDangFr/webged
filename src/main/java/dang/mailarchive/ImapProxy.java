package dang.mailarchive;

import java.net.*;
import java.io.*;
import openplatform.tools.*;
import openplatform.database.*;
import openplatform.proxy.Proxy;

/**
 * ImapProxy.java
 *
 *
 * Created: Mon Oct 17 17:04:45 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class ImapProxy extends Proxy
{
    public ImapProxy(Socket client, String imapserver, int imapport)
        throws Exception
    {
        super(client, imapserver, imapport);
    }
    

    public void endHandler()
    {
        int len = array.size();
        boolean isMail = false;
        for(int i=0; i<len; i++)
        {
            String str = ((String)array.remove(0)).trim();

            if(isMail)
            {
                Rfc822Decode dec = new Rfc822Decode(new ByteArrayInputStream(str.getBytes()), Rfc822Decode.USER_IS_RECEIVER);
                isMail = false;
            }

            
            if(str.indexOf("*") == 0)
            {
                if(str.indexOf("FETCH") != -1 && str.indexOf("BODY[]") != -1)
                    isMail = true;
            }
        }
    }


    public static void main(String[] args) // arg : proxyport imapserver imapport
    {        
        try
        {
            ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
            server.setReceiveBufferSize(4096);
            server.setReuseAddress(false);
        
            String serverStr = args[1];
            int port = Integer.parseInt(args[2]);

            while(true)
            {
                Debug.println(null, Debug.DEBUG,"IMAP PROXY Awaiting connection");
                Socket client = server.accept();
                new ImapProxy(client, serverStr, port);
            }
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }
    
}
