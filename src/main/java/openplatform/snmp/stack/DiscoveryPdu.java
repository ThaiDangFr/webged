package openplatform.snmp.stack;
import java.util.*;

/**
 * This class is used to perform the SNMPv3 USM discovery.
 * This Pdu cannot have any OIDs.
 *
 */
public class DiscoveryPdu extends Pdu
{

    private SnmpContextv3Face context;

/**
 * Constructor.
 *
 * @param cntxt The v3 context of the Pdu
 */
public DiscoveryPdu(SnmpContextv3Face cntxt)
{
    super(cntxt);
    context = cntxt;
}

/**
 * Cannot add any OID. This method is overwritten to prevent users from
 * adding any OID.
 *
 * @exception IllegalArgumentException A discovery Pdu cannot have any
 * OID.
 */
public void addOid(String oid)
throws IllegalArgumentException
{
    throw new IllegalArgumentException("DiscoveryPdu cannot have OID");
}

/** 
 * Cannot add any OID. This method is overwritten to prevent users from
 * adding any OID.
 *
 * @exception IllegalArgumentException A discovery Pdu cannot have any
 * OID.
 * @since 4_12
 */
public void addOid(String oid, AsnObject val) 
{
    throw new IllegalArgumentException("DiscoveryPdu cannot have OID");
}

/** 
 * Cannot add any OID. This method is overwritten to prevent users from
 * adding any OID.
 *
 * @exception IllegalArgumentException A discovery Pdu cannot have any
 * OID.
 * @since 4_12
 */
public void addOid(AsnObjectId oid, AsnObject val) 
{
    throw new IllegalArgumentException("DiscoveryPdu cannot have OID");
}

/**
 * Cannot add any OID. This method is overwritten to prevent users from
 * adding any OID.
 *
 * @exception IllegalArgumentException A discovery Pdu cannot have any
 * OID.
 */
public void addOid(varbind var)
throws IllegalArgumentException
{
    throw new IllegalArgumentException("DiscoveryPdu cannot have OID");
}

/**
 * Cannot add any OID. This method is overwritten to prevent users from
 * adding any OID.
 *
 * @exception IllegalArgumentException A discovery Pdu cannot have any
 * OID.
 * @since 4_12
 */
public void addOid(AsnObjectId oid) 
{
    throw new IllegalArgumentException("DiscoveryPdu cannot have OID");
}

/**
 * Send the Pdu.
 * Note that all properties of the context have to be set before this
 * point.
 */
public boolean send() throws java.io.IOException, PduException
{
    if (added == false)
    {
        // Moved this statement from the constructor because it
        // conflicts with the way the SnmpContextXPool works.
        added = context.addDiscoveryPdu(this);
    }
    Enumeration vbs = reqVarbinds.elements();
    encodedPacket = context.encodeDiscoveryPacket(msg_type, getReqId(),
        getErrorStatus(), getErrorIndex(), vbs);
    addToTrans();
    return added;
}

/**
 * The value of the request is set. This will be called by
 * Pdu.fillin().
 *
 * @see Pdu#new_value
 */
protected void new_value(int n, varbind a_var) { }

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
    notifyObservers();
}

}
