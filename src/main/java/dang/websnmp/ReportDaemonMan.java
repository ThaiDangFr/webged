package dang.websnmp;

import java.util.Hashtable;

import openplatform.chart.TimeConstants;
import openplatform.database.dbean.DBSnmpFormula;
import openplatform.database.dbean.DBSnmpGraphicFormula;
import openplatform.database.dbean.DBSnmpOID;
import openplatform.database.dbean.DBSnmpReport;
import openplatform.scheduler.Scheduler;
import openplatform.snmp.SnmpUtils;
import openplatform.snmp.process.SnmpScheduler;
import openplatform.tools.Debug;
import openplatform.tools.StringArrayList;

/**
 * Class ReportDaemonMan
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ReportDaemonMan implements TimeConstants
{
    public static String SERVICE_ID = null;
    public static String FOLDER_ID = null;

//     public static DBService dbservice;
    private static boolean started = false;
    private static Hashtable taskHT = new Hashtable(); // reportId / schedulerId

    private ReportDaemonMan() {}

    



    /**
     * Called by the server to initialize at start
     */
//     public static void instanciate()
//     {
//         startAllTask();
//     }


    /**
     * Called by the jsp to start a report process
     */
    public static void startDaemon(String reportid)
    {
        // 1. Launch report process

        if(reportid==null) return;

        DBSnmpReport mask = new DBSnmpReport();
        mask.setReportId(reportid);

        DBSnmpReport report = DBSnmpReport.loadByKey(mask);

        if(report==null) return;

        try
        {
            int freq = Integer.parseInt(report.getFrequency());

            ReportDaemon daemon = new ReportDaemon(reportid);
            String sid = Scheduler.every(daemon, freq, freq);
            taskHT.put(reportid, sid);
        }
        catch(Exception e)
        {
            Debug.println(null, Debug.ERROR, e);
        }


        // 2. Launch snmp process
        String hostid = report.getHostId();
        DBSnmpGraphicFormula gfmask = new DBSnmpGraphicFormula();
        gfmask.setReportId(reportid);

        DBSnmpGraphicFormula[] gf = DBSnmpGraphicFormula.load(gfmask);
        
        if(gf == null) return;
        if(hostid == null) return;

        int len = gf.length;
        DBSnmpFormula fmask = new DBSnmpFormula();
        for(int i=0; i<len; i++)
        {
            String formulaid = gf[i].getFormulaId();

            if(formulaid==null) continue;

            fmask.setFormulaId(formulaid);
            DBSnmpFormula f = DBSnmpFormula.loadByKey(fmask);

            if(f == null) continue;

            String formula = f.getFormula();            

            SnmpFormulaParser fparser = SnmpFormulaFactory.formulaParser(formula);
            StringArrayList al = fparser.parseOID();

            if(al == null) continue;

            // LEGEND
//             String legendId = f.getLegendId();
//             DBSnmpOID oidmask = new DBSnmpOID();
//             oidmask.setId(legendId);
//             DBSnmpOID oid = DBSnmpOID.loadByKey(oidmask);
            DBSnmpOID oid = SnmpUtils.parseSimpleFormula(f.getLegend());
            if(oid != null)
                al.add(oid.getOid());
            //

            Debug.println(null, Debug.DEBUG, "Report "+report.getDisplayName()+" contains OIDs="+al);

            SnmpScheduler.startProcess(hostid, al);
        }
    }


    /**
     * Stop a report daemon
     */
    public static void stopDaemon(String reportid)
    {
        if(reportid==null) return;

        Object obj = taskHT.remove(reportid);
        if(obj != null)
        {
            Scheduler.stopTask((String)obj);
        }
    }


    /**
     * Start all report processes
     */
    public static void startAllTask()
    {
        if(started)
        {
            Debug.println(null, Debug.WARNING, "ReportDaemon already started !");
            return;
        }
        
        started = true;

        DBSnmpReport[] report = DBSnmpReport.load(null);

        if(report==null) return;

        int len=report.length;

        for(int i=0; i<len; i++)
        {
            try
            {
                DBSnmpReport r = report[i];
                int freq = Integer.parseInt(r.getFrequency());

                ReportDaemon daemon = new ReportDaemon(r.getReportId());
                String sid = Scheduler.every(daemon, freq, freq);
                taskHT.put(r.getReportId(), sid);
            }
            catch(Exception e)
            {
                Debug.println(null, Debug.ERROR, e);
            }
        }
    }
}
