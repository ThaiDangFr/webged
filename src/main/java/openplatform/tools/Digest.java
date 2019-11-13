package openplatform.tools;

import java.io.InputStream;
import java.io.OutputStream;

import com.twmacinta.util.MD5InputStream;

/**
 * Class Digest
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Digest
{
    public static final int BUFFER = 1024;
    
    static
    {
        com.twmacinta.util.MD5.initNativeLibrary(false);
    }


    public static String MD5(InputStream input)
        throws Exception
    {
        MD5InputStream md = new MD5InputStream(input);

        int num_read;
        byte[] buf = new byte[BUFFER];

        while ((num_read = md.read(buf)) != -1);
        
        String res = com.twmacinta.util.MD5.asHex(md.hash());

        return res;
    }
    
    
    public static String MD5(InputStream input, OutputStream output)
    	throws Exception
    {
        MD5InputStream md = new MD5InputStream(input);

        int num_read;
        byte[] buf = new byte[BUFFER];

        while ((num_read = md.read(buf)) != -1)
        {
        	output.write(buf,0, num_read);
        }
        
        String res = com.twmacinta.util.MD5.asHex(md.hash());

        return res;
    }
    
}
