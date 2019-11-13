package openplatform.session;

import java.util.*;
import openplatform.regexp.*;
import openplatform.tools.*;

/**
 * Class JSPMessage
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class JSPMessage
{
    private ArrayList msg = new ArrayList();
    //private ArrayList i18n = new ArrayList();

    private int status = 0; // 0 1 2
    //private Regexp pattern = new Regexp("I18N\\|(.*)\\|(.*)");

    public void setText(String text)
    {
        if(text == null) return;

        /*
        if(pattern.matches(text))
        {
            StringArrayList sal = pattern.getMatchingResults();
            ParamValue pv = new ParamValue();
            pv.setParam(sal.getString(1));
            pv.setValue(sal.getString(2));
            i18n.add(pv);
        }
        else
        {
        */
        
        msg.add(text);
            
            /*
        }
        */
    }

    /*
    public ArrayList getI18N()
    {
        ArrayList res = (ArrayList)i18n.clone();
        i18n.clear();
        return res;
    } 
    */
    
    
    public ArrayList getList()
    {
        ArrayList res = (ArrayList)msg.clone();
        msg.clear();
        return res;
    }

    public void setStatus(String status)
    {
        if("warning".equalsIgnoreCase(status)) this.status = 1;
        else if("error".equalsIgnoreCase(status)) this.status = 2;
        else this.status = 0;
    }
    
    public boolean isInfo()
    {
        if(status == 0) return true;
        else return false;
    }

    public boolean isWarning()
    {
        if(status == 1) return true;
        else return false;
    }

    public boolean isError()
    {
        if(status == 2) return true;
        else return false;
    }
}
