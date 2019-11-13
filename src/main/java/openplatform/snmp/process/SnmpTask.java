package openplatform.snmp.process;

import java.util.*;
import openplatform.database.dbean.*;
import openplatform.tools.*;


/**
 * Class SnmpTask
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class SnmpTask extends TimerTask
{
    private Logger logger;
    private DBSnmpParam snmpParam;
    private DBSnmpParam pmask = new DBSnmpParam();


    public SnmpTask(Logger logger, String snmpParamId)
    {
        if(logger==null || snmpParamId==null) cancel();

        pmask.setSnmpParamId(snmpParamId);
        snmpParam = DBSnmpParam.loadByKey(pmask);

        Debug.println(this, Debug.DEBUG, "Snmp task <oid="+snmpParam.getOid()+"> started.");
        this.logger = logger;

        if(snmpParam!=null)
        {
            pmask.clear();
            pmask.setSnmpParamId(snmpParam.getSnmpParamId());
        }
    }

    public void run()
    {
        // Test first if Snmp Param exists : if not, cancel that job
        if(DBSnmpParam.count(pmask, null) == 0)
        {
            cancel();
            return;
        }

        logger.log(snmpParam);
    }

    public boolean cancel()
    {
        if(snmpParam!=null) Debug.println(this, Debug.DEBUG, "Snmp task <oid="+snmpParam.getOid()+"> stopped.");
        return super.cancel();
    }

//     public DBSnmpParam getSnmpParam()
//     {
//         return snmpParam;
//     }

//     public void setSnmpParam(DBSnmpParam asnmpParam)
//     {
//         snmpParam=asnmpParam;
//     }

    public Logger getLogger()
    {
        return logger;
    }

//     public void setLogger(Logger alogger)
//     {
//         logger=alogger;
//     }
}
