package openplatform.logger;

import java.io.*;
import openplatform.tools.Debug;

public class LogReader
{
    private BufferedReader breader;
    private String readline;
    private LogFormat logformat;


    public LogReader(String filepath, LogFormat logformat)
        throws FileNotFoundException
    {
        this.logformat = logformat;
        FileInputStream fis = new FileInputStream(filepath);
        InputStreamReader isr = new InputStreamReader(fis);
        breader = new BufferedReader(isr);
        
        readLine();
    }
    

    private void readLine()
    {
        try
        {
            readline = breader.readLine();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            readline = null;
        }
    }


    public boolean hasMoreLogs()
    {
        return readline==null?false:true;
    }

    
    public LogFormat nextLog()
    {
        logformat.clear();
        logformat.encode(readline);
        readLine();
        return logformat;
    }
}
