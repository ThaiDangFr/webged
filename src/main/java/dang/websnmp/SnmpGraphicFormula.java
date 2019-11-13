package dang.websnmp;

import openplatform.database.dbean.*;
import java.util.*;
import openplatform.tools.*;

/**
 * SnmpGraphicFormula.java
 *
 *
 * Created: Mon Jun  6 18:05:30 2005
 *
 * @author Thai Dang
 * @version 1.0
 */
public class SnmpGraphicFormula extends DBSnmpGraphicFormula
{
    public SnmpGraphicFormula(DBSnmpGraphicFormula sgf)
    {
        initBean(sgf);
    }


    public String getFormulaName()
    {
        if(this.formulaId == null) return null;

        DBSnmpFormula fmask = new DBSnmpFormula();
        fmask.setFormulaId(this.formulaId);
        DBSnmpFormula f = DBSnmpFormula.loadByKey(fmask);
        if(f == null) return null;
        
        return f.getDisplayName();
    }

    public HashMap getConditionOptions()
    {
        HashMap map = new OrdHashMap();
        map.put(DBSnmpGraphicFormula.CONDITION_LT, "<");
        map.put(DBSnmpGraphicFormula.CONDITION_EQ, "=");
        map.put(DBSnmpGraphicFormula.CONDITION_GT, ">");
        return map;
    } 
    
    public String getStatus()
    {
        if(condition == null) return "OFF";
        else return "ON";
    }
    
}
