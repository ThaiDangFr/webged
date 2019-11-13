package openplatform.tools;

import java.util.*;

/**
 * Class MultimapObject
 *
<pre>
Sample :

        Multimap mm = new Multimap();
        mm.add("dadou","30");
        mm.add("dadou","yo");
        mm.add("toto","oui");

        System.out.println(mm.getArray("dadou"));
        System.out.println(mm.getArray("toto"));
        System.out.println(mm.getArray("existe pas"));
</pre>
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class MultimapObject
{
    private Hashtable hashtable;

    public MultimapObject()
    {
        hashtable = new Hashtable(25);
    }

    /**
     * return false if keys is null
     **/
    public boolean contains(String key)
    {
        if(key==null)
            return false;

        return hashtable.containsKey(key);
    }

    public void add(String key, Object value)
    {
        if(key==null || value==null)
            return;

        if(!hashtable.containsKey(key))
        {
            ArrayList array = new ArrayList();
            array.add(value);
            hashtable.put(key,array);
        }
        else
        {
            Object obj = hashtable.get(key);
            ArrayList array = (ArrayList)obj;
            array.add(value);
        }
    }

    public void clear()
    {
        hashtable.clear();
    }



    /**
     * return null if nothing found
     **/
    public ArrayList get(String key)
    {
         Object obj = hashtable.get(key);

        if(obj==null)
            return null;

        ArrayList array = (ArrayList)obj;    
        return array;
    }

      	

    public int size()
    {
        return hashtable.size();
    }


    public Enumeration keys()
    {
        return hashtable.keys();
    }


    public ArrayList remove(String key)
    {
        Object obj = hashtable.remove(key);
        if(obj == null) return null;
        else return (ArrayList)obj;
    }
}
