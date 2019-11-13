package openplatform.snmp.stack;

/**
 * This interface contains the SNMP context interface that is needed 
 * by every Pdu to send a SNMP v1, v2c and v3 request. The context also
 * provides functionality to receive traps.
 *
 * @see SnmpContext
 * @see SnmpContextv2c
 * @see SnmpContextv3
 *
 */
public interface SnmpContextBasisFace 
{

    /**
     * The default port number where SNMP requests are send to.
     */
    public final static int DEFAULT_PORT = 161;
    /**
     * The Standard Socket type.
     */
    public final static String STANDARD_SOCKET = "Standard";

    /**
     * The KVM Socket type.
     */
    public final static String KVM_SOCKET = "KVM";

    /**
     * The Maximum number of outstanding PDUs a context can handle at a
     * given moment in time.
     */
    final static int MAXPDU = 20; // if you have more than 20 oustanding PDUS
                                  // change the algorythm  :-)
    /**
     * The Maximum size of a message in octets.
     */
    final static int MSS = 1300;  // maximum recv size;

/**
 * Return the SNMP version of the context.
 *
 * @return The version
 */
public int getVersion();

/**
 * Returns the host
 *
 * @return The host
 */
public String getHost();

/**
 * Returns the port number
 *
 * @return The port no
 */
public int getPort();

/**
 * Returns the type socket 
 *
 * @return The type socket 
 */
public String getTypeSocket();

/**
 * Adds a pdu to the context. This is for internal use only and should
 * NOT be called by the developper. 
 * This is called by the the Pdu itself and is added to the interface to
 * cover the different kind of Contexts.
 */
public boolean addPdu(Pdu pdu)
throws java.io.IOException, PduException;

/**
 * Removes a pdu from the context. This is for internal use only and should
 * NOT be called by the developper. 
 * This is called by the the Pdu itself and is added to the interface to
 * cover the different kind of Contexts.
 */
public boolean removePdu(int requestId);

/**
 * Encodes a pdu. This is for internal use only and should
 * NOT be called by the developper. 
 * This is called by the the Pdu itself and is added to the interface to
 * cover the different kind of Contexts.
 * @return The encoded packet
 */
public byte[] encodePacket(byte msg_type, int rId, int errstat, int errind, 
    java.util.Enumeration ve) 
    throws java.io.IOException, EncodingException;

/**
 * Sends an encoded pdu. This is for internal use only and should
 * NOT be called by the developper. 
 * This is called by the the Pdu itself and is added to the interface to
 * cover the different kind of Contexts.
 * @param packet The encoded packet
 */
public void sendPacket(byte[] packet);

/**
 * Removes the resouces held by this context. Should be called by the
 * user/developper when the context is no longer needed. 
 */
public void destroy();

/**
 * Adds the specified trap listener to receive traps from the host that
 * matches this context. 
 */
public void addTrapListener(TrapListener l) throws java.io.IOException;

/**
 * Removes the specified trap listener.
 */
public void removeTrapListener(TrapListener l) throws java.io.IOException;

/**
 * Processes an incoming trap. The context will try to process the
 * incoming trap, using the SNMP version and other security
 * parameters. If any of these do not correspond, a DecodingException
 * will be thrown.
 */
public Pdu processIncomingTrap(byte [] message) 
throws DecodingException, java.io.IOException;
}
