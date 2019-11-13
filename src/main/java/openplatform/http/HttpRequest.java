package openplatform.http;

import java.util.*;
import openplatform.tools.*;


/**
 * Class HttpRequest
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class HttpRequest
{
    private String url;
    private Hashtable headers;
    private Multimap paramValues;

    public HttpRequest()
    {
        headers = new Hashtable();
        paramValues = new Multimap();
    }


    public void clearHeaders()
    {
        headers.clear();
    }


    public void clearParamValues()
    {
        paramValues.clear();
    }


    public String removeHeader(String name)
    {
        Object obj = headers.remove(name);
        if(obj==null) return null;

        return (String)obj;
    }


    public ArrayList removeParamValues(String param)
    {
        Object obj = paramValues.remove(param);
        if(obj==null) return null;

        return (ArrayList)obj;        
    }


    /**
     * The URL to fetch (example http://...)
     **/
    public void setURL(String url)
    {
        this.url = url;
    }


    /**
     * Add a header
     **/
    public void setHeader(String name, String value)
    {
        headers.put(name, value);
    }


    /**
     * Add a param value pair in the request 
     **/
    public void addParamValuePair(String name, String value)
    {
        paramValues.add(name, value);
    }


    public Enumeration getHeaderNames()
    {
        return headers.keys();
    }

    
    public String getHeader(String param)
    {
        Object obj = headers.get(param);
        if(obj == null) return null;
        return (String)obj;
    }

    public Enumeration getParamValueNames()
    {
        return paramValues.keys();
    }

    public ArrayList getParamValue(String param)
    {
        Object obj = paramValues.getArray(param);
        if(obj == null) return null;
        return (ArrayList)obj;
    }

    public String getURL()
    {
        return url;
    }


//     public static void main(String[] args)
//     {
//         HttpRequest request = new HttpRequest();
//         request.setHeader("header1","header1value");
//         request.addParamValuePair("name","thai");
//         request.addParamValuePair("name","dang");
//         request.addParamValuePair("age","27");

//         Enumeration enum = request.getHeaderNames();
//         while(enum.hasMoreElements())
//             System.out.println(request.getHeader((String)enum.nextElement()));

//         ArrayList array = request.getParamValue("name");
//         System.out.println(array);

//         array = request.getParamValue("age");
//         System.out.println(array);
//     }
}
