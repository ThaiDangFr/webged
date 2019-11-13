package openplatform.mail;

import java.util.*;

/**
 * Class interface
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public abstract class MailPartInterface
{
    protected String fileName;
    protected String description;
    protected String contentID;
    protected String disposition;
    protected HashMap header = new HashMap();

    public void setHeader(String name, String value)
    {
        header.put(name, value);
    }

    public String getHeader(String name)
    {
        Object obj = header.get(name);
        if(obj == null) return null;
        else return (String)obj;
    }
    
    public String[] getHeaderKeys()
    {
        Object[] obj =  header.keySet().toArray();
        if(obj == null) return null;
        String[] res = new String[obj.length];
        int len = res.length;
        for(int i=0; i<len; i++)
        {
            res[i] = (String)obj[i];
        }
        return res;
    }


    public String getDisposition()
    {
        return disposition;
    }

    public void setDisposition(String adisposition)
    {
        disposition=adisposition;
    }

    public String getContentID()
    {
        return contentID;
    }

    public void setContentID(String acontentID)
    {
        contentID=acontentID;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String adescription)
    {
        description=adescription;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String afileName)
    {
        fileName=afileName;
    }
}
