package openplatform.snmp.process;

import openplatform.tools.*;
import java.util.*;
import openplatform.database.dbean.*;
import openplatform.scheduler.*;


/**
 * Class SnmpScheduler
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class SnmpScheduler
{
    /**
     * 60 seconds
     */
    public static final long FREQUENCE = 60;


    private static boolean started = false;
//     private static Timer timer = new Timer(true);
    private static MultimapObject hostidTask = new MultimapObject();

    private SnmpScheduler() {}


    /**
     * start the process for the oids if they are not already started
     */
    public static void startProcess(String hostid, StringArrayList oids)
    {
        if(hostid == null || oids == null) return;

        int len = oids.size();

        if(len==0) return;

        
        DBSnmpParam pmask = new DBSnmpParam();
        for(int i=0; i<len; i++)
        {
            String oid = oids.getString(i);

            pmask.clear();
            pmask.setSnmpHostId(hostid);
            pmask.setOid(oid);

            DBSnmpParam param = DBSnmpParam.loadByKey(pmask);

            if(param==null)
            {
                pmask.store();
                Logger log = new Logger(hostid);
                SnmpTask snmptask = new SnmpTask(log, pmask.getSnmpParamId());
                Scheduler.every(snmptask, 0, (int)FREQUENCE);
//                 timer.schedule(snmptask, 0, FREQUENCE*1000);
                
                hostidTask.add(hostid, snmptask);
            }
            else
                Debug.println(null, Debug.WARNING, "oid="+oid+" : process already started. Skipping.");
        }
    }


    /**
     * Called by initialisation of openplatform
     */
    public static void instanciate()
    {
        startAllTask();
    }


    /**
     * Start all SNMP tasks stored in database
     */
    public static void startAllTask()
    {
        if(started)
        {
            Debug.println(null, Debug.WARNING, "SnmpTask already started !");
            return;
        }
        else
        {
            started = true;

            DBSnmpParam[] dbsnmpParam = DBSnmpParam.load(null);
            if(dbsnmpParam==null || dbsnmpParam.length==0)
            {
                Debug.println(null, Debug.DEBUG, "No SNMP tasks.");
                return;
            }


            int len = dbsnmpParam.length;

            DBSnmpParam maskp = new DBSnmpParam();

            for(int i=0; i<len; i++)
            {
                DBSnmpParam snmpParam = dbsnmpParam[i];
                
                if(snmpParam == null) continue;

                String hid = snmpParam.getSnmpHostId();

                Logger logger = new Logger(hid);
                SnmpTask snmptask = new SnmpTask(logger, snmpParam.getSnmpParamId());
                try
                {
//                     timer.schedule(snmptask, 0, FREQUENCE*1000);
                    Scheduler.every(snmptask, 0, (int)FREQUENCE);
                    hostidTask.add(hid, snmptask);
                }
                catch(Exception e)
                {
                    Debug.println(null, Debug.ERROR, e);
                }
            }
        }
    }



    /**
     * Stop all SNMP tasks
     */
//     public static void stopAllTask()
//     {
//         if(!started)
//         {
//             Debug.println(null, Debug.WARNING, "SnmpTask not started !");
//             return;
//         }
//         else
//         {
//             started = false;

//             Enumeration enum = hostidTask.keys();
//             while(enum.hasMoreElements())
//             {
//                 String hid = (String)enum.nextElement();
//                 ArrayList al = (ArrayList)hostidTask.remove(hid);
                
//                 int len = al.size();
//                 for(int i=0; i<len; i++)
//                 {
//                     SnmpTask st = (SnmpTask)al.remove(0);
//                     st.cancel();
//                     st=null;
//                 }
//             }
//         }
//     }



    public static final ArrayList getSnmpTask(String hostid)
    {
        return hostidTask.get(hostid);
    }

}
