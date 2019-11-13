package dang.websnmp;

import openplatform.database.dbean.*;
import java.util.*;

import dang.cms.CmsLanguage;

/**
 * AdminAlert.java
 *
 *
 * Created: Mon Jun  6 17:27:41 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class AdminAlert extends DBSnmpGraphicFormula
{
	private CmsLanguage cmsLang = new CmsLanguage();
	
    // DO BEG
    public String doSave()
        throws Exception
    {
        if(this.gFormulaId == null) throw new Exception(cmsLang.translate("error.empty"));

        if(this.value == null || this.value.trim().equals(""))
            throw new Exception(cmsLang.translate("miss.parameter"));
        
        DBSnmpGraphicFormula gfmask = new DBSnmpGraphicFormula();
        gfmask.setGFormulaId(this.gFormulaId);
        
        DBSnmpGraphicFormula gf = DBSnmpGraphicFormula.loadByKey(gfmask);

        if(gf == null) throw new Exception(cmsLang.translate("error.empty"));

        gf.setValue(this.value);
        gf.setCondition(this.condition);
        gf.store();

//         this.store();

        return cmsLang.translate("ok.saved");
    }


    public String doDelete()
        throws Exception
    {
        if(this.gFormulaId == null)
            throw new Exception(cmsLang.translate("error.empty"));

        DBSnmpGraphicFormula gfmask = new DBSnmpGraphicFormula();
        gfmask.setGFormulaId(this.gFormulaId);
        
        DBSnmpGraphicFormula gf = DBSnmpGraphicFormula.loadByKey(gfmask);

        if(gf == null)
            throw new Exception(cmsLang.translate("error.empty"));

        gf.setValue(null);
        gf.setCondition(null);
        gf.store();



//         this.value = null;
//         this.condition = null;

//         this.store();

        return "Alert deleted";
    }
    // DO END


    public DBSnmpReport[] getReportList()
    {
        return DBSnmpReport.load(null);
    }


    /**
     * AL of SnmpGraphicFormula
     */
    public ArrayList getAlert()
    {
        ArrayList al = new ArrayList();

        if(this.reportId == null) return al;

        DBSnmpGraphicFormula gfmask = new DBSnmpGraphicFormula();

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
   
}
