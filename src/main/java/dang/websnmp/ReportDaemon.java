package dang.websnmp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import openplatform.database.SQLConstraint;
import openplatform.database.dbean.DBFileBaseCache;
import openplatform.database.dbean.DBSnmpAlert;
import openplatform.database.dbean.DBSnmpAlertUser;
import openplatform.database.dbean.DBSnmpFormula;
import openplatform.database.dbean.DBSnmpGFValue;
import openplatform.database.dbean.DBSnmpGFValueData;
import openplatform.database.dbean.DBSnmpGraphicFormula;
import openplatform.database.dbean.DBSnmpHost;
import openplatform.database.dbean.DBSnmpOID;
import openplatform.database.dbean.DBSnmpReport;
import openplatform.database.dbean.DBUserGroups;
import openplatform.snmp.process.SnmpScheduler;
import openplatform.tools.Debug;
import openplatform.tools.SQLTime;
import dang.cmsbase.Notificator;


/**
 * Class ReportDaemon
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class ReportDaemon extends TimerTask
{

    private DBSnmpReport rmask = new DBSnmpReport();

    private DBSnmpFormula fmask = new DBSnmpFormula();
    private DBSnmpGFValue gfvaluemask = new DBSnmpGFValue();
    private SQLConstraint sqlconst = new SQLConstraint();
    private DBSnmpGraphicFormula maskgf = new DBSnmpGraphicFormula();
    private DBSnmpHost hmask = new DBSnmpHost();
    private DBSnmpOID oidmask = new DBSnmpOID();
    private DBFileBaseCache fbcmask = new DBFileBaseCache();

    private String reportId;

    private ArrayList batchAlerts = new ArrayList();

    /**
     * Constructor
     */
    public ReportDaemon(String reportId)
    {
        this.reportId = reportId;

        if(reportId==null) cancel();
        else
        {
            rmask.setReportId(reportId);

            if(Debug.DEBUGON)
            {
                DBSnmpReport report = loadReport();
                Debug.println(this, Debug.DEBUG, "Report <" + report.getDisplayName() + "> started");
            }
        }     
    }

        
    private DBSnmpReport loadReport()
    {
        return DBSnmpReport.loadByKey(rmask);
    }


  
    /**
     * The task to do periodically
     */
    public void run()
    {
        // Test first if the report still exists. If not, cancel that job
        if(DBSnmpReport.count(rmask, null) == 0)
        {
            cancel();  
            return;
        }
        
        DBSnmpReport report = loadReport();


        long currentTime = System.currentTimeMillis();



        // 1. Load the graphic formula
        maskgf.clear();
        maskgf.setReportId(report.getReportId());
        DBSnmpGraphicFormula[] gf = DBSnmpGraphicFormula.load(maskgf);
        
        if(gf == null) return;

        int len = gf.length;
        String hostid = report.getHostId();




        // 2. For each graphic formula, calculate, update 'lastupdate'
        // It is important to update lastUpdate of SnmpGraphicFormula to have a continous update of SnmpGFValue
        batchAlerts.clear();
        for(int i=0; i<len; i++)
        {
            StringBuffer summ = new StringBuffer(); // a summary per graphic formula

            try
            {
                String gformulaid = gf[i].getGFormulaId();

                long lastcalcul; // last calcul
                String lu = gf[i].getLastUpdateTime();
                if(lu != null)
                    lastcalcul = SQLTime.getJavaTime(lu) + SnmpScheduler.FREQUENCE*1000;
                else
                {
                    long frequency = Long.parseLong(report.getFrequency());
                    lastcalcul = currentTime - frequency*1000; // relative to the frequency because the report start with that delay
                }
                 
                    
                fmask.clear();
                fmask.setFormulaId(gf[i].getFormulaId());
                DBSnmpFormula f = DBSnmpFormula.loadByKey(fmask);

                String formula = f.getFormula();
                
                SnmpFormulaParser parser = SnmpFormulaFactory.formulaParser(formula);
                String nlu = parser.calculate(hostid, gformulaid, lastcalcul);
                gf[i].setLastUpdateTime(nlu);

                if(nlu != null && Debug.DEBUGON)
                {
                    Debug.println(this, Debug.DEBUG, 
                                  "New last update="+ConstantTime.dateFormat.format(new Date(SQLTime.getJavaTime(nlu)),
                                                                                    new StringBuffer(), ConstantTime.fieldpos0));
                }

                gf[i].store();

                // CHECK ALERT
                long last = SQLTime.getJavaTime(gf[i].getLastUpdateTime());
                long delta = Long.parseLong(report.getFrequency())*1000;

                checkAlert(gf[i], last-delta);
            }
            catch(Exception e)
            {
                Debug.println(this, Debug.ERROR, e);
            }
        }

        if(batchAlerts.size() != 0)
            sendAlert();
    }




    /**
     * Cancel that job
     */
    public boolean cancel()
    {
        return super.cancel();
    }

    
    /**
     * @return the report id
     */
    public String getReportId()
    {
        return reportId;
    }


    /**
     * Usefull for the Vector
     */
    public boolean equals(Object obj)
    {
        if(obj == null) return false;
        if(!(obj instanceof ReportDaemon)) return false;

        ReportDaemon rd = (ReportDaemon)obj;

        if(reportId == null)
        {
            if(rd.getReportId() == null) return true;
            else return false;
        }
        else
        {
            return reportId.equals(rd.getReportId());
        }
    }


    public int hashCode()
    {
        if(reportId == null) return 0;
        else return reportId.hashCode();
    }


    /**
     * Check and see if an alert need to be sent
     */
    private void checkAlert(DBSnmpGraphicFormula gf, long fromTime)
        throws Exception
    {
        Debug.println(this, Debug.DEBUG, "Checking alert for GFORMULAID="+gf.getGFormulaId());

        if(gf.getCondition() == null || gf.getValue() == null)
            return;

        if(DBSnmpAlertUser.count(null, null) == 0)
            return;

        DBSnmpGFValue gmask = new DBSnmpGFValue();
        gmask.setGFormulaId(gf.getGFormulaId());

        SQLConstraint constr = new SQLConstraint();
        StringBuffer sb = new StringBuffer();
        sb.append(DBSnmpGFValue.DATETIME).append(SQLConstraint.GREATER_EQ_THAN).append("'")
            .append(SQLTime.getSQLTime(fromTime)).append("'");
        constr.setCustomWhere(sb.toString());

        DBSnmpGFValue[] g = DBSnmpGFValue.load(constr, gmask);

        if(g == null) return;

        int len = g.length;
        DBSnmpGFValueData gdmask = new DBSnmpGFValueData();


        for(int i=0; i<len; i++)
        {
            gdmask.setGfValueId(g[i].getGfValueId());
            DBSnmpGFValueData[] gd = DBSnmpGFValueData.load(gdmask);
            if(gd == null) continue;

            int lengd = gd.length;
            for(int j=0; j<lengd; j++)
            {
                double value = Double.parseDouble(gd[j].getValue());
                double ceil = Double.parseDouble(gf.getValue());

                if(
                    (DBSnmpGraphicFormula.CONDITION_EQ.equals(gf.getCondition())
                     && value == ceil)
                    ||
                    (DBSnmpGraphicFormula.CONDITION_GT.equals(gf.getCondition())
                     && value > ceil)
                    ||
                    (DBSnmpGraphicFormula.CONDITION_LT.equals(gf.getCondition())
                     && value < ceil)
                    )
                {
                    Debug.println(this, Debug.DEBUG, "Adding an alert to the batch");
                    SendAlertBean b = new SendAlertBean();
                    b.setGraphicFormula(gf);
                    b.setValue(value);
                    b.setDate(SQLTime.getJavaTime(g[i].getDateTime()));
                    batchAlerts.add(b);
                }
            }
        }
    }


    private void sendAlert()
    {
        Debug.println(this, Debug.DEBUG, "sendAlert()");

        int sablen = batchAlerts.size();
        if(sablen == 0) return;

        StringBuffer subject = new StringBuffer();
        StringBuffer message = new StringBuffer();

        subject.append("[WEBSNMP] Alert");

        // PREPARE THE MESSAGE

        for(int i=0; i<sablen; i++)
        {
            SendAlertBean sab = (SendAlertBean)batchAlerts.get(i);
            DBSnmpGraphicFormula gf = sab.getGraphicFormula();
            double value = sab.getValue();
            long ldate = sab.getDate();

            if(gf == null) continue;

            DBSnmpReport rmask = new DBSnmpReport();
            rmask.setReportId(gf.getReportId());
            DBSnmpReport r = DBSnmpReport.loadByKey(rmask);

            if(r == null) continue;

            DBSnmpFormula fmask = new DBSnmpFormula();
            fmask.setFormulaId(gf.getFormulaId());
            DBSnmpFormula f = DBSnmpFormula.loadByKey(fmask);
        
            if(f == null) continue;

            hmask.clear();
            hmask.setSnmpHostId(r.getHostId());
            DBSnmpHost h = DBSnmpHost.loadByKey(hmask);
            String hostName = "";
            if(h != null) hostName = h.getDisplayName();

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
            String date = formatter.format(new Date(ldate));

            message.append(" [")
                .append(r.getDisplayName()).append(" (").append(hostName).append(")").append("|")
                .append(f.getDisplayName()).append(" = ").append(Double.toString(value))
                .append("|")
                .append(date)
                .append("] ")
                .append("\n");


            // LOG THE ALERT
//             String hostName = "Unknown";
//             DBSnmpHost hmask = new DBSnmpHost();
//             hmask.setSnmpHostId(r.getHostId());
//             DBSnmpHost h = DBSnmpHost.loadByKey(hmask);
//             if(h != null) hostName = h.getDisplayName();
            DBSnmpAlert alert = new DBSnmpAlert();
            alert.setReportId(gf.getReportId());
            alert.setDate(SQLTime.getSQLTime(ldate));
            alert.setType(DBSnmpAlert.TYPE_POLLING);
            alert.setLevel(DBSnmpAlert.LEVEL_ERROR);
            alert.setSource(hostName);
            alert.setDescription(f.getDisplayName()+" = "+Double.toString(value));
            alert.store();
        }
        


        // SEND THE MESSAGE

        DBSnmpAlertUser[] u = DBSnmpAlertUser.load(null);
        if(u == null) return;

        int len = u.length;

        DBUserGroups ugmask = new DBUserGroups();
        ArrayList userList = new ArrayList();
        for(int i=0; i<len; i++)
        {
            ugmask.clear();
            ugmask.setGroupId(u[i].getGroupId());
            DBUserGroups[] ug = DBUserGroups.load(ugmask);

            if(ug == null) continue;

            int lenug = ug.length;
            
            for(int j=0; j<lenug; j++)
            {
                if(!userList.contains(ug[j].getUserId()))
                    userList.add(ug[j].getUserId());
            }
        }


        len = userList.size();
        for(int i=0; i<len; i++)
        {
            Notificator.send("1", (String)userList.get(i), Notificator.PRIORITY_HIGH, subject.toString(), message.toString());
        }
    }
}
