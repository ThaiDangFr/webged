package openplatform.snmp;

import java.util.ArrayList;


/**
 * Class SnmpResultArrayList
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class SnmpNodeList extends ArrayList
{
    public SnmpNode getSnmpNode(int index)
    {
        return (SnmpNode)get(index);
    }


    public boolean addSnmpNode(SnmpNode snmpNode)
    {
        return add(snmpNode);
    }
}
