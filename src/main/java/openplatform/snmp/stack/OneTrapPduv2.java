package openplatform.snmp.stack;



/**
 * This class represents the ASN SNMPv2c (and higher) Trap Pdu object. 
 * See <a href="http://ietf.org/rfc/rfc1157.txt">RFC 1157</a>.
 *
 */
public class OneTrapPduv2 extends TrapPduv2 
{
/** 
 * Constructor.
 *
 * @param con The context (v2c, v3) of the OneTrapPduv2
 * @see SnmpContext
 */
public OneTrapPduv2(SnmpContextBasisFace con) 
{
    super(con);
}


}
