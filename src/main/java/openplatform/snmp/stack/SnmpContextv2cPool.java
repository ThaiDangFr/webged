package openplatform.snmp.stack;
import java.util.*;

/**
 * This class contains the pool of SNMP v2c contexts.
 * It extends the SnmpContextPool and is similar in every way, except it
 * uses a pool of SnmpContextv2c.
 *
 * <p>
 * Thanks to Seon Lee (slee@virtc.com) for reporting thread safety
 * problems.
 * </p>
 *
 * @see SnmpContextv2c
 * @see SnmpContextPool
 * @see SnmpContextv3Pool
 *
 */
public class SnmpContextv2cPool extends SnmpContextPool 
    implements SnmpContextv2cFace
{


/**
 * Constructor.
 *
 * @param host The host to which the Pdu will send
 * @param port The port where the SNMP server will be
 * @see SnmpContextv2c#SnmpContextv2c(String, int)
 */
public SnmpContextv2cPool(String host, int port) throws java.io.IOException
{
    super(host, port, STANDARD_SOCKET);
}

/**
 * Constructor.
 *
 * @param host The host to which the Pdu will send
 * @param port The port where the SNMP server will be
 * @param typeSocket The type of socket to use. 
 * @see SnmpContextv2c#SnmpContextv2c(String, int, String)
 * @see SnmpContextBasisFace#STANDARD_SOCKET
 * @see SnmpContextBasisFace#KVM_SOCKET
 */
public SnmpContextv2cPool(String host, int port, String typeSocket) 
throws java.io.IOException
{
    super(host, port, typeSocket);
}

/**
 * Return the SNMP version of the context.
 *
 * @return The version
 */
public int getVersion()
{
    return AsnObject.SNMP_VERSION_2c;
}

/**
 * Returns a v2c context from the pool. 
 * This methods checks for an existing context that matches all our
 * properties. If such a context does not exist a new one is created and
 * added to the pool. 
 *
 * This method actually returns a SnmpContextv2c, although it doesn't
 * look like it.
 *
 * @return A context (v2c) from the pool 
 * @see #getHashKey
 * @see SnmpContext
 * @see SnmpContextv2c
 */
protected SnmpContext getMatchingContext() throws java.io.IOException
{
    SnmpContextPoolItem item = null;
    SnmpContextv2c newContext = null;
    String hashKey = getHashKey();

    synchronized(contextPool)
    {
        int count=0;
        if (contextPool.containsKey(hashKey))
        {
            item = (SnmpContextPoolItem) contextPool.get(hashKey);
            newContext = (SnmpContextv2c) item.getContext();
            count = item.getCounter();
        }
        else
        {
            newContext = new SnmpContextv2c(hostAddr, hostPort, socketType);
            newContext.setCommunity(community);
            item = new SnmpContextPoolItem(newContext);
            contextPool.put(hashKey, item);
        }
        count++;
        item.setCounter(count);
    }
    return newContext;
}

}
