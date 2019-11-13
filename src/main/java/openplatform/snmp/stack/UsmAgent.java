package openplatform.snmp.stack;

/**
 * This interface provides the SNMPv3 USM (User-Based Security Model)
 * authoritative details.
 *
 * <p>
 * When the stack is used as authoritative SNMP engine it has to send
 * its Engine ID and clock (i.e. Engine Boots and Engine Time) with each 
 * message. At the
 * moment only when sending a Trap, the stack is authoritative.
 * See <a href="http://ietf.org/rfc/rfc2574.txt">RFC 2574</a>.
 * </p>
 *
 * <p>
 * Since this stack has no means in providing this information, this
 * interface has to be implemented by the user.
 * </p>
 *
 * @see SnmpContextv3#setUsmAgent
 *
 */
public interface UsmAgent
{

/**
 * Returns the authoritative SNMP Engine ID.
 * It (at least within an administrative domain) uniquely and
 * unambiguously identifies the SNMP engine.
 *
 * <p>
 * The Engine ID is the (case insensitive) string representation of a
 * hexadecimal number, without any prefix, for example
 * <b>010000a1d41e4946</b>. 
 * </p>
 * @see SnmpUtilities#toBytes(String)
 */
public String getSnmpEngineId();

/**
 * Returns the authoritative Engine Boots.
 * It is a count of the number of times the SNMP engine has
 * re-booted/re-initialized since snmpEngineID was last configured.
 */
public int getSnmpEngineBoots();

/**
 * Returns the authoritative Engine Time.
 * It is the number of seconds since the snmpEngineBoots counter was
 * last incremented.
 */
public int getSnmpEngineTime();

/**
 * Sets the current snmp context.
 */
public void setSnmpContext(SnmpContextv3 context);
}

