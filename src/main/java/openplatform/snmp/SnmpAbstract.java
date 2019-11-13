package openplatform.snmp;

import openplatform.snmp.stack.AsnInteger;
import openplatform.snmp.stack.AsnNull;
import openplatform.snmp.stack.AsnObject;
import openplatform.snmp.stack.AsnObjectId;
import openplatform.snmp.stack.AsnOctets;
import openplatform.snmp.stack.AsnUnsInteger;
import openplatform.snmp.stack.AsnUnsInteger64;
import openplatform.snmp.stack.BlockPdu;
import openplatform.snmp.stack.Pdu;
import openplatform.snmp.stack.SnmpConstants;
import openplatform.snmp.stack.SnmpContext;
import openplatform.snmp.stack.SnmpContextv2c;
import openplatform.snmp.stack.SnmpContextv3;
import openplatform.snmp.stack.TrapEvent;
import openplatform.snmp.stack.varbind;
import openplatform.tools.Debug;


/**
 * Class SnmpAbstract
 *
 * This is a FACADE to SNMP library
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
abstract public class SnmpAbstract
{
    /**
     * 3000 ms
     **/
    public static final int DEFAULT_TIME_OUT = 2000;
    public static final int SNMP_V1 = 0;
    public static final int SNMP_V2 = 1;
    public static final int SNMP_V3 = 2;

    public static final int GETBULK_NON_REPET = 0;
    public static final int GETBULK_MAX_REPET = 10;
    
    

    public static int SNMP_RETRY = 2;


    private SnmpContext context1;
    private SnmpContextv2c context2;
    private SnmpContextv3 context3;

    private BlockPdu pdu;

    private int version = -1;
    private String ipAddress;
    private String community;
    private String userName;
    private String authent;
    private String privacy;


    private boolean connected = false;


    /**
     * @param version SNMP_V1 or SNMP_V2 or SNMP_V3
     * @param ipAddress the ip address
     * @param community the community string for SNMP_V1 and SNMP_V2
     * @param userName used for SNMP_V3
     * @param authent authent password used for SNMP_V3
     * @param privacy privacy password used for SNMP_V3
     */
    public SnmpAbstract(int version, String ipAddress, String community, String userName, String authent, String privacy)
    {
        initComInterface(version, ipAddress, community, userName, authent, privacy);
    }


    protected void reloadComInterface()
    {
        Debug.println(this, Debug.WARNING, "Trying to reload Snmp link to "+ipAddress);
        close();
        initComInterface(version, ipAddress, community, userName, authent, privacy);
    }


    protected void initComInterface(int version, String ipAddress, String community, String userName, String authent, String privacy)
    {
        this.ipAddress = ipAddress;
        this.community = community;
        this.userName = userName;
        this.authent = authent;
        this.privacy = privacy;

        if(version == SNMP_V2) this.version = SNMP_V2;
        else if(version == SNMP_V3) this.version = SNMP_V3;
        else this.version = SNMP_V1;

        try
        {

            if(this.version == SNMP_V1)
            {
                context1 = new SnmpContext(ipAddress, SnmpContext.DEFAULT_PORT, SnmpContext.STANDARD_SOCKET);
                context1.setCommunity(community);
            }
            else if(this.version == SNMP_V2)
            {
                context2 = new SnmpContextv2c(ipAddress, SnmpContextv2c.DEFAULT_PORT, SnmpContextv2c.STANDARD_SOCKET);
                context2.setCommunity(community);
            }
            else
            {
                context3 = new SnmpContextv3(ipAddress, SnmpContextv3.DEFAULT_PORT, SnmpContextv3.STANDARD_SOCKET);
                context3.setUserName(userName);

                if(authent != null)
                {
                    context3.setUseAuthentication(true);
                    context3.setUserAuthenticationPassword(authent);
                }

                if(privacy != null)
                {
                    context3.setUsePrivacy(true);
                    context3.setUserPrivacyPassword(privacy);
                }

                context3.setAuthenticationProtocol(SnmpContextv3.MD5_PROTOCOL);   
            }


            if(this.version == SNMP_V1) pdu = new BlockPdu(context1);
            else if(this.version == SNMP_V2) pdu = new BlockPdu(context2);
            else if(this.version == SNMP_V3) pdu = new BlockPdu(context3);

            // pdu.setRetryIntervals(new int[]{500,500,2000});
            pdu.setRetryIntervals(new int[]{DEFAULT_TIME_OUT});

            connected = true;
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, "Can't create SnmpContext !");
            Debug.println(this, Debug.ERROR, e);
            connected = false;
        }
    }




    /**
     * @return the oid value
     * example, use 1.3.6.1.2.1.1.1 to have sysdescr
     * (do not use 1.3.6.1.2.1.1.1.0)
     **/
    public SnmpNodeList get(String baseoid)
    {
        if(!connected) reloadComInterface();

        SnmpNodeList snmpList = new SnmpNodeList();

        if(pdu == null) return snmpList;        

        if(version == SNMP_V1)
        {
            pdu.setPduType(BlockPdu.GETNEXT);
        }
        else
        {
            pdu.setPduType(BlockPdu.GETBULK);
            pdu.setBulkParameters(GETBULK_NON_REPET, GETBULK_MAX_REPET);
        }


        String oid = baseoid;

    main:
        while(true)
        {
            Debug.println(this, Debug.DEBUG, "Sending snmp request for oid="+oid+" to "+ipAddress);

            pdu.clear();
            pdu.addOid(oid);
            varbind[] var = null;
            
            
            for(int i=0; i<SNMP_RETRY; i++)
            {
                try
                {
                    var = pdu.getResponseVariableBindings();
                    connected = true;
                    break;
                }
                catch(Exception e)
                {
                    Debug.println(this, Debug.ERROR, e);
                    Debug.println(this, Debug.WARNING, "Try to reconnect "+(i+1)+"/"+SNMP_RETRY);
                    connected = false;
                }
            }
            
            if(var == null || pdu.getErrorStatus() != 0) break;

            int len = var.length;
            for(int i=0; i<len; i++)
            {
                AsnObjectId asnoid = var[i].getOid();
                AsnObject asnobj = var[i].getValue();


                String newoid = asnoid.toString();
                String newvalue = null; // new value is calculated according to the type

                if(!newoid.startsWith(baseoid)) break main;

                SnmpNode node = new SnmpNode();
                
                byte type = asnobj.getRespType();

                switch(type)
                {
                case SnmpConstants.INTEGER:
                    node.setType(SnmpNode.TYPE_INTEGER);
                    AsnInteger ai = (AsnInteger)asnobj;
                    newvalue = Integer.toString(ai.getValue());
                    Debug.println(this, Debug.DEBUG, "Type INTEGER detected");
                    break;

                case SnmpConstants.STRING:
                    try
                    {
                        node.setType(SnmpNode.TYPE_STRING);
                        AsnOctets ao = (AsnOctets)asnobj;
                        //                         String str = ao.toDisplayString();
                        //                         AsnOctets tmpao = new AsnOctets(str.getBytes("UTF-8"));
                        //                         newvalue = new String(tmpao.getBytes(), "UTF-8"); 
                        newvalue = ao.toDisplayString();
                        Debug.println(this, Debug.DEBUG, "Type STRING detected");
                    }
                    catch(Exception e)
                    {
                        Debug.println(this, Debug.ERROR, e);
                    }
                    break;
                    

                case SnmpConstants.IPADDRESS:
                    node.setType(SnmpNode.TYPE_STRING);
                    AsnOctets ao2 = (AsnOctets)asnobj;
                    newvalue = ao2.toIpAddress();
                    Debug.println(this, Debug.DEBUG, "Type IPADDRESS detected");
                    break;


                case SnmpConstants.OPAQUE:
                    node.setType(SnmpNode.TYPE_STRING);
                    AsnOctets ao3 = (AsnOctets)asnobj;
                    newvalue = ao3.toString();
                    Debug.println(this, Debug.DEBUG, "Type OPAQUE detected");
                    break;


                case SnmpConstants.OBJID:
                    node.setType(SnmpNode.TYPE_OID);
                    AsnObjectId aoid = (AsnObjectId)asnobj;
                    newvalue = aoid.getValue();
                    Debug.println(this, Debug.DEBUG, "Type OID detected");
                    break;


                case SnmpConstants.NULLOBJ:
                    node.setType(SnmpNode.TYPE_NULL);
                    AsnNull an = (AsnNull)asnobj;
                    newvalue = an.toString();
                    Debug.println(this, Debug.DEBUG, "Type NULLOBJ detected");
                    break;

                    
                case SnmpConstants.COUNTER:
                    node.setType(SnmpNode.TYPE_UNSINTEGER);
                    AsnUnsInteger aui = (AsnUnsInteger)asnobj;
                    newvalue = Long.toString(aui.getValue());
                    Debug.println(this, Debug.DEBUG, "Type COUNTER detected");
                    break;


                case SnmpConstants.TIMETICKS:
                    node.setType(SnmpNode.TYPE_UNSINTEGER);
                    AsnUnsInteger aui1 = (AsnUnsInteger)asnobj;
                    newvalue = Long.toString(aui1.getValue());
                    Debug.println(this, Debug.DEBUG, "Type TIMETICKS detected");
                    break;


                case SnmpConstants.GAUGE:
                    node.setType(SnmpNode.TYPE_UNSINTEGER);
                    AsnUnsInteger aui2 = (AsnUnsInteger)asnobj;
                    newvalue = Long.toString(aui2.getValue());
                    Debug.println(this, Debug.DEBUG, "Type GAUGE detected");
                    break;


                case SnmpConstants.COUNTER64:
                    node.setType(SnmpNode.TYPE_UNSINTEGER64);
                    AsnUnsInteger64 aui64 = (AsnUnsInteger64)asnobj;
                    newvalue = Long.toString(aui64.getValue());
                    Debug.println(this, Debug.DEBUG, "Type COUNTER64 detected");
                    break;


                default:
                    node.setType(SnmpNode.TYPE_NULL);
                    newvalue = asnobj.toString();
                    Debug.println(this, Debug.WARNING, "Type does not match !");
                    break;
                }

                node.setOid(newoid);
                node.setValue(newvalue);

                snmpList.addSnmpNode(node);

                if(i == (len -1)) oid = newoid;
            }
        }

        return snmpList;
    }


    /**
     * return false if one operation in the list fails
     **/
    public boolean set(SnmpNodeList nodeList)
    {
        if(!connected) reloadComInterface();

        if(nodeList == null) return false;

        int len = nodeList.size();

        for(int i=0; i<len; i++)
        {
            SnmpNode node = nodeList.getSnmpNode(i);
            switch(node.getType())
            {
            case SnmpNode.TYPE_INTEGER:
                return set(node.getOid(), (int)node.getValueAsLong());
            case SnmpNode.TYPE_NULL:
                ;
            case SnmpNode.TYPE_OID:
                return set(node.getOid(), node.getValue());
            case SnmpNode.TYPE_STRING:
                return set(node.getOid(), node.getValue());
            case SnmpNode.TYPE_UNSINTEGER:
                return set(node.getOid(), node.getValueAsLong());
            case SnmpNode.TYPE_UNSINTEGER64:
                return set(node.getOid(), node.getValueAsLong());
            }
        }

        return false;
    }


    /**
     * Close the connection with the device
     **/
    public void close()
    {
        
        if(context1 != null )context1.destroy();
        else if(context2 != null) context2.destroy();
        else if(context3 != null) context3.destroy();

        context1 = null;
        context2 = null;
        context3 = null;
    }


    /**
     * Set timeout
     **/
    public void setSocketTimeout(int milliseconds)
    {
        pdu.setRetryIntervals(new int[]{milliseconds});
    }


    /**
     * Octetstring IPaddress NSAPaddress
     **/
    public boolean set(String oid, String value)
    {
        if(!connected) reloadComInterface();

        if(oid == null || value == null || pdu == null) return false;

        pdu.setPduType(BlockPdu.SET);
        pdu.clear();
        pdu.addOid(oid, new AsnOctets(value));
        varbind var = null;

        try
        {
            var = pdu.getResponseVariableBinding();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }

        AsnObjectId aoid = var.getOid();
        AsnObject ares = var.getValue();

        if(ares == null)
            Debug.println(this, Debug.WARNING,"no answer");
        else
            Debug.println(this, Debug.DEBUG,aoid.toString()+" set to "+ares.toString());


        if(var == null || pdu.getErrorStatus() != 0) return false;
        else return true;
    }


    /**
     * Integer  Uinteger32 Gauge Counter32 Counter64 TimeTicks 
     **/
    public boolean set(String oid, long value)
    {
        if(!connected) reloadComInterface();

        if(oid == null || pdu == null) return false;

        pdu.setPduType(BlockPdu.SET);
        pdu.clear();
        pdu.addOid(oid, new AsnUnsInteger(value));
        varbind var = null;

        try
        {
            var = pdu.getResponseVariableBinding();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }

        AsnObjectId aoid = var.getOid();
        AsnObject ares = var.getValue();

        if(ares == null)
            Debug.println(this, Debug.WARNING,"no answer");
        else
            Debug.println(this, Debug.DEBUG,aoid.toString()+" set to "+ares.toString());


        if(var == null || pdu.getErrorStatus() != 0) return false;
        else return true;
    }


    /**
     * ASN.1 32-bit signed integer
     **/
    public boolean set(String oid, int value)
    {
        if(!connected) reloadComInterface();

        if(oid == null || pdu == null) return false;

        pdu.setPduType(BlockPdu.SET);
        pdu.clear();
        pdu.addOid(oid, new AsnInteger(value));
        varbind var = null;

        try
        {
            var = pdu.getResponseVariableBinding();
        }
        catch(Exception e)
        {
            Debug.println(this, Debug.ERROR, e);
        }

        AsnObjectId aoid = var.getOid();
        AsnObject ares = var.getValue();

        if(ares == null)
            Debug.println(this, Debug.WARNING,"no answer");
        else
            Debug.println(this, Debug.DEBUG,aoid.toString()+" set to "+ares.toString());


        if(var == null || pdu.getErrorStatus() != 0) return false;
        else return true;
    }


    public Pdu processIncomingTrap(TrapEvent evt)
        throws Exception
    {
        byte[] b = evt.getMessage();

        if(this.version == SNMP_V1)
        {
            return context1.processIncomingTrap(b);
        }
        else if(this.version == SNMP_V2)
        {
            return context2.processIncomingTrap(b);
        }
        else
        {
            return context3.processIncomingTrap(b);
        }
    }
}

