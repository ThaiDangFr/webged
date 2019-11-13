package openplatform.snmp.stack;

import java.io.*;
import java.util.*;

/**
 * This class represents the ASN.1 Null object
 *
 */
public class AsnNull extends AsnObject
{

    /** 
     * Default Constructor.
     */
    public AsnNull() 
    {
    }

    /** 
     * Constructor.
     *
     * @param in The input stream from which the value should be read
     * @param len The length of the AsnInteger
     */
    public AsnNull(InputStream in, int len) 
    {
        this();
    }

    /** 
     * Returns the string representation of the AsnNull.
     *
     * @return The string of the AsnNull
     */
    public String toString()
    {
        return "AsnNull";
    }

    void write(OutputStream out, int pos) throws IOException 
    {
        AsnBuildHeader(out, ASN_NULL, 0);
    }

/**
 * Compares this object to the specified object.
 * The result is <code>true</code> if and only if the argument is not
 * <code>null</code> and is a <code>AsnNull</code> object.
 *
 * @param anObject the object to compare this <code>AsnNull</code> 
 *                 against.
 * @return <code>true</code> if the <code>AsnNull </code>are equal;
 *         <code>false</code> otherwise.
 */
public boolean equals(Object anObject) 
{
    if (this == anObject) 
    {
        return true;
    }
    if (anObject instanceof AsnNull) 
    {
        AsnNull anotherNull = (AsnNull)anObject;
        return true;
    }
    return false;
}


/**
 * Returns a hash code for this object. 
 * @return a hash code value for this object.
 */
public int hashCode() 
{
    int h = 5;
    return h;
}

}
