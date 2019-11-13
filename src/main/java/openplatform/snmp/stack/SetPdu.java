package openplatform.snmp.stack;

/**
 * This class represents the SNMP Set Pdu.
 *
 */
public abstract class SetPdu extends Pdu 
{

    /** 
     * Constructor.
     *
     * @param con The context of the Pdu
     */
    public SetPdu(SnmpContextBasisFace con) 
    {
        super(con);
        setMsgType(AsnObject.SET_REQ_MSG);
    }


}
