package openplatform.snmp.stack;
import java.util.*;

/**
 * <p>
 * The OneSetPdu class will set the value of one (1) object (oid), based 
 * on the Set request.
 * </p>
 *
 * <p>
 * Unless an exception occurred the Object to the update() method of the
 * Observer will be a varbind, so any AsnObject type can be returned.
 * In the case of an exception, that exception will be passed.
 * </p>
 *
 * @see varbind
 * @see SetPdu_vec
 *
 */
public class OneSetPdu extends SetPdu 
{
    varbind var;

    /**
     * Constructor.
     *
     * @param con The context of the request
     */
    public OneSetPdu(SnmpContextBasisFace con)
    {
        super(con);
    }

    /**
     * Constructor that will send the request immediately. No Observer
     * is set.
     *
     * @param con the SnmpContextBasisFace
     * @param oid the oid 
     * @param val The value
     */
    public OneSetPdu(SnmpContextBasisFace con, String oid, AsnObject val) 
    throws PduException, java.io.IOException
    {
        this(con, oid, val, null);
    }

    /**
     * Constructor that will send the request immediately. 
     *
     * @param con the SnmpContextBasisFace
     * @param oid the oid 
     * @param val The value
     * @param o the Observer that will be notified when the answer is received
     */
    public OneSetPdu(SnmpContextBasisFace con, String oid, AsnObject val, Observer o) 
    throws PduException, java.io.IOException
    {
        super(con);
        if (o != null) 
        {
            addObserver(o);
        }
        addOid(oid, val);
        send();
    }

    /**
     * The value of the request is set. This will be called by
     * Pdu.fillin(). This is the value of the OID after the Set request
     * was done. If the SNMP server allowed the set, this will be the
     * same value as was set in SetPdu.addOid().
     *
     * @param n the index of the value
     * @param a_var the value
     * @see Pdu#new_value 
     * @see SetPdu#addOid(String, AsnObject)
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
