package openplatform.tools;

import java.util.*;

public class OrdHashtable extends Hashtable
{
    private Vector keys = new Vector();
    private Set setKeys = Collections.synchronizedSet(new LinkedHashSet());

    public Object put(Object key, Object value)
    {
        keys.addElement(key);
        setKeys.add(key);
        return super.put(key, value);
    }

    
    public Enumeration keys()
    {
        return keys.elements();
    }


    public Object get(int index)
    {
        Object obj = keys.get(index);
        return super.get(obj);
    }


    public Object remove(int index)
    {
        Object obj = keys.get(index);
        return remove(obj);
    }


    public Object remove(Object key)
    {
        keys.remove(key);
        setKeys.remove(key);
        return super.remove(key);
    }


    public Set keySet()
    {
        return setKeys;
    }


    public void putAll(Map m)
    {
        keys.addAll(m.keySet());
        setKeys.addAll(m.keySet());
        super.putAll(m);
    }
}
