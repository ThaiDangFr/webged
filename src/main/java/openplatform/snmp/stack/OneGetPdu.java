package openplatform.snmp.stack;
import java.util.*;

/**
 * <p>
 * The OneGetPdu class will ask for one (1) object (oid), based on
 * the Get request.
 * </p>
 *
 * <p>
 * Unless an exception occurred the Object to the update() method of the
 * Observer will be a varbind, so any AsnObject type can be returned.
 * In the case of an exception, that exception will be passed.
 * </p>
 *
 * @see varbind
 *
 */
public class OneGetPdu extends Pdu 
{
    varbind var;

    /**
     * Constructor.
     *
     * @param con The context of the request
     */
    public OneGetPdu(SnmpContextBasisFace con)
    {
        super(con);
    }

    /**
     * Constructor that will send the request immediately. No Observer
     * is set.
     *
     * @param con the SnmpContextBasisFace
     * @param oid the oid 
     */
    public OneGetPdu(SnmpContextBasisFace con, String oid) 
    throws PduException, java.io.IOException
    {
        this(con, oid, null);
    }

    /**
     * Constructor that will send the request immediately. 
     *
     * @param con the SnmpContextBasisFace
     * @param oid the oid 
     * @param o the Observer that will be notified when the answer is received
     */
    public OneGetPdu(SnmpContextBasisFace con, String oid, Observer o) 
    throws PduException, java.io.IOException
    {
        super(con);
        if (o != null) 
        {
            addObserver(o);
        }
        addOid(oid);
        send();
    }

    /**
     * The value of the request is set. This will be called by
     * Pdu.fillin().
     *
     * @param n the index of the value
     * @param a_var the value
     * @see Pdu#new_value 
     */
    protected void new_value(int n, varbind a_var) 
    {
        if (n == 0) 
        {
            var = a_var;
        }
    }

    /**
     * The methods notifies all observers. 
     * This will be called by Pdu.fillin().
     * 
     * <p>
     * Unless an exception occurred the Object to the update() method of the
     * Observer will be a varbind, so any AsnObject type can be returned.
     * In the case of an exception, that exception will be passed.
     * </p>
     */
    protected void tell_them()  
    {
        notifyObservers(var);
    }

}
