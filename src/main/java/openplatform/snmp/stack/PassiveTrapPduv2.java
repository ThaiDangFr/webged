package openplatform.snmp.stack;

/**
 * This class represents the ASN SNMP v2c (and higher) Trap Pdu object
 * that does not create a thread to send itself. It must be used with the
 * context class PassiveSnmpContextv2c. The original purpose of the
 * Passive classes is to allow the stack to be used in environments where
 * thread creation is unwanted, eg database JVMs such as Oracle JServer.
 * See <a href="http://ietf.org/rfc/rfc1905.txt">RFC 1905</a>.
 *
 *
 * @see PassiveTrapPduv1
 * @since 4_12
 *
 */
public class PassiveTrapPduv2 extends TrapPduv2
{

/**
 * Constructor.
 *
 * @param con The context (v2c) of the Pdu.
 * This is of type PassiveSnmpContextv2c to ensure that the correct threading
 * behaviour occurs.
 */
public PassiveTrapPduv2(PassiveSnmpContextv2c con)
{
    super(con);

    // this makes the base class Pdu believe that the trap is already 
    // awaiting transmission therefore it does not create a transmitter 
    // for this pdu
    added = true;
}

/**
 * Override of the operation in Pdu. Send the trap in the
 * callers thread. That is, don't create a sending thread
 * or add it to a queue or anything, just go straight to the socket.
 */
public void addToTrans()
{
    sendme();
}


}
