package openplatform.snmp.stack;

/**
 * This class represents the SNMP Inform Request Pdu.
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
 * @see DefaultTrapContext#DEFAULT_TRAP_PORT
 * @since 4_12
 *
 */
public abstract class InformPdu extends Pdu 
{

/** 
 * Constructor.
 *
 * @param con The context of the Pdu
 */
public InformPdu(SnmpContextBasisFace con) 
{
    super(con);
    setMsgType(AsnObject.INFORM_REQ_MSG);
}

}
