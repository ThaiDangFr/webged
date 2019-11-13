package openplatform.snmp.stack;

/**
 * <p>
 * The InformPdu_vec class will inform a manager about a number of 
 * objects (OIDs), based on the Inform request.
 * </p>
 *
 * <p>
 * Specify with <code>addOid()</code> the OIDs that should be informed with this
 * InformPdu request. No more than <code>count</code> (see constructor) 
 * should be added.
 * Add an Observer to the InformPdu with <code>addObserver()</code>, and 
 * send the InformPdu with <code>send()</code>.
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
 * <p>
 * Note this PDU should be send to port 162 (the default trap port) by
 * default. You will have to create a SnmpContext with the
 * DefaultTrapContext.DEFAULT_TRAP_PORT as parameter!
 * </p>
 *
 * <p><em>
 * <font color="red">
 * Note:
 * The stack so far only supports <u>sending</u> an Inform. Receiving an Inform
 * and replying with a Response is NOT yet supported!
 * </font>
 * </em></p>
 *
 * @see OneInformPdu
 * @see Pdu#addOid
 * @see Pdu#send
 * @see varbind
 * @see DefaultTrapContext#DEFAULT_TRAP_PORT
 * @since 4_12
 *
 */
public class InformPdu_vec extends InformPdu 
{

    varbind[]  value;

/**
 * Constructor.
 *
 * @param con The context of the request
 * @param count The number of OIDs to be get
 */
public InformPdu_vec(SnmpContextBasisFace con, int count) 
{
    super(con);
    value = new varbind[count];
}

/**
 * The value of the request is set. This will be called by
 * InformPdu.fillin().
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
 * This will be called by InformPdu.fillin().
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
