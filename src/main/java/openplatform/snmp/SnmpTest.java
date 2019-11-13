package openplatform.snmp;

import openplatform.snmp.stack.*;
import java.util.*;

public class SnmpTest
{
    /**
       VOIR ASN DECODER et ASN ENCODER
       apres c'est juste des send et receive de datagram packet
    */



//     public static void main(String[] arg)
//         throws Exception
//     {
//         SnmpTest context = new SnmpTest();


//         TimeWindow timew = new TimeWindow();
//         UsmAgent usmAgent =  new DefaultUsmAgent();

//         Pdu []  pdus = new Pdu[SnmpContextBasisFace.MAXPDU];
//         String hostAddr = "acacias";
//         int hostPort = SnmpContextBasisFace.DEFAULT_PORT;
//         String typeSocket = SnmpContextBasisFace.STANDARD_SOCKET;

//         Transmitter [] transmitters = new Transmitter[SnmpContextBasisFace.MAXPDU];

//         String basename = hostAddr+"_"+ hostPort;
//         //         TrapReceivedSupport trapSupport = new TrapReceivedSupport(this);

//         boolean isDestroyed = false;
//         int maxRecvSize = SnmpContextBasisFace.MSS;

//         ContextSocketFace soc = new StandardSocket();
//         soc.create(hostAddr, hostPort);

//         String userName = "dangconsultingro";
//         boolean useAuthentication = true;
//         String userAuthenticationPassword = "dangconsultingro";
//         boolean usePrivacy = true;
//         String userPrivacyPassword = "dangconsultingro";
        
//         int authenticationProtocol = SnmpContextv3Face.MD5_PROTOCOL;
//         //         Vector reqVarbinds = new Vector(1,1);
//         int type = BlockPdu.GETBULK;
//         int non_rep = 0;
//         int max_rep = 10;
//         String oid = "1.3.6.1.2.1.25.2.3.1.5";
//         varbind vb = new varbind(oid);
//         //         reqVarbinds.add(vb);

//         int non_repeaters = non_rep;
//         int max_repetitions = max_rep;
        
//         Vector reqVarbinds = new Vector(1,1);
//         reqVarbinds.addElement(var);

//         int errstat = AsnObject.SNMP_ERR_NOERROR;
//         int errind  = 0x00;
//         int req_id = 1;
//         byte msg_type = AsnObject.GETBULK_REQ_MSG;

//         Enumeration vbs = reqVarbinds.elements();
//         byte[] encodedPacket = context.encodePacket(msg_type, req_id, errstat, 
//                                                     errind, vbs);
//         soc.send(encodedPacket);
//         ByteArrayInputStream in = soc.receive(maxRecvSize);
//         AsnDecoder rpdu = new AsnDecoder();
//         context.ProcessIncomingMessage(rpdu, in);

//         //         Pdu pdu = new OneGetBulkPdu(context);
//         //         ((OneGetBulkPdu)pdu).setNonRepeaters(non_rep);
//         //         ((OneGetBulkPdu)pdu).setMaxRepetitions(max_rep);
//         //         pdu.addOid(vb);
//         //         pdu.setRetryIntervals(retry_intervals);
//         //         pdu.transmit();
//         varbind [] vars = pdu.getResponseVarbinds();
        
//     }

}
