package openplatform.snmp.stack;

/**
 * This class contains the SNMP v2c context that is needed by every Pdu to
 * send a SNMP v2c request in environments where thread creation is
 * unwanted.
 *
 * <p>
 * This extends SnmpContextv2c so that it does not create any
 * threads to send pdus. It must be used with the
 * PDU class PassiveTrapPduv2. The original purpose of the
 * Passive classes is to allow the stack to be used in environments where
 * thread creation is unwanted, eg database JVMs such as Oracle JServer.
 * See <a href="http://ietf.org/rfc/rfc1905.txt">RFC 1905</a>.
 * </p>
 *

 *
 *
 */
public class PassiveSnmpContextv2c extends SnmpContextv2c
{


/**
 * Constructor.
 *
 * @param a_host The host to which the Pdu will send
 * @param a_port The port where the SNMP server will be
 */
public PassiveSnmpContextv2c(String a_host, int a_port)
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
public PassiveSnmpContextv2c(String a_host, int a_port, String socketType)
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
