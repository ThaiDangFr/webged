package openplatform.snmp.stack;



/**
 * This class represents the ASN SNMPv1 Trap Pdu object. 
 * See <a href="http://ietf.org/rfc/rfc1157.txt">RFC 1157</a>.
 *
 */
public class OneTrapPduv1 extends TrapPduv1 
{


/** 
 * Constructor.
 *
 * @param con The context v1 of the OneTrapPduv1
 * @see SnmpContext
 */
public OneTrapPduv1(SnmpContext con) 
{
    super(con);
}


}
