package openplatform.snmp.stack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;


/**
 * This class contains the SNMP v2c context that is needed by every Pdu to
 * send a SNMP v2c request.
 *
 * <p>
 * <code>destroy()</code> should be called when the context is no longer
 * used. This is the only way the threads will be stopped and garbage
 * collected.
 * </p>
 *
 * @see SnmpContextv2cFace
 * @see SnmpContextv2cPool
 */
public class SnmpContextv2c extends SnmpContext 
implements SnmpContextv2cFace, Cloneable
{

/**
 * Constructor.
 *
 * @param host The host to which the Pdu will send
 * @param port The port where the SNMP server will be
 * @see AbstractSnmpContext#AbstractSnmpContext(String, int)
 */
public SnmpContextv2c(String host, int port) throws java.io.IOException
{
    super(host, port);
}

/**
 * Constructor.
 *
 * @param host The host to which the Pdu will send
 * @param port The port where the SNMP server will be
 * @param typeSocketA The type of socket to use. 
 * @see AbstractSnmpContext#AbstractSnmpContext(String, int, String)
 * @see SnmpContextBasisFace#STANDARD_SOCKET
 * @see SnmpContextBasisFace#KVM_SOCKET
 */
public SnmpContextv2c(String host, int port, String typeSocketA) 
throws java.io.IOException
{
    super(host, port, typeSocketA);
}

public int getVersion()
{
    return AsnObject.SNMP_VERSION_2c;
}


/**
 * Encodes a SNMP v2c pdu packet. 
 */
public byte[] encodePacket(byte msg_type, int rId, int errstat, 
      int errind, Enumeration ve) 
      throws java.io.IOException, EncodingException
{
    byte [] packet = null;
    if (isDestroyed == true)
    {
        throw new EncodingException("Context can no longer be used, since it is already destroyed");
    }
    else
    {
        AsnEncoder enc = new AsnEncoder();
        ByteArrayOutputStream bay = enc.EncodeSNMPv2c(this, msg_type, rId, errstat,
              errind, ve);

        int sz = bay.size();
        if (sz > maxRecvSize)
        {
            throw new EncodingException("Packet size ("+ sz 
                + ") is > maximum size (" + maxRecvSize +")");
        }
        packet = bay.toByteArray();
    }
    return packet;
}

/**
 * Processes an incoming SNMP v2c message.
 */
protected void ProcessIncomingMessage(AsnDecoder rpdu, ByteArrayInputStream in)
throws DecodingException, IOException
{
    AsnPduSequence pduSeq = rpdu.DecodeSNMPv2c(in, getCommunity());
    if (pduSeq != null)
    {
        // got a message
        Integer rid = new Integer(pduSeq.getReqId());
        Pdu answ = getPdu(rid);
        if (answ != null)
        {
            answ.fillin(pduSeq);
        }
        else
        {
            if (AsnObject.debug > 3)
            {
                System.out.println("ProcessIncomingMessage(): No Pdu with reqid " + rid.intValue());
            }
        }
    }
    else
    {
        if (AsnObject.debug > 3)
        {
            System.out.println("ProcessIncomingMessage(): Error - missing seq input");
        }
    }
}

public Pdu processIncomingTrap(byte [] message) 
throws DecodingException, IOException
{
    AsnDecoder rpdu = new AsnDecoder();
    ByteArrayInputStream in = new ByteArrayInputStream(message);
    AsnPduSequence pduSeq = rpdu.DecodeSNMPv2c(in, getCommunity());

    TrapPduv2 trapPdu = new OneTrapPduv2(this);
    trapPdu.fillin(pduSeq);
    return trapPdu;
}

/**
 * Returns a clone of this SnmpContextv2c.
 *
 * @exception CloneNotSupportedException Thrown when the constructor
 * generates an IOException
 */
public Object clone() throws CloneNotSupportedException
{
    SnmpContextv2c clContext = null;
    try
    {
        clContext = new SnmpContextv2c(hostAddr, hostPort, typeSocket);
        clContext.setCommunity(new String(community));
    }
    catch (java.io.IOException exc)
    {
        throw new CloneNotSupportedException("IOException " 
            + exc.getMessage());
    }
    return clContext;
}

/**
 * Returns a string representation of the object.
 * @return The string
 */
public String toString()
{
    StringBuffer buffer = new StringBuffer("SnmpContextv2c[");
    buffer.append("host=").append(hostAddr);
    buffer.append(", port=").append(hostPort);
    buffer.append(", socketType=").append(typeSocket);
    buffer.append(", community=").append(community);
    buffer.append("]");
    return buffer.toString();
}

}
