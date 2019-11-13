package openplatform.snmp.stack;

/**
 * Thrown to indicate that the Pdu cannot be build or encoded.
 * 
 */
public class EncodingException extends PduException 
{

/** 
 * Constructs an EncodingException with no specified detail message. 
 */
public EncodingException() 
{
    super();
}

/** 
 * Constructs an EncodingException with the specified detail
 * message. 
 *
 * @param str The detail message.
 */
public EncodingException(String str) 
{
    super(str);
}


}
