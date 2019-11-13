package openplatform.snmp.stack;
import java.util.*;

/**
 * The InterfacesPdu class will ask for the number of current interfaces.
 * For each interface it will send an InterfacePdu to get the
 * information of the specific interface.
 *
 * @see InterfacePdu
 *
 *
 */
public class InterfacesPdu extends InterfacePdu
{
    /**
     * ifNumber
     * The number of network interfaces (regardless of their current state) 
     * present on this system.
     */
    final static String IFNUMBER      ="1.3.6.1.2.1.2.1.0";

    InterfacePdu [] ifs;


/**
 * Constructor that will send the request immediately.
 *
 * @param con the SnmpContextBasisFace
 * @param o the Observer that will be notified when the answer is received
 * @param interfs the index of the requested interface
 */
public InterfacesPdu(SnmpContextBasisFace con, Observer o, int interfs) 
throws PduException, java.io.IOException
{
    super(con);
    ifs = new InterfacePdu [interfs];
    for (int interf=0; interf < interfs; interf++)
    {
        addOids(interf);
        ifs[interf] = new InterfacePdu(con);
    }
    if (o!=null) 
    {
        addObserver(o);
    }
    send();
}

/**
 * Returns how many interfaces are present.
 *
 * @return the number of interfaces
 */
public static int getNumIfs(SnmpContextBasisFace con)
throws PduException, java.io.IOException
{
    int ifCount =0;

    if (con != null)
    {
        OneIntPdu numIfs = new OneIntPdu(con, IFNUMBER);
        if (numIfs.waitForSelf())
        {
            ifCount = numIfs.getValue().intValue();
        }
    }
    return ifCount; 
} 

/**
 * Returns the interfaces.
 *
 * @return the interfaces as an array of InterfacePdu
 */
public InterfacePdu [] getInterfacePdus()
{
    return ifs;
}

/**
 * The value of the request is set. This will be called by
 * Pdu.fillin().
 *
 * @param n the index of the value
 * @param res the value
 * @see Pdu#new_value 
 */
protected void new_value(int n, varbind res)  
{
    int thif = n / 4;
    if (thif < ifs.length)
    {
        ifs[thif].new_value(n%4,res);
    }
}


}

