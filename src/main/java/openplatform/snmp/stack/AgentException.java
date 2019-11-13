package openplatform.snmp.stack;

/**
 * Thrown to indicate that the response Pdu was received OK, but the Pdu
 * contains an error. 
 * 
 */
public class AgentException extends PduException 
{

/** 
 * Constructs an AgentException with no specified detail message. 
 *
 */
public AgentException() 
{
    super();
}

/** 
 * Constructs an AgentException with the specified detail
 * message. 
 *
 * @param str The detail message.
 */
public AgentException(String str) 
{
    super(str);
}


}
