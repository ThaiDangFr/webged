package openplatform.tools;

import java.util.*;

public class IteratorEnumeration implements Enumeration 
{
    private Iterator iterator;

    public IteratorEnumeration(Iterator iterator)
    {
        this.iterator = iterator;
    }

    public boolean hasMoreElements()
    {
        return iterator.hasNext();
    }

    public Object nextElement() throws NoSuchElementException
    {
        return iterator.next();
    }
}
