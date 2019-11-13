package openplatform.snmp.stack;

import java.io.*;
import java.util.*;

/**
 * The AsnPduSequence class know how a Pdu v1 and v2c is build, it knows its
 * sequence.
 *
 */
class AsnPduSequence extends AsnSequence 
{

    AsnPduSequence(InputStream in, int len, int pos) throws IOException 
    {
        super(in,len,pos);
    }

    int getReqId() 
    {
        AsnInteger rid = (AsnInteger) getObj(0);
        return(rid.getValue());
    }

    int getWhatError() 
    {
        AsnInteger estat = (AsnInteger) getObj(1);
        return (estat.getValue());
    }

    int getWhereError() 
    {
        AsnInteger estat = (AsnInteger) getObj(2);
        return (estat.getValue());
    }

    AsnSequence getVarBind()
    {
        return (AsnSequence) getObj(3);
    }

    /** 
     * recursively look for a pduSequence object
     * - got one :-)
     */
    AsnObject findPdu() 
    {
        return this;  
    }

    int getValue() 
    {
        AsnSequence varBind = (AsnSequence) getObj(3);
        AsnSequence varPair = (AsnSequence) varBind.getObj(0);
        AsnInteger val = (AsnInteger) varPair.getObj(1);
        int value =  val.getValue();

        return value;
    }

    boolean hadError() 
    {
        return (SNMP_ERR_NOERROR != getWhatError());
    }

}
