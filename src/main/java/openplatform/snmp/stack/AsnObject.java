package openplatform.snmp.stack;

import java.io.*;
import java.util.*;

/**
 * This class represents the ASN.1 base class.
 * SMIv1 <a href="http://ietf.org/rfc/rfc1155.txt">RFC 1155</a>.
 * SMIv2 <a href="http://ietf.org/rfc/rfc2578.txt">RFC 2578</a>.
 *
 * <p><pre>
 * &lt;ASN Object&gt; = &lt;type&gt; &lt;length&gt; &lt;ASN Object&gt;
 * or
 * &lt;ASN Object&gt; = &lt;type&gt; &lt;length&gt; &lt;value&gt;
 * </pre></p>
 *
 */

public abstract class AsnObject extends Object implements SnmpConstants
{

    public static int debug = 0;
    /** 
     * The type of object.
     */
    protected byte type;

    /** 
     * The starting position of the AsnObject in the SNMP sequence. That
     * is the position of the 'type' byte. 
     */
    protected int startPos = 0;

    /** 
     * The length of the header. That is the number of bytes of the 'type'
     * and 'length' fields. Since there are multiple valid encodings for
     * a given AsnObject, this length is not fixed!
     */
    protected int headerLength = 0; 

    /** 
     * The length of the actual contents. That is the same as the value of
     * the 'length' field.
     */
    protected int contentsLength = 0;

    /** 
     * Flag to signal the object is of a correct type.
     */
    protected boolean isCorrect = true;

    abstract void write (OutputStream out, int pos) throws IOException;

    /**
     * Returns a string representation of the object.
     * @return The string
     */
    public abstract String toString();

/**
 * Sets the new, global level of debug information for the stack package.
 * The default value is zero, i.e. no debug info at all. All debug
 * messages are printed to System.out.
 * <p>
 * The following messages will appear when debug is &gt; (greater than)
 * <ul>
 * <li><code>0</code> - IOExceptions that won't be thrown to the user</li>
 * <li><code>1</code> - DecodingExceptions that won't be thrown to the user (received message with wrong SNMP version)</li>
 * <li><code>2</code> - DecodingExceptions when trying to decode traps</li>
 * <li><code>3</code> - ProcessIncomingMessage errors: (corresponding Pdu cannot be found)</li> 
 * <li><code>4</code> - Discovery of EngineId, Timeline</li>
 * <li><code>5</code> - Receiving traps, version/host do not correspond</li>
 * <li><code>6</code> - Pdu messages, sending etc</li>
 * <li><code>10</code> - Asn decoding of incoming messages</li>
 * <li><code>12</code> - Transmitter messages, Socket messages</li>
 * <li><code>15</code> - Not so important IOExceptions, like InterruptedIOException</li>
 * </ul>
 * </p>
 *
 * @param newDebug the new debug value
 */
public static void setDebug(int newDebug)
{
    debug = newDebug;
}

    AsnObject AsnReadHeader(InputStream in) throws IOException
    {
        return AsnReadHeader(in, 0);
    }

    /**
     * @param pos The starting position, i.e. the pos the 'type' byte.
     */
    AsnObject AsnReadHeader(InputStream in, int pos) throws IOException
    {
        int len, got;
        AsnObject ret = null;
        int had = in.available();

        //Type byte
        type = (byte) in.read();
        if (type != -1)
        {
            len = getLengthPacket(in);
            int off = in.available();
            int headLength = had - off;

            got = 0;
            byte body[] = new byte[len];
            if (len > 0)
            {
                got = in.read(body, 0, len);
            }
            if (got > -1)
            {
                // Always decode, unless in.read returned an error 
                // (i.e. got == -1):
                //
                // 1.
                // If we haven't received all bytes (i.e. got < len)
                // we will still try and decode as much as we can:
                // Since body[] has the length 'len' (i.e. the
                // length according to the packet), it will be padded with
                // zero
                // 
                // 2.
                // If len was 0, got would have been been -1, but since
                // no read was performed, got is 0.
                // A len of 0 is a valid case however.

                ByteArrayInputStream buf = new ByteArrayInputStream(body);
                ret = AsnMakeMe(buf, type, len, pos, headLength);

                if (got != len)
                {
                    ret.isCorrect = false;
                    if (debug > 10)
                    {
                        System.out.println("\nAsnObject.AsnReadHeader():"
                            + " incorrect packet. Length = " + (int) len
                            + ", Got = " + got);
                    }
                }
            }
            else
            {
                if (debug > 10)
                {
                    System.out.println("\nAsnObject.AsnReadHeader(): Length = " 
                        + (int) len
                        + ", Got = " + got 
                        + ". Not processing any further.");
                }
            }
        }
        return ret;
    }


    AsnObject AsnMakeMe(InputStream in, byte t, int len, int pos, 
        int headLength) throws IOException
    {
        AsnObject me = this;
        type = t;

        switch (type)
        {
          case CONS_SEQ :
            me = new AsnSequence(in, len, (pos+headLength));
            break;
          //case INFORM_REQ_MSG :
          case GET_RSP_MSG :
          case GET_RPRT_MSG :
          case TRPV2_REQ_MSG :
            me = new AsnPduSequence(in, len, (pos+headLength));
            break;
          case TRP_REQ_MSG :
            me = new AsnTrapPduv1Sequence(in, len, (pos+headLength));
            break;
          case ASN_INTEGER :
            me = new AsnInteger(in, len);
            break;
          case TIMETICKS :
          case COUNTER :
          case GAUGE :
            me = new AsnUnsInteger(in, len);
            break;
          case COUNTER64 :
            me = new AsnUnsInteger64(in, len); // TODO test
            break;
          case ASN_OBJECT_ID :
            me = new AsnObjectId(in, len);
            break;
          case IPADDRESS :
          case ASN_OCTET_STR :
          case OPAQUE :
            me = new AsnOctets(in, len);
            break;
          case ASN_NULL :
            me = new AsnNull(in, len);
            break;
          case NSAP_ADDRESS :
            me = new AsnOctets(in,len);
            break;
          case UINTEGER32 :
            me = new AsnUnsInteger(in,len);
            break;
          case SNMP_VAR_NOSUCHOBJECT:
          case SNMP_VAR_NOSUCHINSTANCE:
          case SNMP_VAR_ENDOFMIBVIEW:
            me = new AsnPrimitive(type);
            break;
          default :
            if (debug > 0)
            {
                System.out.println("AsnObject.AsnMakeMe():"
                    + " Bad Type 0x" + SnmpUtilities.toHex(type));
            }
            me = new AsnNull(in, len);
            me.isCorrect = false;
            break;
        }
        me.type = type;
        me.startPos = pos;
        me.headerLength = headLength;
        me.contentsLength = len;

        if (debug > 10)
        {
            System.out.println("AsnObject.AsnMakeMe():" 
                  + " headerLength = " + me.headerLength
                  + ", contentsLength = " + me.contentsLength);
            System.out.println("\ttype = 0x" 
                  + SnmpUtilities.toHex(type) 
                  + ", " + me.getClass().getName() 
                  + ": value = " + me.toString()
                  + ", startPos = " + me.startPos
                  + ", correct = " + me.isCorrect);
        }
        return me;
    }

    /** Add a child to the sequence
     */
    AsnObject add (AsnObject child)
    {
        return child;
    }

    /** recursively look for a pduSequence object
     *  if not overridden - then not a sequence -
     */
    AsnObject findPdu()
    {
        return null;
    }

    /** recursively look for a pduSequence object
     *  if not overridden - then not a sequence -
     */
    AsnObject findTrapPduv1()
    {
        return null;
    }

    /** 
     * Output ASN header.
     */
    void AsnBuildHeader(OutputStream out, byte t, int length) 
          throws IOException
    {
        int count;

        type = t;
        // Type byte
        out.write(type);

        // Length bytes
        count = getLengthBytes(length);
        headerLength = count + 1;
        contentsLength = length;
        if (debug > 10)
        {
            System.out.println("AsnBuildHeader(): "
                + "type = 0x" + SnmpUtilities.toHex(type)
                + ", headerLength = " + headerLength
                + ", contentsLength = " + contentsLength);
        }

        if (count > 1)
        {
            // Write long form prefix byte
            --count;
            byte tmp = (byte) (0x80 | (byte) count);
            out.write(tmp);
        }
        while(count!=0)
        {
            // Write length bytes
            out.write((byte)((length >> (--count << 3)) & 0xFF));
        }
    }

    int size() 
    { 
        return 0; 
    }

    /** 
     * Returns length field size for a packet length.
     * Returns:
     * 1   if length will fit in short form (0x00-0x7f)
     * 2+  if length >= 0x80
     */
    int getLengthBytes(int length)
    {
        int mask, count;

        if (length < 0x80)
        {
            // Short form.. 1 byte
            return 1;
        }
        else
        {
          // Long form.. prefix byte + length bytes
          mask = 0xFF000000;
          for(count=4; (length&mask)==0; count--)
          {
              mask >>= 8;
          }
          return count+1;
        }
    }


    int getLengthPacket(InputStream in) throws IOException
    {
        int length = 0;
        byte mask =(byte) 0x7f;
        byte len = (byte) in.read();

        if ((0x80 & len) != 0)
        {
            // long form

            int count = (mask & len);
            if (count < 4)
            {
                byte data[] = new byte[count];
                int n = in.read(data, 0, count);
                if (n !=  count)
                {
                    throw new IOException("AsnObject.getLengthPacket(): Not enough data");
                } 
                else 
                {
                    /*
                     * Thanks to Julien Conan (jconan@protego.net) 
                     * for improving this code.
                     */
                    DataInputStream dis = new DataInputStream(
                          new ByteArrayInputStream(data));

                    for (n=0; n<count; n++)
                    {
                        length = (length << 8) + dis.readUnsignedByte();
                    }

                }
            }
        } 
        else
        {
            // short form
            length = (int) (mask & len);
        }
        return (length);
    }

    int getContentsPos()
    {
        return startPos + headerLength;
    }
    int getContentsLength()
    {
        return contentsLength;
    }

    /**
     * Returns the response type.
     * @return The response type.
     * @see #getRespTypeString()
     */
    public byte getRespType()
    {
        return type;
    }

    /**
     * Returns the response type as string.
     * @return The response type.
     * @see #getRespType()
     */
    public String getRespTypeString()
    {
        String str = "";
        switch(type)
        {
            case ASN_BOOLEAN:
                str = "ASN_BOOLEAN";
                break;
            case ASN_INTEGER:
                str = "ASN_INTEGER";
                break;
            case ASN_BIT_STR:
                str = "ASN_BIT_STR";
                break;
            case ASN_OCTET_STR:
                str = "ASN_OCTET_STR";
                break;
            case ASN_NULL:
                str = "ASN_NULL";
                break;
            case ASN_OBJECT_ID:
                str = "ASN_OBJECT_ID";
                break;
            case ASN_SEQUENCE:
                str = "ASN_SEQUENCE";
                break;
            case ASN_SET:
                str = "ASN_SET";
                break;
            case ASN_UNIVERSAL:
                str = "ASN_UNIVERSAL";
                break;
            case ASN_PRIVATE:
                str = "ASN_PRIVATE";
                break;
            case ASN_CONSTRUCTOR:
                str = "ASN_CONSTRUCTOR";
                break;
            case ASN_EXTENSION_ID:
                str = "ASN_EXTENSION_ID";
                break;
            case IPADDRESS:
                str = "IPADDRESS";
                break;
            case COUNTER:
                str = "COUNTER";
                break;
            case GAUGE:
                str = "GAUGE";
                break;
            case TIMETICKS:
                str = "TIMETICKS";
                break;
            case OPAQUE:
                str = "OPAQUE";
                break;
            case NSAP_ADDRESS:
                str = "NSAP_ADDRESS";
                break;
            case COUNTER64:
                str = "COUNTER64";
                break;
            case UINTEGER32:
                str = "UINTEGER32";
                break;
            default:
                str = "0x" + SnmpUtilities.toHex(type);
        }
        return str;
    }
}


