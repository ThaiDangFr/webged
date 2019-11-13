package openplatform.snmp.stack;

import java.util.*;

/**
 * Transmitter is a thread that sends pdus, when done
 * it waits.
 *
*/
public class Transmitter extends Object implements Runnable
{

    Pdu pdu = null;
    Thread me;
    String myName;

    public Transmitter(String name)
    {
        me = new Thread(this,name);
        me.setPriority(me.MIN_PRIORITY);
        if (AsnObject.debug > 12)
        {
            System.out.println("Transmitter(): Made thread " + name);
        }
        myName =name;
        me.start();
    }

    /**
     * The method for the Runnable interface.
     * 
     * It will do a sit() untill stand() wakes it up. The Pdu will then
     * be transmitted
     *
     * @see #sit()
     * @see #stand()
     * @see Pdu#transmit()
     */
    public void run()
    {
        while (me != null)
        {
            sit();
            synchronized (this) 
            {
                if (pdu != null)
                {
                    pdu.transmit();
                    // I will say this only once....
                    pdu = null; 
                }
            }
        }
    }

    /** 
     * Returns the string representation of the Transmitter.
     *
     * @return The string of the Transmitter
     */
    public String toString()
    {
        StringBuffer buffer = new StringBuffer(getClass().getName());
        buffer.append("[");
        buffer.append("name=").append(myName);
        buffer.append("]");
        return buffer.toString();
    }

    synchronized void setPdu(Pdu p)
    {
        pdu = p;
    }

    /**
     * This method is the counterpart of stand().
     *
     * It does not more than waiting to be notified.
     *
     * @see #stand()
     * @see #run()
     */
    synchronized void sit()
    {
        while ((me != null) && (pdu == null))
        {
            try
            {
                wait();
            }
            catch (InterruptedException iw)
            {
                ;
            }

        }
    }

    /**
     * This method is the counterpart of sit().
     *
     * The Pdu will call this method when it is sent.
     * This method will notify itself and in the end it is transmitted
     * in run.
     *
     * @see #sit()
     * @see #run()
     * @see Pdu#send
     */
    synchronized void stand()
    {
        notifyAll();
    }

    /**
     * It may be sleeping (as opposed to wait()ing
     * so send it a kick.
     */
    void interruptMe()
    {
        // unsafe ?
        me.interrupt();
    }

    void destroy()
    {
        me = null;
        pdu=null;
        stand();
    }
}

