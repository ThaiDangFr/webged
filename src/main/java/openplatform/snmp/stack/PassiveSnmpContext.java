package openplatform.snmp.stack;

/**
 * This class contains the SNMP v1 context that is needed by a Pdu to
 * send a SNMP v1 request in environments where thread creation is
 * unwanted.
 *
 * <p>
 * This extends SnmpContext so that it does not create any
 * threads to send pdus. It must be used with the
 * PDU class PassiveTrapPduv1. The original purpose of the
 * Passive classes is to allow the stack to be used in environments where
 * thread creation is unwanted, eg database JVMs such as Oracle JServer.
 * See <a href="http://ietf.org/rfc/rfc1905.txt">RFC 1905</a>.
 * </p>
 *

 *
 *
 */
public class PassiveSnmpContext extends SnmpContext
{


/**
 * Constructor.
 *
 * @param a_host The host to which the Pdu will send
 * @param a_port The port where the SNMP server will be
 */
public PassiveSnmpContext(String a_host, int a_port)
throws java.io.IOException
{
    super(a_host, a_port);
}

/**
 * Constructor.
 *
 * @param a_host The host to which the Pdu will send
 * @param a_port The port where the SNMP server will be
 * @param socketType The type of socket to use.
 */
public PassiveSnmpContext(String a_host, int a_port, String socketType)
throws java.io.IOException
{
    super(a_host, a_port, socketType);
}

/**
 * Overrides the AbstractSnmpContext.activate() to do nothing.
 * This prevents the creation of threads in the base class.
 *
 */
protected void activate()
{
    // do nothing
}

}
