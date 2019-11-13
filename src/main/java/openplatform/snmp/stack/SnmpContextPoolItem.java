package openplatform.snmp.stack;

import java.util.*;

/**
 * This class contains one context and one reference counter.
 * The reference counter
 * maintains how many objects reference this context. 
 * It is a helper class for the context pools, to improve the
 * synchronisation. 
 *
 * @see SnmpContextPool
 * @see SnmpContextv2cPool
 * @see SnmpContextv3Pool
 * @since 4_12
 *
 */
class SnmpContextPoolItem 
{

    private SnmpContextBasisFace context = null;
    private int counter = 0;

/**
 * Constructor.
 *
 * @param con The context
 */
SnmpContextPoolItem(SnmpContextBasisFace con)
{
    context = con;
    counter = 0;
}


SnmpContextBasisFace getContext()
{
    return context;
}

int getCounter()
{
    return counter;
}

void setCounter(int i)
{
    counter = i;
}

/**
 * Returns a string representation of the object.
 * @return The string
 */
public String toString()
{
    StringBuffer buffer = new StringBuffer("SnmpContextPoolItem[");
    buffer.append("context=").append(context.toString());
    buffer.append(", counter=").append(counter);
    buffer.append("]");
    return buffer.toString();
}


}
