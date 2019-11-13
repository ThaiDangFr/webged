package openplatform.snmp.process;

import openplatform.database.dbean.DBSnmpHost;
import openplatform.tools.Debug;

/**
 * Class Factory
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Factory
{
    private Factory() {}


    /**
     * Return a DBSnmpHost object. If it does not exist, the object is created in database.
     * @param ip the IP address of the host
     * @param community the snmp community
     * @param snmpVersion Snmp.SNMP_V1 or Snmp.SNMP_V2 etc.
     **/
    public static DBSnmpHost snmpHost(String ip, String community, int snmpVersion)
    {
        DBSnmpHost mask = new DBSnmpHost();

//         try
//         {
//             InetAddress inetadd = InetAddress.getByName(ip);        
//             mask.setIpAddress(inetadd.getHostAddress());
//         }
//         catch(Exception e)
//         {
//             Debug.println(null, Debug.ERROR, e);
//         }

        mask.setIpAddress(ip);
        mask.setCommunity(community);
        mask.setSnmpVersion(Integer.toString(snmpVersion));
       

        DBSnmpHost snmpH = DBSnmpHost.loadByKey(mask);

        if(snmpH!=null)
            return snmpH;
        else
        {
            mask.store();

            if(mask.isInError())
            {
                Debug.println(null, Debug.ERROR, "Can't create DBSnmpHost object !");
                return null;
            }
            else
                return mask;
        }
    }


    /**
     * Return a DBSnmpParam object.
     * @param host the Snmp Agent Host
     * @param oid the oid
     **/
//     public static DBSnmpParam snmpParam(DBSnmpHost host, String oid)
//     {
//         DBSnmpParam mask = new DBSnmpParam();
//         mask.setSnmpHostId(host.getSnmpHostId());
//         mask.setOid(oid);
        
//         DBSnmpParam snmpP = DBSnmpParam.loadByKey(mask);
//         if(snmpP!=null)
//             return snmpP;
//         else
//         {
//             mask.store();
//             if(mask.isInError())
//             {
//                 Debug.println(null, Debug.ERROR, "Can't create DBSnmpParam object !");
//                 return null;
//             }
//             else
//                 return mask;
//         }
//     }
}
