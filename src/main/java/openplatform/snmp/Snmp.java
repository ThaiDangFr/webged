package openplatform.snmp;

import openplatform.tools.*;
import openplatform.snmp.stack.*;

/**
 * Class Snmp
 *
 * Don't use any factory things
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Snmp extends SnmpAbstract
{
    public Snmp(int version, String ipAddress, String community, String userName, String authent, String privacy)
        throws Exception
    {
        super(version, ipAddress, community, userName, authent, privacy);
    }


    public static void dumpNodeList(SnmpNodeList nl)
    {
        int len = nl.size();
        for(int i=0; i< len; i++)
        {
            SnmpNode node = nl.getSnmpNode(i);
            Debug.println(null, Debug.DEBUG, node.getOid()+" => "+node.getValue());
        }
    }



    
    public static void main(String[] args)
    {
        try
        {
        //AsnObject.debug = 100;

        /*
        System.out.println("SNMP V1");
        Snmp snmp = new Snmp(Snmp.SNMP_V1, "bora", "public", null, null, null);
        SnmpNodeList nl = snmp.get("1.3.6.1.2.1.1.1");
        dumpNodeList(nl);
        snmp.close();

              
        System.out.println("\n\nSNMP V2");
        snmp = new Snmp(Snmp.SNMP_V2, "bora", "public", null, null, null);
        nl = snmp.get("1.3.6.1.2.1.1.2");
        dumpNodeList(nl);
        snmp.close();


        System.out.println("\n\nSNMP V3");
        snmp = new Snmp(Snmp.SNMP_V3, "bora", null, "dangconsultingro", "dangconsultingro", "dangconsultingro");
        nl = snmp.get("1.3.6.1.2.1.1.7");
        dumpNodeList(nl);
        snmp.close();
        */

        Snmp snmp = new Snmp(Snmp.SNMP_V1, "bora", "private", null, null, null);
//        SnmpNodeList nl = snmp.get("1.3.6.1.2.1.1.4");
//        dumpNodeList(nl);
        snmp.set("1.3.6.1.2.1.1.4.0", "nospam@dangconsulting.fr");
        snmp.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
