package openplatform.tools;

import java.util.*;

/**
 * Class Multimap
 *
<pre>
Sample :

        Multimap mm = new Multimap();
        mm.add("dadou","30");
        mm.add("dadou","yo");
        mm.add("toto","oui");

        String[] res = mm.get("dadou");
        String[] res2 = mm.get("toto");
        String[] res3 = mm.get("existepas");

        for(int i=0;i&lt;res.length;i++)
            System.out.println(res[i]);

        System.out.println();

        for(int i=0;i&lt;res2.length;i++)
            System.out.println(res2[i]);
        
        System.out.println();

        for(int i=0;res3!=null && i&lt;res3.length;i++)
            System.out.println(res3[i]);
</pre>
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Multimap
{
    private HashMap hashmap;

    public Multimap()
    {
        hashmap = new HashMap(25);
    }

    /**
     * return false if keys is null
     **/
    public boolean contains(String key)
    {
        if(key==null)
            return false;

        return hashmap.containsKey(key);
    }

    public void add(String key, String value)
    {
        if(key==null || value==null)
            return;

        if(!hashmap.containsKey(key))
        {
            ArrayList array = new ArrayList();
            array.add(value);
            hashmap.put(key,array);
        }
        else
        {
            Object obj = hashmap.get(key);
            ArrayList array = (ArrayList)obj;
            array.add(value);
        }
    }

    public void clear()
    {
        hashmap.clear();
    }


    public ArrayList remove(String key)
    {
        if(key==null)
            return null;

        Object obj = hashmap.remove(key);

        if(obj==null)
            return null;

        ArrayList array = (ArrayList)obj;    
        return array;
    }


    /**
     * return null if nothing found
     **/
    public String[] get(String key)
    {
        if(key==null)
            return null;

        Object obj = hashmap.get(key);

        if(obj==null)
            return null;

        ArrayList array = (ArrayList)obj;
        Object[] objt = array.toArray();
        String[] st = new String[objt.length];
        for(int i=0;i<st.length;i++)
            st[i]=(String)objt[i];

        return st;
    }


    /**
     * return null if nothing found
     **/
    public ArrayList getArray(String key)
    {
         Object obj = hashmap.get(key);

        if(obj==null)
            return null;

        ArrayList array = (ArrayList)obj;    
        return array;
    }


    public Enumeration keys()
    {
        Set set = hashmap.keySet();
        return new IteratorEnumeration(set.iterator());
    }
}
