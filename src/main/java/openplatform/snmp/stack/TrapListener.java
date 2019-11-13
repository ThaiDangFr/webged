
package openplatform.snmp.stack;


/**
 * The listener interface for receiving trap events.
 *
 */
public interface TrapListener extends java.util.EventListener
{


/**
 * Invoked when a trap is received.
 */
public void trapReceived(TrapEvent evt);

}
