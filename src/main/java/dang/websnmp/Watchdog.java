package dang.websnmp;

import openplatform.tools.*;
import java.util.*;
import openplatform.snmp.process.*;
import openplatform.database.dbean.*;
import openplatform.snmp.SnmpFactory;
import openplatform.scheduler.Scheduler;
import openplatform.snmp.*;

/**
 * Class Watchdog
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class Watchdog extends TimerTask
{
//     public static final long LOW_CHECK = SnmpScheduler.FREQUENCE * 16;
//     public static final long MEDIUM_CHECK = SnmpScheduler.FREQUENCE * 8;
//     public static final long HIGH_CHECK = SnmpScheduler.FREQUENCE * 4;
//     private static final long STARTING_DELAY = SnmpScheduler.FREQUENCE;

    private DBSnmpFormula fmask = new DBSnmpFormula();
    private DBSnmpReport rmask = new DBSnmpReport();
    private DBSnmpParam pmask = new DBSnmpParam();
    private DBSnmpGraphicFormula gfmask = new DBSnmpGraphicFormula();


    private HashSet oidSet = new HashSet();
    private StringArrayList missing = new StringArrayList();



    public Watchdog()
    {
        Debug.println(this, Debug.DEBUG, "Watchdog started.");        
    }


    public boolean cancel()
    {
        Debug.println(this, Debug.DEBUG, "Watchdog stopped.");
        return super.cancel();
    }


    public void run()
    {
        Debug.println(this, Debug.DEBUG, "Watchdog waking up...");

        DBSnmpHost[] h = DBSnmpHost.load(null);
        if(h == null || h.length == 0) return;

        int len = h.length;

        for(int i=0; i<len; i++)
        {
            checkFormulaIntegrity(h[i]);
            checkHostIntegrity(h[i]);
        }
    }


    private void checkHostIntegrity(DBSnmpHost h)
    {
        String hid = h.getSnmpHostId();
        ArrayList al = SnmpScheduler.getSnmpTask(hid);

        if(al == null) return;

        int allen = al.size();
        for(int j=0; j<allen; j++)
        {
            SnmpTask task = (SnmpTask)al.get(j);
            Logger logger = task.getLogger();
            String hostId = logger.getHostId();
            DBSnmpHost host = ((SnmpFactory)SnmpFactory.getSnmp(hostId)).getSnmpHost();
            
            if(host == null) continue;

            if(DBSnmpHost.count(host, null) == 0)
            {
                SnmpFactory.reloadSnmp(host.getSnmpHostId());

                Debug.println(this, Debug.WARNING, "Snmp process updated for host "+h.getDisplayName());
            }
        }
    }


    private void checkFormulaIntegrity(DBSnmpHost h)
    {   
        oidSet.clear();
        missing.clear();

        String hid = h.getSnmpHostId();
        rmask.clear();
        rmask.setHostId(hid);

        DBSnmpReport[] rps = DBSnmpReport.load(rmask);


        if(rps != null && rps.length != 0)
        {
            int rpslen = rps.length;
            for(int j=0; j<rpslen; j++)
            {
                String rid = rps[j].getReportId();
                gfmask.clear();
                gfmask.setReportId(rid);

                DBSnmpGraphicFormula[] gf = DBSnmpGraphicFormula.load(gfmask);
                if(gf == null || gf.length == 0) continue;

                int lengf = gf.length;

                for(int k=0; k<lengf; k++)
                {
                    String fid = gf[k].getFormulaId();
                    fmask.clear();
                    fmask.setFormulaId(fid);

                    DBSnmpFormula f = DBSnmpFormula.loadByKey(fmask);
                    if(f == null) continue;

                    String formula = f.getFormula();
                    SnmpFormulaParser parser = SnmpFormulaFactory.formulaParser(formula);
                    oidSet.addAll(parser.parseOID());
                    
//                     DBSnmpOID oidmask = new DBSnmpOID();
//                     oidmask.setId(f.getLegendId());
//                     DBSnmpOID oid = DBSnmpOID.loadByKey(oidmask);
                    DBSnmpOID oid = SnmpUtils.parseSimpleFormula(f.getLegend());
                    if(oid != null) oidSet.add(oid.getOid());
                }
            }
        }

        Debug.println(this, Debug.DEBUG, oidSet.size() + " oids found for host "+h.getDisplayName());
            

        // Check if there are no useless DBSnmpParam
        pmask.clear();
        pmask.setSnmpHostId(hid);
        DBSnmpParam[] param = DBSnmpParam.load(pmask);
        if(param != null)
        {
            int plen = param.length;
            for(int j=0; j<plen; j++)
            {
                String oid = param[j].getOid();
                if(!oidSet.contains(oid))
                {
                    param[j].delete();
                    Debug.println(this, Debug.WARNING, "Deleting useless snmp process hostid="+hid+" oid="+oid);
                }
            }
        }


        // Create missing Snmp process
        Iterator it = oidSet.iterator();
        while(it.hasNext())
        {
            String oid = (String)it.next();
            pmask.clear();
            pmask.setSnmpHostId(hid);
            pmask.setOid(oid);

            if(DBSnmpParam.count(pmask, null) == 0)
            {
                missing.addString(oid);
                Debug.println(this, Debug.WARNING, "Starting missing snmp process hostid="+hid+" oid="+oid);
            }
        }

        if(missing.size() > 0)
            SnmpScheduler.startProcess(hid, missing);
    }




    private String getHostId(String reportId)
    {
        rmask.clear();
        rmask.setReportId(reportId);

        DBSnmpReport r = DBSnmpReport.loadByKey(rmask);
        if(r == null) return null;
        else return r.getHostId();
    }


}
