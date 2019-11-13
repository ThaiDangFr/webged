package openplatform.tools;

import java.util.ArrayList;

/**
 * Class StringArrayList
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class StringArrayList extends ArrayList
{
    public String getString(int index)
    {
        return (String)get(index);
    }


    public boolean addString(String string)
    {
        return add(string);
    }
}
