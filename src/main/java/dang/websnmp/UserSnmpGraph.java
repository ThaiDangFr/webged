package dang.websnmp;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import openplatform.chart.TimeChart;
import openplatform.chart.TimeChartImage;
import openplatform.database.SQLConstraint;
import openplatform.database.dbean.DBSnmpFormula;
import openplatform.database.dbean.DBSnmpGFValue;
import openplatform.database.dbean.DBSnmpGFValueData;
import openplatform.database.dbean.DBSnmpGraphicFormula;
import openplatform.database.dbean.DBSnmpHost;
import openplatform.database.dbean.DBSnmpOID;
import openplatform.database.dbean.DBSnmpParam;
import openplatform.database.dbean.DBSnmpReport;
import openplatform.database.dbean.DBSnmpValue;
import openplatform.database.dbean.DBSnmpValueData;
import openplatform.file.Repository;
import openplatform.snmp.SnmpUtils;
import openplatform.tools.Debug;
import openplatform.tools.OrdHashMap;
import openplatform.tools.SQLTime;
import dang.cms.CmsLanguage;

/**
 * UserSnmpGraph.java
 *
 *
 * Created: Fri Jun 10 11:41:12 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class UserSnmpGraph
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    public static final int WIDTH = 395;
    public static final int HEIGHT = 295;

    private String gFormulaId;
    private String reportId;
    private String beginTime;
    private String endTime;
    private String fileBaseId;
    private String excelFileBaseId;
    
    public DBSnmpGraphicFormula gfmask = new DBSnmpGraphicFormula();
    public DBSnmpGFValue gfvmask = new DBSnmpGFValue();
    public DBSnmpFormula fmask = new DBSnmpFormula();
    public DBSnmpGFValueData vdmask = new DBSnmpGFValueData();

    
    // DO BEG

    public String doGenerateGraph()
        throws Exception
    {
        this.fileBaseId = null;
        this.excelFileBaseId = null;

        if(this.gFormulaId == null)
        	throw new Exception(cmsLang.translate("error.empty"));
        
        if(this.reportId == null)
        	throw new Exception(cmsLang.translate("error.empty"));

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");

        Date beginDate = (Date)formatter.parse(beginTime.trim());
        Date endDate = (Date)formatter.parse(endTime.trim());

        gfvmask.setGFormulaId(gFormulaId);

        SQLConstraint constr = new SQLConstraint();
        StringBuffer sb = new StringBuffer();
        sb.append(DBSnmpGFValue.DATETIME).append(SQLConstraint.GREATER_EQ_THAN).append("'").append(SQLTime.getSQLTime(beginDate.getTime())).append("'").append(SQLConstraint.AND).append(DBSnmpGFValue.DATETIME).append(SQLConstraint.LOWER_THAN).append("'").append(SQLTime.getSQLTime(endDate.getTime())).append("'");
        constr.setCustomWhere(sb.toString());

        DBSnmpGFValue[] gfvalues = DBSnmpGFValue.load(constr, gfvmask);

        if(gfvalues == null || gfvalues.length == 0) return null;

        String name = "Unknown";

        gfmask.clear();
        gfmask.setGFormulaId(gFormulaId);
        DBSnmpGraphicFormula gf = DBSnmpGraphicFormula.loadByKey(gfmask);

        DBSnmpFormula f = null;

        if(gf != null)
        {
            
            String formulaId = gf.getFormulaId();
            fmask.clear();
            fmask.setFormulaId(formulaId);
            f = DBSnmpFormula.loadByKey(fmask);
            if(f != null) name = f.getDisplayName();
        }


        TimeChartImage tci = new TimeChartImage(name, "Time", "");

        int len = gfvalues.length;
        
        HashMap map = new HashMap(); // index-TimeChart

        long chartBeginDate = SQLTime.getJavaTime(gfvalues[0].getDateTime());
        long chartEndDate = SQLTime.getJavaTime(gfvalues[gfvalues.length-1].getDateTime());

        boolean haveBeginDate = false;
        int lenvd = -1;
        StringBuffer excel = new StringBuffer();

        long previousTime = -1;

        // used to limit number of points to display
        int gap = 1;
        //

        for(int i=0; i<len; i=i+gap)
        {
            vdmask.clear();
            vdmask.setGfValueId(gfvalues[i].getGfValueId());
            DBSnmpGFValueData[] vd = DBSnmpGFValueData.load(vdmask);


            // BLANK VALUES
            if(vd == null || vd.length == 0)
                continue;
            

            // HAVE THE BEGIN DATE (BEFORE ARE BLANK VALUES)
            if(!haveBeginDate)
            {
                chartBeginDate = SQLTime.getJavaTime(gfvalues[i].getDateTime());
                haveBeginDate = true;

                // Optimisation of graphics display (limited to 60 points) 19/9/2005
                long delta = chartEndDate - chartBeginDate;
                gap = (int)(Math.round(delta/3600000));
                if(gap == 0) gap = 1;
                Debug.println(this, Debug.DEBUG, "DELTA="+delta+" GAP="+gap);
                //
            }

            lenvd = vd.length;

            excel.append(new Date(SQLTime.getJavaTime(gfvalues[i].getDateTime())));

            for(int j=0; j<lenvd; j++)
            {
                excel.append(";").append(vd[j].getValue());

                Object obj = map.get(Integer.toString(j));
                TimeChart tchart;
                if(obj == null)
                {
                    // legend
                    String legend = Integer.toString(j);
                    
                    if(f != null)
                    {
                        DBSnmpOID oid = SnmpUtils.parseSimpleFormula(f.getLegend());

                        if(oid != null)
                        {
                            DBSnmpReport rmask = new DBSnmpReport();
                            rmask.setReportId(this.reportId);
                            DBSnmpReport r = DBSnmpReport.loadByKey(rmask);
                            
                            if(r != null)
                            {
                                String soid = oid.getOid();
                                DBSnmpParam pmask = new DBSnmpParam();
                                pmask.setOid(soid);
                                pmask.setSnmpHostId(r.getHostId());
                                DBSnmpParam param = DBSnmpParam.loadByKey(pmask);

                                if(param != null)
                                {
                                    SQLConstraint constr2  = new SQLConstraint();
                                    constr2.setLimit(1);
                                    StringBuffer sb2 = new StringBuffer();
                                    sb2.append(DBSnmpValue.DATETIME).append(SQLConstraint.GREATER_EQ_THAN).append("'").append(gfvalues[i].getDateTime()).append("'");
                                    constr2.setCustomWhere(sb2.toString());
                                    DBSnmpValue vmask = new DBSnmpValue();
                                    vmask.setSnmpParamId(param.getSnmpParamId());
                                    DBSnmpValue v = DBSnmpValue.loadByKey(vmask, constr2);
                                    if(v != null)
                                    {
                                        DBSnmpValueData vdmask = new DBSnmpValueData();
                                        vdmask.setSnmpValueId(v.getSnmpValueId());
                                        DBSnmpValueData[] vd2 = DBSnmpValueData.load(vdmask);
                                        if(vd2 != null && vd2.length > j)
                                        {
                                            legend = vd2[j].getValue();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // legend
                    
                    tchart = new TimeChart(legend, chartBeginDate);
                    tci.addTimeChart(tchart);
                    map.put(Integer.toString(j), tchart);
                }
                else
                    tchart = (TimeChart)obj;

                double value = Double.parseDouble(vd[j].getValue());
                tchart.addValue(SQLTime.getJavaTime(gfvalues[i].getDateTime()),  value);
            }

            excel.append("\n");
        }

        //Debug.println(this, Debug.DEBUG, "EXCEL="+excel.toString());
        excelFileBaseId = Repository.storeTempFile("data.txt",new ByteArrayInputStream(excel.toString().getBytes("UTF-8")));

        byte[] b = tci.getEPSBytes(WIDTH, HEIGHT);
        this.fileBaseId = Repository.storeTempFile(new ByteArrayInputStream(b));

        return null;
    }

    // DO END

    public HashMap getReportOptions()
    {
        HashMap map = new OrdHashMap();

        DBSnmpReport[] report = DBSnmpReport.load(null);
        if(report == null) return map;

        int len = report.length;

        DBSnmpHost hmask = new DBSnmpHost();
        for(int i=0; i<len; i++)
        {
            String hostName = "";
            hmask.setSnmpHostId(report[i].getHostId());
            DBSnmpHost h = DBSnmpHost.loadByKey(hmask);
            if(h != null) hostName = h.getDisplayName();

            map.put(report[i].getReportId(), report[i].getDisplayName()+" ("+hostName+")");
        }

        return map;
    }
    

    /**
     * Array of SnmpGraphicFormula
     */
    public ArrayList getSnmpGraphicFormula()
    {
        ArrayList al = new ArrayList();
        
        if(this.reportId == null) return al;
        
        gfmask.clear();
        gfmask.setReportId(this.reportId);

        DBSnmpGraphicFormula[] gf = DBSnmpGraphicFormula.load(gfmask);

        if(gf == null) return al;

        int len = gf.length;

        for(int i=0; i<len; i++)
        {
            al.add(new SnmpGraphicFormula(gf[i]));
        }

        return al;
    }


    // GET / SET


    /**
     * Gets the value of gFormulaId
     *
     * @return the value of gFormulaId
     */
    public final String getGFormulaId()
    {
        return this.gFormulaId;
    }

    /**
     * Sets the value of gFormulaId
     *
     * @param argGFormulaId Value to assign to this.gFormulaId
     */
    public final void setGFormulaId(final String argGFormulaId)
    {
        this.gFormulaId = argGFormulaId;
    }

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
     * Sets the value of reportId
     *
     * @param argReportId Value to assign to this.reportId
     */
    public final void setReportId(final String argReportId)
    {
        this.reportId = argReportId;
    }

    /**
     * Gets the value of beginTime
     *
     * @return the value of beginTime
     */
    public final String getBeginTime()
    {
        return this.beginTime;
    }

    /**
     * Sets the value of beginTime
     *
     * @param argBeginTime Value to assign to this.beginTime
     */
    public final void setBeginTime(final String argBeginTime)
    {
        this.beginTime = argBeginTime;
    }

    /**
     * Gets the value of endTime
     *
     * @return the value of endTime
     */
    public final String getEndTime()
    {
        return this.endTime;
    }

    /**
     * Sets the value of endTime
     *
     * @param argEndTime Value to assign to this.endTime
     */
    public final void setEndTime(final String argEndTime)
    {
        this.endTime = argEndTime;
    }

    /**
     * Gets the value of fileBaseId
     *
     * @return the value of fileBaseId
     */
    public final String getFileBaseId()
    {
        return this.fileBaseId;
    }

    /**
     * Sets the value of fileBaseId
     *
     * @param argFileBaseId Value to assign to this.fileBaseId
     */
    public final void setFileBaseId(final String argFileBaseId)
    {
        this.fileBaseId = argFileBaseId;
    }

    /**
     * Gets the value of excelFileBaseId
     *
     * @return the value of excelFileBaseId
     */
    public final String getExcelFileBaseId()
    {
        return this.excelFileBaseId;
    }

    /**
     * Sets the value of excelFileBaseId
     *
     * @param argExcelFileBaseId Value to assign to this.excelFileBaseId
     */
    public final void setExcelFileBaseId(final String argExcelFileBaseId)
    {
        this.excelFileBaseId = argExcelFileBaseId;
    }


//     public static void main(String[] args)
//         throws Exception
//     {
//         DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");

//         Date beginDate = (Date)formatter.parse("29/07/2005 19:02:08");
//         System.out.println(beginDate);
//     }
}
