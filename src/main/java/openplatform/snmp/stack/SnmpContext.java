package openplatform.snmp.stack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
/**
 * This class contains the SNMP v1 context that is needed by every Pdu to
 * send a SNMP v1 request.
 *
 * <p>
 * <code>destroy()</code> should be called when the context is no longer
 * used. This is the only way the threads will be stopped and garbage
 * collected.
 * </p>
 *
 * @see SnmpContextFace
 * @see SnmpContextPool
 */
public class SnmpContext extends AbstractSnmpContext 
implements SnmpContextFace, Cloneable
{

    /**
     * The default community name. This is <em>public</em>.
     */
    public final static String Default_Community = "public";

    String community = Default_Community;

/**
 * Constructor.
 *
 * @param host The host to which the Pdu will send
 * @param port The port where the SNMP server will be
 * @see AbstractSnmpContext#AbstractSnmpContext(String, int)
 */
public SnmpContext(String host, int port) throws java.io.IOException
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
public SnmpContext(String host, int port, String typeSocketA) 
throws java.io.IOException
{
    super(host, port, typeSocketA);
}

public int getVersion()
{
    return AsnObject.SNMP_VERSION_1;
}

/**
 * Returns the community name.
 */
public String getCommunity()
{
    return community;
}

/**
 * Sets the community name.
 * This community name will be used for all PDU send with this context.
 * The default community name is <em>public</em>.
 *
 * @see #Default_Community
 */
public void setCommunity(String newCommunity)
{
    community = newCommunity;
}

/**
 * Encodes a pdu packet. 
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
        ByteArrayOutputStream bay = enc.EncodeSNMP(this, msg_type, rId, errstat,
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
 * Encodes a Trap1 pdu packet. 
 */
public byte[] encodePacket(byte msg_type, String enterprise, 
      byte[] IpAddress, int generic_trap, int specific_trap, 
      long timeTicks, Enumeration ve) 
      throws java.io.IOException, EncodingException
{
    byte [] packet = null;
    AsnEncoder enc = new AsnEncoder();
    ByteArrayOutputStream bay = enc.EncodeSNMP(this, msg_type, enterprise, 
          IpAddress, generic_trap, specific_trap, timeTicks, ve);

    int sz = bay.size();
    if (sz > maxRecvSize)
    {
        throw new EncodingException("Packet size ("+ sz 
            + ") is > maximum size (" + maxRecvSize +")");
    }
    packet = bay.toByteArray();
    return packet;
}

/**
 * Processes an incoming SNMP v1 message.
 */
protected void ProcessIncomingMessage(AsnDecoder rpdu, ByteArrayInputStream in)
throws DecodingException, IOException
{
    AsnPduSequence pduSeq = rpdu.DecodeSNMP(in, getCommunity());
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
    AsnTrapPduv1Sequence pduSeq = rpdu.DecodeTrap1Pdu(in, getCommunity());

    TrapPduv1 trapPdu = new OneTrapPduv1(this);
    trapPdu.fillin(pduSeq);
    return trapPdu;
}

/**
 * Returns a clone of this SnmpContext.
 *
 * @exception CloneNotSupportedException Thrown when the constructor
 * generates an IOException
 */
public Object clone() throws CloneNotSupportedException
{
    SnmpContext clContext = null;
    try
    {
        clContext = new SnmpContext(hostAddr, hostPort, typeSocket);
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
    StringBuffer buffer = new StringBuffer("SnmpContext[");
    buffer.append("host=").append(hostAddr);
    buffer.append(", port=").append(hostPort);
    buffer.append(", socketType=").append(typeSocket);
    buffer.append(", community=").append(community);
    buffer.append("]");
    return buffer.toString();
}

}
