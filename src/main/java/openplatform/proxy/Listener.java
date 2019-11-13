package openplatform.proxy;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import openplatform.tools.Debug;

/**
 * Listener.java
 *
 *
 * Created: Fri Oct 14 19:41:16 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class Listener implements Runnable
{
    private Proxy proxy;
    private String name;
    private DataInputStream in = null;
    private DataOutputStream out = null;

    public Listener(String name, DataInputStream in, DataOutputStream out, Proxy proxy)
    {
        this.name = name;
        this.proxy = proxy;
        this.in = in;
        this.out = out;

        Thread t = new Thread(this);
        t.setDaemon(false);
        t.start();
    }


    public void run()
    {
        try
        {
            byte[] resp = new byte[4096];
            int bytes_read;
            String str = null;

            StringBuffer msg = new StringBuffer();

            while((bytes_read = in.read(resp)) != -1)
            {
                str = new String(resp,0,bytes_read);
                Debug.println(this, Debug.DEBUG, name+" ["+str.trim()+"]");
                out.write(resp,0,bytes_read);
                out.flush();

                msg.append(str);
                
                if(resp[bytes_read-2] == '\r' && resp[bytes_read-1] == '\n')
                {
                    Debug.println(this, Debug.DEBUG, "\\r\\n : Adding PART");
                    proxy.addMessage(msg.toString().trim());
                    msg = new StringBuffer();
                }
                else
                    Debug.println(this, Debug.DEBUG, "Continuing to APPEND");
            }
        }
        catch(Exception e) {}
        finally
        {
            Debug.println(this, Debug.DEBUG, name+" THREAD end");

            try
            {
                if(in != null) in.close();
                if(out != null) out.close();
            }
            catch(Exception e)
            {
                Debug.println(this, Debug.ERROR, name+" "+e);
                Debug.println(this, Debug.ERROR, e);
            }
            
            proxy.end();
        }
    }  
}
