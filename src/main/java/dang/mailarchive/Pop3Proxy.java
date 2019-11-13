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
 * Created: Fri Oct 14 16:52:15 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class Pop3Proxy extends Proxy
{
    public Pop3Proxy(Socket client, String pop3server, int pop3port)
        throws Exception
    {
        super(client, pop3server, pop3port);
    }
    

    public void endHandler()
    {
        int len = array.size();

        // 1. Retrieve the message
        boolean append = false;
        boolean retr = false;
        StringBuffer message = new StringBuffer();

        for(int i=0; i<len; i++)
        {
            String oristr = (String)array.remove(0);

            String str = oristr.trim();


            // appending # bug fix for buggy mail server...
            if(append)
            {
                Debug.println(this, Debug.DEBUG, "Appending");
                message.append(str);

                if((byte)oristr.charAt(oristr.length()-3) == '\r'
                   && (byte)oristr.charAt(oristr.length()-2) == '\n'
                   && (byte)oristr.charAt(oristr.length()-1) == '.')
                {
                    append = false;

                    if(message.length() != 0)
                    {
                        Debug.println(this, Debug.DEBUG, "MESSAGE "+message);
                        Rfc822Decode dec = new Rfc822Decode(new ByteArrayInputStream(message.toString().getBytes()), Rfc822Decode.USER_IS_RECEIVER);
                        message = new StringBuffer();
                        continue;
                    }
                }
            }



            if(str.indexOf("RETR") == 0)
            {
                retr = true;
                continue;
            }
            
            
            if(retr && str.indexOf("+OK") == 0)
            {
                append = true;
                retr = false;
            }

        }


    }


    public static void main(String[] args) // arg : proxyport pop3server pop3port
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
                Debug.println(null, Debug.DEBUG,"POP3 PROXY Awaiting connection");
                Socket client = server.accept();
                new Pop3Proxy(client, serverStr, port);
            }
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }
    }
    
}
