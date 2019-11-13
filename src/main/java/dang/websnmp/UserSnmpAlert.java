package dang.websnmp;

import java.util.*;
import openplatform.database.dbean.*;
import openplatform.database.*;
import openplatform.tools.*;

/**
 * UserSnmpAlert.java
 *
 *
 * Created: Tue Jun 21 14:58:54 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class UserSnmpAlert 
{
    private int offset;
    public static final int LIMIT = 20;


    public HashMap getPageOptions()
    {
        HashMap map = new OrdHashMap();

        int in = DBSnmpAlert.count(null,null);
        float f = (float)in/(float)LIMIT;
        int len = (int)f;

        if(in%LIMIT!=0) len++;

        for(int i=0; i<len; i++)
            map.put(Integer.toString(i), Integer.toString(i));
        
        if(map.size() == 1) return null;
        else return map;
    }

    


    public ArrayList getSnmpAlert()
    {
        ArrayList array = new ArrayList();

        SQLConstraint constr = new SQLConstraint();
        constr.setOrderBy(DBSnmpAlert.DATE);
        constr.setOrderByBiggestFirst(true);
        constr.setLimitOffset(offset*LIMIT);
        constr.setLimit(LIMIT);

        DBSnmpAlert[] alert = DBSnmpAlert.load(constr, null);
        if(alert == null) return array;

        int len = alert.length;
        
        for(int i=0; i<len; i++)
            array.add(new SnmpAlert(alert[i]));

        return array;
    }
    
    // GET / SET

    /**
     * Gets the value of offset
     *
     * @return the value of offset
     */
    public final int getOffset()
    {
        return this.offset;
    }

    /**
     * Sets the value of offset
     *
     * @param argOffset Value to assign to this.offset
     */
    public final void setOffset(final int argOffset)
    {
        this.offset = argOffset;
    }


}
