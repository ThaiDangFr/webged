
 
package openplatform.snmp.stack;
import java.util.*;

/**
 * <p>
 * The OneGetBulkPdu class performs a getBulkRequest and collects 
 * the response varbinds into a Vector.
 * </p>
 *
 * <p>
 * If no exception occurred whilst receiving the response, the Object to the 
 * update() method of the Observer will be an Vector of
 * varbinds, so they may contains any AsnObject type.
 * If an exception occurred, that exception will be passed as the Object
 * to the update() method.
 * </p>
 *
 * @see varbind
 * @see java.util.Vector
 *
 */
public class OneGetBulkPdu extends GetBulkPdu 
{

    /**
     * The GetBulk request is the only request that will return more
     * variables than you've sent.
     */
    java.util.Vector vars;

/**
 * Constructor.
 *
 * @param con The context of the request
 */
public OneGetBulkPdu(SnmpContextBasisFace con)
{
    super(con);
    vars = new java.util.Vector();
}


/**
 * Returns a vector with the response varbinds.
 */
public java.util.Vector getVarbinds()
{
    return vars;
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
        vars = new java.util.Vector();
    }
    vars.addElement(a_var);
}

/**
 * The methods notifies all observers. 
 * This will be called by Pdu.fillin().
 * 
 * <p>
 * If no exception occurred whilst receiving the response, the Object to the 
 * update() method of the Observer will be an Vector of
 * varbinds, so they may contains any AsnObject type.
 * If an exception occurred, that exception will be passed as the Object
 * to the update() method.
 * </p>
 *
 * @see java.util.Vector
 */
protected void tell_them()  
{
    notifyObservers(vars);
}

}
