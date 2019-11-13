package openplatform.snmp.stack;
import java.util.*;

/**
 * The UpSincePdu class will send a Get request for the sysUpTime and
 * will calculate that date the system rebooted the last time.
 *
 */
public class UpSincePdu extends Pdu
{

    Date since;
    /**
     * The oid of sysUpTime
     */
    public final static String SYSUPTIME="1.3.6.1.2.1.1.3.0";

    /**
     * Constructor that will send the request immediately.
     *
     * @param con The context of the request
     * @param o the Observer that will be notified when the answer is received
     */
    public UpSincePdu(SnmpContextBasisFace con, Observer o) 
    throws PduException, java.io.IOException
    {
        super(con);
        addOid(SYSUPTIME);
        if (o != null)
        {
            addObserver(o);
        }
        send();
    }


    /**
     * Returns the date when the system went up, (sysUpTime).
     * @return the date
     */
    public Date getDate()
    {
        return since;
    }

    /**
     * The value of the request is set. This will be called by
     * Pdu.fillin().
     *
     * @param n the index of the value
     * @param res the value
     * @see Pdu#new_value 
     */
    protected void new_value(int n, varbind res)  
    {
        // given the uptime in centi seconds and the time now,
        // calculate the time it rebooted
    
        AsnObject val = res.getValue();
        if (val instanceof AsnUnsInteger)
        {
            AsnUnsInteger va = (AsnUnsInteger) res.getValue();
            if (n == 0) 
            {
                long value = va.getValue();
                Date now = new Date();
                long then = now.getTime();
                then -= 10 * value;
                since = new Date(then);
            }
        }
        else
        {
            since = null;
        }
    }

    /**
     * The methods notifies all observers. 
     * This will be called by Pdu.fillin().
     * 
     * <p>
     * Unless an exception occurred the Object to the update() method of the
     * Observer will be a Date.
     * In the case of an exception, that exception will be passed.
     * </p>
     */
    protected void tell_them()  
    {
        notifyObservers(since);
    }

}

