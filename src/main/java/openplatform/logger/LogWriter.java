package openplatform.logger;

import java.io.*;
import openplatform.tools.Debug;

public class LogWriter
{
    private FileOutputStream fos;

    public LogWriter(String filepath)
        throws FileNotFoundException
    {
        fos = new FileOutputStream(filepath, true);
    }

    public boolean appendln(LogFormat logformat)
    {
        try
        {
            String str = logformat.decode();
            byte[] byt = str.getBytes("UTF-8");
            fos.write(byt);
            fos.write((int)'\n');
            return true;
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return false;
        }
    }

    public void close()
    {
        try
        {
            fos.flush();
            fos.close();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }
    }
}
