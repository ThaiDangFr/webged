package openplatform.snmp.stack;


/**
 * <p>
 * The GetPdu_vec class will ask for a number of objects (OIDs), based 
 * on the Get request.
 * </p>
 *
 * <p>
 * Specify with <em>addOid()</em> the OIDs that should be requested with this
 * Pdu request. No more than <em>count</em> (see constructor) should be added.
 * Add an Observer to the Pdu with <em>addObserver()</em>, and send the Pdu
 * with <em>send()</em>.
 * </p>
 *
 * <p>
 * If no exception occurred whilst receiving the response, the Object to the 
 * update() method of the Observer will be an array of
 * varbinds, so they may contains any AsnObject type.
 * If an exception occurred, that exception will be passed as the Object
 * to the update() method.
 * </p>
 *

 * @see Pdu#addOid
 * @see Pdu#send
 * @see varbind
 */
public class GetPdu_vec extends Pdu 
{

    varbind[]  value;

    /**
     * Constructor.
     *
     * @param con The context of the request
     * @param count The number of OIDs to be get
     */
    public GetPdu_vec(SnmpContextBasisFace con, int count) 
    {
        super(con);
        value = new varbind[count];
    }

    /**
     * The value of the request is set. This will be called by
     * Pdu.fillin().
     *
     * @param n the index of the value
     * @param var the value
     * @see Pdu#new_value 
     */
    protected void new_value(int n, varbind var) 
    {
        if (n <value.length) 
        {
            value[n] = var;
        }
    }

    /**
     * The methods notifies all observers. 
     * This will be called by Pdu.fillin().
     * 
     * <p>
     * If no exception occurred whilst receiving the response, the Object to the 
     * update() method of the Observer will be an array of
     * varbinds, so they may contains any AsnObject type.
     * If an exception occurred, that exception will be passed as the Object
     * to the update() method.
     * </p>
     */
    protected void tell_them()  
    {
        notifyObservers(value);
    }
}
