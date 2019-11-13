package openplatform.snmp.stack;

/**
 * This interface contains the (basic) SNMP context interface that is needed 
 * by every Pdu to send a SNMP v1 request. The context also
 * provides functionality to receive traps.
 *
 * @see SnmpContext
 * @see SnmpContextv2c
 * @see SnmpContextv3
 *
 */
public interface SnmpContextFace extends SnmpContextBasisFace
{


/**
 * Returns the community name.
 */
public String getCommunity();


/**
 * Sets the community name.
 */
public void setCommunity(String newCommunity);

}
