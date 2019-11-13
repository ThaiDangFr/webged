package openplatform.snmp.stack;

/**
 * This class represents the ASN SNMP v2c (and higher) Trap Pdu object.
 * See <a href="http://ietf.org/rfc/rfc1905.txt">RFC 1905</a>.
 *
 * @see TrapPduv1
 */
public abstract class TrapPduv2 extends Pdu 
{

/** 
 * Constructor.
 *
 * @param con The context (v2c or v3) of the Pdu
 *
 */
public TrapPduv2(SnmpContextBasisFace con) 
{
    super(con);
    setMsgType(AsnObject.TRPV2_REQ_MSG);

    // Thanks to Steven Bolton (sbolton@cereva.com) for pointing out
    // that the trap pdu is not filled in properly (see Pdu.fillin())
    // because answered stays true. 
    answered = false; 
}

/**
 * The trap Pdu does not get a response back. So it should be sent once.
 *
 */
void transmit() 
{
    transmit(false);
}

/**
 * Returns the string representation of this object.
 *
 * @return The string of the Pdu
 */
public String toString()
{
    return super.toString(true);
}

/**
 * Has no meaning, since there is not response.
 */
protected void new_value(int n, varbind res){}

/**
 * Has no meaning, since there is not response.
 */
protected void tell_them(){}

}
