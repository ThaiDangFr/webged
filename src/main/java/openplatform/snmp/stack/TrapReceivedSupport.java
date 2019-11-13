package openplatform.snmp.stack;

import java.util.*;

/**
 * This is a utility class that can be used by classes that support trap
 * listener functionality. 
 * You can use an instance of this class as a member field
 * of your class and delegate various work to it. 
 *
 */
public class TrapReceivedSupport 
{
    private Object source;
    private transient Vector trapListeners;

/**
 * The constructor.
 *
 * @param src The source of the trap events when they are fired. 
 */
public TrapReceivedSupport(Object src)
{
    source = src;
}

/**
 * Removes all the listeners.
 */
public synchronized void empty()
{
    if (trapListeners != null)
    {
        trapListeners.removeAllElements();
    }
}

/**
 * Returns the number of listeners.
 *
 * @return The number of listeners.
 */
public synchronized int getListenerCount()
{
    int c=0;
    if (trapListeners != null)
    {
        c = trapListeners.size();
    }
    return c;
}

/**
 * Adds the specified trap listener to receive traps. 
 */ 
public synchronized void addTrapListener(TrapListener listener)
{
    if (trapListeners == null)
    {
        trapListeners = new Vector (5);
    }
    if (trapListeners.contains(listener) == false)
    {
        trapListeners.addElement(listener);
    }
}

/**
 * Removes the specified trap listener.
 */
public synchronized void removeTrapListener(TrapListener listener)
{
    if (trapListeners != null)
    {
        trapListeners.removeElement(listener);
    }
}

/**
 * Fires an undecoded trap event.
 * The event is fired to all listeners, unless one of them consumes it.
 * This behaviour is different from the decoded trap event.
 * The idea is that for undecoded traps it is very unlikely that more
 * than one party (usually SnmpContext objects) is interested.
 *
 * @param version The SNMP version of the trap
 * @param hostAddress The IP address of the host where the trap came from
 * @param message The trap in bytes
 *
 * @return Whether or not the event has been consumed.
 * @see #fireTrapReceived(Pdu)
 */
public boolean fireTrapReceived(int version, String hostAddress, byte [] message)
{
    boolean isConsumed = false;
    Vector copyOfListeners = null;
    if (trapListeners != null)
    {
        synchronized (trapListeners)
        {
            copyOfListeners = (Vector) trapListeners.clone();
        }
    }

    if (copyOfListeners != null)
    {
        int l = message.length;
        int sz = copyOfListeners.size();
        for (int i=sz-1; i>=0 && isConsumed == false; i--)
        {
            TrapListener listener = (TrapListener) copyOfListeners.elementAt(i);

            byte [] copyOfMessage = new byte[l];
            System.arraycopy(message, 0, copyOfMessage, 0, l);
            TrapEvent evt = new TrapEvent(source, version, hostAddress, copyOfMessage);
            listener.trapReceived(evt);
            isConsumed = (evt.isConsumed());
        }
    }
    return isConsumed;
}

/**
 * Fires a decoded trap event.
 * The event is fired to all listeners, whether they consume it or not.
 * This behaviour is different from the undecoded trap event.
 * 
 * @param pdu The decoded trap pdu.
 * @see #fireTrapReceived(int, String, byte [])
 */
public void fireTrapReceived(Pdu pdu)
{
    Vector copyOfListeners = null;
    if (trapListeners != null)
    {
        synchronized (trapListeners)
        {
            copyOfListeners = (Vector) trapListeners.clone();
        }
    }

    if (copyOfListeners != null)
    {
        int sz = copyOfListeners.size();
        for (int i=sz-1; i>=0; i--)
        {
            TrapListener listener = (TrapListener) copyOfListeners.elementAt(i);

            TrapEvent evt = new TrapEvent(source, pdu);
            listener.trapReceived(evt);
        }
    }
}

}
