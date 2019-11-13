package openplatform.snmp.stack;

/**
 * This class represents the ASN SNMP v1 Trap Pdu object
 * that does not create a thread to send itself. It must be used with the
 * context class PassiveSnmpContext. The original purpose of the
 * Passive classes is to allow the stack to be used in environments where
 * thread creation is unwanted, eg database JVMs such as Oracle JServer.
 * See <a href="http://ietf.org/rfc/rfc1905.txt">RFC 1905</a>.
 *
 *
 * @see PassiveTrapPduv2
 *
 */
public class PassiveTrapPduv1 extends TrapPduv1
{
/**
 * Constructor.
 *
 * @param con The context (v1) of the Pdu.
 * This is of type PassiveSnmpContext to ensure that the correct threading
 * behaviour occurs.
 */
public PassiveTrapPduv1(PassiveSnmpContext con)
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
