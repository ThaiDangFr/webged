package openplatform.snmp.stack;
import java.util.*;

/**
 * <p>
 * The OneIntPdu class will ask for one (1) object (oid) of the 
 * AsnInteger type, based on the Get request.
 * </p>
 *
 * <p>
 * Unless an exception occurred the Object to the update() method of the
 * Observer will be an Integer.
 * In the case of an exception, that exception will be passed.
 * </p>
 *
 * @see GetPdu_vec
 *
 */
public class OneIntPdu extends Pdu 
{
    Integer value;

    /**
     * Constructor.
     *
     * @param con The context of the request
     */
    public OneIntPdu(SnmpContextBasisFace con) 
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
    public OneIntPdu(SnmpContextBasisFace con, String oid) 
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
    public OneIntPdu(SnmpContextBasisFace con, String oid, Observer o) 
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
     * Returns the value (the answer) of this request.
     *
     * @return the value
     */
    public Integer getValue()
    {
        return value;
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
        AsnObject val = res.getValue();
        if (val instanceof AsnInteger)
        {
            AsnInteger va = (AsnInteger) res.getValue();
            if (n == 0) 
            {
                value = new Integer(va.getValue());
            }
        }
        else
        {
            value = null;
        }
    }

    /**
     * The methods notifies all observers. 
     * This will be called by Pdu.fillin().
     * 
     * <p>
     * Unless an exception occurred the Object to the update() method of the
     * Observer will be an Integer.
     * In the case of an exception, that exception will be passed.
     * </p>
     */
    protected void tell_them()  
    {
        notifyObservers(value);
    }

}
