package openplatform.snmp.stack;

import java.io.*;
import java.util.*;


/**
 * This class represents ASN.1 32-bit unsigned integer. It is used for
 * TIMETICKS, COUNTER, GAUGE.
 *
 * @see AsnObject#TIMETICKS
 * @see AsnObject#COUNTER 
 * @see AsnObject#GAUGE
 *
 */
public class AsnUnsInteger extends AsnObject
{
    protected long value;

    /** 
     * Constructor. The type of the AsnUnsInteger defaults to TIMETICKS.
     *
     * @param v The value of the AsnUnsInteger
     * @see SnmpConstants#TIMETICKS
     */
    public AsnUnsInteger(long v) 
    { 
        this(v, TIMETICKS);
    }

    /** 
     * Constructor to create a specific type of AsnUnsInteger.
     *
     * @param v The value of the AsnUnsInteger
     * @param t The type of the AsnUnsInteger
     * @see AsnObject#TIMETICKS
     * @see AsnObject#COUNTER 
     * @see AsnObject#GAUGE
     */
    public AsnUnsInteger(long v, byte t) 
    { 
        this.value = v; 
        this.type = t;
    }


    /** 
     * Constructor.
     *
     * @param in The input stream from which the value should be read
     * @param len The length of the AsnUnsInteger
     */
    public AsnUnsInteger(InputStream in, int len) throws IOException
    {
        byte data[] = new byte[len];
        if (len != in.read(data,0,len))
        {
            throw new IOException("AsnUnsInteger(): Not enough data");
        }
        long val = bytesToLong(data);
        this.value = val;
    }

    /** 
     * Returns the value representation of the AsnUnsInteger.
     *
     * @return The value of the AsnUnsInteger
     */
    public long getValue()
    {
        return value;
    }

    /** 
     * Returns the string representation of the AsnUnsInteger.
     *
     * @return The string of the AsnUnsInteger
     */
    public String toString()
    {
        return (String.valueOf(value));
    }

    /** 
     * Get number of bytes integer occupies.
     */
    int size()
    {
        int  count, empty = 0x00, sign = 0x00;

        if (value < 0)
        {
            empty = 0xFF;
            sign  = 0x80;
        }

        // 32-bit integer.. change to 56 to write 64-bit long
        // loop through bytes in value while it is 'empty'
        for(count=24; count>0; count-=8)
        {
            if ( ((value >> count) & 0xFF) != empty) break;
        }

        // Check sign bit.. make sure negative's MSB bit is 1,
        // positives is 0
        // (0x00000080 = 0x00 0x80) 0xFFFFFF01 => 0xFF 0x01
        // (0x0000007F = 0x7F)      0xFFFFFF80 => 0x80
        if (((value >> count) & 0x80) != sign) count += 8;
        return (count>>3)+1;
    }


    /** 
     * Output integer.
     */
    void write(OutputStream out, int pos) throws IOException
    {
        int  count, empty = 0x00, sign = 0x00;

        if (value < 0)
        {
            empty = 0xFF;
            sign  = 0x80;
        }

        // Get count
        for(count=24; count>0; count-=8)
        {
            if ( ((value >> count) & 0xFF) != empty) break;
        }
        if (((value >> count) & 0x80) != sign) count += 8;

        // Build header and write value
        AsnBuildHeader(out, type, (count>>3)+1);

        if (debug > 10)
        {
            System.out.println("\tAsnUnsInteger(): value = " + value
                + ", pos = " + pos);
        }

        for(; count>=0; count-=8)
        {
            out.write((byte)((value >> count) & 0xFF));
        }
    }

    /**
     * Changes an array of bytes into a long.
     * Thanks to Julien Conan (jconan@protego.net) for improving 
     * this method.
     *
     * @param data the array of bytes
     * @return the int represenation of the array
     */
    protected long bytesToLong(byte[] data) throws IOException
    {
        DataInputStream dis = new DataInputStream(
              new ByteArrayInputStream(data));

        long val = 0;
        int size = data.length;

        for (int n=0; n<size; n++)
        {
            val = (val << 8) + dis.readUnsignedByte();
        }

        return val;
    }

    /**
     * Compares this object to the specified object.  The result is
     * <code>true</code> if and only if the argument is not
     * <code>null</code> and is an <code>AsnUnsInteger</code> object that
     * contains the same <code>long</code> value as this object.
     *
     * @param   obj   the object to compare with.
     * @return  <code>true</code> if the objects are the same;
     *          <code>false</code> otherwise.
     */
    public boolean equals(Object obj) 
    {
        if (obj instanceof AsnUnsInteger) 
        {
            return value == ((AsnUnsInteger)obj).value;
        }
        return false;
    }


    /**
     * Returns a hash code for this <code>AsnUnsInteger</code>.
     *
     * @return  a hash code value for this object, equal to the 
     *          hash of the primitive <code>long</code> value represented 
     *          by this <code>AsnUnsInteger</code> object. 
     */
    public int hashCode() 
    {
        // nicked from Long.hashCode
        return (int)(value ^ (value >>> 32));
    }
}
