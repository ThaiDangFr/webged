package openplatform.snmp.stack;

/**
 * This class represents the SNMP GetNext Pdu.
 *
 */
public abstract class GetNextPdu extends Pdu 
{

    /** 
     * Constructor.
     *
     * @param con The context of the Pdu
     */
    public GetNextPdu(SnmpContextBasisFace con) 
    {
        super(con);
        setMsgType(AsnObject.GETNEXT_REQ_MSG);
    }

}
