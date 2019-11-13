package openplatform.snmp.stack;


public class PduException extends Exception 
{

/** 
 * Constructs a PduException with no specified detail message. 
 *
 */
public PduException() 
{
    super();
}

/** 
 * Constructs a PduException with the specified detail
 * message. 
 *
 * @param str The detail message.
 */
public PduException(String str) 
{
    super(str);
}


}
