package openplatform.snmp;

import openplatform.tools.Debug;

/**
 * Class SnmpResult
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class SnmpNode
{
    public static final int TYPE_NULL = -1;
    public static final int TYPE_INTEGER = 0;
    public static final int TYPE_OID = 1;
    public static final int TYPE_STRING = 2;
    public static final int TYPE_UNSINTEGER = 3;
    public static final int TYPE_UNSINTEGER64 = 4;
    

    private String oid;
    private String value;
    private int type;


    public SnmpNode()
    {
        oid=null;
        value=null;
        type=TYPE_NULL;
    }


    /**
     * Return the value as a string if the type is INTEGER.
     * Return -1 in case of error
     **/
    public long getValueAsLong()
    {
        if(type != TYPE_INTEGER)
            return -1;

        try
        {
            return Long.parseLong(value);
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
            return -1;
        }
    }


    public int getType()
    {
        return type;
    }


    public void setType(int atype)
    {
        type=atype;
    }

  
    public String getValue()
    {
        return value;
    }

    public void setValue(String avalue)
    {
        value=avalue;
    }


    public String getOid()
    {
        return oid;
    }


    public void setOid(String aoid)
    {
        oid=aoid;
    }


    public String toString()
    {
        return value;
    }
}
