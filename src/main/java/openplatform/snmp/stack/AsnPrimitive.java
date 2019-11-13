package openplatform.snmp.stack;

import java.io.*;
import java.util.*;

/**
 * This class represents the Exception values for SNMP v2c, v3:
 * SNMP_VAR_NOSUCHOBJECT, SNMP_VAR_NOSUCHINSTANCE, SNMP_VAR_ENDOFMIBVIEW
 *
 * @see #SNMP_VAR_NOSUCHOBJECT
 * @see #SNMP_VAR_NOSUCHINSTANCE
 * @see #SNMP_VAR_ENDOFMIBVIEW
 */
public class AsnPrimitive extends AsnObject
{

    private byte type;

    /** 
     * Default Constructor.
     *
     * @param t The primitive type
     * @see #SNMP_VAR_NOSUCHOBJECT
     * @see #SNMP_VAR_NOSUCHINSTANCE
     * @see #SNMP_VAR_ENDOFMIBVIEW
     */
    public AsnPrimitive(byte t) 
    {
        type = t;
    }

    /** 
     * Returns the string representation of the AsnPrimitive.
     *
     * @return The string of the AsnPrimitive
     */
    public String toString()
    {
        String str = "AsnPrimitive ";
        if (type == SNMP_VAR_NOSUCHOBJECT)
        {
            str = "No such object";
        }
        else if (type == SNMP_VAR_NOSUCHINSTANCE)
        {
            str = "No such instance";
        }
        else if (type == SNMP_VAR_ENDOFMIBVIEW)
        {
            str = "End of MIB view";
        }
        return str;
    }

    void write(OutputStream out, int pos) throws IOException 
    {
        AsnBuildHeader(out, type, 0);
    }


    /**
     * Compares this object to the specified object.  The result is
     * <code>true</code> if and only if the argument is not
     * <code>null</code> and is an <code>AsnPrimitive</code> object that
     * contains the same <code>type</code> as this object.
     *
     * @param   obj   the object to compare with.
     * @return  <code>true</code> if the objects are the same;
     *          <code>false</code> otherwise.
     */
    public boolean equals(Object obj) 
    {
        if (obj instanceof AsnPrimitive) 
        {
            return type == ((AsnPrimitive)obj).type;
        }
        return false;
    }


    /**
     * Returns a hash code for this <code>AsnPrimitive</code>.
     *
     * @return  a hash code value for this object, equal to the 
     *          type represented by this 
     *          <code>AsnPrimitive</code> object. 
     */
    public int hashCode() 
    {
        return (int) type;
    }
}
