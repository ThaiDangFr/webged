package openplatform.snmp.stack;



/**
 * Class PassiveSnmpContextv3
 *
 * Thread-less context
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class PassiveSnmpContextv3 extends SnmpContextv3
{

    public PassiveSnmpContextv3(String host, int port)
        throws java.io.IOException
    {
        super(host, port);
    }


    public PassiveSnmpContextv3(String host, int port, String typeSocketA)
        throws java.io.IOException
    {
        super(host, port, typeSocketA);
    }


    /**
     * Overrides the AbstractSnmpContext.activate() to do nothing.
     * This prevents the creation of threads in the base class.
     *
     */
    protected void activate()
    {
        // do nothing
    }
}
