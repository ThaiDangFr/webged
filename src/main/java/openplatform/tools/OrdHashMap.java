package openplatform.tools;

import java.util.*;

public class OrdHashMap extends HashMap
{
    private LinkedHashSet keys = new LinkedHashSet();

    public void clear()
    {
        super.clear();
        keys.clear();
    }
    

    public Object put(Object key, Object value)
    {
        keys.add(key);
        return super.put(key, value);
    }


    public void putAll(Map m)
    {
        keys.addAll(m.keySet());
        super.putAll(m);
    }


    public Object remove(Object obj)
    {
        keys.remove(obj);
        return super.remove(obj);
    }

    
//     public Set entrySet()
//     {
//         return super.entrySet();
//     }


    public Set keySet()
    {
        return keys;
    }
 

//     public Collection values()
//     {
//         return super.values();
//     }
}
