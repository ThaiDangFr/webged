package openplatform.http;

import openplatform.tools.Debug;
import java.util.*;


/**
 * Class HttpResponse
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class HttpResponse
{
    private int statusCode;
    private byte[] data;
    private Hashtable headers;

    public HttpResponse()
    {
        headers = new Hashtable();
    }


    public int getStatusCode()
    {
        return statusCode;
    }


    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }


    public byte[] getData()
    {
        return data;
    }


    public void setData(byte[] data)
    {
        this.data = data;
    }


    public String getDataAsString(String encodage)
    {
        try
        {
            return new String(data, encodage);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return null;
        }
    }


    public Enumeration getHeaderNames()
    {
        return headers.keys();
    }


    public String getHeader(String name)
    {
        if(name==null)
            return null;

        Object obj = headers.get(name);
        return (String)obj;
    }


    public String getContentType()
    {
        Object obj = headers.get("Content-Type");
        if(obj == null) return null;
        else return (String)obj;
    }

    
    public String getContentLength()
    {
        Object obj = headers.get("Content-Length");
        if(obj == null) return null;
        else return (String)obj;
    }


    public void setHeader(String name, String value)
    {
        if(name==null || value==null)
            return;

        headers.put(name, value);
    }
}
