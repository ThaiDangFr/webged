package openplatform.database;

import java.util.*;

/**
 * Class ParamValuePair
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ParamValuePair
{
    private HashMap hashmap;

    
    public ParamValuePair()
    {
        hashmap = new HashMap();
    }


    public void set(String param, Object value)
    {
        if(param!=null)
        {
            hashmap.put(param,value);
        }
    }


    public void removeWithKey(String param)
    {
        if(param!=null)
        {
            hashmap.remove(param);
        }
    }


    public void reset()
    {
        hashmap.clear();
    }


    public String get(String key)
    {
        Object obj = hashmap.get(key);
        if(obj==null)
            return null;
        else
            return (String)obj;
    }

    public Object getObject(String key)
    {
        Object obj = hashmap.get(key);
        if(obj==null)
            return null;
        else
            return obj;
    }
    
    
    public String[] keys()
    {
        Set set = hashmap.keySet();
        Object[] obj = set.toArray();
        String[] resp = new String[obj.length];
        for(int i=0;i<resp.length;i++)
            resp[i] = (String)obj[i];

        return resp;
    }

    public int size()
    {
        return hashmap.size();
    }

}
