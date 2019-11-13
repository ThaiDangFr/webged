package openplatform.tools;

import java.util.ArrayList;


/**
 * Class ErrorList
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ErrorList extends ArrayList
{

    public void addError(String st)
    {
    }

    
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        int len = this.size();
        for(int i=0; i<len; i++)
        {
            String err = (String)this.get(i);
            sb.append(err);

            if(i<len-1) sb.append(",");
        }

        return sb.toString();
    }
    





}
