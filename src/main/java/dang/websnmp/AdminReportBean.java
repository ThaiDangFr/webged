package dang.websnmp;

import openplatform.database.dbean.*;
import openplatform.tools.*;
import java.util.*;

import dang.cms.CmsLanguage;
import openplatform.pushlet.*;
import openplatform.database.SQLConstraint;
import openplatform.file.*;

/**
 * Class AdminReportBean
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class AdminReportBean
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    private String reportId;
    private String displayName;
    private String hostId;
    private String frequency = "300"; // default 5 minutes

    private String formulaId;

    private HashMap formulaHash = new HashMap(); // id-name

    private DBSnmpHost hmask = new DBSnmpHost();
    private DBSnmpReport rmask = new DBSnmpReport();
    private DBSnmpGraphicFormula gfmask = new DBSnmpGraphicFormula();
    private DBSnmpFormula fmask = new DBSnmpFormula();
    private SQLConstraint sqlconstr = new SQLConstraint();
    
    private String[] reportIds;
    private String[] formulaIds;



    // DO BEG

    public String doClear()
        throws Exception
    {
        this.displayName = null;
        this.hostId = null;
        this.frequency = "300";

        reportIds = null;
        formulaIds = null;

        formulaHash.clear();
        return null;
    }

    /**
     * Delete some reports
     */
    public String doDeleteReport()
        throws Exception
    {
        if(reportIds == null || reportIds.length == 0)
        {
            throw new Exception(cmsLang.translate("not.found"));
        }


        int len = reportIds.length;

        for(int i=0; i<len; i++)
        {
            String rid = reportIds[i];
            ReportDaemonMan.stopDaemon(rid);

            rmask.clear();
            rmask.setReportId(rid);

            DBSnmpReport.delete(rmask);
        }

        return cmsLang.translate("item.deleted");
    }


    /**
     * Save the report.
     * Save the associated formulas.
     */
    public String doSave()
        throws Exception
    {
        if(displayName == null || displayName.trim().equals(""))
        {
            throw new Exception(cmsLang.translate("error.empty.name"));
        }

       

        // Check if there are some formulas
        if(formulaHash.size() == 0)
        {
            throw new Exception(cmsLang.translate("error.empty")+" Formula");
        }


        // Load the report or create a new one
        DBSnmpReport report;
        if(reportId != null)
        {
            rmask.clear();
            rmask.setReportId(reportId);
            report = DBSnmpReport.loadByKey(rmask);
        
            if(report == null) return null;
            else ReportDaemonMan.stopDaemon(report.getReportId());
        }
        else
        {
            report = new DBSnmpReport();
        }



        
        // host changed ?
        boolean hostChanged = false;
        if(hostId != null && !hostId.equals(report.getHostId())) hostChanged = true;
        //




        report.setDisplayName(displayName);
        report.setHostId(hostId);
        report.setFrequency(frequency);

        report.store();

        if(report.isInError())
        {
            if(reportId != null) ReportDaemonMan.startDaemon(reportId);
            throw new Exception(cmsLang.translate("duplicate.valu.name"));
        }


        reportId = report.getReportId();      

        // Delete in the database the formulas
        gfmask.clear();
        gfmask.setReportId(reportId);
        DBSnmpGraphicFormula[] gform = DBSnmpGraphicFormula.load(gfmask);
        if(gform != null)
        {
            int len = gform.length;
            for(int i=0; i<len; i++)
            {
                String fid = gform[i].getFormulaId();
                if(!formulaHash.containsKey(fid)) gform[i].delete();
            }
        }
        

        // Store the associated formulas
        Set set = formulaHash.keySet();
        Iterator it = set.iterator();
        while(it.hasNext())
        {
            String fid = (String)it.next();
            gfmask.clear();
            gfmask.setFormulaId(fid);
            gfmask.setReportId(reportId);

            if(DBSnmpGraphicFormula.count(gfmask, null) == 0)
                gfmask.store();
        }


        // If hostChanged => delete associated file,reset summary and recreate DBSnmpGraphicFormula
        // (to automatically delete old snmpGFValue)
        if(hostChanged)
        {
            Debug.println(this, Debug.DEBUG, "Host changed => delete old graphics");

            if(gform != null)
            {
                DBSnmpGraphicFormula newgform = new DBSnmpGraphicFormula();

                int len = gform.length;
                for(int i=0; i<len; i++)
                {
                    newgform.clear();
                    newgform.setReportId(gform[i].getReportId());
                    newgform.setFormulaId(gform[i].getFormulaId());
                    
                    gform[i].delete();
                    newgform.store();
                }
            }
        }
        //


        ReportDaemonMan.startDaemon(reportId);
        return null;
    }

    public String doDeleteFormula()
        throws Exception
    {
        if(formulaIds == null)
        {
            throw new Exception(cmsLang.translate("error.empty"));
        }

        int len = formulaIds.length;

        for(int i=0; i<len; i++)
        {
            formulaHash.remove(formulaIds[i]);
            Debug.println(this, Debug.DEBUG, formulaIds[i] + " removed");
        }

        return null;
    }
    // DO END




    public String getReportName()
    {
        return displayName;
    }


    public void setReportName(String adisplayName)
    {
        displayName=adisplayName;
    }


    /**
     * Get all DBSnmpReport
     */
    public DBSnmpReport[] getReportList()
    {
        return DBSnmpReport.load(null);
    }


    public String getHostName()
    {
        if(hostId == null) return null;

        hmask.clear();
        hmask.setSnmpHostId(hostId);

        DBSnmpHost h = DBSnmpHost.loadByKey(hmask);

        if(h==null) return null;

        return h.getDisplayName();
    }






    /**
     * Set a reportId.
     * The formula hashmap is initialized.
     */
    public void setReportId(String reportId)
    {
        Debug.println(this, Debug.DEBUG, "reportId="+reportId);

        this.reportId = reportId;

        Debug.println(this, Debug.DEBUG, "Re-Initializing formula list");

        if(reportId != null)
        {
            rmask.clear();
            rmask.setReportId(reportId);
            DBSnmpReport report = DBSnmpReport.loadByKey(rmask);

            if(report == null) return;

            this.displayName = report.getDisplayName();
            this.hostId = report.getHostId();
            this.frequency = report.getFrequency();



            gfmask.clear();
            gfmask.setReportId(reportId);
            DBSnmpGraphicFormula[] gf = DBSnmpGraphicFormula.load(gfmask);

            if(gf==null || gf.length==0) return;

            int len = gf.length;

            for(int i=0; i<len; i++)
            {
                String formulaid = gf[i].getFormulaId();
                String formulaname;

                if(formulaid == null) continue;

                fmask.clear();
                fmask.setFormulaId(formulaid);

                DBSnmpFormula f = DBSnmpFormula.loadByKey(fmask);

                if(f == null) continue;
                
                formulaname = f.getDisplayName();
                
                formulaHash.put(formulaid, formulaname);
            }
        }
    }


    public HashMap getHostOptions()
    {
        OrdHashMap map = new OrdHashMap();
        DBSnmpHost[] h = DBSnmpHost.load(null);
        
        if(h!=null)
        {
            int len = h.length;

            for(int i=0; i<len; i++)
            {
                String name = h[i].getDisplayName();
                String value = h[i].getSnmpHostId();

                if(name != null) map.put(name, value);
            }
        }

        return map;
    }



  
    public String getSelectedHost()
    {
        return hostId;
    }



    public HashMap getAssociatedFormula()
    {
        return formulaHash;
    }


    /**
     * Return a map of formula not associated yet
     */
    public HashMap getAvailableFormula()
    {
        HashMap map = new HashMap();
        DBSnmpFormula[] f = DBSnmpFormula.load(null);
        if(f==null || f.length==0) return map;

        int len = f.length;
        for(int i=0; i<len; i++)
        {
            String fid = f[i].getFormulaId();
            String name = f[i].getDisplayName();

            if(!formulaHash.containsKey(fid)) map.put(name, fid);
        }

        return map;
    }


    /**
     * Add the formula to the report
     */
    public void setAddToReport(String formulaid)
    {
        if(formulaid==null) return;

        fmask.clear();
        fmask.setFormulaId(formulaid);

        DBSnmpFormula f = DBSnmpFormula.loadByKey(fmask);

        if(f==null) return;

        String name = f.getDisplayName();

        formulaHash.put(formulaid, name);
    }
    
    
    public String getFormulaDesc()
    {
        if(formulaId == null)
        {
            HashMap map = getAvailableFormula();
            Set s = map.keySet();
            Iterator it = s.iterator();
            if(it.hasNext())
            {
                String name = (String)it.next();
                formulaId = (String)map.get(name);
            }
        }

        if(formulaId==null) return null;

        fmask.clear();
        fmask.setFormulaId(formulaId);
        DBSnmpFormula f = DBSnmpFormula.loadByKey(fmask);
        if(f==null) return null;
        else return f.getDescription();
    }
 


    // GET / SET


    /**
     * Gets the value of reportId
     *
     * @return the value of reportId
     */
    public final String getReportId()
    {
        return this.reportId;
    }

    /**
     * Gets the value of displayName
     *
     * @return the value of displayName
     */
    public final String getDisplayName()
    {
        return this.displayName;
    }

    /**
     * Sets the value of displayName
     *
     * @param argDisplayName Value to assign to this.displayName
     */
    public final void setDisplayName(final String argDisplayName)
    {
        this.displayName = argDisplayName;
    }

    /**
     * Gets the value of hostId
     *
     * @return the value of hostId
     */
    public final String getHostId()
    {
        return this.hostId;
    }

    /**
     * Sets the value of hostId
     *
     * @param argHostId Value to assign to this.hostId
     */
    public final void setHostId(final String argHostId)
    {
        this.hostId = argHostId;
    }


    /**
     * Gets the value of formulaId
     *
     * @return the value of formulaId
     */
    public final String getFormulaId()
    {
        return this.formulaId;
    }

    /**
     * Sets the value of formulaId
     *
     * @param argFormulaId Value to assign to this.formulaId
     */
    public final void setFormulaId(final String argFormulaId)
    {
        this.formulaId = argFormulaId;
    }

    /**
     * Gets the value of formulaHash
     *
     * @return the value of formulaHash
     */
    public final HashMap getFormulaHash()
    {
        return this.formulaHash;
    }

    /**
     * Sets the value of formulaHash
     *
     * @param argFormulaHash Value to assign to this.formulaHash
     */
    public final void setFormulaHash(final HashMap argFormulaHash)
    {
        this.formulaHash = argFormulaHash;
    }

    /**
     * Gets the value of hmask
     *
     * @return the value of hmask
     */
    public final DBSnmpHost getHmask()
    {
        return this.hmask;
    }

    /**
     * Sets the value of hmask
     *
     * @param argHmask Value to assign to this.hmask
     */
    public final void setHmask(final DBSnmpHost argHmask)
    {
        this.hmask = argHmask;
    }

    /**
     * Gets the value of rmask
     *
     * @return the value of rmask
     */
    public final DBSnmpReport getRmask()
    {
        return this.rmask;
    }

    /**
     * Sets the value of rmask
     *
     * @param argRmask Value to assign to this.rmask
     */
    public final void setRmask(final DBSnmpReport argRmask)
    {
        this.rmask = argRmask;
    }

    /**
     * Gets the value of gfmask
     *
     * @return the value of gfmask
     */
    public final DBSnmpGraphicFormula getGfmask()
    {
        return this.gfmask;
    }

    /**
     * Sets the value of gfmask
     *
     * @param argGfmask Value to assign to this.gfmask
     */
    public final void setGfmask(final DBSnmpGraphicFormula argGfmask)
    {
        this.gfmask = argGfmask;
    }

    /**
     * Gets the value of fmask
     *
     * @return the value of fmask
     */
    public final DBSnmpFormula getFmask()
    {
        return this.fmask;
    }

    /**
     * Sets the value of fmask
     *
     * @param argFmask Value to assign to this.fmask
     */
    public final void setFmask(final DBSnmpFormula argFmask)
    {
        this.fmask = argFmask;
    }

    /**
     * Gets the value of sqlconstr
     *
     * @return the value of sqlconstr
     */
    public final SQLConstraint getSqlconstr()
    {
        return this.sqlconstr;
    }

    /**
     * Sets the value of sqlconstr
     *
     * @param argSqlconstr Value to assign to this.sqlconstr
     */
    public final void setSqlconstr(final SQLConstraint argSqlconstr)
    {
        this.sqlconstr = argSqlconstr;
    }

    /**
     * Gets the value of reportIds
     *
     * @return the value of reportIds
     */
    public final String[] getReportIds()
    {
        return this.reportIds;
    }

    /**
     * Sets the value of reportIds
     *
     * @param argReportIds Value to assign to this.reportIds
     */
    public final void setReportIds(final String[] argReportIds)
    {
        this.reportIds = argReportIds;
    }

    /**
     * Gets the value of formulaIds
     *
     * @return the value of formulaIds
     */
    public final String[] getFormulaIds()
    {
        return this.formulaIds;
    }

    /**
     * Sets the value of formulaIds
     *
     * @param argFormulaIds Value to assign to this.formulaIds
     */
    public final void setFormulaIds(final String[] argFormulaIds)
    {
        this.formulaIds = argFormulaIds;
    }
    
}
