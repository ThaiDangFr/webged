package dang.mailarchive;

import java.net.*;
import java.io.*;
import openplatform.tools.*;
import openplatform.database.*;
import openplatform.proxy.Proxy;

/**
 * Pop3Proxy.java
 *
 *
 * Created: Thu Oct  6 15:36:06 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class SmtpProxy extends Proxy
{
    public SmtpProxy(Socket client, String smtpserver, int smtpport)
    {
        super(client, smtpserver, smtpport);
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
                Rfc822Decode dec = new Rfc822Decode(new ByteArrayInputStream(str.getBytes()), Rfc822Decode.USER_IS_SENDER);
                break;
            }

            int idx = str.indexOf(" ");
            if(idx != -1)
            {
                String code = str.substring(0,idx).trim();
                if("354".equals(code)) isMail = true;
            }            
        }
    }

    
    public static void main(String[] args) // arg : proxyport server port
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
                Debug.println(null, Debug.DEBUG,"SMTP PROXY Awaiting connection");
                Socket client = server.accept();
                new SmtpProxy(client, serverStr, port);
            }
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }
    
}
