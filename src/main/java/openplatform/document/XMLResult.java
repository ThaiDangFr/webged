package openplatform.document;

import java.util.*;
import openplatform.database.*;

/**
 * Class XMLResult
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class XMLResult
{
    private String value;
    private ParamValuePair attr = new ParamValuePair();


    public void addAttribute(String name, String value)
    {
        attr.set(name, value);
    }

    public String getAttribute(String name)
    {
        return attr.get(name);
    }

    public String[] attributeKeys() { return attr.keys(); }
    

    public int attributesLength()
    {
        return attr.size();
    }

    public void clearAttributes()
    {
        attr.reset();
    }

    public void removeAttribute(String key)
    {
        attr.removeWithKey(key);
    }



    public String getValue()
    {
        return value;
    }

    public void setValue(String avalue)
    {
        value=avalue;
    }
    
    /**
     * Used for debug purpose
     */
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(value).append("(");

        String[] keys = attributeKeys();
        int len = keys.length;
        for(int i=0; i<len; i++)
        {
            sb.append(keys[i]).append("=").append(getAttribute(keys[i]));
               
            if(i<len-1) sb.append(",");
        }

        sb.append(")");

        return sb.toString();
    }

}
